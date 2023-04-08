package io.pedro.aguilar.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AtualizacaoStatusPedidoDTO {
    private String novoStatus;

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }
}
