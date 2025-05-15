package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Repository.FaqRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Service.FaqService;

@Service
public class FaqServiceImpl implements FaqService
{
    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Faq saveNewByProject(Faq faqDTO) throws Exception 
    {
        Project project = this.projectRepository.findById(faqDTO.getProjectId())
        .orElseThrow(() -> new Exception("Project not found"));
        Faq newFaq = new Faq();
        newFaq.setQuestion(faqDTO.getQuestion());
        newFaq.setAnswer(faqDTO.getAnswer());
        newFaq.setProject(project);
        return this.faqRepository.save(newFaq);
    }

    @Override
    public List<Faq> saveFaqsByProjectId(Long projectId, List<Faq> faqDTOs) throws Exception 
    {
        Project project = this.projectRepository.findById(projectId)
        .orElseThrow(() -> new Exception("Project not found"));

        List<Faq> faqs = new ArrayList<>();
        for (Faq faqDTO : faqDTOs) 
        {
            Faq newFaq = new Faq();
            newFaq.setQuestion(faqDTO.getQuestion());
            newFaq.setAnswer(faqDTO.getAnswer());
            newFaq.setProject(project);
            faqs.add(this.faqRepository.save(newFaq));
        }
        return faqs;
    }

    @Override
    public Faq getById(Long Id) throws Exception 
    {
        Faq faq = this.faqRepository.findById(Id)
        .orElseThrow(() -> new Exception("FAQ not found"));
        return faq;
    }

    @Override
    public Faq updateById(Faq faqDTO) throws Exception 
    {
        Faq faq = this.faqRepository.findById(faqDTO.getId())
        .orElseThrow(() -> new Exception("FAQ not found"));
        Optional.ofNullable(faqDTO.getQuestion()).ifPresent(faq::setQuestion);
        Optional.ofNullable(faqDTO.getAnswer()).ifPresent(faq::setAnswer);
        return this.faqRepository.save(faq);
    }

    @Override
    public List<Faq> updateFaqsByProjectId(List<Faq> faqDTOs) throws Exception 
    {
        List<Faq> faqs = new ArrayList<>();
        for (Faq faqDTO : faqDTOs) 
        {
            Faq faq = this.faqRepository.findById(faqDTO.getId())
            .orElseThrow(() -> new Exception("FAQ not found"));

            Optional.ofNullable(faqDTO.getQuestion()).ifPresent(faq::setQuestion);
            Optional.ofNullable(faqDTO.getAnswer()).ifPresent(faq::setAnswer);
            faqs.add(this.faqRepository.save(faq));
        }
        return faqs;
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        Faq faq = this.faqRepository.findById(Id)
        .orElseThrow(() -> new Exception("FAQ not found"));
        this.faqRepository.delete(faq);
        return "FAQ deleted successfully";
    }

    @Override
    public List<Faq> getAll(Long projectId) throws Exception 
    {
        List<Faq> faqs = this.faqRepository.findAll();
        List<Faq> filteredFaqs = faqs.stream()
        .filter(faq -> faq.getProject().getId().equals(projectId))
        .collect(Collectors.toList());
        return filteredFaqs;
    }

}
