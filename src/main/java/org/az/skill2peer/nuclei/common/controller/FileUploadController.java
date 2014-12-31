package org.az.skill2peer.nuclei.common.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.az.skill2peer.nuclei.common.Urls;
import org.az.skill2peer.nuclei.common.model.S3File;
import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private static void log(final MultipartFile file) throws IOException {
        LOGGER.info("File Length:\t" + file.getBytes().length);
        LOGGER.info("File Type:\t" + file.getContentType());
        LOGGER.info("File Name:\t" + file.getOriginalFilename());
    }

    @RequestMapping(value = Urls.FILE_UPLOAD, method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(
            @RequestParam("file") final MultipartFile file) {

        final String name = file.getOriginalFilename();
        final Integer userId = SecurityUtil.getCurrentUser().getId();

        if (!file.isEmpty()) {
            try {
                ////
                log(file);
                ////

                final byte[] bytes = file.getBytes();

                final File uploadFilePart = new File(name + "-uploaded");
                final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFilePart));
                stream.write(bytes);
                stream.close();

                //XXX: upload only when course is saved

                return name;
            } catch (final Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @RequestMapping(value = "/uploadXX", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(final MultipartHttpServletRequest request)
            throws Exception {
        LOGGER.info("handleFileUpload");
        final Iterator<String> itrator = request.getFileNames();
        final MultipartFile multiFile = request.getFile(itrator.next());
        try {
            // just to show that we have actually received the file
            LOGGER.info("File Length:" + multiFile.getBytes().length);
            LOGGER.info("File Type:" + multiFile.getContentType());
            final String fileName = multiFile.getOriginalFilename();
            LOGGER.info("File Name:" + fileName);
            final String path = request.getServletContext().getRealPath("/");

            //making directories for our required path.
            final byte[] bytes = multiFile.getBytes();
            final File directory = new File(path + "/uploads");
            directory.mkdirs();
            // saving the file
            //            final File file = new File(directory.getAbsolutePath()
            //                    + System.getProperty("file.separator")
            //                    + picture.getName());
            //            final BufferedOutputStream stream = new BufferedOutputStream(
            //                    new FileOutputStream(file));
            //            stream.write(bytes);
            //            stream.close();
        } catch (final Exception e) {
            e.printStackTrace();
            throw new Exception("Error while loading the file");
        }
        return "File Uploaded successfully.";
    }

    @RequestMapping(value = Urls.FILE_UPLOAD, method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

//    public void uploadToS3(final File uploadFilePart) {
//        final S3File s3File = new S3File();
//        s3File.name = uploadFilePart.getName();
//        s3File.file = uploadFilePart;
//        s3File.save();
//        uploadFilePart.delete();
//    }
}
