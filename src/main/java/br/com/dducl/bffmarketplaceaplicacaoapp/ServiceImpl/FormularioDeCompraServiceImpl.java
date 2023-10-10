package br.com.dducl.bffmarketplaceaplicacaoapp.ServiceImpl;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.*;
import br.com.dducl.bffmarketplaceaplicacaoapp.Service.FormularioDeCompraService;

import java.util.ArrayList;
import java.util.List;
public class FormularioDeCompraServiceImpl implements FormularioDeCompraService {

    @Override
    public FormularioDeCompraListDTO consultarProdutos(){

        FormularioDeCompraDTO formulario = new FormularioDeCompraDTO();

        List<FormularioDeCompraDTO> formularioLista = new ArrayList<>();

        for(int i = 1 ; i < 11 ; i++ ) {
            formulario.setIdProduto(i);
            formulario.setDescricaoProduto("Produto " + i);
            formulario.setConteudoProduto(i + "00g");
            formulario.setQuantidadeProduto(i++);
            formulario.setValorProduto(i*1.5);
            formulario.setFornecedor("Fornecedor " + i);
            formularioLista.add(formulario);
        }

        return (FormularioDeCompraListDTO) formularioLista;

    }

    @Override
    public FormularioDeCompraDTO incluirProduto(){

        return null;

    }

}
