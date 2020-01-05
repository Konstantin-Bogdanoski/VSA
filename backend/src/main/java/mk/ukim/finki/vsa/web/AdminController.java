package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.IncorrectVideoUploadException;
import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.UserService;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserService userService;
    private VideoService videoService;
    //TODO: Change the folder where the videos will be saved on your PC
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    @PostMapping("/upload")
    //@PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video upload(@RequestParam("video") MultipartFile video,
                        @RequestParam("name") String name,
                        @RequestParam("length") Double length) {
        if (video.isEmpty() || name.isEmpty() || length.isInfinite() || length.isNaN() || length < 0)
            throw new IncorrectVideoUploadException();
        Video newVid = new Video();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setLength(length);
            newVid.setFileName(video.getOriginalFilename());
            // Save info about video in DB
            videoService.save(newVid);
            // Get the video and save it somewhere
            byte[] bytes = video.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + video.getOriginalFilename() + "/" + video.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVid;
    }

    @PatchMapping("/patch")
    //@PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video patch(@RequestParam("videoID") Long vID,
                       @RequestParam("video") MultipartFile video,
                       @RequestParam("name") String name,
                       @RequestParam("length") Double length) {
        if (video.isEmpty() || name.isEmpty() || length.isInfinite() || length.isNaN() || length < 0)
            throw new IncorrectVideoUploadException();
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        Video newVid = videoService.findOne(vID).get();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setLength(length);
            newVid.setFileName(video.getOriginalFilename());
            // Save info about video in DB
            videoService.save(newVid);
            // Get the video and save it somewhere
            byte[] bytes = video.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + video.getOriginalFilename() + "/" + video.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVid;
    }

    @DeleteMapping("/delete")
    //@PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video delete(@RequestParam("videoID") Long vID) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        Video video = videoService.findOne(vID).get();
        videoService.delete(video);
        try {
            Path path = Paths.get(UPLOADED_FOLDER + video.getFileName() + "/" + video.getFileName());
            Files.delete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }

    @GetMapping("/video")
    public MultipartFile get(@RequestParam("videoID") Long vID) {
        //TODO: Implement method
    }
}
