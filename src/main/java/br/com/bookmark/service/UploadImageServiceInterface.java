package br.com.bookmark.service;

import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

public interface UploadImageServiceInterface {

    URI uploadFile(MultipartFile file, String imageName, String folder);

}
