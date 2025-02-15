package com.bank.example.service;


import com.bank.example.dto.CashBackCategoryDetailedDto;
import com.bank.example.dto.CashBackCategoryDto;
import com.bank.example.model.CashBackCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CashBackCategoryService extends GenericService<Long, CashBackCategory> {

    @Transactional
    void breakCategoryCompanyLinks(Long categoryId);

    @Transactional
    void breakCategoryAccountLinks(Long categoryId);

    List<CashBackCategoryDetailedDto> getDetailedCategories();

    List<CashBackCategoryDto> getCashBackCategoriesWithCompanyAndAmount(Long accountId);
}
