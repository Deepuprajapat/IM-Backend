package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.Locality;

@Component
public interface LocalityService 
{
    Locality saveNewLocality(Long cityId, Locality localityDTO) throws Exception;

    Locality findById(Long Id) throws Exception;

    Locality updateLocalityById(Long Id, Long cityId, Locality localityDTO) throws Exception;

    String deleteLocalityById(Long Id) throws Exception;

    List<Locality> getAllLocalities(Long cityId, String cityName, Boolean isActive);

    Locality findByUrl(String url) throws Exception;
    
}
