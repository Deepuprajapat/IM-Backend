package com.realestate.invest.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Source;

public interface SourceRepository extends JpaRepository <Source, Long>
{
    
    Source findByName(String name);

    List<Source> findByNameContainingIgnoreCase(String name);

}
