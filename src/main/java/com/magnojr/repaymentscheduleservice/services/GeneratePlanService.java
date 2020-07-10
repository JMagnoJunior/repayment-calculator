package com.magnojr.repaymentscheduleservice.services;

import com.magnojr.repaymentscheduleservice.domain.BorrowerPayment;
import com.magnojr.repaymentscheduleservice.domain.RepayentPlan;
import dtos.RepaymentPlanParametersDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class GeneratePlanService {

    private final RepaymentCalculatorService repaymentCalculatorService;

    public RepayentPlan generateRepaymentPlan(RepaymentPlanParametersDTO repaymentPlanParametersDTO) {
        log.info("Generating repayment plan for input: {}", repaymentPlanParametersDTO);

        List<BorrowerPayment> borrowerPayments = buildListOfBorrowerPaymentFromParameters(repaymentPlanParametersDTO);

        return RepayentPlan.builder()
                .borrwerPayments(borrowerPayments)
                .build();
    }

    private List<BorrowerPayment> buildListOfBorrowerPaymentFromParameters(RepaymentPlanParametersDTO repaymentPlanParametersDTO) {
        final int duration = repaymentPlanParametersDTO.getDuration();
        final double nominalRate = repaymentPlanParametersDTO.getNominalRate();

        double interestPrincipal = repaymentPlanParametersDTO.getLoanAmount();
        double annuityPayment = repaymentCalculatorService.calculateAnnuityPayment(
                repaymentPlanParametersDTO.getDuration(),
                repaymentPlanParametersDTO.getNominalRate(),
                repaymentPlanParametersDTO.getLoanAmount()
        );

        LocalDate startDate = repaymentPlanParametersDTO.getStartDate().toLocalDate();

        return getListOfBorrowerPayment(duration, interestPrincipal, nominalRate, annuityPayment, startDate);
    }

    private List<BorrowerPayment> getListOfBorrowerPayment(final int duration,
                                                           final double initialOutstandingPrincipal,
                                                           final double nominalRate,
                                                           final double annuityPayment,
                                                           final LocalDate startDate) {

        List<BorrowerPayment> borrowerPayments = new ArrayList<>();
        double outstandingPrincipal = initialOutstandingPrincipal;
        for (int i = 0; i < duration; i++) {

            BorrowerPayment borrowerPayment = repaymentCalculatorService.calculateBorrowerPayment(
                    outstandingPrincipal,
                    nominalRate,
                    annuityPayment,
                    startDate.plusMonths(i)
            );
            outstandingPrincipal = borrowerPayment.getRemainingOutstandingPrincipal();

            borrowerPayments.add(borrowerPayment);
        }
        return borrowerPayments;
    }

}
