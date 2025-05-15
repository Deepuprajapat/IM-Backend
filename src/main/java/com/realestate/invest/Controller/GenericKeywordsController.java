package com.realestate.invest.Controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.GenericKeywords;
import com.realestate.invest.Service.GenericKeywordsService;

/**
 * @This controller class handles HTTP requests related to generic keywords.
 *
 * @author Abhishek Srivastav
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/generic/keywords")
public class GenericKeywordsController 
{

    @Autowired
    private GenericKeywordsService genericKeywordsService;

    @Autowired
    private RestTemplate restTemplate;

    
    Logger logger = LoggerFactory.getLogger(GenericKeywordsController.class);

    
    /**
     * @Saves new generic keywords.
     *
     * @param genericKeywords The generic keywords to be saved.
     * @return ResponseEntity containing saved generic keywords or error response.
     */
    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewGenericKeywords(@RequestBody GenericKeywords genericKeywords)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.saveNewGenericKeywords(genericKeywords);
            return new ResponseEntity<>(gKeywords, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Deletes generic keywords by ID.
     *
     * @param id The ID of the generic keywords to be deleted.
     * @return ResponseEntity indicating success or error response.
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/{id}")
    public ResponseEntity<?> deleteGenericKeywordsById(@PathVariable Long id)
    {
        try
        {
            this.genericKeywordsService.deleteGenericKeywordsById(id);
            SuccessResponse successResponse = new SuccessResponse("Generic keyword deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * @Updates generic keywords by ID.
     *
     * @param genericKeywords The updated generic keywords.
     * @param id              The ID of the generic keywords to be updated.
     * @return ResponseEntity containing updated generic keywords or error response.
     */
    @PatchMapping("/update/by/{id}")
    public ResponseEntity<?> updateGenericKeywordsById(@RequestBody GenericKeywords genericKeywords, @PathVariable Long id)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.updateGenericKeywordsById(genericKeywords, id);
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves generic keywords by ID.
     *
     * @param id The ID of the generic keywords to be retrieved.
     * @return ResponseEntity containing retrieved generic keywords or error response.
     */
    @RequestMapping(value = "/get/by/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getGenericKeywordsById(@PathVariable Long id)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.getGenericKeywordsById(id);
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves all generic keywords.
     *
     * @return ResponseEntity containing list of generic keywords or error response.
     */
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllGenericKeywords()
    {
        try
        {
            List<GenericKeywords> gKeywords = this.genericKeywordsService.getAllGenericKeywords();
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles HTTP POST requests for saving a list of GenericKeywords object   s in bulk.
     *
     * @param keywords A list of GenericKeywords objects received in the request body.
     * @return A ResponseEntity containing either the saved GenericKeywords objects and a HTTP status
     *  of 201 (Created) if the saving operation is successful, or an ErrorResponse containing
     *  details of the exception and a HTTP status of 400 (Bad Request) if an error occurs.
     */
    @PostMapping("/bulk-generic-keywords")
    public ResponseEntity<?> saveBulkGenericKeywords(@RequestBody List<GenericKeywords> keywords) 
    {
        try
        {
            List<GenericKeywords> savedKeywords = genericKeywordsService.saveBulkGenericKeywords(keywords);
            return new ResponseEntity<>(savedKeywords, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves generic keywords by Keywords.
     *
     * @param Keywords The Keywords of the generic keywords to be retrieved.
     * @return ResponseEntity containing retrieved generic keywords or error response.
     */
    @RequestMapping(value = "/get/by/keywords/{keywords}", method = RequestMethod.GET)
    public ResponseEntity<?> getByKeywords(@PathVariable String keywords)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.getByKeywords(keywords);
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves generic keywords by Url.
     *
     * @param Url The url of the generic keywords to be retrieved.
     * @return ResponseEntity containing retrieved generic keywords or error response.
     */
    // @RequestMapping(value = "/get/by/url/{url}", method = RequestMethod.GET)
    public ResponseEntity<?> getByUrl(@PathVariable String url)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.getByUrl(url);
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves generic keywords by Path.
     *
     * @param Path The path of the generic keywords to be retrieved.
     * @return ResponseEntity containing retrieved generic keywords or error response.
     */
    @RequestMapping(value = "/get/by/path/{path}", method = RequestMethod.GET)
    public ResponseEntity<?> getByPath(@PathVariable String path)
    {
        try
        {
            GenericKeywords gKeywords = this.genericKeywordsService.getByPath(path);
            return new ResponseEntity<>(gKeywords, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    // @GetMapping("/get/by/path/data/{path}")
    // public Object getByPathData(@PathVariable String path) throws Exception 
    // {
    //     GenericKeywords genericKeywords = this.genericKeywordsService.getByPath(path);
    //     if (genericKeywords == null) 
    //     {
    //         throw new Exception("No match found");
    //     }

    //     String queryParams = genericKeywords.getUrl();
    //     if (queryParams.startsWith("allProjects?")) 
    //     {
    //         queryParams = queryParams.replace("allProjects?", "");
    //         queryParams = queryParams.replace("search", "name");
    //         queryParams = queryParams.replace("locationId", "cityId");
    //     }

    //     String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
    //     String apiUrl = baseUrl + "/project/get/all?" + queryParams + "&size=100";
    //     System.out.println("apiurl : " + apiUrl);
    //     ResponseEntity<Object> response = restTemplate.getForEntity(apiUrl, Object.class);
    //     return response.getBody();
    // }

    @GetMapping("/get/by/path/data/{path}")
    public Object getByPathData(@PathVariable String path) throws Exception 
    {
        GenericKeywords genericKeywords = this.genericKeywordsService.getByPath(path);
        if (genericKeywords == null) 
        {
            throw new Exception("No match found for path: " + path);
        }

        String queryParams = genericKeywords.getUrl();
        if (queryParams.startsWith("allProjects?")) 
        {
            queryParams = queryParams.replace("allProjects?", "")
            .replace("search", "name")
            .replace("locationId", "cityId");
        }

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        String apiUrl = baseUrl + "/project/get/all?" + queryParams + "&size=100";

        System.out.println("Calling API URL: " + apiUrl);

        try 
        {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody(), Object.class);
        } 
        catch (JsonProcessingException e) 
        {
            throw new Exception("Invalid JSON response received: " + e.getMessage());
        } 
        catch (Exception e) 
        {
            throw new Exception("Error while calling API: " + e.getMessage());
        }
    }

    
}
