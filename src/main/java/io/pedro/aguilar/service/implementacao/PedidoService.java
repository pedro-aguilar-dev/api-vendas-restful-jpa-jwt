package io.pedro.aguilar.service.implementacao;

import io.pedro.aguilar.DTO.PedidoDTO;
import io.pedro.aguilar.classesDeDominio.Pedido;
import io.pedro.aguilar.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido> obterPedidoCompleto(int idPedido);

    Pedido atuaizarStatusPedido(int id, StatusPedido statusPedido);
}
