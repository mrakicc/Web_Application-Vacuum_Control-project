package rs.raf.domaci_3.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.raf.domaci_3.model.User;
import rs.raf.domaci_3.model.Vacuum;

import java.util.Date;
import java.util.List;

@Repository
public interface VacuumRepository extends CrudRepository<Vacuum, Long> {

    Vacuum findByName(String name);
    List<Vacuum> findAllByAddedBy(User user);
    List<Vacuum> findAllByStatus(String status);
    List<Vacuum> findAllByDateAdded(Date date);

    @Query("Select v from Vacuum v where(:name is null or v.name like %:name%) and (:status is null or v.status = :status) and(v.dateAdded between :dateFrom and :dateTo)")
    List<Vacuum> findByNameAndStatus(String name, String status, Date dateFrom, Date dateTo);

}
