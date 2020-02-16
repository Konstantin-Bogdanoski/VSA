package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.IncorrectVideoUploadException;
import mk.ukim.finki.vsa.exception.VideoAlreadyExistsException;
import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.UserService;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private VideoService videoService;
    private Logger logger = Logger.getLogger(AdminController.class.getName());
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    public AdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public List<Video> get() {
        return videoService.findAll();
    }

    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Video upload(@RequestParam MultipartFile file,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "description") String description,
                        @RequestParam(value = "imgLink") String imgLink,
                        @RequestParam(value = "imdbLink") String imdbLink,
                        @RequestParam(value = "qualities", required = false) List<String> qualities) {
        if (file.isEmpty() || name.isEmpty())
            throw new IncorrectVideoUploadException();
        logger.info("Uploading new video");
        Video newVid = new Video();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setImdbLink(imdbLink);
            newVid.setImgLink(imgLink);
            newVid.setDescription(description);
            newVid.setFileName(file.getOriginalFilename());
            // Get the video and save it somewhere
            byte[] bytes = file.getBytes();
            File check = new File(UPLOADED_FOLDER + file.getOriginalFilename());
            if (!check.exists()) {
                check.mkdirs();
                check = new File(UPLOADED_FOLDER + file.getOriginalFilename() + "/" + file.getOriginalFilename());
                check.createNewFile();

                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename() + "/" + file.getOriginalFilename());
                Files.write(path, bytes);

                // Save info about video in DB
                videoService.save(newVid);

                //TODO: Start FFMPEG process to transform the video in HLS format
            } else
                throw new VideoAlreadyExistsException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(newVid.getFileName() + " has been uploaded");
        return newVid;
    }

    @PatchMapping("/patch/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video patch(@PathVariable("id") Long vID,
                       @RequestParam("name") String name,
                       @RequestParam("description") String description,
                       @RequestParam("imgLink") String imgLink,
                       @RequestParam("imdbLink") String imdbLink,
                       @RequestParam(value = "quailties", required = false) List<String> qualities) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        Video newVid = videoService.findOne(vID).get();
        newVid.setName(name);
        newVid.setKey();
        newVid.setDescription(description);
        newVid.setImgLink(imgLink);
        newVid.setImdbLink(imdbLink);
        return newVid;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video delete(@PathVariable("id") Long vID) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        logger.info("Trying to remove video");
        Video video = videoService.findOne(vID).get();
        ;
        try {
            Path path = Paths.get(UPLOADED_FOLDER + video.getFileName() + "/" + video.getFileName());
            Files.deleteIfExists(path);
            path = Paths.get(UPLOADED_FOLDER + video.getFileName());
            Files.deleteIfExists(path);
            videoService.delete(video);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(video.getFileName() + " has been removed");
        return video;
    }
}
