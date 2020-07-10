package com.magnojr.repaymentscheduleservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepaymentPlanParametersDTO {

    @Positive
    private double loanAmount;
    @Positive
    private double nominalRate;
    @PositiveOrZero
    private int duration;
    @NotNull
    private OffsetDateTime startDate;

}
