package io.pedro.aguilar.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

//dto que representa os detalhes e visualização do pedido

public class InformacoesPedidoDTO {

    private int idPedido;
    private String cpfCliente;
    private String nomeCliente;
    private BigDecimal totalPedido;
    private String dataPedido;
    private String statusPedido;
    private List<InformacaoItemPedidoDTO> items;



}
