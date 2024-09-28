package rs.raf.domaci_3.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.raf.domaci_3.model.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
