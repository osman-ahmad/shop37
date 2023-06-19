package ibf2022csf.upload.uploadfiletos3.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    
    @Autowired
    private AmazonS3 s3Client;

    @Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

    //, (MultipartFile file, String title, String complain)
    public String upload(MultipartFile file) throws IOException{
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "Kenneth");
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFilename", file.getOriginalFilename());
        // userData.put("title", title);
        // userData.put("complain", complain);
        
    //     ObjectMetadata metadata = new ObjectMetadata();
    //     metadata.setContentType(file.getContentType());
    //     metadata.setContentLength(file.getSize());
    //     metadata.setUserMetadata(userData);
    //     String key = UUID.randomUUID().toString()
    //         .substring(0, 8);
    //     System.out.println(">>>> " + file.getOriginalFilename());
        // StringTokenizer tk = new StringTokenizer(file.getOriginalFilename(), ".");
        // int count = 0;
        // String filenameExt = "";
        // while(tk.hasMoreTokens()){
        //     if(count == 1){
        //         filenameExt = tk.nextToken();
        //         break;
        //     }else{
        //         filenameExt = tk.nextToken();
        //         count++;
        //     }
    //     }
    //     // if(filenameExt.equals("blob"))
    //     filenameExt = filenameExt + ".png";
        
    //     PutObjectRequest putRequest = 
    //         new PutObjectRequest(
    //             bucketName, "myobject%s.%s".formatted(key, filenameExt)
    //                     , file.getInputStream(), metadata);
    //     putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
    //     s3Client.putObject(putRequest);
    //     return "myobject%s.%s".formatted(key, filenameExt);

    // }
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("application/octet-stream");
    metadata.setContentLength(file.getSize());
    metadata.setUserMetadata(userData);

    String key = UUID.randomUUID().toString().substring(0, 8);

    PutObjectRequest putRequest = new PutObjectRequest(
        bucketName,
        "myobject%s.png".formatted(key),
        file.getInputStream(),
        metadata
    );
    putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
    s3Client.putObject(putRequest);

    
    // return "myobject%s.png".formatted(key);
    // String imageUrl = s3Client.getUrl(bucketName, "myobject%s.png".formatted(key)).toString();
    // System.out.println(">>>> " + imageUrl);
    // return imageUrl;
    return getImageUrl(key);
    }   
    
    private String getImageUrl(String key) {
        return String.format("https://%s.sgp1.cdn.digitaloceanspaces.com/myobject%s.png", bucketName, key);
    }
}