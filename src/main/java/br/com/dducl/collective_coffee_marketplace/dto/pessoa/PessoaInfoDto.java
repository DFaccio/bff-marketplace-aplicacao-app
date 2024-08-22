package br.com.dducl.collective_coffee_marketplace.dto.pessoa;

import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.dto.Dto;
import br.com.dducl.collective_coffee_marketplace.dto.EnderecoDto;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id", "dataCadastro"}, allowGetters = true, ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PessoaInfoDto extends Dto {

    @NotBlank(message = "Nome é um campo obrigatório")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?: [A-Za-zÀ-ÖØ-öø-ÿ]+)+$")
    @Schema(example = "Ana Maria")
    private String nome;

    @NotBlank(message = "Identificação (CPF/CNPJ) é um campo obrigatório")
    @Length(min = 11, max = 14)
    @Schema(example = "79402901000")
    private String documento;

    @Email(message = "Não é um e-mail válido")
    @NotBlank
    @Schema(example = "ana@email.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "\\+55 [0-9]{3} 9 [0-9]{4}-[0-9]{4}")
    private String telefone;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataCadastro;

    private boolean ativo;

    private EnderecoDto endereco;

    @Schema(example = "COMPRADOR")
    private Perfil perfil;

    @NotNull
    private List<ChavesPixDto> chavesPix;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "perfil", include = JsonTypeInfo.As.EXTERNAL_PROPERTY, visible = true)
    private PessoaPerfilDto perfilDto;
}
