package com.client.ws.rasmooplus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

    private Long cvv;

    private String documentNumber;

    private String number;

    private Long installments;

    private Long month;

    private Long year;
}
