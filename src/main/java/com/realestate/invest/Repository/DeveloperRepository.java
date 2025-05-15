package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.Developer;

public interface DeveloperRepository extends JpaRepository <Developer, Long>
{

    Developer findByDeveloperName(String developerName);

    Developer findByDeveloperUrl(String developerUrl);
}
