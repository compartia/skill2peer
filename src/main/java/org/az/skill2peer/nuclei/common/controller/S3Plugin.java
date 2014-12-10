package org.az.skill2peer.nuclei.common.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

//@Component
public class S3Plugin {
    public static AmazonS3 amazonS3;

    public static final String AWS_ACCESS_KEY = "aws.access.key";

    public static final String AWS_S3_BUCKET = "aws.s3.bucket";

    public static final String AWS_SECRET_KEY = "aws.secret.key";

    public static String s3Bucket;

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Plugin.class);

    @Resource
    private Environment env;

    //    @Override
    public void assertEnabled() {

        env.getRequiredProperty(AWS_ACCESS_KEY);
        env.getRequiredProperty(AWS_SECRET_KEY);
        env.getRequiredProperty(AWS_S3_BUCKET);
        //        return (application.configuration().keys().contains(AWS_ACCESS_KEY) &&
        //                application.configuration().keys().contains(AWS_SECRET_KEY) && application
        //                .configuration()
        //                .keys()
        //                .contains(AWS_S3_BUCKET));
    }

    @PostConstruct
    public void onStart() {
        final String accessKey = env.getRequiredProperty(AWS_ACCESS_KEY);
        final String secretKey = env.getRequiredProperty(AWS_SECRET_KEY);
        s3Bucket = env.getRequiredProperty(AWS_S3_BUCKET);

        if ((accessKey != null) && (secretKey != null)) {
            final AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            amazonS3 = new AmazonS3Client(awsCredentials);

            //            if (!amazonS3.doesBucketExist(s3Bucket)) {
            //                throw new IllegalArgumentException("Bucket " + s3Bucket + " does not exist");
            //            }
            //amazonS3.createBucket(s3Bucket);
            LOGGER.info("Using S3 Bucket: " + s3Bucket);
        }
    }

}
