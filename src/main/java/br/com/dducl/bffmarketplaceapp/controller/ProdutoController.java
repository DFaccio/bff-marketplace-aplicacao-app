package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.negocio.ProdutoBusiness;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {

    @Resource
    private ProdutoBusiness business;


    @PostMapping("/new")
    public ResponseEntity<Void> insert(@RequestBody ProdutoDto produto) throws ValidacoesException {
        business.insert(produto);

        return ResponseEntity.noContent().build();
    }

}
