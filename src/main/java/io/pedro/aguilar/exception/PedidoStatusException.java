package io.pedro.aguilar.exception;

public class PedidoStatusException extends RuntimeException {
    public PedidoStatusException() {
        super("Pedido não encontrado");
    }
}
