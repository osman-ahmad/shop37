package ibf2022csf.upload.uploadfiletos3.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022csf.upload.uploadfiletos3.services.FileUploadService;
import ibf2022csf.upload.uploadfiletos3.services.S3Service;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
public class FileUploadController {
    @Autowired
    private S3Service s3Svc;

    @Autowired
    private FileUploadService ffSvc;


    @PostMapping(path = "/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
                                    produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(
        @RequestPart MultipartFile file,
        @RequestPart String title,
        @RequestPart String complain
        ) throws SQLException {
        String key = "";
        try {
            key = s3Svc.upload(file, title, complain);
            this.ffSvc.upload(file, title, complain);

        } catch (IOException | SQLException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        JsonObject payload = Json.createObjectBuilder()
            .add("imageKey", key)
            .build();
        return ResponseEntity.ok(payload.toString());
    }    
    
    
}
