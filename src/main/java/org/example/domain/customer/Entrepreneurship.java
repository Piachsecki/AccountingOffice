package org.example.domain.customer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.domain.customer.TaxPayments.TaxPaymentForm;

public record Entrepreneurship(
        EntrepreneurshipForm entrepreneurshipForm,
        TaxPaymentForm taxPaymentForm
) {

}
