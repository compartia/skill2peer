package org.az.skill2peer.nuclei.common.model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.az.skill2peer.nuclei.common.controller.S3Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
//import play.Logger;
//import play.db.ebean.Model;
//import plugins.S3Plugin;

//@Entity
public class S3File /*extends Model */{
    private static final Logger LOGGER = LoggerFactory.getLogger(S3File.class);

    @Transient
    public File file;

    @Id
    public UUID id;

    public String name;

    private String bucket;

    // @Override
    public void delete() {
        if (S3Plugin.amazonS3 == null) {
            LOGGER.error("Could not delete because amazonS3 was null");
            throw new RuntimeException("Could not delete");
        }
        else {
            S3Plugin.amazonS3.deleteObject(bucket, getActualFileName());
            //            super.delete();
        }
    }

    public URL getUrl() throws MalformedURLException {
        return new URL("https://s3.amazonaws.com/" + bucket + "/" + getActualFileName());
    }

    // @Override
    public void save() {
        if (S3Plugin.amazonS3 == null) {
            LOGGER.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        }
        else {
            this.bucket = S3Plugin.s3Bucket;

            //            super.save(); // assigns an id

            final PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
            final PutObjectResult putObjectResult = S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
            //            LOGGER.error(putObject.get)
        }
    }

    private String getActualFileName() {
        return id + "/" + name;
    }

}
