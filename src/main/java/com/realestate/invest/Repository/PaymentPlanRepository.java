package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Long>
{
    List<PaymentPlan> findByProject(Project project);
}
