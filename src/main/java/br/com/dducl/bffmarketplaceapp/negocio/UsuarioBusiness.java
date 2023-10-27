package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.UsuarioCadastroDto;
import br.com.dducl.bffmarketplaceapp.dto.UsuarioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.SenhaUsuario;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Usuario;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PessoaRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.UsuarioRepository;
import br.com.dducl.bffmarketplaceapp.util.PasswordUtils;
import br.com.dducl.bffmarketplaceapp.util.conversores.UsuarioConversor;
import br.com.dducl.bffmarketplaceapp.util.enums.StatusSenha;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioBusiness {

    @Resource
    private UsuarioRepository usuarioRepository;

    @Resource
    private UsuarioConversor conversor;

    @Resource
    private PessoaRepository pessoaRepository;

    public void insert(UsuarioCadastroDto dto) throws ValidationsException {
        Optional<Pessoa> pessoa = pessoaRepository.findPessoaByIdentificadorEquals(dto.getPessoaId());

        if (pessoa.isEmpty()) {
            throw new ValidationsException(String.format("N\u00E3o foi encontrado cadastro com identificador %s", dto.getPessoaId()));
        }

        Optional<Usuario> usuario = usuarioRepository.findByNomeEquals(dto.getUsername());

        if (usuario.isPresent()) {
            throw new ValidationsException("Usu\u00E1rio j\u00E1 cadastrado!");
        }

        Usuario newUsuario = new Usuario();
        newUsuario.setNome(dto.getUsername());
        newUsuario.setPerfil(dto.getPerfil());
        newUsuario.setSenha(new ArrayList<>());

        SenhaUsuario senha = new SenhaUsuario();
        senha.setSenha(PasswordUtils.encrypt(dto.getPassword()));
        senha.setStatus(StatusSenha.ATIVO);
        senha.setUltimaAtualizacao(LocalDateTime.now());

        newUsuario.getSenha().add(senha);

        Pessoa toUpdate = pessoa.get();
        toUpdate.setUsuario(newUsuario);

        pessoaRepository.save(toUpdate);
    }

    public void update(String username, UsuarioDto usuarioDto) {

    }

    public void updatePassword(String username, String password) {

    }


    public UsuarioDto findByUsername(String username) {
        return null;
    }
}
