package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/api/video")
public class MainController {
    private VideoService videoService;

    public MainController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public List<Video> getVideos() {
        return videoService.findAll();
    }

    @GetMapping("/{id}")
    public Video getVideo(@PathVariable(name = "id") Long id) {
        Optional<Video> video = videoService.findOne(id);
        if (!video.isPresent())
            throw new VideoNotFoundException();
        return video.get();
    }

    @GetMapping("/{id}/stream")
    public MultipartFile playVideo(@PathVariable(name = "id") Long id, @RequestParam(name = "quality", required = true) short quality) {
        // TODO: Stream the selected video with the quality
        return null;
    }
}
