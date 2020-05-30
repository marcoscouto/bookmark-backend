package br.com.bookmark.utils;

import br.com.bookmark.exception.FileException;
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
public class UploadImageS3 {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket.covers}")
    private String bucketCovers;

    @Value("${aws.s3.bucket.profiles}")
    private String bucketProfiles;

    public URI uploadFile(MultipartFile multipartFile, String imageName, String folder) {
        try {
            if (multipartFile.getSize() > 2000000)
                throw new FileException("Max size file is 2MB, this file have " + (multipartFile.getSize() / 1000000) + "MB");
            InputStream is = multipartFile.getInputStream();
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(multipartFile.getContentType());
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

}
