package com.ines.controller.services;

import com.ines.EmailManager;
import com.ines.controller.EmailLoginResult;
import com.ines.model.MailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.*;

public class LoginService extends Service<EmailLoginResult>{

    MailAccount mailAccount;
    EmailManager emailManager;

    public LoginService(MailAccount mailAccount, EmailManager emailManager) {
        this.mailAccount = mailAccount;
        this.emailManager = emailManager;
    }

    private EmailLoginResult login() {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAccount.getEmail(), mailAccount.getPassword());
            }
        };

        try {
            Session session = Session.getInstance(mailAccount.getProperties(), authenticator);
            Store store = session.getStore("imaps");
            store.connect(mailAccount.getProperties().getProperty("incomingHost"),
                    mailAccount.getEmail(),
                    mailAccount.getPassword());
            mailAccount.setStore(store);
            emailManager.addEmailAccount(mailAccount);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_SERVER_ERROR;
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_WRONG_CREDENTIALS;
        } catch (MessagingException e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_OTHER_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return EmailLoginResult.FAILED_OTHER_ERROR;
        }
        return EmailLoginResult.SUCCESS;
    }

    @Override
    protected Task<EmailLoginResult> createTask() {
        return new Task<EmailLoginResult>() {
            @Override
            protected EmailLoginResult call() throws Exception {
                return login();
            }
        };
    }
}
