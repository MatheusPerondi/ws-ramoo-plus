package com.client.ws.rasmooplus.integration;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
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
}
