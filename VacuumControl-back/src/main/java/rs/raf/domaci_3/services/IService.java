package rs.raf.domaci_3.services;

import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    <S extends T> S save(S var1) throws BadCredentialsException;

    List<T> findAll();

    void deleteById(ID var1);

}
