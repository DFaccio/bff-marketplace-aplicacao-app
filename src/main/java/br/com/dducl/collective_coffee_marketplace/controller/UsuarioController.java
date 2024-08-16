package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.UsuarioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.dto.UsuarioDto;
import br.com.dducl.collective_coffee_marketplace.negocio.UsuarioBusiness;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
@Tag(name = "Usuário")
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
