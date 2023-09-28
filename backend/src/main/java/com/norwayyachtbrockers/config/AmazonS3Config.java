//package com.norwayyachtbrockers.config;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//
//public class AmazonS3Config {
//    @Value("${s3.access.key.id}")
//    private String accessKeyId;
//
//    @Value("${s3.access.key.secret}")
//    private String accessKeySecret;
//
//    @Value("${s3.region.name}")
//    private String s3RegionName;
//
//    @Bean
//    public AmazonS3 getAmazonS3Client() {
//        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId,
//                accessKeySecret);
//
//        return AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(s3RegionName)
//                .build();
//    }
//}
