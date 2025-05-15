package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.Faq;

@Component
public interface FaqService 
{
    
    Faq saveNewByProject(Faq faqDTO) throws Exception;

    List<Faq> saveFaqsByProjectId(Long projectId, List<Faq> faqDTOs) throws Exception;

    Faq getById(Long Id) throws Exception;

    Faq updateById(Faq faqDTO) throws Exception;

    List<Faq> updateFaqsByProjectId(List<Faq> faqDTOs) throws Exception;

    String deleteById(Long Id) throws Exception;

    List<Faq> getAll(Long projectId) throws Exception;

}
