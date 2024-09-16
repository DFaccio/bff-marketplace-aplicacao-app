package br.com.dducl.collective_coffee_marketplace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestUtils {

    protected final ObjectMapper objectMapper;

    public TestUtils() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    protected File getMock(String path) {
        return new File(path);
    }

    protected void assertJsonEquals(String jsonExpected, String jsonActual) throws JSONException, JsonProcessingException {
        JSONAssert.assertEquals(
                jsonExpected,
                jsonActual,
                false);
    }

    protected <T> T getMockAsClass(String filepath, Class<T> tClass) throws IOException {
        return this.objectMapper.readValue(
                getMock(filepath),
                tClass
        );
    }

    protected String getExpectedResult(String filepath, Class<?> tClass) throws IOException {
        return objectMapper.writeValueAsString(
                getMockAsClass(filepath, tClass)
        );
    }

    protected <T> List<T> getMockAsClass(String filepath, TypeReference<List<T>> typeReference) throws IOException {
        return this.objectMapper.readValue(
                getMock(filepath),
                typeReference
        );
    }
}
