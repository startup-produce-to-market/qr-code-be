package com.fb.qr.code.storage;

import com.fb.qr.code.error.ErrorConstants;
import com.fb.qr.code.error.StoreQrFileException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ConditionalOnProperty(value = "qr.code.file.storage", havingValue = "google")
@Component
public class GoogleDriveQrFileStorage implements QrCodeFileStorage {

    private Drive googleDriveClient;

    @PostConstruct
    private void createGoogleDriveInstance() {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault().createScoped(List.of(DriveScopes.DRIVE_FILE));
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            // Build a new authorized API client service.
            googleDriveClient = new Drive.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance(), requestInitializer).setApplicationName("Drive samples").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeFile(byte[] fileContents, Map<String, String> properties) {
        try {
            InputStream inputStream = new ByteArrayInputStream(fileContents);
            File file = File.createTempFile(properties.get("id"), properties.get("extension"));
            FileUtils.copyInputStreamToFile(inputStream, file);
            com.google.api.services.drive.model.File drivefile = new com.google.api.services.drive.model.File();
            drivefile.setName(properties.get("id") + "." + properties.get("extension"));
            FileContent fileContent = new FileContent(properties.get("content-type"), file);
            com.google.api.services.drive.model.File createdDriveFile = googleDriveClient.files().create(drivefile, fileContent).execute();
        } catch (Exception e) {
            log.error("Error storing qr file : {}", properties.get("id") + "." + properties.get("extension"), e);
            Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("qrFileName", properties.get("id") + "." + properties.get("extension"));
            throw StoreQrFileException.builder().message(ErrorConstants.STORE_QR_FILE_ERROR_MSG)
                    .errorCode(ErrorConstants.STORE_QR_FILE_ERROR_CODE)
                    .errorAttributes(errorAttributes).build();
        }
    }

    @Override
    public String generatePublicUrl(String filePath) {
        return "";
    }

    @Override
    public ByteArrayOutputStream readFile(String fileName) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            googleDriveClient.files().get(fileName).executeMediaAndDownloadTo(outputStream);
            return outputStream;
        } catch (IOException e) {
            log.error("Error retrieving the qr file : {}", fileName, e);
            Map<String, Object> errorAttributes = new HashMap<>();
            errorAttributes.put("qrFileName", fileName);
            throw StoreQrFileException.builder().message(ErrorConstants.RETRIEVE_QR_FILE_ERROR_MSG)
                    .errorCode(ErrorConstants.RETRIEVE_QR_FILE_ERROR_CODE)
                    .errorAttributes(errorAttributes).build();
        }
    }
}
