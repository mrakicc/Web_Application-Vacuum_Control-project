package rs.raf.domaci_3.services;

import org.springframework.stereotype.Service;
import rs.raf.domaci_3.model.ErrorMassage;
import rs.raf.domaci_3.repositories.ErrorRepository;

import java.util.List;

@Service
public class ErrorService {

    private ErrorRepository errorRepository;

    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public List<ErrorMassage> findAllByUserId(Long userId) {
        return this.errorRepository.findAllByUserId(userId);
    }

}
