package io.pedro.aguilar.repositoriosJPA;

import io.pedro.aguilar.classesDeDominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    //metodo para encontrar o usuário pelo username
    Optional<Usuario> findByUsername(String username);
}
