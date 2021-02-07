package com.ines.model;

import javax.mail.Store;
import java.util.Properties;

public class MailAccount {

    private String email;
    private String password;

    private Properties properties;

    public MailAccount(String email, String password) {
        this.email = email;
        this.password = password;
        properties = new Properties();
        setGoogleProperties();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private void setGoogleProperties(){
        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps");

        properties.put("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.host", "smtp.gmail.com");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");
    }


    private Store store;


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
