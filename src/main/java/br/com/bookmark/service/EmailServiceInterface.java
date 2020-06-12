package br.com.bookmark.service;

import java.util.UUID;

public interface EmailServiceInterface {

    void sendAccountConfirmationEmail(String email, String name, UUID id);

    void sendChangePasswordEmail(String email, String name, String password);
}
