package br.com.dducl.collective_coffee_marketplace.util.exceptions;

import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;

public class NotFoundException extends Exception {

    public NotFoundException(Integer id, String entity) {
        super(String.format("%s com id %d n\u00E3o foi encontrado(a)!", entity, id));
    }

    public NotFoundException(String identificador, String entity) {
        super(String.format("%s com identificador %s n\u00E3o foi encontrado(a)!", entity, identificador));
    }

    public NotFoundException(String code) {
        super(MessageUtil.getMessage(code));
    }
}
