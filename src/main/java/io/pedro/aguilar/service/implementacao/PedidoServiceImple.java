package io.pedro.aguilar.service.implementacao;


import io.pedro.aguilar.DTO.ItemPedidoDTO;
import io.pedro.aguilar.DTO.PedidoDTO;
import io.pedro.aguilar.classesDeDominio.Cliente;
import io.pedro.aguilar.classesDeDominio.ItemPedido;
import io.pedro.aguilar.classesDeDominio.Pedido;
import io.pedro.aguilar.classesDeDominio.Produto;
import io.pedro.aguilar.enums.StatusPedido;
import io.pedro.aguilar.exception.PedidoStatusException;
import io.pedro.aguilar.exception.RegraNegocioException;
import io.pedro.aguilar.repositoriosJPA.ClienteDAO;
import io.pedro.aguilar.repositoriosJPA.ItemPedidoDAO;
import io.pedro.aguilar.repositoriosJPA.PedidoDAO;
import io.pedro.aguilar.repositoriosJPA.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImple implements PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private ProdutoDAO produtoDAO;

    @Autowired
    private ItemPedidoDAO itemPedidoDAO;

    @Transactional
    @Override
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteDAO.findById(idCliente).orElseThrow( () ->
                new RegraNegocioException("Id do cliente não encontrado " +
                idCliente));
        Pedido pedido = new Pedido();
        pedido.setValor_total(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidoList = converter(pedido, pedidoDTO.getItems());

        itemPedidoDAO.saveAll(itemPedidoList);
        pedidoDAO.save(pedido);
        pedido.setItems(itemPedidoList);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(int idPedido) {

        //ao fazer o Fetch ele irá trazer o registro do pedido completo
        return pedidoDAO.findByIdFetchItems(idPedido);
    }

    @Override
    public Pedido atuaizarStatusPedido(int id, StatusPedido statusPedido) {
        return pedidoDAO
                .findById(id)
                .map(pedido -> {
                    pedido.setStatusPedido(statusPedido);
                    pedidoDAO.save(pedido);
                    return pedido;
                }).orElseThrow( () -> new PedidoStatusException());
    }


    public List<ItemPedido> converter(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty())
            throw new RegraNegocioException("Não é possivel realizar um pedido sem items");

        return items
                .stream()
                .map(dto ->{
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtoDAO.findById(idProduto).orElseThrow( () ->
                            new RegraNegocioException("Id do produto não encontrado " +
                            idProduto));

                    ItemPedido  itemPedido = new ItemPedido();
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    itemPedido.setQtItens(dto.getQuantidade());

                    return itemPedido;

                }).collect(Collectors.toList());
    }

}
