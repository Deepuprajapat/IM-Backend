package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.City;
import com.realestate.invest.Model.Locality;
import com.realestate.invest.Repository.CityRepository;
import com.realestate.invest.Repository.LocalityRepository;
import com.realestate.invest.Service.LocalityService;
import com.realestate.invest.Utils.OTPGenerator;

@Service
public class LocalityServiceImpl implements LocalityService
{

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public Locality saveNewLocality(Long cityId, Locality localityDTO) throws Exception 
    {
        City city = this.cityRepository.findById(cityId).orElseThrow(() -> new Exception(" City not found"));
        Locality existing = this.localityRepository.findByLocalityNameAndCity(localityDTO.getLocalityName(), city);
        if(existing != null) throw new Exception(existing.getLocalityName() + " is already exist for "+existing.getCity().getCityName());
        Locality locality =  new Locality();
        locality.setCreatedDate(new Date().getTime());
        locality.setCity(city);
        locality.setLocalityName(localityDTO.getLocalityName());

        String lowerCase = locality.getLocalityName().toLowerCase();
        String localityUrl = lowerCase.replaceAll("\\s", "-");
        Locality existingUrl = this.localityRepository.findByLocalityUrl(localityUrl);
        String newLocalityUrl = (existingUrl != null) ? (localityUrl + OTPGenerator.generateUUID().substring(13)) : localityUrl;
        locality.setLocalityUrl(newLocalityUrl);

        return this.localityRepository.save(locality);
    }

    @Override
    public Locality findById(Long Id) throws Exception 
    {
        Locality locality = this.localityRepository.findById(Id).orElseThrow(() ->  new Exception("Locality not found"));
        return locality;
    }

    @Override
    public Locality updateLocalityById(Long Id, Long cityId, Locality localityDTO) throws Exception 
    {
        Locality locality = this.localityRepository.findById(Id).orElseThrow(() ->  new Exception("Locality not found"));
        locality.setUpdatedDate(new Date().getTime());

        if(cityId != null)
        {
            City city = this.cityRepository.findById(cityId).orElseThrow(() -> new Exception(" City not found"));
            locality.setCity(city);
        }
        
        if(localityDTO.getLocalityName() != null && !localityDTO.getLocalityName().equals(locality.getLocalityName()))
        {
            Optional.ofNullable(localityDTO.getLocalityName()).ifPresent(locality::setLocalityName);
            String lowerCase = locality.getLocalityName().toLowerCase();
            String localityUrl = lowerCase.replaceAll("\\s", "-");
            Locality existingUrl = this.localityRepository.findByLocalityUrl(localityUrl);
            String newLocalityUrl = (existingUrl != null) ? (localityUrl + OTPGenerator.generateUUID().substring(13)) : localityUrl;
            locality.setLocalityUrl(newLocalityUrl);
        }

        return this.localityRepository.save(locality);
    }

    @Override
    public String deleteLocalityById(Long Id) throws Exception 
    {
        Locality locality = this.localityRepository.findById(Id).orElseThrow(() ->  new Exception("Locality not found"));
        this.localityRepository.delete(locality);
        String message = "Locality deleted successfully";
        return message;
    }

    @Override
    public List<Locality> getAllLocalities(Long cityId, String cityName, Boolean isActive) 
    {
        List<Locality> localities = this.localityRepository.findAll();
        List<Locality> filteredLocalities = localities.stream().filter( lc ->
        (cityId == null || lc.getCity().getId().equals(cityId)) &&
        (cityName == null || lc.getCity().getCityName().toLowerCase().contains(cityName.toLowerCase())) &&
        (isActive == null || lc.getCity().isActive() == isActive))
        .collect(Collectors.toList());
        return filteredLocalities;
    }

    @Override
    public Locality findByUrl(String url) throws Exception 
    {
        Locality locality = this.localityRepository.findByLocalityUrl(url);
        if(locality == null) throw new Exception("Locality not found");
        return locality;
    }

}
