package com.bank.example.service;

import com.bank.example.dao.AccountDao;
import com.bank.example.dto.AccountSettingsScansDto;
import com.bank.example.dto.AccountSumDto;
import com.bank.example.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AccountServiceImpl extends AbstractService<Long, Account> implements AccountService {

    private final AccountDao accountDao;
    private final SettingsService settingsService;
    private final DocumentScansService documentScansService;
    private final CardService cardService;

    public AccountServiceImpl(AccountDao accountDao, SettingsService settingsService, DocumentScansService documentScansService, CardService cardService) {
        super(accountDao);
        this.accountDao = accountDao;
        this.settingsService = settingsService;
        this.documentScansService = documentScansService;
        this.cardService = cardService;
    }


    @Override
    public List<Account> getAccountsByIds(List<Long> accountIds) {
        return accountDao.getAccountsByIds(accountIds);
    }

    @Override
    public List<AccountSumDto> getTopAccountListBySumOnDeposit() {
        return accountDao.getTopAccountListBySumOnDeposit();
    }

    @Override
    @Transactional
    public void remove(Account account) {
        Set<CashBackCategory> cashBackCategories = new HashSet<>(account.getCashBackCategories());
        for (CashBackCategory category : cashBackCategories) {
            category.removeAccount(account);
        }

        Set<CashBackCompany> cashBackCompanies = new HashSet<>(account.getCashBackCompanies());
        for (CashBackCompany company : cashBackCompanies) {
            company.removeAccount(account);
        }

        // Удаляем настройки аккаунта
        Settings settings = settingsService.getByKey(account.getId());
        if (settings != null) {
            settingsService.remove(settings);
        }

        // Удаляем сканы документов аккаунта
        DocumentScans documentScans = documentScansService.getByKey(account.getId());
        if (documentScans != null) {
            documentScansService.remove(documentScans);
        }

//        // Удаляем карту аккаунта
//        List<Card> cards = cardService.getByKey(account.getId());
//        for (Card card : cards) {
//            cardService.remove(card);
//        }

        super.remove(account);
    }

    @Override
    @Transactional
    public AccountSettingsScansDto getAccountSettingsScansDto(Long accountId) {
        Account account = accountDao.getByKey(accountId);

        Settings settings = settingsService.getByKey(accountId);
        DocumentScans documentScans = documentScansService.getByKey(accountId);

        return AccountSettingsScansDto.builder()
                .accountId(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .settingsId(settings != null ? settings.getId() : null)
                .isNotificationAllowed(settings != null && settings.getIsNotificationAllowed())
                .isPayInStoresAllowed(settings != null && settings.getIsPayInStoresAllowed())
                .isPayOnlineAllowed(settings != null && settings.getIsPayOnlineAllowed())
                .isCashWithdrawalAllowed(settings != null && settings.getIsCashWithdrawalAllowed())
                .documentScansId(documentScans != null ? documentScans.getId() : null)
                .passport(documentScans != null ? documentScans.getPassport() : null)
                .ITN(documentScans != null ? documentScans.getITN() : null)
                .insuranceNumber(documentScans != null ? documentScans.getInsuranceNumber() : null)
                .build();
    }
}
