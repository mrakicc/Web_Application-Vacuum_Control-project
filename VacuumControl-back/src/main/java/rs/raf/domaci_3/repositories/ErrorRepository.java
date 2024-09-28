package rs.raf.domaci_3.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.raf.domaci_3.model.ErrorMassage;

import java.util.List;

@Repository
public interface ErrorRepository extends CrudRepository<ErrorMassage, Long> {

    List<ErrorMassage> findAllByUserId(Long userId);

}
