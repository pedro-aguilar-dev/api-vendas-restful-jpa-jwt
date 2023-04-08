package io.pedro.aguilar.repositoriosJPA;

import io.pedro.aguilar.classesDeDominio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteDAO extends JpaRepository<Cliente, Integer> {

    @Query(value = "select * from Cliente where nome like %:like%", nativeQuery = true)
    List<Cliente> BuscarPorLike(@Param("like") String like);
}
