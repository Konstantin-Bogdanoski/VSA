package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 * This class represents the main controller with which our users will communicate
 * All users can look at videos, but only an admin can perform CRUD operations
 */
@RestController
@RequestMapping("/api/video")
@CrossOrigin("*")
public class MainController {
    private VideoService videoService;

    public MainController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Method to return all of the videos
     */
    @GetMapping
    public List<Video> getVideos() {
        return videoService.findAll();
    }

    /**
     * Method used to get information about specific video
     */
    @GetMapping("/{id}")
    public Video getVideo(@PathVariable(name = "id") Long id) {
        Optional<Video> video = videoService.findOne(id);
        if (!video.isPresent())
            throw new VideoNotFoundException();
        return video.get();
    }
}
