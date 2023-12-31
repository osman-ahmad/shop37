package ibf2022csf.upload.uploadfiletos3.controller;

import java.io.IOException;
import java.sql.SQLException;
// import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2022csf.upload.uploadfiletos3.model.Post;
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

    // private static final String BASE64_PREFIX = "data:image/png;base64,";

    @PostMapping(path = "/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, 
                                    produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(
        @RequestPart MultipartFile file,
        @RequestPart String title,
        @RequestPart String complain
        ) throws SQLException {
        // String key = "";
        // String imageUrl = "";
        try {
            // key = s3Svc.upload(file, title, complain);
            // imageUrl = s3Svc.getImageUrl(key);
            // this.ffSvc.upload(file, title, complain);
            String imageUrl = s3Svc.upload(file);
            this.ffSvc.upload(title, complain, imageUrl);
            System.out.println("imageUrl:>>>>>>>>> " + imageUrl);
            JsonObject payload = Json.createObjectBuilder()
            .add("imageKey", imageUrl)
            .build();
            return ResponseEntity.ok(payload.toString());

        } catch (IOException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
        
    }    

    @GetMapping(path="/get-image/{postId}")
    public ResponseEntity<String> retrieveImage(@PathVariable Integer postId){
        Optional<Post> r = this.ffSvc.getPostById(postId);
        Post p = r.get();
        // String encodedString  = Base64.getEncoder().encodeToString(p.getImage());
        // String imageUrl = p.getImageUrl();
        JsonObject payload = Json.createObjectBuilder()
                                // .add("image", BASE64_PREFIX + encodedString)
                                .add("imageUrl", p.getImageUrl())
                                .add("title", p.getTitle())
                                .add("complain", p.getComplain())
                                .build();

        System.out.println("payload: " + payload.toString());
        return ResponseEntity.ok(payload.toString());
    }
    
    
}
