package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTypeDto {

    private Long id;

    @NotBlank(message = "Valor não pode ser nulo")
    @Size(min = 6, message = "valor minimo igual a 6")
    private String name;

    @NotBlank(message = "Valor não pode ser nulo")
    @Size(min = 10, message = "Valor minimo igual a 10 caracteres")
    private String description;
}
