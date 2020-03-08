package mk.ukim.finki.vsa.service;

import mk.ukim.finki.vsa.model.Video;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface VideoService extends BaseEntityCrudService<Video> {
    Video findByName(String name);
}
