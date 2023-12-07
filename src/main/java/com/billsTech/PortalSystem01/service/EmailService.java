package com.billsTech.PortalSystem01.service;


import com.billsTech.PortalSystem01.util.EmailUtil;;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Set;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
import static jakarta.mail.Message.RecipientType.TO;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final Gmail service;
//    private final Complaints complaints;
public EmailService( ) throws GeneralSecurityException, IOException {
    NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
            .setApplicationName("Mail Test")
            .build();
}

    @Async
    public void sendSimpleEmailMessage(String firstName,String lastName, String emailTo, String matricNumber){
        try {
//            ComplaintRequest req = new ComplaintRequest();
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("bosingwa100@gmail.com"));
            mimeMessage.addRecipient(TO, new InternetAddress(emailTo));
            mimeMessage.setSubject("ISSUED COMPLAINT !");
            mimeMessage.setText(EmailUtil.getEmailMessage(firstName,lastName,"http://localhost:8081",matricNumber));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            mimeMessage.writeTo(buffer);
            byte[] rawMessageBytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
            Message msg = new Message();
            msg.setRaw(encodedEmail);
            sendMessages(msg);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }



    Message sendMessages(Message msg) {
        try {
            msg = service.users().messages().send("bosingwa100@gmail.com", msg).execute();
            return msg;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        InputStream in = EmailService.class.getResourceAsStream("/client_secret_952014320176-bo5m6mf3bj6g0ar4hpb592vc3gjht8jd.apps.googleusercontent.com.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}
