package dev.ArtBlast.Services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class MediaService {
    
    @Autowired
    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) throws FileUploadException{
        //resolve filename and content type
        String original = Optional.ofNullable(file.getOriginalFilename())
            .orElseThrow(() -> new UnsupportedMediaTypeStatusException("Filename is missing."))
            .toLowerCase();
        String contentType = Optional.ofNullable(file.getContentType())
            .orElseThrow(() -> new UnsupportedMediaTypeStatusException("Content-Type is unknown."));

        //Build object key
        String key = String.format("%s/%s-%s", "images", UUID.randomUUID(), original);

        //Prepare S3 Put request
        PutObjectRequest req = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build();

        //Perform upload
        try {
            s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new FileUploadException("File Upload to Cloudflare R2 failed", e);
        }
        return key;
    }

    private String getFileExtension(String filename){
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length()-1){
            throw new IllegalArgumentException("Invalid file extension in filename: " + filename);
        }
        return filename.substring(idx+1);
    }
}
