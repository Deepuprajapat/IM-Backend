package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Repository.PaymentPlanRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Service.PaymentPlanService;

@Service
public class PaymentPlanServiceImpl implements PaymentPlanService
{
    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public PaymentPlan saveNewByProject(PaymentPlan paymentPlanDTO) throws Exception 
    {
        Project project = this.projectRepository.findById(paymentPlanDTO.getProjectId())
        .orElseThrow(() -> new Exception("Project not found"));

        PaymentPlan newPaymentPlan = new PaymentPlan();
        newPaymentPlan.setPaymentPlanName(paymentPlanDTO.getPaymentPlanName());
        newPaymentPlan.setPaymentPlanValue(paymentPlanDTO.getPaymentPlanValue());
        newPaymentPlan.setProject(project);
        return this.paymentPlanRepository.save(newPaymentPlan);
    }

    @Override
    public List<PaymentPlan> savePaymentPlansByProjectId(Long projectId, List<PaymentPlan> paymentPlanDTOs) throws Exception 
    {
        Project project = this.projectRepository.findById(projectId)
        .orElseThrow(() -> new Exception("Project not found"));

        List<PaymentPlan> paymentPlans = new ArrayList<>();
        for (PaymentPlan paymentPlanDTO : paymentPlanDTOs) 
        {
            PaymentPlan newPaymentPlan = new PaymentPlan();
            newPaymentPlan.setPaymentPlanName(paymentPlanDTO.getPaymentPlanName());
            newPaymentPlan.setPaymentPlanValue(paymentPlanDTO.getPaymentPlanValue());
            newPaymentPlan.setProject(project);
            paymentPlans.add(this.paymentPlanRepository.save(newPaymentPlan));
        }
        return paymentPlans;
    }

    @Override
    public PaymentPlan getById(Long id) throws Exception 
    {
        PaymentPlan paymentPlan = this.paymentPlanRepository.findById(id)
        .orElseThrow(() -> new Exception("Payment Plan not found"));
        return paymentPlan;
    }

    @Override
    public PaymentPlan updateById(PaymentPlan paymentPlanDTO) throws Exception 
    {
        PaymentPlan paymentPlan = this.paymentPlanRepository.findById(paymentPlanDTO.getId())
        .orElseThrow(() -> new Exception("Payment Plan not found"));

        Optional.ofNullable(paymentPlanDTO.getPaymentPlanName()).ifPresent(paymentPlan::setPaymentPlanName);
        Optional.ofNullable(paymentPlanDTO.getPaymentPlanValue()).ifPresent(paymentPlan::setPaymentPlanValue);  
        return this.paymentPlanRepository.save(paymentPlan);
    }

    @Override
    public List<PaymentPlan> updatePaymentPlansByProjectId(List<PaymentPlan> paymentPlanDTOs) throws Exception 
    {
        List<PaymentPlan> updatedPaymentPlans = new ArrayList<>();
        for (PaymentPlan paymentPlanDTO : paymentPlanDTOs) 
        {
            PaymentPlan paymentPlan = this.paymentPlanRepository.findById(paymentPlanDTO.getId())
            .orElseThrow(() -> new Exception("Payment Plan not found"));

            Optional.ofNullable(paymentPlanDTO.getPaymentPlanName()).ifPresent(paymentPlan::setPaymentPlanName);
            Optional.ofNullable(paymentPlanDTO.getPaymentPlanValue()).ifPresent(paymentPlan::setPaymentPlanValue);  
            updatedPaymentPlans.add(this.paymentPlanRepository.save(paymentPlan));
        }
        return updatedPaymentPlans;
    }

    @Override
    public String deleteById(Long id) throws Exception 
    {
        PaymentPlan paymentPlan = this.paymentPlanRepository.findById(id)
        .orElseThrow(() -> new Exception("Payment Plan not found"));
        this.paymentPlanRepository.delete(paymentPlan);
        return "Payment Plan deleted successfully";
    }

    @Override
    public List<PaymentPlan> getAll(Long projectId) throws Exception 
    {
        List<PaymentPlan> paymentPlans = this.paymentPlanRepository.findAll();
        List<PaymentPlan> filteredPaymentPlans = paymentPlans.stream()
        .filter(paymentPlan -> paymentPlan.getProject().getId().equals(projectId))
        .collect(Collectors.toList());
        return filteredPaymentPlans;
    }
    
}
