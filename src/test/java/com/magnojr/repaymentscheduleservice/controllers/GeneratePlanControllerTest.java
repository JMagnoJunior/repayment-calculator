package com.magnojr.repaymentscheduleservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magnojr.repaymentscheduleservice.domain.BorrowerPayment;
import com.magnojr.repaymentscheduleservice.domain.RepayentPlan;
import com.magnojr.repaymentscheduleservice.dtos.RepaymentPlanParametersDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import utils.TestDataProvider;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GeneratePlanControllerTest {


    @Autowired
    private GeneratePlanController generatePlanController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldGenerateRepaymentPlanControllerWhenReceiveValidInputParameters() throws Exception {

        final RepaymentPlanParametersDTO parameteres = TestDataProvider.buildDefaultRepaymentPlanDTO();
        MockHttpServletResponse response = mockMvc.perform(post("/generate-plan")
                .contentType("application/json")
                .content(toJson(parameteres)))
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();

        RepayentPlan repayentPlan = toRepaymentPlan(response.getContentAsString());

        List<BorrowerPayment> borrowerPaymentList = repayentPlan.getBorrwerPayments();
        Assertions.assertThat(borrowerPaymentList.get(0).getBorrowerPaymentAmount()).isEqualTo(219.36);
        Assertions.assertThat(borrowerPaymentList.get(0).getPrincipal()).isEqualTo(198.53);
        Assertions.assertThat(borrowerPaymentList.get(0).getInterest()).isEqualTo(20.83);
        Assertions.assertThat(borrowerPaymentList.get(0).getInitialOutstandingPrincipal()).isEqualTo(5000);
        Assertions.assertThat(borrowerPaymentList.get(0).getRemainingOutstandingPrincipal()).isEqualTo(4801.47);
        Assertions.assertThat(borrowerPaymentList.get(0).getDate()).isEqualTo(LocalDate.parse("2018-01-01"));

        Assertions.assertThat(borrowerPaymentList.get(1).getBorrowerPaymentAmount()).isEqualTo(219.36);
        Assertions.assertThat(borrowerPaymentList.get(1).getPrincipal()).isEqualTo(199.35);
        Assertions.assertThat(borrowerPaymentList.get(1).getInterest()).isEqualTo(20.01);
        Assertions.assertThat(borrowerPaymentList.get(1).getInitialOutstandingPrincipal()).isEqualTo(4801.47);
        Assertions.assertThat(borrowerPaymentList.get(1).getRemainingOutstandingPrincipal()).isEqualTo(4602.12);
        Assertions.assertThat(borrowerPaymentList.get(1).getDate()).isEqualTo(LocalDate.parse("2018-02-01"));

        Assertions.assertThat(borrowerPaymentList.get(23).getBorrowerPaymentAmount()).isEqualTo(219.28);
        Assertions.assertThat(borrowerPaymentList.get(23).getPrincipal()).isEqualTo(218.37);
        Assertions.assertThat(borrowerPaymentList.get(23).getInterest()).isEqualTo(0.91);
        Assertions.assertThat(borrowerPaymentList.get(23).getInitialOutstandingPrincipal()).isEqualTo(218.37);
        Assertions.assertThat(borrowerPaymentList.get(23).getRemainingOutstandingPrincipal()).isEqualTo(0);
        Assertions.assertThat(borrowerPaymentList.get(23).getDate()).isEqualTo(LocalDate.parse("2019-12-01"));

    }

    @Test
    void itShouldReturnClientErrorWhenReceiveInvalidInputParameter() throws Exception {

        final RepaymentPlanParametersDTO invalidParameter = TestDataProvider.buildRepaymentPlanDTOWithNegativeLoanAmount();

        mockMvc.perform(post("/generate-plan")
                .contentType("application/json")
                .content(toJson(invalidParameter)))
                .andExpect(status().is4xxClientError());
    }

    private String toJson(RepaymentPlanParametersDTO parameters) {
        try {
            return objectMapper.writeValueAsString(parameters);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private RepayentPlan toRepaymentPlan(String json) {
        try {
            return objectMapper.readValue(json, RepayentPlan.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
