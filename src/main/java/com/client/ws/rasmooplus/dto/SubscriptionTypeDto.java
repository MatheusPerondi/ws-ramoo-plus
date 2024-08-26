package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionTypeDto {

    private Long id;

    @NotBlank(message = "campo name não pode ser nulo ou vazio")
    @Size(min = 5, max = 30)
    private String name;

    @Max(value = 12, message = "campo acessMonth não pode ser maior que 12")
    private Long accessMonths;

    @NotNull(message = "campo price não pode ser nulo")
    private BigDecimal price;

    @NotBlank(message = "campo productKey não pode ser nulo ou vazio")
    @Size(min = 5, max = 15, message = "campo productKey deve ter tamanho entre 5 e 15")
    private String productKey;
}
