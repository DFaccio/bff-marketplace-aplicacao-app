package br.com.dducl.collective_coffee_marketplace.negocio.chave_pix;

import br.com.dducl.collective_coffee_marketplace.TestUtils;
import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.chavespix.ChavesPixRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa.PessoaRepository;
import br.com.dducl.collective_coffee_marketplace.negocio.ChavePixBusiness;
import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ChavePixBusinessTest extends TestUtils {

    @Mock
    private ChavesPixRepository repository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private ChavePixBusiness business;

    private static final String PATH = "src/test/java/br/com/dducl/collective_coffee_marketplace/negocio/chave_pix/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName(value = "Teste lança exceção documento não encontrado - insert")
    void testThrowExceptionDocumentNotFound() {
        when(pessoaRepository.findPessoaByDocumentoEquals(any(String.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                business.insert("333.999.888-89", new ArrayList<>()));

        assertEquals(
                MessageUtil.getMessage("PESSOA_DOCUMENTO_NAO_ENCONTRADO"),
                exception.getMessage());
    }

    @Test
    @DisplayName(value = "Lança exceçao chave não encontrada - update")
    void testThrowExceptionKeyNotFound() {
        when(repository.findByChaveAndDocumentoPessoa(any(String.class), any(String.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                business.update("333.999.888-89", "chave", new ChavesPixDto()));

        assertEquals(
                MessageUtil.getMessage("CHAVE_NAO_ENCONTRADA_DOCUMENTO_PESSOA"),
                exception.getMessage());
    }

    @Test
    @DisplayName(value = "Lança exceçao chave já cadastrada - update")
    void testThrowExceptionKeyAlreadySaved() {
        when(repository.findByChaveAndDocumentoPessoa(any(String.class), any(String.class)))
                .thenReturn(Optional.of(new ChavesPix()));

        ValidationsException exception = assertThrows(ValidationsException.class, () ->
                business.update("333.999.888-89", "chave", new ChavesPixDto("chave-nova", true)));

        assertEquals(
                MessageUtil.getMessage("CHAVE_JA_CADASTRADA"),
                exception.getMessage());
    }

    @Test
    @DisplayName(value = "Teste inserir - sucesso")
    void testInserir() throws IOException, NotFoundException, NoSuchFieldException, ValidationsException, JSONException {
        List<ChavesPixDto> chaves = getMockAsClass(PATH + "insert_dto.json", new TypeReference<List<ChavesPixDto>>() {
        });

        Pessoa pessoa = getMockAsClass(PATH + "pessoa.json", Pessoa.class);

        when(pessoaRepository.findPessoaByDocumentoEquals(any(String.class)))
                .thenReturn(Optional.of(pessoa));

        when(pessoaRepository.save(any(Pessoa.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        fixClock();

        business.insert("123.895.659-89", chaves);

        super.assertJsonEquals(
                super.getExpectedResult(PATH + "insert_should.json", Pessoa.class),
                super.objectMapper.writeValueAsString(pessoa)
        );
    }

    private void fixClock() {
        when(Instant.now(clock))
                .thenReturn(Instant.parse("2024-01-01T10:00:00Z"));

        when(clock.getZone())
                .thenReturn(ZoneId.of("America/Sao_Paulo"));
    }
}