package com.realestate.invest.Repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.realestate.invest.Model.StaticSiteData;
import jakarta.transaction.Transactional;

/**
 *  The {@code StaticSiteDataRepostiory} interface provides data access methods for the StaticSiteData entity.
 *  It extends the JpaRepository interface for basic CRUD operations and defines custom queries for retrieving StaticSiteData by ID & names.
 * @author Abhishek Srivastav
 * @version 1.0
 * 
 */
public interface StaticSiteDataRepository extends JpaRepository<StaticSiteData, Long>
{

    @Transactional
    @Query(value = "SELECT * FROM static_site_data where privacy_policy IS NOT NULL ORDER BY created_on DESC", nativeQuery = true)
    List<StaticSiteData> findAllPrivacyPolicies();

    @Transactional
    @Query(value = "SELECT * FROM static_site_data where term_of_services IS NOT NULL ORDER BY created_on DESC",nativeQuery = true)
    List<StaticSiteData> findAllTermsOfServices();

    @Transactional
    @Query(value = "SELECT * FROM static_site_data where refund_policy IS NOT NULL ORDER BY created_on DESC", nativeQuery = true)
    List<StaticSiteData> findAllRefundPolicies();

    @Transactional
    @Query(value = "select * from static_site_data where user_id = ?1",nativeQuery = true)
    public List<StaticSiteData> getStaticSiteDataByUserId(Long user_id);
    
    @Transactional
    @Query(value = "SELECT * FROM static_site_data where about_us IS NOT NULL ORDER BY created_on DESC", nativeQuery = true)
    List<StaticSiteData> findAboutUs();


}
