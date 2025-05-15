package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.GenericKeywords;
import com.realestate.invest.Repository.GenericKeywordsRepository;
import com.realestate.invest.Service.GenericKeywordsService;
import com.realestate.invest.Utils.OTPGenerator;

@Service
public class GenericKeywordsServiceImpl implements GenericKeywordsService
{

    @Autowired
    private GenericKeywordsRepository gKeywordsRepository;

    @Override
    public GenericKeywords saveNewGenericKeywords(GenericKeywords genericKeywords) throws Exception 
    {
        GenericKeywords existingKeywords = this.gKeywordsRepository.findBySearchTerms(genericKeywords.getSearchTerms());
        if(existingKeywords != null) throw new Exception("Keyword is already existed"); 
        GenericKeywords gKeywords = new GenericKeywords();
        gKeywords.setSearchTerms(genericKeywords.getSearchTerms());

        String lowerCase = genericKeywords.getSearchTerms().toLowerCase();
        String pathUrl = lowerCase.replaceAll("\\s", "-");
        GenericKeywords existingUrl = this.gKeywordsRepository.findByPath(pathUrl);
        String newGenericPath = (existingUrl != null) ? (pathUrl + OTPGenerator.generateUUID().toString().substring(13)) : pathUrl;
        
        gKeywords.setPath(newGenericPath); 
        gKeywords.setUrl(genericKeywords.getUrl());
        return this.gKeywordsRepository.save(gKeywords);
    }

    @Override
    public void deleteGenericKeywordsById(Long id) throws Exception 
    {
        GenericKeywords exKeywords = this.gKeywordsRepository.findById(id).orElseThrow(() ->  new Exception("Keywords not found"));
        this.gKeywordsRepository.delete(exKeywords);
    }

    @Override
    public GenericKeywords updateGenericKeywordsById(GenericKeywords genericKeywords, Long id) throws Exception 
    {
        GenericKeywords exKeywords = this.gKeywordsRepository.findById(id).
        orElseThrow(() -> new Exception("keywords not found"));
        Optional.ofNullable(genericKeywords.getPath()).ifPresent(exKeywords::setPath);
        Optional.ofNullable(genericKeywords.getUrl()).ifPresent(exKeywords::setUrl);

        return this.gKeywordsRepository.save(exKeywords);
    }

    @Override
    public GenericKeywords getGenericKeywordsById(Long id) throws Exception 
    {
        GenericKeywords exKeywords = this.gKeywordsRepository.findById(id).
        orElseThrow(() -> new Exception("Keywords not found"));
        return exKeywords;
    }

    @Override
    public List<GenericKeywords> getAllGenericKeywords() 
    {
        return this.gKeywordsRepository.findAll();
    }

    @Override
    public List<GenericKeywords> saveBulkGenericKeywords(List<GenericKeywords> keywords) 
    {
        List<GenericKeywords> savedKeywords = new ArrayList<>();
        for (GenericKeywords keyword : keywords) 
        {
            if (keyword.getSearchTerms() != null && !keyword.getSearchTerms().isEmpty()) 
            {
                GenericKeywords genericKeywords = new GenericKeywords();
                String lowerCase = keyword.getSearchTerms().toLowerCase();
                String pathUrl = lowerCase.replaceAll("\\s", "-");
                GenericKeywords existingUrl = this.gKeywordsRepository.findByPath(pathUrl);
                String newGenericPath = (existingUrl != null) ? (pathUrl + OTPGenerator.generateUUID().toString().substring(13)) : pathUrl;
                
                genericKeywords.setPath(newGenericPath);
                genericKeywords.setSearchTerms(keyword.getSearchTerms());
                genericKeywords.setUrl(keyword.getUrl());
                savedKeywords.add(genericKeywords);
            } 
        }
        return this.gKeywordsRepository.saveAll(savedKeywords);
    }

    @Override
    public GenericKeywords getByKeywords(String keywords) throws Exception 
    {
        GenericKeywords genericKeywords = this.gKeywordsRepository.findBySearchTerms(keywords);
        if(genericKeywords == null) throw new Exception("No match found");
        return genericKeywords;
    }

    @Override
    public GenericKeywords getByUrl(String url) throws Exception 
    {
        GenericKeywords genericKeywords = this.gKeywordsRepository.findByUrl(url);
        if(genericKeywords == null) throw new Exception("No match found");
        return genericKeywords;
    }

    @Override
    public GenericKeywords getByPath(String path) throws Exception 
    {
        GenericKeywords genericKeywords = this.gKeywordsRepository.findByPath(path);
        if(genericKeywords == null) throw new Exception("No match found");
        return genericKeywords;
    }   
    
}
