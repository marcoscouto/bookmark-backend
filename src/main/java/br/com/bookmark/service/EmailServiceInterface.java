package br.com.bookmark.service;

public interface EmailServiceInterface {

    void sendAccountConfirmationEmail(String email, String name);

    void sendChangePasswordEmail(String email, String name, String password);
}
