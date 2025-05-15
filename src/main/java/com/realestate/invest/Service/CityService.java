package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.City;

@Component
public interface CityService 
{
    City saveNewCity(City city) throws Exception;

    City findById(Long Id) throws Exception;

    City updateCityById( Long Id, City city) throws Exception;

    String deleteCityById(Long Id) throws Exception;

    List<City> getAllCity(String stateName, String cityName, Boolean isActive) throws  Exception;

    City findByUrl(String url) throws Exception;
    
}
