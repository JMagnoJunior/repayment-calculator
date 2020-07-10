package com.magnojr.repaymentscheduleservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowerPayment {

    private LocalDate date;
    private double borrowerPaymentAmount;
    private double initialOutstandingPrincipal;
    private double interest;
    private double principal;
    private double remainingOutstandingPrincipal;

}
