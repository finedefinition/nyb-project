package com.norwayyachtbrockers.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsCognitoConfig {
    @Value("${aws.cognito.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.cognito.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.cognito.region}")
    private String region;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
    }

    @Bean
    public AWSCognitoIdentityProvider cognitoClient(AWSCredentialsProvider awsCredentialsProvider) {
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(Regions.fromName(region))
                .build();
    }
}
