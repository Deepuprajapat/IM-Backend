package com.realestate.invest.ServiceImpl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.MalformedURLException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.realestate.invest.Service.CloudService;
import com.realestate.invest.Utils.SecretUtils;
import java.net.URL;

@Service
public class CloudServiceImpl implements CloudService
{
    
    @Autowired 
    private AmazonS3 amazonS3;


    /**
     * @Uploads a file to a specified folder on AWS.
     * 
     * @param folderName The name of the folder in AWS S3 where the file will be uploaded.
     * @param file The {@link MultipartFile} representing the file to upload.
     * @return A {@link String} indicating the result or URL of the uploaded file.
     * @throws IOException If an error occurs while processing or uploading the file.
     */
    @Override
    public String uploadFileOnAwsDynamic(String folderName, MultipartFile file) throws IOException 
    {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) 
        {
            fileExtension = originalFileName.substring(dotIndex);
            originalFileName = originalFileName.substring(0, dotIndex);
        }

        String fileName = folderName + "/" + originalFileName + fileExtension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectResult result = amazonS3.putObject(SecretUtils.AWSBUCKET, fileName, file.getInputStream(), metadata);
        System.out.println("AWS Running" + result.toString());
        URL url = amazonS3.getUrl(SecretUtils.AWSBUCKET, fileName);
        String cleanurl = url.toString();
        String cdnUrl = cleanurl.replace("myimwebsite.s3.ap-south-1.amazonaws.com", "image.investmango.com");
        return cdnUrl;
    }


    /**
     * @Uploads a file to a specified folder on AWS.
     * 
     * @param folderName The name of the folder in AWS S3 where the file will be uploaded.
     * @param file The {@link MultipartFile} representing the file to upload.
     * @return A {@link String} indicating the result or URL of the uploaded file.
     * @throws IOException If an error occurs while processing or uploading the file.
     */
    @Override
    public String uploadFileOnAwsForProjectAndProperty(String folderName, MultipartFile file) throws IOException 
    {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) 
        {
            fileExtension = originalFileName.substring(dotIndex);
            originalFileName = originalFileName.substring(0, dotIndex);
        }

        String fileName = folderName + "/" + originalFileName + fileExtension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        PutObjectResult result = amazonS3.putObject(SecretUtils.AWSBUCKET, fileName, file.getInputStream(), metadata);
        System.out.println("AWS Running" + result.toString());
        URL url = amazonS3.getUrl(SecretUtils.AWSBUCKET, fileName);
        String cleanurl = url.toString();
        String cdnUrl = cleanurl.replace("myimwebsite.s3.ap-south-1.amazonaws.com", "image.investmango.com");
        return cdnUrl;
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
    public void deleteFileFromAws(String fileUrl) 
    {
        String filePath = extractFilePathFromUrl(fileUrl);

        if (filePath != null) 
        {
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(SecretUtils.AWSBUCKET, filePath);
            amazonS3.deleteObject(deleteObjectRequest);
            System.out.println("File deleted from AWS S3: " + fileUrl);
        } 
        else 
        {
            System.out.println("Invalid URL provided: " + fileUrl);
        }
    }

    private String extractFilePathFromUrl(String fileUrl) 
    {
        try 
        {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            return path.startsWith("/") ? path.substring(1) : path;
        } 
        catch (MalformedURLException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    
}
