package br.com.dducl.collective_coffee_marketplace.util;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor
public class MessageUtil {

    private static final String MESSAGE_FILE_PATH = "/messages.properties";

    private static final Properties properties = load();

    private static Properties load() {
        Properties props = new Properties();

        try (InputStream fileInputStream = MessageUtil.class.getResourceAsStream(MESSAGE_FILE_PATH)) {
            props.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error on message file!", e);
        }

        return props;
    }

    public static String getMessage(String code) {
        return properties.getProperty(code);
    }

    public static String getMessage(String code, String... values) {
        return String.format(getMessage(code), values);
    }
}