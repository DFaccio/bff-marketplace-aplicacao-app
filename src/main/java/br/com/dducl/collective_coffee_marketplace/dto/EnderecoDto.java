package br.com.dducl.collective_coffee_marketplace.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id"}, allowGetters = true, ignoreUnknown = true)
@Builder
public class EnderecoDto extends Dto {

    @Schema(example = "Casa")
    private String apelido;

    @NotBlank
    @Schema(example = "Av. Raimundo de Aquino")
    private String logradouro;

    @Schema(example = "420")
    private String numero;

    @NotBlank
    @Schema(example = "Vila Pai Eterno")
    private String bairro;

    @NotBlank
    @Schema(example = "Trindade")
    private String cidade;

    @NotBlank
    @Length(min = 2, max = 2)
    @Schema(example = "GO")
    private String estado;

    private String complemento;

    @NotBlank
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
    @Schema(example = "75380-000")
    private String cep;
}
