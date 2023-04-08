package io.pedro.aguilar.classesDeDominio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @NotNull
    private int id;

    @Column(name = "nome")
    @NotEmpty(message = "{campos.nome.obrigatorio}")
    private String nome;

    @Column(name = "cpf")
    @CPF(message = "{campo.cpf.invalido}")
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    private String cpf;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

