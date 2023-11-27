package org.example.domain.customer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domain.customer.TaxPayments.TaxPaymentForm;

@NoArgsConstructor
@AllArgsConstructor
public record Entrepreneurship(
        EntrepreneurshipForm entrepreneurshipForm,
        TaxPaymentForm taxPaymentForm
) {

}
