package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/")
public class MainController {
    private VideoService videoService;
}
