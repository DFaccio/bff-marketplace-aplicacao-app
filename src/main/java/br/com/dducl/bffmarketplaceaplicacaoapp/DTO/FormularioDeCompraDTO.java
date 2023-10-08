package br.com.dducl.bffmarketplaceaplicacaoapp.DTO.FormularioDeCompraDTO;

public class FormularioDeCompraDTO {
    
    private final int idProduto;
    private final String descricaoProduto;
    private final String conteudoProduto;
    private final int quantidadeProduto;
    private final double valorProduto;
    private final String fornecedor;

    public FormularioDeCompraDTO(int idProduto, String descricaoProduto, String conteudoProduto, int quantidadeProduto, double valorProduto, String fornecedor){
        this.idProduto = idProduto;
        this.descricaoProduto = descricaoProduto;
        this.conteudoProduto = conteudoProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.valorProduto = valorProduto;
        this.fornecedor = fornecedor;
    }

    public int getIdProduto(){
        return idProduto;
    }

    public void setIdProduto(int idProduto){
        this.idProduto = idProduto;
    }

    public String getDescricaoProduto(){
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto){
        this.descricaoProduto = descricaoProduto;
    }

    public String getConteudoProduto(){
        return conteudoProduto;
    }

    public void setConteudoProduto(String conteudoProduto){
        this.conteudoProduto = conteudoProduto;
    }

    public int getQuantidadeProduto(){
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto){
        this.quantidadeProduto = quantidadeProduto;
    }

    public double getValorProduto(){
        return valorProduto;
    }

    public void setValorProduto(double valorProduto){
        this.valorProduto = valorProduto;
    }

    public String getFornecedor(){
        return fornecedor;
    }

    public void setFornecedor(String fornecedor){
        this.fornecedor = fornecedor;
    }

}
