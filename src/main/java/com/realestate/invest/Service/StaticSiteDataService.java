package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.StaticSiteData;

/**
 * @This interface defines the contract for managing static site data, including operations
 * @such as saving, updating, retrieving, and deleting various types of static site information.
 * @Author: Abhishek Srivastav
 */
@Component
public interface StaticSiteDataService 
{
    /**
     * @Saves new static site data for the specified user.
     *
     * @param siteData The static site data to be saved.
     * @param userId  The unique identifier of the user associated with the data.
     * @return The saved static site data.
     * @throws Exception If there is an error during the save operation.
     */
    StaticSiteData saveNewStaticSiteData(StaticSiteData siteData, Long userId) throws Exception;

    /**
     * @Updates static site data identified by the given ID.
     *
     * @param siteData The static site data to be updated.
     * @param id       The unique identifier of the static site data to be updated.
     * @return The updated static site data.
     * @throws Exception If there is an error during the update operation.
     */
    StaticSiteData updateStaticSiteDataById(StaticSiteData siteData, Long id) throws Exception;

    /**
     * @Retrieves all refund policies.
     *
     * @return A list of static site data representing refund policies.
     */
    StaticSiteData findAllRefundPolicies();

    /**
     * @Retrieves all terms of services.
     *
     * @return A list of static site data representing terms of services.
     */
    StaticSiteData findAllTermsOfServices();

    /**
     * @Retrieves all privacy policies.
     *
     * @return A list of static site data representing privacy policies.
     */
    StaticSiteData findAllPrivacyPolicies();

    /**
     * @Retrieves all static site data.
     *
     * @return A list of all static site data.
     */
    List<StaticSiteData> getAllStaticSiteDatas();

    /**
     * @Retrieves static site data by its unique identifier.
     *
     * @param dataId The unique identifier of the static site data to be retrieved.
     * @return The static site data identified by the given ID.
     * @throws Exception If there is an error during the retrieval operation.
     */
    StaticSiteData getStaticSiteDataById(Long dataId) throws Exception;

    /**
     * @Deletes static site data by its unique identifier.
     *
     * @param dataId The unique identifier of the static site data to be deleted.
     * @throws Exception If there is an error during the deletion operation.
     */
    void deleteStaticSiteDataById(Long dataId) throws Exception;

    /**
     * @Retrieves static site data associated with the specified user.
     *
     * @param userId The unique identifier of the user for whom static site data is retrieved.
     * @return A list of static site data associated with the given user.
     * @throws Exception If there is an error during the retrieval operation.
     */
    List<StaticSiteData> getStaticSiteDataByUserId(Long userId) throws Exception;

    /**
     * @Retrieves information about the organization or entity described in the "About Us" section.
     *
     * @return A list of static site data representing information about the organization or entity.
     */
    StaticSiteData findAboutUs();

}
