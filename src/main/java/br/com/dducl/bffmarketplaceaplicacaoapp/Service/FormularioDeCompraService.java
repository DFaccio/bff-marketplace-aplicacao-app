package br.com.dducl.bffmarketplaceaplicacaoapp.Service;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.*;
import br.com.dducl.bffmarketplaceaplicacaoapp.Service.*;

public interface FormularioDeCompraService {
    
    private List<FormularioDeCompraDTO> consultarProdutos;

    private FormularioDeCompraDTO incluirProduto;

    @Autowired
    private FormularioDeCompraService formularioService;




}
