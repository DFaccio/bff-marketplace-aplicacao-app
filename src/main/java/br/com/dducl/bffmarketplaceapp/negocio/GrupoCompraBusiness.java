package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraCadastroUpdateDto;
import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraFullDto;
import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.GrupoCompra;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.GrupoCompraRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.conversores.GrupoCompraConversor;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoCompraBusiness {

    @Resource
    private GrupoCompraRepository repository;

    @Resource
    private GrupoCompraConversor conversor;

    @Resource
    private PessoaBusiness pessoaBusiness;

    public GrupoCompraFullDto insert(GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        GrupoCompra grupoCompra = conversor.converte(getPessoas(grupoCompraDto.getPessoas()), grupoCompraDto);
        grupoCompra.setDataCriacao(LocalDateTime.now());

        grupoCompra = repository.save(grupoCompra);

        return conversor.converte(grupoCompra);
    }

    private List<PessoaDto> getPessoas(List<String> identificadores) throws NotFoundException {
        List<PessoaDto> pessoas = new ArrayList<>();

        for (String identificador : identificadores) {
            pessoas.add(pessoaBusiness.findByIdentificador(identificador));
        }

        return pessoas;
    }

    public GrupoCompraFullDto update(GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        Optional<GrupoCompra> optional = repository.findById(grupoCompraDto.getId());

        if (optional.isEmpty()) {
            throw new NotFoundException(grupoCompraDto.getId(), "Grupo de Compra");
        }

        GrupoCompra grupoCompra = conversor.converte(getPessoas(grupoCompraDto.getPessoas()), grupoCompraDto);
        grupoCompra.setDataCriacao(optional.get().getDataCriacao());

        grupoCompra = repository.save(grupoCompra);

        return conversor.converte(grupoCompra);
    }

    public ResultadoPaginado<GrupoCompraFullDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("id"));

        Page<GrupoCompra> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }
}
