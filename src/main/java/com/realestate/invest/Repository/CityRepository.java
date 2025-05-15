package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.City;

public interface CityRepository extends JpaRepository<City, Long>
{
    City findByCityName(String cityName);

    City findByCityNameAndStateName(String cityName, String stateName);

    City findByCityUrl(String cityUrl);

}
