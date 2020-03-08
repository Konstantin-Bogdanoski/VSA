package mk.ukim.finki.vsa.service;

import mk.ukim.finki.vsa.model.Quality;

/**
 * @author Natasha Stojanova
 */
public interface QualityService extends BaseEntityCrudService<Quality> {
    Quality findByValue(String value);
}
