package com.bank.example.service;

import com.bank.example.dao.DepositDao;
import com.bank.example.model.Account;
import com.bank.example.model.Card;
import com.bank.example.model.Deposit;
import com.bank.example.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class DepositServiceImpl extends AbstractService<Long, Deposit> implements DepositService {

    private final DepositDao depositDao;
    private final AccountService accountService;
    private final CardService cardService;
    private final DepositRepository depositRepository;

    public DepositServiceImpl(DepositDao depositDao, AccountService accountService, CardService cardService, DepositRepository depositRepository) {
        super(depositDao);
        this.depositDao = depositDao;
        this.accountService = accountService;
        this.cardService = cardService;
        this.depositRepository = depositRepository;
    }

    @Override
    @Transactional
    public void deleteOutDatedDeposit(List<Long> accountIds) {
        List<Deposit> deposits = depositRepository.findDepositsByAccountIds(accountIds);
        for (Deposit deposit : deposits) {
            if (DAYS.between(deposit.getCloseDate(), LocalDate.now()) > 1) {
                Card card = cardService.getDefaultCardByAccountId(deposit.getAccount().getId());
                card.addSum(deposit.getSum());

                deposit.getInterests().forEach(i -> i.setDeposit(null)); // Обнуляем связи
                depositDao.remove(deposit);
                cardService.update(card);
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
}
