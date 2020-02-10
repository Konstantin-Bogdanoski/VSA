package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.model.Quality;
import mk.ukim.finki.vsa.repository.QualityRepository;
import mk.ukim.finki.vsa.service.QualityService;
import org.springframework.stereotype.Service;

/**
 * @author Natasha Stojanova
 */
@Service
public class QualityServiceImpl extends BaseEntityCrudServiceImpl<Quality, QualityRepository> implements QualityService {
    private QualityRepository qualityRepository;

    public QualityServiceImpl(QualityRepository qualityRepository) {
        this.qualityRepository = qualityRepository;
    }

    @Override
    public QualityRepository getRepository() {
        return qualityRepository;
    }
}
