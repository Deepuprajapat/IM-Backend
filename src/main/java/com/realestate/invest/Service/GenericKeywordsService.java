package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.GenericKeywords;

/**
 * @This interface defines methods to interact with generic keywords.
 *
 * @author Abhishek Srivastav
 */
@Component
public interface GenericKeywordsService 
{
    /**
     * @Saves new generic keywords.
     *
     * @param genericKeywords The generic keywords to be saved.
     * @return The saved generic keywords.
     * @throws Exception if an error occurs during the saving process.
     */
    GenericKeywords saveNewGenericKeywords(GenericKeywords genericKeywords) throws Exception;

    /**
     * @Deletes generic keywords by ID.
     *
     * @param id The ID of the generic keywords to be deleted.
     * @throws Exception if an error occurs during the deletion process.
     */
    void deleteGenericKeywordsById(Long id)throws Exception;
    
    /**
     * @Updates generic keywords by ID.
     *
     * @param genericKeywords The updated generic keywords.
     * @param id              The ID of the generic keywords to be updated.
     * @return The updated generic keywords.
     * @throws Exception if an error occurs during the update process.
     */
    GenericKeywords updateGenericKeywordsById(GenericKeywords genericKeywords, Long id)throws Exception;

    /**
     * @Retrieves generic keywords by ID.
     *
     * @param id The ID of the generic keywords to be retrieved.
     * @return The retrieved generic keywords.
     * @throws Exception if an error occurs during the retrieval process.
     */
    GenericKeywords getGenericKeywordsById(Long id)throws Exception;

    /**
     * @Retrieves all generic keywords.
     *
     * @return A list of all generic keywords.
     */
    List<GenericKeywords> getAllGenericKeywords();
    
    /**
     * Saves a list of GenericKeywords objects in bulk.
     *
     * @param keywords A list of GenericKeywords objects to be saved in bulk.
     * @return A list of GenericKeywords objects that have been successfully saved.
     * @throws NullPointerException if the provided list of keywords is null.
     * @throws DataStorageException if an error occurs while saving the bulk of GenericKeywords objects.
     */
    List<GenericKeywords> saveBulkGenericKeywords(List<GenericKeywords> keywords);

    /**
     * @Retrieves generic keywords based on a given set of keywords.
     *
     * @param keywords A string containing the keywords to search for.
     * @return A GenericKeywords object containing the relevant information based on the provided keywords.
     * @throws Exception If an error occurs during the retrieval process.
     */
    GenericKeywords getByKeywords(String keywords) throws Exception;

    /** 
     * @Retrieves generic keywords based on a given URL.
     *
     * @param url The URL from which to retrieve the keywords.
     * @return A GenericKeywords object containing the relevant information based on the provided URL.
     * @throws Exception If an error occurs during the retrieval process.
     */
    GenericKeywords getByUrl(String url) throws Exception;

    /**
     * @Retrieves generic keywords based on a given file path.
     *
     * @param path The file path from which to retrieve the keywords.
     * @return A GenericKeywords object containing the relevant information based on the provided file path.
     * @throws Exception If an error occurs during the retrieval process.
     */
    GenericKeywords getByPath(String path) throws Exception;

}

