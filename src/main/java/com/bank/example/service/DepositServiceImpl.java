package com.bank.example.service;

import com.bank.example.dao.DepositDao;
import com.bank.example.model.Account;
import com.bank.example.model.Card;
import com.bank.example.model.Deposit;
import com.bank.example.repository.CardRepository;
import com.bank.example.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class DepositServiceImpl extends AbstractService<Long, Deposit> implements DepositService {

    private final DepositDao depositDao;
    private final AccountService accountService;
    private final CardService cardService;
    private final DepositRepository depositRepository;
    private final CardRepository cardRepository;

    public DepositServiceImpl(DepositDao depositDao, AccountService accountService, CardService cardService, DepositRepository depositRepository, CardRepository cardRepository) {
        super(depositDao);
        this.depositDao = depositDao;
        this.accountService = accountService;
        this.cardService = cardService;
        this.depositRepository = depositRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public void deleteOutDatedDeposit(List<Long> accountIds) {
        List<Deposit> deposits = depositRepository.findDepositsByAccountIds(accountIds);

        List<Card> defaultCardsList = cardRepository.findDefaultCardsByAccountIds(accountIds);

        Map<Long, Card> defaultCards = convertCardsToMap(defaultCardsList);

        for (Deposit deposit : deposits) {
            if (DAYS.between(deposit.getCloseDate(), LocalDate.now()) > 1) {
                Card card = defaultCards.get(deposit.getAccount().getId());
                if (card != null) {
                    card.addSum(deposit.getSum());
                    deposit.getInterests().forEach(i -> i.setDeposit(null)); // Обнуляем связи
                    depositDao.remove(deposit);
                }
            }
        }
    }


    @Override
    @Transactional
    public List<Long> removeAllDepositsByAccountId(Long accountId) {
        Account account = accountService.getByKey(accountId);
        List<Long> depositIds = account.getDeposits().stream().map(Deposit::getId).collect(Collectors.toList());
        account.setDeposits(null);

        return depositIds;
    }

    private Map<Long, Card> convertCardsToMap(List<Card> cards) {
        Map<Long, Card> cardMap = new HashMap<>();
        for (Card card : cards) {
            cardMap.put(card.getAccount().getId(), card);
        }
        return cardMap;
    }
}

