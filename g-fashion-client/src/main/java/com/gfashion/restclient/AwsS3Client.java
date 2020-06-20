package com.gfashion.restclient;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    public String uploadFile(String imagePath, String imageName, MultipartFile multipartFile) throws IOException {
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

        String fileUrl = cdnUrl + imagePath + "large/" + originalFilename;
        System.out.println(fileUrl);
        uploadFileToS3bucket(imageKey, file);
        Files.delete(path);

        return originalFilename;
    }

    public String uploadFile(String imagePath, MultipartFile multipartFile) throws IOException {
        String baseName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
        return uploadFile(imagePath, baseName, multipartFile);
    }

    public String uploadFile(String imagePath, String imageName, String urlStr) throws IOException {
        URL url = new URL(urlStr);
        String fileName = url.getFile();
        File file = new File(saveLocation, fileName);
        FileUtils.copyURLToFile(url, file);


        String extension = FilenameUtils.getExtension(fileName);
        String originalFilename = imageName + "." + extension;
        String imageKey = imagePath + originalFilename;

        String fileUrl = cdnUrl + imagePath + "large/" + originalFilename;
        System.out.println(fileUrl);
        uploadFileToS3bucket(imageKey, file);
        file.deleteOnExit();

        return originalFilename;
    }

    public String uploadFile(String imagePath, String urlStr) throws IOException {
        String baseName = FilenameUtils.getBaseName(urlStr);
        return uploadFile(imagePath, baseName, urlStr);
    }

    private void uploadFileToS3bucket(String fileName, File file) {
        s3Client.putObject(bucketName, fileName, file);
    }
}
