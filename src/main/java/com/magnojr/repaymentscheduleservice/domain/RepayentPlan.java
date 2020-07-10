package com.magnojr.repaymentscheduleservice.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepayentPlan {

    private List<BorrowerPayment> borrwerPayments;

}
