package io.pedro.aguilar.controllers;


import io.pedro.aguilar.DTO.AtualizacaoStatusPedidoDTO;
import io.pedro.aguilar.DTO.InformacaoItemPedidoDTO;
import io.pedro.aguilar.DTO.InformacoesPedidoDTO;
import io.pedro.aguilar.DTO.PedidoDTO;
import io.pedro.aguilar.classesDeDominio.ItemPedido;
import io.pedro.aguilar.classesDeDominio.Pedido;
import io.pedro.aguilar.enums.StatusPedido;
import io.pedro.aguilar.repositoriosJPA.PedidoDAO;
import io.pedro.aguilar.service.implementacao.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @Autowired
    private PedidoDAO pedidoDAO;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //irá retornar integer porq a proposta é retornar somente o id do pedido gerado
    public Integer inserirPedido(@RequestBody @Valid  PedidoDTO pedidoDTO) {
        //transformar o dto em um modelo de dados para enviar para o service
        //é o service que irá processar o pagamento

        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable("id") int id) {

        return pedidoService.obterPedidoCompleto(id)
                .map(p -> conveterParaPedidoDTO(p))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Id do pedido não encotrado" + id)
                );
    }


    //convertendo o pedido passado no parametro para um objeto InformacoesPedidoDTO
    //o objeto InformacoesPedidoDTO representa o que irá ser exibido nos detalhes do pedido
    public InformacoesPedidoDTO conveterParaPedidoDTO(Pedido pedido) {
        //o builder constroi uma instancia da classe automaticamente
        //não será preciso instanciar um objeto de InformacoesPedidoDTO
        return InformacoesPedidoDTO.builder()
                .idPedido(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .totalPedido(pedido.getValor_total())
                .cpfCliente(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .items(conveter(pedido.getItems()))
                .statusPedido(pedido.getStatusPedido().toString())
                .build();
    }

    private List<InformacaoItemPedidoDTO> conveter(List<ItemPedido> items) {

        //se a lista de items estar vazia
        if (CollectionUtils.isEmpty(items))
            //irá retornar uma lista vazia
            return Collections.emptyList();

        return items.stream().map(item -> InformacaoItemPedidoDTO
                        .builder().descProduto(item.getProduto().getDesc())
                        .precoUnitario(item.getProduto().getPreco_unitario())
                        .quantidadeProduto(item.getQtItens())
                        .build()

                //o metodo map retorna uma stream é preciso converta-la para uma lista
                //o metodo abaixo trasnforma a strem em uma lista
        ).collect(Collectors.toList());

    }

    //o patch é utilizado em atualizações especificas
    //ao usar o put é preciso passar as informações do objeto inteiro
    //o que não for passado no put ficará como nulo no banco
    //com o patch é possivel fazer a atualização de um campo só e ignorar os outros
    //o patch ignora os campos e não os deixa como nulos no banco

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable("id") int id, @RequestBody AtualizacaoStatusPedidoDTO dto){

        String statusPedido =  dto.getNovoStatus();

        //o metodo valueof irá transformaar a string em um enum
        //é preciso que a string seja do tipo enum StatusPedido
        //porq no 2 parametro do metodo atualizarStatusPedido é passado um objeto StatusPedido
        pedidoService.atuaizarStatusPedido(id, StatusPedido.valueOf(statusPedido));

    }

}
