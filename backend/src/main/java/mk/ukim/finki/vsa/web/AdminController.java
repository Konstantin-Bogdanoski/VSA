package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.IncorrectVideoUploadException;
import mk.ukim.finki.vsa.exception.UnableToDeleteVideoException;
import mk.ukim.finki.vsa.exception.VideoAlreadyExistsException;
import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private VideoService videoService;
    /**
     * Logger which will help us view the status of our backend service
     */
    private Logger logger = Logger.getLogger(AdminController.class.getName());
    /**
     * Static string which represents the folder where we will store the videos
     * Update it for your machine
     */
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    public AdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Method that retrieves all of the videos in the database
     */
    @GetMapping
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public List<Video> get() {
        return videoService.findAll();
    }

    /**
     * Method used for uploading the video
     */
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Video upload(@RequestParam(required = false) MultipartFile file,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "description") String description,
                        @RequestParam(value = "imgLink") String imgLink,
                        @RequestParam(value = "hq", required = false) boolean highQuality,
                        @RequestParam(value = "lq", required = false) boolean lowQuality) {
        if (file.isEmpty() || name.isEmpty())
            throw new IncorrectVideoUploadException();
        logger.info("[UPLOAD] Uploading new video");
        Video newVid = new Video();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setImgLink(imgLink);
            if (highQuality)
                newVid.setHQ(true);
            if (lowQuality)
                newVid.setLQ(true);
            newVid.setMQ(true);
            newVid.setDescription(description);
            String parentDir = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().length() - 4).replace(" ", "_");
            newVid.setFileName(parentDir + "/" + file.getOriginalFilename().replace(" ", "_"));
            // Get the video and save it somewhere
            byte[] bytes = file.getBytes();
            File check = new File(UPLOADED_FOLDER + parentDir);
            if (!check.exists()) {
                if (!check.mkdirs())
                    throw new IOException();
                check = new File(UPLOADED_FOLDER + newVid.getFileName());
                if (!check.createNewFile())
                    throw new IOException();
                Path path = Paths.get(UPLOADED_FOLDER + newVid.getFileName());
                Files.write(path, bytes);
                logger.info("[UPLOAD] " + newVid.getFileName() + " has been uploaded, trying to encode video");
                try {
                    formatVideo(newVid.getFileName(), newVid.getKey(), highQuality, lowQuality);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("[UPLOAD] Video encoded");
                // Save info about video in DB
                newVid.setFileName(newVid.getFileName() + ".m3u8");
                videoService.save(newVid);
            } else
                throw new VideoAlreadyExistsException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("[UPLOAD] " + newVid.getFileName() + " has been successfully uploaded");
        return newVid;
    }

    /**
     * Method used to update information about the specific video
     * NOTE: We will not support remoove+reupload of the video file (.mp4)
     * because the same process needs to be done as uploading a new video
     */
    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video patch(@PathVariable("id") Long vID,
                       @RequestParam("name") String name,
                       @RequestParam("description") String description,
                       @RequestParam("imgLink") String imgLink,
                       @RequestParam(value = "quailties", required = false) List<String> qualities) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        Video newVid = videoService.findOne(vID).get();
        newVid.setName(name);
        newVid.setKey();
        newVid.setDescription(description);
        newVid.setImgLink(imgLink);
        videoService.save(newVid);
        return newVid;
    }


    /**
     * Method used to remove the video from the file system and from the database
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Long delete(@PathVariable("id") Long vID) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        logger.info("[REMOVE] Trying to remove video");
        Video video = videoService.findOne(vID).get();
        try {
            if (deleteDirectory(new File(UPLOADED_FOLDER + video.getFileName().split("/")[0])))
                videoService.delete(video);
            else
                throw new UnableToDeleteVideoException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("[REMOVE] " + video.getFileName() + " has been removed");
        return video.getId();
    }


    /**
     * Function used to recursively delete a directory containing the video
     */
    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    /**
     * Function used to format the video
     *
     * @param path = path of the video (video/video.mp4)
     * @param key  = encryption key of the video
     * @param hq   = is the video HQ (1920x1080)
     * @param lq   = is the video LQ (800x600)
     *             Default formatting is the medium quality (1280x720)
     */
    private void formatVideo(String path, String key, boolean hq, boolean lq) throws IOException, InterruptedException {
        String loc = UPLOADED_FOLDER + path.split("/")[0];
        String fileName = path.split("/")[1];
        // 0 - Medium Quality, 1 - Medium + Low, 2 - Medium + High, 3 - All
        int quality = 0;
        if (lq && hq)
            quality = 3;
        else if (lq)
            quality = 1;
        else if (hq)
            quality = 2;

        /*The script formatVideo.sh takes 4 arguements, Location, Quality, FileName and Key
         Location: Parent folder of video
              if video.path == /home/konstantin/Videos/video/video.mp4
                  location = /home/konstantin/Videos/video
                  fileName = video.mp4 */

        String[] cmd = {UPLOADED_FOLDER + "formatVideo.sh", loc, quality + "", fileName, key};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.inheritIO();
        Process p = pb.start();
        logger.info("[SCRIPT] Waiting for script");
        p.waitFor();
        logger.info("[SCRIPT] Done waiting for script");
        p.destroy();
    }
}
