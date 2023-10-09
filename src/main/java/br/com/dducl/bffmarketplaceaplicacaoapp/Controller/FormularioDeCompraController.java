package br.com.dducl.bffmarketplaceaplicacaoapp.Controller;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.*;

@RestController
public class FormularioDeCompraController {
    
    private String LISTAR_PRODUTOS = "listarProdutos";

    @Autowired
    private FormularioDeCompraService formularioService;
 
    @GetMapping(LISTAR_PRODUTOS)
    public List<FormularioDeCompraListDTO> listarProdutos(){

        List<FormularioDeCompraListDTO> listaProdutos = formularioService.consultarProdutos();

        return listaProdutos;

    }

}
