package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.City;
import com.realestate.invest.Repository.CityRepository;
import com.realestate.invest.Service.CityService;
import com.realestate.invest.Utils.OTPGenerator;

@Service
public class CityServiceImpl implements CityService 
{

    @Autowired
    private CityRepository cityRepository;

    @Override
    public City saveNewCity(City cityDTO) throws Exception 
    {
        City existing = this.cityRepository.findByCityName(cityDTO.getCityName());
        if (existing != null) throw new Exception(cityDTO.getCityName() + " is already exists");
        City city = new City();
        city.setCreatedDate(new Date().getTime());
        city.setStateName(cityDTO.getStateName());
        city.setCityName(cityDTO.getCityName());
        city.setPhoneNumber(cityDTO.getPhoneNumber());
        city.setActive(true);

        String lowerCase = city.getCityName().toLowerCase() + " " + city.getStateName().toLowerCase();
        String cityUrl = lowerCase.replaceAll("\\s", "-");
        City existingUrl = this.cityRepository.findByCityUrl(cityUrl);
        String newcityUrl = (existingUrl != null) ? (cityUrl + OTPGenerator.generateUUID().substring(13)) : cityUrl;
        city.setCityUrl(newcityUrl);

        return this.cityRepository.save(city);
    }

    @Override
    public City findById(Long Id) throws Exception 
    {
        City city = this.cityRepository.findById(Id).orElseThrow(() -> new Exception("City not found"));
        return city;
    }

    @Override
    public City updateCityById(Long Id, City cityDTO) throws Exception 
    {
        City city = this.cityRepository.findById(Id).orElseThrow(() -> new Exception("City not found"));
        Optional.ofNullable(cityDTO.getStateName()).ifPresent(city::setStateName);

        if (cityDTO.getCityName() != null && !cityDTO.getCityName().equals(city.getCityName())) 
        {
            Optional.ofNullable(cityDTO.getCityName()).ifPresent(city::setCityName);
            String lowerCase = city.getCityName().toLowerCase() + " " + city.getStateName().toLowerCase();
            String cityUrl = lowerCase.replaceAll("\\s", "-");
            City existingUrl = this.cityRepository.findByCityUrl(cityUrl);
            String newcityUrl = (existingUrl != null) ? (cityUrl + OTPGenerator.generateUUID().substring(13)) : cityUrl;
            city.setCityUrl(newcityUrl);
        }
        city.setActive(true);
        System.out.println("phone : " + cityDTO.getPhoneNumber());

        if (cityDTO.getPhoneNumber() != null) 
        {
            if (city.getPhoneNumber() == null) 
            {
                city.setPhoneNumber(new ArrayList<>());
            }
            System.out.println("watch inside : " + cityDTO.getPhoneNumber());
            city.setPhoneNumber(cityDTO.getPhoneNumber());
        }
        
        return this.cityRepository.save(city);
    }

    @Override
    public String deleteCityById(Long Id) throws Exception 
    {
        City city = this.cityRepository.findById(Id).orElseThrow(() -> new Exception("City not found"));
        this.cityRepository.delete(city);
        String messsage = "City deleted successfully";
        return messsage;
    }

    @Override
    public List<City> getAllCity(String stateName, String cityName, Boolean isActive) throws Exception 
    {
        List<City> cities = this.cityRepository.findAll();
        List<City> filteredCities = cities.stream()
                .filter(city -> (stateName == null || city.getStateName().toLowerCase().equals(stateName.toLowerCase()))
                        &&
                        (cityName == null || city.getCityName().toLowerCase().contains(cityName.toLowerCase())) &&
                        (isActive == null || city.isActive() == isActive))
                .collect(Collectors.toList());
        return filteredCities;
    }

    @Override
    public City findByUrl(String url) throws Exception 
    {
        City city = this.cityRepository.findByCityUrl(url);
        if (city == null) throw new Exception("City not found");
        return city;
    }

}
