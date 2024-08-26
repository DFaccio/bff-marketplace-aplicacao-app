package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraFullDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoCompra;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.GrupoCompraRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.GrupoCompraConversor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GrupoCompraBusiness {

    @Resource
    private GrupoCompraRepository repository;

    @Resource
    private GrupoCompraConversor conversor;

    @Resource
    private PessoaBusiness pessoaBusiness;

    public GrupoCompraFullDto insert(GrupoCompraFullDto grupoCompraDto) throws NotFoundException, ValidationsException {
        GrupoCompra grupoCompra = conversor.converte(grupoCompraDto);
        grupoCompra.setDataCriacao(LocalDateTime.now());

        if (!grupoCompra.getPessoas().isEmpty()) {
            validateAndInsertPessoas(grupoCompra);
        }

        grupoCompra = repository.save(grupoCompra);

        return conversor.converte(grupoCompra);
    }

    private void validateAndInsertPessoas(GrupoCompra grupoCompra) {
        //TODO Verificar as pessoas que não tem documento cadastrado e enviar erro
        // TODO Adicioanr documento no findall de pessoas

    }

    public GrupoCompraFullDto update(GrupoCompraFullDto grupoCompraDto) throws NotFoundException, ValidationsException {
        Optional<GrupoCompra> optional = repository.findById(grupoCompraDto.getId());

        if (optional.isEmpty()) {
            throw new NotFoundException("NAO_ENCONTRADO", "Grupo de Compra");
        }

        GrupoCompra toUpdate = optional.get();
        GrupoCompra newValues = conversor.converte(grupoCompraDto);

        // todo finalizar alterações. Lembrar que as pessoas já devem ser cadastradas.
        // Administrador não deve ser alterado. Pessoas não mencionadas devem ser excluídas
        return null;
    }

    public ResultadoPaginado<GrupoCompraFullDto> findAll(boolean status, Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by(Sort.Order.asc("dataCriacao")));

        Page<GrupoCompra> pagina = repository.findByAtivo(status, pageable);

        return conversor.converteEntidades(pagina);
    }
}
