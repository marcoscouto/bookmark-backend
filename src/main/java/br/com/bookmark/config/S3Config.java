package br.com.bookmark.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${aws.access_key_id}")
    private String key;

    @Value("${aws.secret_access_key}")
    private String accessKey;

    @Value("${aws.s3.region}")
    private String region;

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCred = new BasicAWSCredentials(key, accessKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCred))
                .build();

        return s3Client;
    }
}
