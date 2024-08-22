package br.com.dducl.collective_coffee_marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id"}, allowGetters = true, ignoreUnknown = true)
public class ChavesPixDto extends Dto {

    @NotBlank(message = "Chave é um campo obrigatório")
    private String chave;

    private boolean ativo;
}