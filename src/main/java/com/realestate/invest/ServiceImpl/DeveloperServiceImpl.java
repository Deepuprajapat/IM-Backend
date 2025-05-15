package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.DeveloperDTO;
import com.realestate.invest.Model.City;
import com.realestate.invest.Model.Developer;
import com.realestate.invest.Repository.CityRepository;
import com.realestate.invest.Repository.DeveloperRepository;
import com.realestate.invest.Service.DeveloperService;
import com.realestate.invest.Utils.OTPGenerator;

@Service
public class DeveloperServiceImpl implements DeveloperService
{

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Developer saveNewDeveloperByUser(Long userId, DeveloperDTO developerDTO) throws Exception 
    {
        Developer existing = this.developerRepository.findByDeveloperName(developerDTO.getDeveloperName());
        if(existing != null) throw new Exception(developerDTO.getDeveloperName()+" is already exist");
        City city = this.cityRepository.findById(developerDTO.getCityId()).orElseThrow(() -> new Exception("City not found"));
        Developer developer = new Developer();
        developer.setCreatedDate(new Date().getTime());
        developer.setDeveloperName(developerDTO.getDeveloperName());
        developer.setDeveloperLegalName(developerDTO.getDeveloperLegalName());
        developer.setCity(city);
        developer.setDeveloperAddress(developerDTO.getDeveloperAddress());
        developer.setDeveloperLogo(developerDTO.getDeveloperLogo());
        developer.setAltDeveloperLogo(developerDTO.getAltDeveloperLogo());
        developer.setAbout(developerDTO.getAbout());
        developer.setDisclaimer(developerDTO.getDisclaimer());
        developer.setEstablishedYear(developerDTO.getEstablishedYear());
        developer.setProjectDoneNo(developerDTO.getProjectDoneNo());
        developer.setOverview(developerDTO.getOverview());
        developer.setPhone(developerDTO.getPhone());
        developer.setIsVerified(developerDTO.getIsVerified());
        developer.setIsActive(developerDTO.getIsActive());
        
        String lowerCase = developer.getDeveloperName().toLowerCase();
        String developerUrl = lowerCase.replaceAll("\\s", "-");
        Developer existingUrl = this.developerRepository.findByDeveloperUrl(developerUrl);
        String newDeveloperUrl = (existingUrl != null) ? (developerUrl + OTPGenerator.generateUUID().substring(13)) : developerUrl;
        developer.setDeveloperUrl(newDeveloperUrl);

        developer.setIsActive(true);
        return this.developerRepository.save(developer);

    }

    @Override
    public Developer getById(Long Id) throws Exception 
    {
        Developer developer = this.developerRepository.findById(Id)
        .orElseThrow(() -> new Exception("Developer not found"));
        return developer;
    }

    @Override
    public Developer updateDeveloperById(Long Id, DeveloperDTO developerDTO) throws Exception 
    {
        Developer developer = this.developerRepository.findById(Id)
        .orElseThrow(() -> new Exception("Developer not found"));

        developer.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(developerDTO.getDeveloperLegalName()).ifPresent(developer::setDeveloperLegalName);
        Optional.ofNullable(developerDTO.getDeveloperAddress()).ifPresent(developer::setDeveloperAddress);
        Optional.ofNullable(developerDTO.getDeveloperLogo()).ifPresent(developer::setDeveloperLogo);
        Optional.ofNullable(developerDTO.getAltDeveloperLogo()).ifPresent(developer::setAltDeveloperLogo);
        Optional.ofNullable(developerDTO.getAbout()).ifPresent(developer::setAbout);
        Optional.ofNullable(developerDTO.getDisclaimer()).ifPresent(developer::setDisclaimer);
        Optional.ofNullable(developerDTO.getEstablishedYear()).ifPresent(developer::setEstablishedYear);
        Optional.ofNullable(developerDTO.getProjectDoneNo()).ifPresent(developer::setProjectDoneNo);
        Optional.ofNullable(developerDTO.getOverview()).ifPresent(developer::setOverview);
        Optional.ofNullable(developerDTO.getPhone()).ifPresent(developer::setPhone);
        if (developerDTO.getCityId() != null) developer.setCity(cityRepository.findById(developerDTO.getCityId()).orElseThrow(() -> new Exception("City not found")));

        if(developerDTO.getDeveloperName() != null && !developerDTO.getDeveloperName().equals(developer.getDeveloperName()))
        {
            Optional.ofNullable(developerDTO.getDeveloperName()).ifPresent(developer::setDeveloperName);
            String lowerCase = developer.getDeveloperName().toLowerCase();
            String developerUrl = lowerCase.replaceAll("\\s", "-");
            Developer existingUrl = this.developerRepository.findByDeveloperUrl(developerUrl);
            String newDeveloperUrl = (existingUrl != null) ? (developerUrl + OTPGenerator.generateUUID().substring(13)) : developerUrl;
            developer.setDeveloperUrl(newDeveloperUrl);
        }

        if(developerDTO.getIsActive() != null) developer.setIsActive(developerDTO.getIsActive());
        if(developerDTO.getIsVerified() != null) developer.setIsVerified(developerDTO.getIsVerified());

        return this.developerRepository.save(developer);
    }

    @Override
    public String deleteDeveloperById(Long Id) throws Exception 
    {
        Developer developer = this.developerRepository.findById(Id)
        .orElseThrow(() -> new Exception("Developer not found"));
        this.developerRepository.delete(developer);
        String message = "Developer deleted successfully";
        return message;
    }

    @Override
    public List<Developer> getAllDevelopers(Boolean isVerifed, Boolean isActive, String name) 
    {
        List<Developer> developers = this.developerRepository.findAll();
        List<Developer> filteredDevelopers = developers.stream().filter( dl ->
        (isVerifed == null || dl.getIsVerified() == isVerifed) &&
        (isActive == null || dl.getIsActive() == isActive) &&
        (name == null || dl.getDeveloperName().toLowerCase().contains(name.toLowerCase())))
        .collect(Collectors.toList());
        return filteredDevelopers;
    }

    @Override
    public Developer findByurl(String url) throws Exception 
    {
        Developer developer = this.developerRepository.findByDeveloperUrl(url);
        if(developer == null) throw new Exception("Developer not found");
        return developer;
    }
    
}
