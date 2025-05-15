package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.realestate.invest.Model.City;
import com.realestate.invest.Model.Locality;

public interface LocalityRepository extends JpaRepository<Locality, Long>
{
    Locality findByLocalityNameAndCity(String localityName , City city);

    Locality findByLocalityNameAndCityCityName(String localityName , String cityName);

    Locality findByLocalityUrl(String url);

    @Query("SELECT l FROM Locality l WHERE LOWER(l.localityName) LIKE CONCAT('%', :sectorPart, '%') " +
        "AND LOWER(l.city.cityName) LIKE CONCAT('%', :cityPart, '%')")
    List<Locality> findByCityAndLocalityName(@Param("sectorPart") String sectorPart, 
                                            @Param("cityPart") String cityPart);

    Locality findByLocalityNameIgnoringCase(String localityName);

}
