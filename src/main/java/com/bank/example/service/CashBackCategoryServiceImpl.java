package com.bank.example.service;

import com.bank.example.converter.CashBackCategoryDetailedConverter;
import com.bank.example.dao.*;
import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.model.Account;
import com.bank.example.model.CashBackCategory;
import com.bank.example.model.CashBackCompany;
import com.bank.example.repository.CashBackCategoryRepository;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Accessors(chain = true)
public class CashBackCategoryServiceImpl extends AbstractService<Long, CashBackCategory> implements CashBackCategoryService {

    private final CashBackCategoryDao cashBackCategoryDao;

    @Autowired
    private CashBackCategoryRepository cashBackCategoryRepository;

    @Autowired
    public CashBackCategoryServiceImpl(CashBackCategoryDao cashBackCategoryDao, CashBackCategoryRepository cashBackCategoryRepository) {
        super(cashBackCategoryDao);
        this.cashBackCategoryRepository = cashBackCategoryRepository;
        this.cashBackCategoryDao = cashBackCategoryDao;
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
}
