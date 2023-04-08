package io.pedro.aguilar.DTO;

import io.pedro.aguilar.classesDeDominio.ItemPedido;
import io.pedro.aguilar.validacoesEspecificas.NotEmptyList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {


    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private int cliente;

    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;


    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ItemPedidoDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemPedidoDTO> items) {
        this.items = items;
    }
}
