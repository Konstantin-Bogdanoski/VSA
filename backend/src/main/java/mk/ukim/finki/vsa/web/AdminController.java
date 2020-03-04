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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private Logger logger = Logger.getLogger(AdminController.class.getName());
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    public AdminController(VideoService videoService) {
        this.videoService = videoService;
    }

    private void formatVideo(String path) throws IOException, InterruptedException {
        String loc = UPLOADED_FOLDER + path.replace(" ", "\\ ");
        String[] cmd = {"/home/konstantin/Videos/formatVideo.sh", loc};
        Process p = Runtime.getRuntime().exec(cmd);
        String s;
        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        while ((s = br.readLine()) != null)
            System.out.println(s);
        p.waitFor();
        System.out.println("exit: " + p.exitValue());
        p.destroy();
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
        logger.info("[UPLOAD] Uploading new video");
        Video newVid = new Video();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setImdbLink(imdbLink);
            newVid.setImgLink(imgLink);
            newVid.setDescription(description);
            String parentDir = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().length() - 4);
            newVid.setFileName(parentDir + "/" + file.getOriginalFilename());
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
                try {
                    formatVideo(newVid.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Save info about video in DB
                newVid.setFileName(newVid.getFileName() + ".mpd");
                videoService.save(newVid);

            } else
                throw new VideoAlreadyExistsException();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("[UPLOAD] " + newVid.getFileName() + " has been uploaded");
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

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video delete(@PathVariable("id") Long vID) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        logger.info("[REMOVE] Trying to remove video");
        Video video = videoService.findOne(vID).get();
        try {
            deleteDirectory(new File(UPLOADED_FOLDER + video.getFileName().split("/")[0]));
            videoService.delete(video);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("[REMOVE]" + video.getFileName() + " has been removed");
        return video;
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
