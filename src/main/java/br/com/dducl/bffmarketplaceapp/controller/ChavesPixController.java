package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.ChavesPixDto;
import br.com.dducl.bffmarketplaceapp.negocio.ChavePixBusiness;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chaves-pix")
public class ChavesPixController {

    @Resource
    private ChavePixBusiness chavePixBusiness;

    @PostMapping(value = "/new")
    public ResponseEntity<Void> insert(@Valid @RequestBody ChavesPixDto chavesPixDto, @RequestParam String identificador) throws NotFoundException, ValidationsException, NoSuchFieldException {
        chavePixBusiness.insert(identificador, chavesPixDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/pessoa/{identificador}")
    public ResponseEntity<Void> update(@Valid @RequestBody ChavesPixDto chavesPixDto, @PathVariable String identificador, @RequestParam String chave) throws ValidationsException {
        chavePixBusiness.update(chave, chavesPixDto, identificador);

        return ResponseEntity.noContent().build();
    }
}
