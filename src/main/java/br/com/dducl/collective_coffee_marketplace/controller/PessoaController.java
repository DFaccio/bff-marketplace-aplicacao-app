package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaInfoDto;
import br.com.dducl.collective_coffee_marketplace.negocio.FornecedorBusiness;
import br.com.dducl.collective_coffee_marketplace.negocio.PessoaBusiness;
import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.enums.Perfil;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pessoa")
@Tag(name = "Pessoa", description = "Gerenciamento de fornecedor, comprador e administrador")
public class PessoaController {

    private final PessoaBusiness business;

    private final FornecedorBusiness fornecedorBusiness;

    public PessoaController(PessoaBusiness business, FornecedorBusiness fornecedorBusiness) {
        this.business = business;
        this.fornecedorBusiness = fornecedorBusiness;
    }

    @GetMapping
    @Operation(description = "Recuperar pessoas por perfil")
    public ResponseEntity<ResultadoPaginado<? extends PessoaDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                          @RequestParam(required = false) Integer initialPage,
                                                                          @RequestParam Perfil perfil) {
        Pagination page = new Pagination(initialPage, pageSize);

        if (Perfil.FORNECEDOR.equals(perfil)) {
            return ResponseEntity.ok(fornecedorBusiness.findAll(page));
        }

        return ResponseEntity.ok(business.findAll(perfil, page));
    }

    @PostMapping
    @Operation(description = "Cadastrar forncedor, comprador ou adminitrador.")
    public ResponseEntity<? extends PessoaDto> insert(@Valid @RequestBody PessoaInfoDto pessoa) throws ValidationsException {
        if (pessoa.getPerfil() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("message", MessageUtil.getMessage("PERFIL_OBRIGATORIO"))
                    .build();
        }

        if (Perfil.FORNECEDOR.equals(pessoa.getPerfil())) {
            return ResponseEntity.ok(fornecedorBusiness.insert(pessoa));
        }

        return ResponseEntity.ok(business.insert(pessoa));
    }

    @PutMapping(value = "/{documento}")
    @Operation(description = "Atualizar cadastro de pessoa, incluindo os dados base e o endereço. Não atualiza documento e chaves PIX")
    public ResponseEntity<PessoaDto> update(@Valid @RequestBody PessoaDto pessoa,
                                            @Parameter(description = "Documento da pessoa que terá informações atualizadas") @PathVariable String documento) throws ValidationsException, NotFoundException {
        return ResponseEntity.ok(business.update(documento, pessoa));
    }

    @GetMapping(value = "/{perfil}/{documento}")
    @Operation(description = "Buscar pessoa por documento e perfil")
    public ResponseEntity<? extends PessoaDto> findByDocument(@Parameter(description = "Documento da pessoa que terá informações atualizadas") @PathVariable String documento,
                                                              @Parameter(description = "Perfil") @PathVariable Perfil perfil) throws NotFoundException {
        if (Perfil.FORNECEDOR.equals(perfil)) {
            return ResponseEntity.ok(fornecedorBusiness.findFornecedorByDocumento(documento));
        }

        return ResponseEntity.ok(business.findByIdentificador(perfil, documento));
    }

    @PutMapping(value = "/fornecedor/documento/{documento}")
    @Operation(description = "Alterar pessoa cadastrada para fornecedor")
    public ResponseEntity<FornecedorDto> updateToVendor(@Parameter(description = "Razão Social")@RequestBody String razaoSocial, @PathVariable String documento) throws NotFoundException, ValidationsException {
        return ResponseEntity.ok(fornecedorBusiness.updateToVendor(documento, razaoSocial));
    }

    @PutMapping(value = "/fornecedor/{documento}/new")
    @Operation(description = "Atualizar a razão social")
    public ResponseEntity<FornecedorDto> updateRazaoSocial(@RequestParam String razaoSocial, @PathVariable String documento) throws NotFoundException, ValidationsException {
        return ResponseEntity.ok(fornecedorBusiness.updateRazaoSocial(documento, razaoSocial));
    }
}
