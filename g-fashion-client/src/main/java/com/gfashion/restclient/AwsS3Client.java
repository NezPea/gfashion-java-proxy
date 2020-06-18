package com.gfashion.restclient;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AwsS3Client {

    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    @Value("${aws.s3.region}")
    private String region;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.s3.cdnUrl}")
    private String cdnUrl;
    @Value("${aws.s3.saveLocation}")
    private String saveLocation;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initAmazon() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(String imagePath, String imageName, MultipartFile multipartFile) throws AmazonClientException, IOException {
        String fileUrl;
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        String originalFilename = imageName + "." + extension;
        Path path = Paths.get(saveLocation + originalFilename);
        Path parentPath = path.getParent();
        if (!Files.exists(parentPath)) {
            Files.createDirectories(parentPath);
        }
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        File file = path.toFile();
        String imageKey = imagePath + originalFilename;

        fileUrl = cdnUrl + imagePath + "thumbnail/" + originalFilename;
        uploadFileToS3bucket(imageKey, file);
        Files.delete(path);

        return fileUrl;
    }

    public String uploadFile(String imagePath, MultipartFile multipartFile) throws AmazonClientException, IOException {
        String baseName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
        return uploadFile(imagePath, baseName, multipartFile);
    }

    private void uploadFileToS3bucket(String fileName, File file) {
        s3Client.putObject(bucketName, fileName, file);
    }
}
