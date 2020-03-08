package mk.ukim.finki.vsa.web;

import mk.ukim.finki.vsa.exception.IncorrectVideoUploadException;
import mk.ukim.finki.vsa.exception.VideoAlreadyExistsException;
import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import mk.ukim.finki.vsa.model.Quality;
import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.service.QualityService;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.http.MediaType;
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
    private QualityService qualityService;
    private Logger logger = Logger.getLogger(AdminController.class.getName());
    private static String UPLOADED_FOLDER = "/home/konstantin/Videos/";

    public AdminController(VideoService videoService, QualityService qualityService) {
        this.videoService = videoService;
        this.qualityService = qualityService;
    }

    private void formatVideo(String path, boolean hq, boolean lq) throws IOException, InterruptedException {
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
        String[] cmd = {"/home/konstantin/Videos/formatVideo.sh", loc, quality + "", fileName};
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        String s;
        /*BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        while ((s = br.readLine()) != null)
            System.out.println(s);*/
        logger.info("[SCRIPT] Waiting for script");
        p.waitFor();
        logger.info("[SCRIPT] Done waiting for script");
        p.destroy();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated() && hasRole('ROLE_ADMIN')")
    public List<Video> get() {
        return videoService.findAll();
    }

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
            List<Quality> qualities = new ArrayList<>();
            qualities.add(qualityService.findByValue("Medium"));
            if (highQuality)
                qualities.add(qualityService.findByValue("High"));
            if (lowQuality)
                qualities.add(qualityService.findByValue("Low"));
            newVid.setQualities(qualities);
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
                    formatVideo(newVid.getFileName(), highQuality, lowQuality);
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
        logger.info("[REMOVE] " + video.getFileName() + " has been removed");
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
