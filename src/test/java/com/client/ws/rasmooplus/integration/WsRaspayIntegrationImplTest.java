package com.client.ws.rasmooplus.integration;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class WsRaspayIntegrationImplTest {
    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOk(){
        CustomerDto dto = new CustomerDto(null, "10138020981", "teste@test", "matheus", "perondi");
        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOk(){
        OrderDto dto = new OrderDto(null, "66d0c009afca645cbee8a74f", BigDecimal.ZERO, "MONTH22");
        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void processPaymentWhenDtoOk(){
        CreditCardDto creditCardDto = new CreditCardDto(123L, "10138020981", "1231313123123", 0L,0L, 2025L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, "66d0c009afca645cbee8a74f", "66d0c66dafca645cbee8a750");
        wsRaspayIntegration.processPayment(paymentDto);
    }

}
