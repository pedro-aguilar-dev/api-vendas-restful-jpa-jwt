package io.pedro.aguilar.controllers;


import io.pedro.aguilar.classesDeDominio.Cliente;
import io.pedro.aguilar.classesDeDominio.Produto;
import io.pedro.aguilar.repositoriosJPA.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {


    @Autowired
    private ProdutoDAO produtoDAO;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto inserirProduto(@RequestBody @Valid  Produto produto){
        return produtoDAO.save(produto);

    }

    @PutMapping("/atualizar/{id}")
    public Produto atualizarProduto( @Valid @RequestBody Produto produto, @PathVariable int id){
          return produtoDAO
                  .findById(id)
                  .map(
                          produtoExistente ->{
                              produto.setId(produtoExistente.getId());
                              produtoDAO.save(produto);
                              return produto;
                          }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                          "Cliente não encontrado"));

    }

    @DeleteMapping("/deletar/{id}")
    public Class<Void> deletarProduto(@PathVariable int id){
          return produtoDAO
                  .findById(id)
                  .map( produto -> {
                      produtoDAO.delete(produto);
                      return Void.TYPE;
                  }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                          "Cliente não criado"));
    }

    @GetMapping
    public List<Produto> buscar(Cliente filtro){

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() //ignorar a diferença entre minuscula e maiuscula na hora da consula

                // fazer o match na string e ver se  ela está contida em algum registro
                //é uma das formas de se fazer um like
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


               Example example = Example.of(filtro, matcher);

               List<Produto> ListaProdutos = produtoDAO.findAll(example);
               return ListaProdutos;
    }
    @GetMapping("/buscarPorId/{id}")
    public Produto buscarPorId(@PathVariable int id){
         return produtoDAO
                 .findById(id)
                 .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                         "Cliente não encontrado"));
    }



}
