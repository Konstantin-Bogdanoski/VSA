package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.model.Video;
import mk.ukim.finki.vsa.repository.VideoRepository;
import mk.ukim.finki.vsa.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class VideoServiceImpl extends BaseEntityCrudServiceImpl<Video, VideoRepository> implements VideoService {

    private VideoRepository repository;

    @Override
    protected VideoRepository getRepository() {
        return repository;
    }
}
