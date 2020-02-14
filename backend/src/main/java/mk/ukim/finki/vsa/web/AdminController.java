package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.IncorrectVideoUploadException;
import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.UserService;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    private UserService userService;
    private VideoService videoService;
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    public AdminController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
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

            //TODO: Start FFMPEG process to transform the video in HLS format

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVid;
    }

    @PatchMapping("/patch")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video patch(@RequestParam("videoID") Long vID,
                       @RequestParam("video") MultipartFile video,
                       @RequestParam("name") String name,
                       @RequestParam("length") Double length) {
        if (video.isEmpty() || name.isEmpty() || length.isInfinite() || length.isNaN() || length < 0)
            throw new IncorrectVideoUploadException();
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        // Retrieve video which will be patched
        Video newVid = videoService.findOne(vID).get();
        try {
            newVid.setName(name);
            newVid.setKey();
            newVid.setLength(length);
            if (!video.getOriginalFilename().equals(newVid.getFileName())) {
                // Get the video and save it somewhere
                byte[] bytes = video.getBytes();
                // First delete the old video
                Path oldPath = Paths.get(UPLOADED_FOLDER + newVid.getFileName() + "/" + newVid.getFileName());
                Files.deleteIfExists(oldPath);

                // Then save the new video
                newVid.setFileName(video.getOriginalFilename());
                Path newPath = Paths.get(UPLOADED_FOLDER + video.getOriginalFilename() + "/" + video.getOriginalFilename());
                Files.write(newPath, bytes);

                //TODO: Start FFMPEG process to transform the video in HLS format

                // Save info about video in DB
                videoService.save(newVid);
            } else {
                videoService.save(newVid);
                // Get the video and save it somewhere
                byte[] bytes = video.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + video.getOriginalFilename() + "/" + video.getOriginalFilename());
                Files.write(path, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newVid;
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public Video delete(@RequestParam("videoID") Long vID) {
        if (!videoService.findOne(vID).isPresent())
            throw new VideoNotFoundException();
        Video video = videoService.findOne(vID).get();
        videoService.delete(video);
        try {
            Path path = Paths.get(UPLOADED_FOLDER + video.getFileName() + "/" + video.getFileName());
            // Will throw NoSuchFileException if the file does not exist
            Files.deleteIfExists(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return video;
    }
}
