package com.bank.example.listener;

import com.bank.example.model.CashBackCategory;
import com.bank.example.service.CashBackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.PreRemove;

@Component
public class CashBackCategoryListener {

    @Autowired
    private CashBackCategoryService cashBackCategoryService;

    @PreRemove
    public void preRemove(CashBackCategory cashBackCategory) {
        cashBackCategoryService.breakCategoryCompanyLinks(cashBackCategory.getId());
        cashBackCategoryService.breakCategoryAccountLinks(cashBackCategory.getId());

    }
}
