package com.realestate.invest.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Source;
import com.realestate.invest.Repository.SourceRepository;
import com.realestate.invest.Service.SourceService;

@Service
public class SourceServiceImpl implements SourceService 
{

    @Autowired
    private SourceRepository sourceRepository;

    @Override
    public Source saveNewSource(Source source) throws Exception 
    {
        Source existingSource = this.sourceRepository.findByName(source.getName());
        if(existingSource != null) throw new Exception(source.getName()+" is already exist");
        Source newSource = new Source();
        newSource.setName(source.getName());
        return this.sourceRepository.save(newSource);
    }

    @Override
    public Source findById(Long Id) throws Exception 
    {
        Source source = this.sourceRepository.findById(Id).orElseThrow(() -> new Exception("No data found"));
        return source;
    }

    @Override
    public List<Source> findByName(String name) throws Exception 
    {
        List<Source> sources = this.sourceRepository.findByNameContainingIgnoreCase(name);
        if(sources == null) throw new Exception("No data found");
        return sources;
    }

    @Override
    public Source updateSourceById(Long Id, Source source) throws Exception 
    {
        Source existingSource = this.sourceRepository.findById(Id).orElse(null);
        if(existingSource == null) throw new Exception("No data found");
        if(source.getName() != null)
        {
            existingSource.setName(source.getName());
        }
        return this.sourceRepository.save(existingSource);
    }

    @Override
    public String deleteSourceById(Long Id) throws Exception 
    {
        Source source = this.sourceRepository.findById(Id).orElse(null);
        if(source == null) throw new Exception("No data found");
        this.sourceRepository.delete(source);
        String message = "Source deleted successfully";
        return message;
    }

    @Override
    public List<Source> getAllSources() 
    {
        List<Source> sources = this.sourceRepository.findAll();
        return sources;
    }
    
}
