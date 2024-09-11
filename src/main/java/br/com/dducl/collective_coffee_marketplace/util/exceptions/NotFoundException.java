package br.com.dducl.collective_coffee_marketplace.util.exceptions;

import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;

public class NotFoundException extends Exception {

    public NotFoundException(String code) {
        super(MessageUtil.getMessage(code));
    }
}
