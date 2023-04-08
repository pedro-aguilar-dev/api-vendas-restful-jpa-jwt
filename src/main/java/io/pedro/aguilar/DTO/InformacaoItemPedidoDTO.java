package io.pedro.aguilar.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InformacaoItemPedidoDTO {

    //dto que representa os detalhes e visualização do item do pedido
    private String descProduto;
    private int quantidadeProduto;
    private BigDecimal precoUnitario;


}
