package br.com.dducl.collective_coffee_marketplace.util.exceptions;

import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;

public class NotFoundException extends Exception {

    public NotFoundException(String... replace) {
        super(MessageUtil.getMessage("NAO_ENCONTRADO", replace));
    }
}
