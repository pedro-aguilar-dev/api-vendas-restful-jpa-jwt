package io.pedro.aguilar.classesDeDominio;


import jdk.nashorn.internal.runtime.logging.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    @Column(name = "desc")
    private String desc;

    @NotNull(message = "{campo.preco.obrigatorio")
    @Column(name = "preco_unitario", precision = 20, scale = 2)
    private BigDecimal preco_unitario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(BigDecimal preco_unitario) {
        this.preco_unitario = preco_unitario;
    }
}
