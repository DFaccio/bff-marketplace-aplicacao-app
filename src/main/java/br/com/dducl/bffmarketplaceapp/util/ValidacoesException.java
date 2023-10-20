package br.com.dducl.bffmarketplaceapp.util;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidacoesException extends Exception {

    public ValidacoesException(String message) {
        super(message);
    }
}
