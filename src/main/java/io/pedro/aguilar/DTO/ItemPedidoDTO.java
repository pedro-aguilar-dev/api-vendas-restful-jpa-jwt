package io.pedro.aguilar.DTO;

import java.math.BigDecimal;

public class ItemPedidoDTO {

    private int produto;
    private int quantidade;
    private BigDecimal total_items;

    public int getProduto() {
        return produto;
    }

    public void setProduto(int produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal_items() {
        return total_items;
    }

    public void setTotal_items(BigDecimal total_items) {
        this.total_items = total_items;
    }
}
