package br.com.dducl.bffmarketplaceaplicacaoapp.DTO;

import br.com.dducl.bffmarketplaceaplicacaoapp.DTO.FormularioDeCompraDTO;

public class FormularioDeCompraListDTO {
    
    private List<FormularioDeCompraDTO> formularioDeCompras;

    public FormularioDeCompraListDTO(List<FormularioDeCompraDTO> formularioDeCompras){
        this.formularioDeCompras = formularioDeCompras;
    }

    public List<FormularioDeCompraDTO> getFormularioDeCompras(){
        return formularioDeCompras;
    }

    public void setFormularioDeCompras(List<FormularioDeCompraDTO> formularioDeCompras){
        this.formularioDeCompras = formularioDeCompras;
    }

}
