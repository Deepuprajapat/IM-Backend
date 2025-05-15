package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Amenities;


public interface AmenitiesRepository extends JpaRepository<Amenities, Long>
{
    Amenities findByAmenitiesName(String amenitiesName);

    Amenities findByAmenitiesCategoryAndAmenitiesName(String category, String name);
    
}
