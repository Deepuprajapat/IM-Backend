package com.realestate.invest.Service;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @This interface defines the contract for managing image uploads to Cloud within the system.
 * It provides a method for uploading an image file and returning a response as a Map.
 *
 * @param <T> The type of data to be returned in the Map response.
 * @author Abhishek Srivastav
 */
@Component
public interface CloudService 
{
    

    /**
     * @Uploads a file to a specified folder on AWS.
     * 
     * @param folderName The name of the folder in AWS S3 where the file will be uploaded.
     * @param file The {@link MultipartFile} representing the file to upload.
     * @return A {@link String} indicating the result or URL of the uploaded file.
     * @throws IOException If an error occurs while processing or uploading the file.
     */
    String uploadFileOnAwsDynamic(String folderName, MultipartFile file) throws IOException;

    /**
     * @Uploads a file to a specified folder on AWS.
     * 
     * @param folderName The name of the folder in AWS S3 where the file will be uploaded.
     * @param file The {@link MultipartFile} representing the file to upload.
     * @return A {@link String} indicating the result or URL of the uploaded file.
     * @throws IOException If an error occurs while processing or uploading the file.
     */
    String uploadFileOnAwsForProjectAndProperty(String folderName, MultipartFile file) throws IOException;

    /**
     * @Deletes a file from AWS S3 based on the specified file URL.
     * 
     * @param fileUrl The URL of the file to be deleted. Must be a valid S3 URL.
     * 
     * @throws IllegalArgumentException If {@code fileUrl} is null or empty.
     * @throws AmazonServiceException If AWS S3 responds with an error during deletion.
     * @throws AmazonClientException If a client-side error occurs while making the request.
     */
    void deleteFileFromAws(String fileUrl);
}
