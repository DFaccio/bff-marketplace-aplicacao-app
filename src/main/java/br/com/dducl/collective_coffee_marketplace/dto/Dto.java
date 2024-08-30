package br.com.dducl.collective_coffee_marketplace.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
}
