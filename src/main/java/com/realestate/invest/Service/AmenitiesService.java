package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.Amenities;

@Component
public interface AmenitiesService 
{
    Amenities saveNewAmenities(Amenities amenitiesDTO) throws Exception;

    Amenities findById(Long Id) throws Exception;

    Amenities updateAmenitiesById(Long Id, Amenities amenitiesDTO) throws Exception;

    String deleteAmenitiesById(Long Id) throws Exception;

    List<Amenities> getAllAmenities(String category, String name) throws Exception;

}
