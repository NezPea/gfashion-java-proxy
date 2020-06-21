package com.gfashion.api;

import com.amazonaws.AmazonClientException;
import com.gfashion.restclient.AwsS3Client;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * REST controller for G-Fashion media manage api
 */
@RestController
@RequestMapping(path = "/gfashion/v1", produces = MediaType.ALL_VALUE)
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class GfashionMediaResource {

    @Autowired
    private final AwsS3Client awsClient;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = awsClient.uploadFile(file, "products/images/");
            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
        } catch (AmazonClientException | IOException e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
