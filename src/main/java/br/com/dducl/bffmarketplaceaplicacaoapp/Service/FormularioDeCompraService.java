package br.com.dducl.bffmarketplaceaplicacaoapp.Service;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.FormularioDeCompraDTO;
import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.FormularioDeCompraListDTO;
import org.springframework.stereotype.Service;

@Service
public interface FormularioDeCompraService {
    
    FormularioDeCompraListDTO consultarProdutos();

    FormularioDeCompraDTO incluirProduto();

}
