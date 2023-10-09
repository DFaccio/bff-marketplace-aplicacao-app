package br.com.dducl.bffmarketplaceaplicacaoapp.Controller;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.*;
import br.com.dducl.bffmarketplaceaplicacaoapp.Service.FormularioDeCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FormularioDeCompraController extends Throwable {
    
    private final String LISTAR_PRODUTOS = "/listarProdutos";

    @Autowired
    private FormularioDeCompraService formularioService;

    public FormularioDeCompraController(FormularioDeCompraService formularioService){
        this.formularioService = formularioService;
    }
 
    @GetMapping(LISTAR_PRODUTOS)
    public List<FormularioDeCompraDTO> listarProdutos(){

        return (List<FormularioDeCompraDTO>) formularioService.consultarProdutos();

    }

}
