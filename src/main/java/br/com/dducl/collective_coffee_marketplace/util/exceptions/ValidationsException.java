package br.com.dducl.collective_coffee_marketplace.util.exceptions;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationsException extends Exception {

    public ValidationsException(String message) {
        super(message);
    }
}
