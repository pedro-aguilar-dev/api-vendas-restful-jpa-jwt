package io.pedro.aguilar.repositoriosJPA;

import io.pedro.aguilar.classesDeDominio.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {
}
