package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.PaymentPlan;

@Component
public interface PaymentPlanService 
{
    PaymentPlan saveNewByProject(PaymentPlan paymentPlanDTO) throws Exception;

    List<PaymentPlan> savePaymentPlansByProjectId(Long projectId, List<PaymentPlan> paymentPlanDTOs) throws Exception;

    PaymentPlan getById(Long id) throws Exception;

    PaymentPlan updateById(PaymentPlan paymentPlanDTO) throws Exception;

    List<PaymentPlan> updatePaymentPlansByProjectId(List<PaymentPlan> paymentPlanDTOs) throws Exception;

    String deleteById(Long id) throws Exception;

    List<PaymentPlan> getAll(Long projectId) throws Exception;

}
