package io.pedro.aguilar.repositoriosJPA;

import io.pedro.aguilar.classesDeDominio.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoDAO extends JpaRepository<Produto, Integer> {
}
