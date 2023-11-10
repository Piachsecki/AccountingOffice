package org.example.port.in.invoice;

import org.example.domain.customer.CustomerId;
import org.example.domain.money.Money;

import java.time.YearMonth;

public interface CountHealthInsuranceContributionUseCase {
    Money CalculateHealthInsuranceContribution(CustomerId customerId, YearMonth monthToCalculateHealthInsuranceContribution);
}
