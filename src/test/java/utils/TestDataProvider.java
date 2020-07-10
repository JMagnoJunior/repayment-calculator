package utils;

import dtos.RepaymentPlanParametersDTO;

import java.time.OffsetDateTime;

public class TestDataProvider {

    public static RepaymentPlanParametersDTO buildDefaultRepaymentPlanDTO() {
        return RepaymentPlanParametersDTO.builder()
                .duration(24)
                .loanAmount(5000)
                .nominalRate(5)
                .startDate(OffsetDateTime.parse("2018-01-01T00:00:01Z"))
                .build();
    }

    public static RepaymentPlanParametersDTO buildRepaymentPlanDTOWithNegativeLoanAmount() {
        return RepaymentPlanParametersDTO.builder()
                .duration(24)
                .loanAmount(-5000)
                .nominalRate(5)
                .startDate(OffsetDateTime.parse("2018-01-01T00:00:01Z"))
                .build();
    }
}
