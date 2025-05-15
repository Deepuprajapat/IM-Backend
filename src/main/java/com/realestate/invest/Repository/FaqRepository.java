package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.Project;

public interface FaqRepository extends JpaRepository<Faq, Long> 
{

    List<Faq> findByProject(Project project);
    
}
