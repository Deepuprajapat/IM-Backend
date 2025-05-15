package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.DeveloperDTO;
import com.realestate.invest.Model.Developer;

@Component
public interface DeveloperService 
{

    Developer saveNewDeveloperByUser(Long userId, DeveloperDTO developerDTO) throws Exception;

    Developer getById(Long Id) throws Exception;

    Developer updateDeveloperById(Long Id, DeveloperDTO developerDTO) throws Exception;

    String deleteDeveloperById(Long Id) throws Exception;

    List<Developer> getAllDevelopers(Boolean isVerifed, Boolean isActive, String name);

    Developer findByurl(String url) throws Exception;

}
