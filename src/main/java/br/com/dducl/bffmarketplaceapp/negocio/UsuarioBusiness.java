package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.UsuarioCadastroDto;
import br.com.dducl.bffmarketplaceapp.dto.UsuarioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.SenhaUsuario;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Usuario;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.FornecedorRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PessoaRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.UsuarioRepository;
import br.com.dducl.bffmarketplaceapp.util.PasswordUtils;
import br.com.dducl.bffmarketplaceapp.util.conversores.UsuarioConversor;
import br.com.dducl.bffmarketplaceapp.util.enums.Perfil;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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

    @Resource
    private FornecedorRepository fornecedorRepository;

    public void insert(UsuarioCadastroDto dto) throws ValidationsException {
        validaUsuarioInserir(dto);

        Usuario usuario = conversor.converte(dto);
        usuario.setSenha(new ArrayList<>());

        SenhaUsuario senha = new SenhaUsuario(PasswordUtils.encrypt(dto.getPassword()));
        usuario.getSenha().add(senha);

        usuario = usuarioRepository.save(usuario);

        Pessoa pessoa = pessoaRepository.findPessoaByIdentificadorEquals(dto.getPessoaId()).get();

        pessoa.setUsuario(usuario);
    }

    private void validaUsuarioInserir(UsuarioCadastroDto dto) throws ValidationsException {
        Optional<Pessoa> pessoa = pessoaRepository.findPessoaByIdentificadorEquals(dto.getPessoaId());

        if (pessoa.isEmpty()) {
            throw new ValidationsException(String.format("N\u00E3o foi encontrado cadastro com identificador %s", dto.getPessoaId()));
        }

        validaTipoPerfil(dto.getPessoaId(), dto.getPerfil());

        validaUsuarioJaCadastrado(dto.getUsername());
    }

    private void validaTipoPerfil(String identificador, Perfil perfil) throws ValidationsException {
        if (Perfil.FORNECEDOR.equals(perfil)) {
            Optional<Fornecedor> optional = fornecedorRepository.findFornecedorByPessoaIdentificador(identificador);

            if (optional.isEmpty()) {
                throw new ValidationsException("Para perfil de fornecedor, o identificar deve ser de um fornecedor!");
            }
        }
    }

    private void validaUsuarioJaCadastrado(String username) throws ValidationsException {
        Optional<Usuario> usuario = usuarioRepository.findByNomeEquals(username);

        if (usuario.isPresent()) {
            throw new ValidationsException("Usu\u00E1rio j\u00E1 cadastrado!");
        }
    }

    public void updatePassword(String username, String password) throws NotFoundException, ValidationsException {
        Optional<Usuario> optional = usuarioRepository.findByNomeEquals(username);

        if (optional.isEmpty()) {
            throw new NotFoundException(username, "Usu\u00E1rio");
        }

        String senhaEncriptografada = PasswordUtils.encrypt(password);

        if (isSenhaJaUtilizada(senhaEncriptografada, username)) {
            throw new ValidationsException("Nova senha n\u00E3o pode igual a senhas anteriores!");
        }

        Usuario usuario = optional.get();

        usuario.getSenha().forEach(senha -> {
            if (senha.isSenhaAtiva()) {
                senha.desativaSenha();
            }
        });

        usuario.getSenha().add(new SenhaUsuario(senhaEncriptografada));

        usuarioRepository.save(usuario);
    }

    private boolean isSenhaJaUtilizada(String password, String username) {
        Optional<Usuario> usuario = usuarioRepository.findByNomeEqualsAndSenha_senha(username, password);

        return usuario.isPresent();
    }


    public UsuarioDto findByUsername(String username) throws NotFoundException {
        Optional<Usuario> optional = usuarioRepository.findByNomeEquals(username);

        if (optional.isEmpty()) {
            throw new NotFoundException(username, "Usu\u00E1rio");
        }

        return conversor.converte(optional.get());
    }
}
