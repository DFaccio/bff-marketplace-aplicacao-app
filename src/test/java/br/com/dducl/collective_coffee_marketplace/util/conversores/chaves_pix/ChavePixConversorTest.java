package br.com.dducl.collective_coffee_marketplace.util.conversores.chaves_pix;

import br.com.dducl.collective_coffee_marketplace.TestUtils;
import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;
import br.com.dducl.collective_coffee_marketplace.util.conversores.ChavePixConversor;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ChavePixConversorTest extends TestUtils {

    private final ChavePixConversor conversor = new ChavePixConversor();

    private static final String PATH = "src/test/java/br/com/dducl/collective_coffee_marketplace/util/conversores/chaves_pix/";

    @Test
    void testConverteDtoToEntidade() throws IOException, JSONException {
        ChavesPix toConvert = super.getMockAsClass(PATH + "entidade.json", ChavesPix.class);

        ChavesPixDto shouldBe = conversor.converte(toConvert);

        ChavesPixDto expected = getMockAsClass(PATH + "dto.json", ChavesPixDto.class);
        expected.setId(1);

        super.assertJsonEquals(
                super.objectMapper.writeValueAsString(expected),
                super.objectMapper.writeValueAsString(shouldBe));
    }

    @Test
    void testConverteEntidadeToDto() throws IOException, JSONException {
        ChavesPixDto toConvert = super.getMockAsClass(PATH + "dto.json", ChavesPixDto.class);
        toConvert.setId(1);

        ChavesPix shouldBe = conversor.converte(toConvert);

        super.assertJsonEquals(
                getExpectedResult(PATH + "entidade.json", ChavesPix.class),
                super.objectMapper.writeValueAsString(shouldBe));
    }
}