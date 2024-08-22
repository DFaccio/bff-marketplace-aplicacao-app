package br.com.dducl.collective_coffee_marketplace.util.exceptions;


import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;

@Setter
@Getter
public class ValidationsException extends Exception {

    public ValidationsException(String code) {
        super(MessageUtil.getMessage(code));
    }
}
