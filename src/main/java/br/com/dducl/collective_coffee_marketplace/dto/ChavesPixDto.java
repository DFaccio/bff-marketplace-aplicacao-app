package br.com.dducl.collective_coffee_marketplace.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id"}, allowGetters = true, ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChavesPixDto extends Dto {

    @NotBlank(message = "Chave é um campo obrigatório")
    private String chave;

    private boolean ativo;
}