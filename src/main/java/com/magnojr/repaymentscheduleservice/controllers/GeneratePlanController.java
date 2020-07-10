package com.magnojr.repaymentscheduleservice.controllers;

import com.magnojr.repaymentscheduleservice.domain.RepayentPlan;
import com.magnojr.repaymentscheduleservice.services.GeneratePlanService;
import dtos.RepaymentPlanParametersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeneratePlanController {

    private final GeneratePlanService generatePlanService;

    @PostMapping(path = "/generate-plan", consumes = "application/json", produces = "application/json")
    public RepayentPlan generateRepaymentPlan(@RequestBody @Validated RepaymentPlanParametersDTO repayentPlanParametersDTO) {
        return generatePlanService.generateRepaymentPlan(repayentPlanParametersDTO);
    }

}
