package com.magnojr.repaymentscheduleservice.services;

import com.magnojr.repaymentscheduleservice.domain.BorrowerPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Slf4j
class RepaymentCalculatorService {

    private static final int DAYS_YEAR = 360;
    private static final int DAYS_MONTH = 30;

    static double calculateInterest(final double rate, final double initialOutstandingPrincipal) {
        final double ratePercent = rate / 100;

        final double result = (ratePercent * DAYS_MONTH * initialOutstandingPrincipal) / DAYS_YEAR;
        return roundDecimalPoints(result);
    }

    static double calculatePrincipal(final double borrowerPaymentAmount, final double interest) {
        final double result = borrowerPaymentAmount - interest;
        return roundDecimalPoints(result);
    }


    static double calculateAnnuityPayment(final int duration, final double yearlyRate, final double loanAmount) {
        final double monthlyRate = convertFromYearlyToMonthly(yearlyRate / 100);
        final double result = (monthlyRate * loanAmount) / (1 - Math.pow((1 + monthlyRate), -duration));
        return roundDecimalPoints(result);
    }


    static BorrowerPayment calculateBorrowerPayment(final double outstandingPrincipal, final double rate, final double annuityPayment, final LocalDate date) {

        final double initialOutstandingPrincipal = roundDecimalPoints(outstandingPrincipal);
        final double interest = calculateInterest(rate, initialOutstandingPrincipal);

        final double principalCalculated = calculatePrincipal(annuityPayment, interest);

        final double principal = principalCalculated < initialOutstandingPrincipal ?
                principalCalculated : initialOutstandingPrincipal;

        final double remainingOutStandingPrincipal = getRemainingOutstandingPrincipal(outstandingPrincipal, principal);

        final double borrowerPaymentAmount = principalCalculated < initialOutstandingPrincipal ?
                annuityPayment : calculateBorrowerPaymentAmount(principal, interest);

        return BorrowerPayment.builder()
                .borrowerPaymentAmount(borrowerPaymentAmount)
                .initialOutstandingPrincipal(initialOutstandingPrincipal)
                .principal(principal)
                .interest(interest)
                .remainingOutstandingPrincipal(remainingOutStandingPrincipal)
                .date(date)
                .build();
    }

    private static double calculateBorrowerPaymentAmount(final double principal, final double interest) {
        final double result = principal + interest;
        return roundDecimalPoints(result);
    }

    private static double convertFromYearlyToMonthly(final double rate) {
        return rate / 12;
    }

    private static double roundDecimalPoints(final double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private static double getRemainingOutstandingPrincipal(final double outstandingPrincipal, final double principal) {
        return (outstandingPrincipal - principal) > 0 ?
                roundDecimalPoints((outstandingPrincipal - principal)) :
                0;
    }


}
