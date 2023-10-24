package br.com.dducl.bffmarketplaceapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(value = {"id"})
@Getter
@Setter
public class ChavesPixDto extends Dto {

    private String chave;

    private boolean ativo;
}
