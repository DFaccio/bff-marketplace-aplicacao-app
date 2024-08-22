package br.com.dducl.collective_coffee_marketplace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
}
