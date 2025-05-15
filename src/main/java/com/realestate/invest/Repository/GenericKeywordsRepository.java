package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.GenericKeywords;

/**
 *  The {@code GenericKeywordsRepository} interface provides data access methods for the GenericKeywords entity.
 *  It extends the JpaRepository interface for basic CRUD operations and defines custom queries for retrieving GenericKeywordsRepository by ID & names.
 * @author Abhishek Srivastav
 * @version 1.0
 * 
 */
public interface GenericKeywordsRepository extends JpaRepository <GenericKeywords, Long>
{
    GenericKeywords findBySearchTerms(String searchTerms);

    GenericKeywords findByPath(String path);

    GenericKeywords findByUrl(String url);

}
