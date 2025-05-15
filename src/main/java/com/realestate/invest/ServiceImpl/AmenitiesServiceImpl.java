package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Amenities;
import com.realestate.invest.Repository.AmenitiesRepository;
import com.realestate.invest.Service.AmenitiesService;

@Service
public class AmenitiesServiceImpl implements AmenitiesService
{

    @Autowired 
    private AmenitiesRepository amenitiesRepository;

    @Override
    public Amenities saveNewAmenities(Amenities amenitiesDTO) throws Exception 
    {
        Amenities existingAmenities = this.amenitiesRepository.findByAmenitiesName(amenitiesDTO.getAmenitiesName());
        if(existingAmenities != null) throw new Exception(amenitiesDTO.getAmenitiesName()+" is already exists");
        Amenities amenities = new Amenities();
        amenities.setCreatedDate(new Date().getTime());
        amenities.setAmenitiesCategory(amenitiesDTO.getAmenitiesCategory());
        amenities.setAmenitiesName(amenitiesDTO.getAmenitiesName());
        amenities.setAmenitiesUrl(amenitiesDTO.getAmenitiesUrl());
        return this.amenitiesRepository.save(amenities);
    }

    @Override
    public Amenities findById(Long Id) throws Exception 
    {
        Amenities amenities = this.amenitiesRepository.findById(Id)
        .orElseThrow(() -> new Exception("Amenity not found"));
        return amenities;
    }

    @Override
    public Amenities updateAmenitiesById(Long Id, Amenities amenitiesDTO) throws Exception 
    {
        Amenities existingAmenities = this.amenitiesRepository.findById(Id)
        .orElseThrow(() -> new Exception("Amenity not found"));
        existingAmenities.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(amenitiesDTO.getAmenitiesCategory()).ifPresent(existingAmenities::setAmenitiesCategory);
        Optional.ofNullable(amenitiesDTO.getAmenitiesName()).ifPresent(existingAmenities::setAmenitiesName);
        Optional.ofNullable(amenitiesDTO.getAmenitiesUrl()).ifPresent(existingAmenities::setAmenitiesUrl);
        return this.amenitiesRepository.save(existingAmenities);
    }

    @Override
    public String deleteAmenitiesById(Long Id) throws Exception 
    {
        Amenities amenities = this.amenitiesRepository.findById(Id)
        .orElseThrow(() -> new Exception("Amenity not found"));
        this.amenitiesRepository.delete(amenities);
        String message = "Amenity deleted successfully";
        return message;
        
    }

    @Override
    public List<Amenities> getAllAmenities(String category, String name) throws Exception 
    {
        List<Amenities> amenities = this.amenitiesRepository.findAll();
        List<Amenities> filteredAmenities = amenities.stream().filter(amn ->
        (category == null || amn.getAmenitiesCategory().toLowerCase().equals(category.toLowerCase())) &&
        (name == null || amn.getAmenitiesName().toLowerCase().contains(name.toLowerCase())))
        .collect(Collectors.toList());
        return filteredAmenities;
    }
    
}
