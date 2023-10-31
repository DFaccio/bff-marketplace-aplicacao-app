package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.UsuarioCadastroDto;
import br.com.dducl.bffmarketplaceapp.dto.UsuarioDto;
import br.com.dducl.bffmarketplaceapp.negocio.UsuarioBusiness;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Resource
    private UsuarioBusiness usuarioBusiness;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioCadastroDto dto) throws ValidationsException {
        usuarioBusiness.insert(dto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/new/password/{username}")
    public ResponseEntity<Void> updatePassword(@PathVariable String username, @RequestParam String password) throws NotFoundException, ValidationsException {
        usuarioBusiness.updatePassword(username, password);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UsuarioDto> findByUsername(@PathVariable String username) throws NotFoundException {
        return ResponseEntity.ok(usuarioBusiness.findByUsername(username));
    }
}
