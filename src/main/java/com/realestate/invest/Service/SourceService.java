package com.realestate.invest.Service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.realestate.invest.Model.Source;

@Component
public interface SourceService 
{
    
    Source saveNewSource(Source source) throws Exception;

    Source findById(Long Id) throws Exception;

    List<Source> findByName(String name) throws Exception;

    Source updateSourceById(Long Id, Source source) throws Exception;

    String deleteSourceById(Long Id) throws Exception;

    List<Source> getAllSources();
}
