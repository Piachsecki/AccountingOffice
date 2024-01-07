package org.example.adapter.out.database.repository.mappers.impl;

import org.example.adapter.out.database.entity.CustomerDatabaseEntity;
import org.example.adapter.out.database.repository.mappers.EntrepreneurshipMapper;
import org.example.domain.customer.Entrepreneurship;
import org.example.domain.customer.EntrepreneurshipForm;
import org.example.domain.customer.TaxPayments.*;
import org.springframework.stereotype.Service;

@Service
public class EntrepreneurshipMapperImpl implements EntrepreneurshipMapper {
    @Override
    public Entrepreneurship mapToDomainEntrepreneurshipFromCustomerDatabaseEntity(CustomerDatabaseEntity customerDatabaseEntity) {
        EntrepreneurshipForm entrepreneurshipForm = EntrepreneurshipForm.valueOf(customerDatabaseEntity.getEntrepreneurshipForm());
        String taxPaymentForm = customerDatabaseEntity.getTaxPaymentForm();
        TaxRate taxRate = null;
        for (TaxRate value : TaxRate.values()) {
            if (customerDatabaseEntity.getTaxRate().equals(value.getValue())) {
                taxRate = value;
            }
        }

        TaxPaymentForm taxPaymentFormToReturn = null;
        IndustryType industryType = null;
        if ("LumpSumTax".equals(taxPaymentForm)) {
            if ("0.17".equals(taxRate.getValue())) {
                industryType = IndustryType.SOFTWARE_DEVELOPER;
            }
            if ("0.15".equals(taxRate.getValue())) {
                industryType = IndustryType.DOCTOR;
            }
            if ("0.085".equals(taxRate.getValue())) {
                industryType = IndustryType.TENANT;
            }
            if ("0.055".equals(taxRate.getValue())) {
                industryType = IndustryType.FARMER;
            }
            taxPaymentFormToReturn = new LumpSumTax(industryType);
        } else if ("FlatTax".equals(taxPaymentForm)) {
            taxPaymentFormToReturn = new FlatTax();
        } else {
            taxPaymentFormToReturn = new GeneralTax();

        }

        return new Entrepreneurship(entrepreneurshipForm, taxPaymentFormToReturn);
    }
}
