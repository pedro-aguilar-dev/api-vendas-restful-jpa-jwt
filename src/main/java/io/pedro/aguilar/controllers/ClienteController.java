package io.pedro.aguilar.controllers;

import io.pedro.aguilar.classesDeDominio.Cliente;
import io.pedro.aguilar.repositoriosJPA.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserirCliente(@RequestBody @Valid Cliente cliente) {
        return clienteDAO.save(cliente);
    }

    @GetMapping("{id}")
    public Cliente buscarCliente(@PathVariable("id") int id){
        return clienteDAO
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

    @GetMapping("/like/{like}")
    public List<Cliente> buscarPorLike(@Valid @PathVariable("like") String like){
        return clienteDAO.BuscarPorLike(like);
    }

    @PutMapping("{id}")
    public Cliente atualizarCliente(@RequestBody Cliente cliente, @PathVariable int id){
        return clienteDAO
                .findById(id)
                .map(clienteExistente ->{
                    cliente.setId(clienteExistente.getId());
                    clienteDAO.save(cliente);
                    return cliente;

        }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }

      @DeleteMapping("/deletar/{id}")
      @ResponseStatus(HttpStatus.NO_CONTENT)
      private Class<Void> deletarCliente(@PathVariable("id") int id){
             return clienteDAO
                     .findById(id)
                     .map(cliente ->{
                         clienteDAO.delete(cliente);
                         return Void.TYPE;
                     }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                             "Cliente não encontrado"));
    }

}
