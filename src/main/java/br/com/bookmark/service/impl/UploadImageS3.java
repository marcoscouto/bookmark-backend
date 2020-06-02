package br.com.bookmark.service.impl;

import br.com.bookmark.exception.FileException;
import br.com.bookmark.service.UploadImageServiceInterface;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UploadImageS3 implements UploadImageServiceInterface {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket.covers}")
    private String bucketCovers;

    @Value("${aws.s3.bucket.profiles}")
    private String bucketProfiles;

    private final Integer IMAGE_MAX_SIZE = 2000000;

    @Override
    public URI uploadFile(MultipartFile file, String imageName, String folder) {
        try {
            verifyMultipartFileSize(file);
            InputStream is = file.getInputStream();
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(file.getContentType());
            s3Client.putObject(getBucketFolder(folder), imageName, is, meta);
            return s3Client.getUrl(getBucketFolder(folder), imageName).toURI();
        } catch (IOException | URISyntaxException e) {
            throw new FileException("Update file error");
        }
    }

    private String getBucketFolder(String type) {
        if (type == "book")
            return bucketCovers;
        if (type == "profile")
            return bucketProfiles;
        throw new FileException("Bucket Folder not founded");
    }

    private void verifyMultipartFileSize(MultipartFile file){
        if (file.getSize() > IMAGE_MAX_SIZE)
            throw new FileException("Max size file is 2MB, this file have " + (file.getSize() / 1000000) + "MB");
    }

}
