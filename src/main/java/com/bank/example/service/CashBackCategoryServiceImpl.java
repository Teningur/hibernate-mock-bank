package com.bank.example.service;

import com.bank.example.converter.CashBackCategoryDetailedConverter;
import com.bank.example.dao.*;
import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.dto.CashBackCategoryDto;
import com.bank.example.dto.CashBackTransactionDto;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.repository.CashBackCategoryRepository;
import com.bank.example.repository.CashBackCompanyRepository;
import com.bank.example.repository.TransactionRepository;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Accessors(chain = true)
public class CashBackCategoryServiceImpl extends AbstractService<Long, CashBackCategory> implements CashBackCategoryService {

    private final CashBackCategoryDao cashBackCategoryDao;

    private final CashBackCategoryRepository cashBackCategoryRepository;
    private final CashBackCompanyRepository cashBackCompanyRepository;
    private final TransactionRepository transactionRepository;


    @Autowired
    public CashBackCategoryServiceImpl(CashBackCategoryDao cashBackCategoryDao, CashBackCategoryRepository cashBackCategoryRepository, CashBackCompanyRepository cashBackCompanyRepository, TransactionRepository transactionRepository) {
        super(cashBackCategoryDao);
        this.cashBackCategoryRepository = cashBackCategoryRepository;
        this.cashBackCategoryDao = cashBackCategoryDao;
        this.cashBackCompanyRepository = cashBackCompanyRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void breakCategoryCompanyLinks(Long categoryId) {
        CashBackCategory cashBackCategory = cashBackCategoryDao.getByKey(categoryId);

        if (cashBackCategory != null) {
            Set<CashBackCompany> cashBackCompanies = cashBackCategory.getCashBackCompanies();
            for (CashBackCompany cashBackCompany : cashBackCompanies) {
                cashBackCompany.setCashBackCategories(null);
            }
        }
    }

    @Override
    @Transactional
    public void breakCategoryAccountLinks(Long categoryId) {
        CashBackCategory cashBackCategory = cashBackCategoryDao.getByKey(categoryId);

        if (cashBackCategory != null) {
            Set<Account> accounts = cashBackCategory.getAccounts();
            for (Account account : accounts) {
                account.setCashBackCategories(null);
            }
        }
    }


    public List<CashBackCategoryDetailedDto> getDetailedCategories() {
        List<CashBackCategory> categories = cashBackCategoryRepository.findAllDetailed();
        return CashBackCategoryDetailedConverter.convertEntityToDtoList(categories);
    }

    public List<CashBackCategoryDto> getCashBackCategoriesWithCompanyAndAmount(Long accountId) {
        return cashBackCategoryRepository.findByAccountId(accountId).stream()
                .map(category -> new CashBackCategoryDto(
                        category.getId(),
                        category.getName(),
                        transactionRepository.findByAccountIdAndCategoryId(accountId, category.getId()).stream()
                                .map(transaction -> {
                                    CashBackCompany company = category.getCashBackCompanies().stream().findFirst().orElse(null);
                                    return new CashBackTransactionDto(
                                            transaction.getId(),
                                            company != null ? company.getId() : null,
                                            company != null ? company.getName() : "Unknown",
                                            transaction.getAmount().doubleValue(),
                                            transaction.getDateTime().toLocalDate()
                                    );
                                })
                                .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t1.getTransactionDate()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}

