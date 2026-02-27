package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.entity.Rol;

public interface RolRepository extends CrudRepository<Rol,Long>{

    Optional<Rol> findByNombre(String nombre);
}
