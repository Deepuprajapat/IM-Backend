package com.realestate.invest.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.realestate.invest.Utils.SecretUtils;

/**
 * @Configuration class for setting up AWS S3 client.
 * @Author: Abhishek Srivastav
 */
@Configuration
public class AwsS3Config 
{

    /**
     * @Configures and provides an {@link AmazonS3} client bean.
     *
     * @return an {@link AmazonS3} client instance configured with AWS credentials and region.
     */
    @Bean
    AmazonS3 client()
    {
        AWSCredentials credentials = new BasicAWSCredentials(SecretUtils.AWSACCESSKEY, SecretUtils.AWSSECRETKEY);
        
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(SecretUtils.AWSREGION).build();

        return amazonS3;
    }
    
}
