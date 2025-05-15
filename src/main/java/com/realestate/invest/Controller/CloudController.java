package com.realestate.invest.Controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Service.CloudService;

/**
 * @This class, {@code Cloud Controller}, implements the {@code Cloud Controller} interface,
 * providing methods for uploading images to Cloud.
 *
 * @Author: Abhishek Srivastav
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/cloud")
public class CloudController 
{
    
    @Autowired
    private CloudService ciService;

    Logger logger = LoggerFactory.getLogger(CloudController.class);

    /**
     * @Uploads a file to a specific folder in AWS S3.
     *
     * @param folderName The name of the folder in S3.
     * @param file The file to upload.
     * @return A success response if the file is uploaded successfully.
     */
    @PostMapping("/s3/upload/folder/docs")
    public ResponseEntity<?> uploadFileOnAwsDynamic(@RequestParam String folderName, @RequestParam MultipartFile file) 
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.ciService.uploadFileOnAwsDynamic(folderName, file));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(IOException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Uploads a file to a specific folder in AWS S3.
     *
     * @param folderName The name of the folder in S3.
     * @param file The file to upload.
     * @return A success response if the file is uploaded successfully.
     */
    @PostMapping("/s3/upload/project/property/images")
    public ResponseEntity<?> uploadFileOnAwsForProjectAndProperty(@RequestParam String folderName, @RequestParam MultipartFile file) 
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.ciService.uploadFileOnAwsForProjectAndProperty(folderName, file));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(IOException e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    
    /**
     * @Deletes a file from AWS S3 based on the specified file URL.
     * 
     * @param fileUrl The URL of the file to be deleted. Must be a valid S3 URL.
     * 
     * @throws IllegalArgumentException If {@code fileUrl} is null or empty.
     * @throws AmazonServiceException If AWS S3 responds with an error during deletion.
     * @throws AmazonClientException If a client-side error occurs while making the request.
     */
    @DeleteMapping("/s3/delete/folder/docs")
    public ResponseEntity<?> deleteFileFromAws(@RequestParam String file) 
    {
        try
        {
            this.ciService.deleteFileFromAws(file);
            SuccessResponse successResponse = new SuccessResponse("File deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
