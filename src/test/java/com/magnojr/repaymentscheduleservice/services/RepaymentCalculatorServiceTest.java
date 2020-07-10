package com.magnojr.repaymentscheduleservice.services;

import com.magnojr.repaymentscheduleservice.domain.BorrowerPayment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class RepaymentCalculatorServiceTest {


    @Test
    public void itShouldCalculateInterestWhenReceiveAValidRateAndInitialOutstandingPrincipal() {
        double result = RepaymentCalculatorService.calculateInterest(5, 5000);
        Assertions.assertThat(result).isEqualTo(20.83);
    }

    @Test
    public void itShouldCalculateInterestEqualsToZeroWhenReceiveRateEqualToZero() {
        double result = RepaymentCalculatorService.calculateInterest(0.00, 5000);
        Assertions.assertThat(result).isEqualTo(0.0);
    }

    @Test
    public void itShouldCalculateInterestWhenReceiveANegativeValueForInitialOutstandingPrincipal() {
        double result = RepaymentCalculatorService.calculateInterest(5, -5000);
        Assertions.assertThat(result).isEqualTo(-20.83);
    }

    @Test
    public void itShouldCalculatePrincipalWhenReceiveBorrowerPaymentAmountAndInterest() {
        double result = RepaymentCalculatorService.calculatePrincipal(219.36, 20.83);
        Assertions.assertThat(result).isEqualTo(198.53);
    }

    @Test
    public void itShouldCalculateAnnuityWhenReceiveValidParameters() {
        double result = RepaymentCalculatorService.calculateAnnuityPayment(24, 5, 5000);
        Assertions.assertThat(result).isEqualTo(219.36);
    }

    @Test
    public void itShouldCalculatePrincipalWhenReceive() {
        double result = RepaymentCalculatorService.calculatePrincipal(219.36, 20.83);
        Assertions.assertThat(result).isEqualTo(198.53);
    }

    @Test
    public void itShouldCalculateBorrowerPayment() {
        LocalDate date = LocalDate.now();
        BorrowerPayment result = RepaymentCalculatorService.calculateBorrowerPayment(5000, 5, 219.36, date);

        Assertions.assertThat(result.getPrincipal()).isEqualTo(198.53);
        Assertions.assertThat(result.getBorrowerPaymentAmount()).isEqualTo(219.36);
        Assertions.assertThat(result.getInitialOutstandingPrincipal()).isEqualTo(5000);
        Assertions.assertThat(result.getRemainingOutstandingPrincipal()).isEqualTo(4801.47);
        Assertions.assertThat(result.getDate()).isEqualTo(date);
    }

}
