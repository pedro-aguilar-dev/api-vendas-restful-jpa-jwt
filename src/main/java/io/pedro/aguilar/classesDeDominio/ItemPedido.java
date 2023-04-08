package io.pedro.aguilar.classesDeDominio;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itemPedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "quantidade")
    private  int qtItens;

    @Column(name = "valor_total_itens", precision = 20, scale = 2)
    private BigDecimal valor_total_itens;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtItens() {
        return qtItens;
    }

    public void setQtItens(int qtItens) {
        this.qtItens = qtItens;
    }

    public BigDecimal getValor_total_itens() {
        return valor_total_itens;
    }

    public void setValor_total_itens(BigDecimal valor_total_itens) {
        this.valor_total_itens = valor_total_itens;
    }
}
