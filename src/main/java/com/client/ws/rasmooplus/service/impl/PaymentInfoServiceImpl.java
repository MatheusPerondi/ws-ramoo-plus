package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.model.UserPaymentInfo;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;

    private final UserPaymentInfoRepository userPaymentInfoRepository;

    private final WsRaspayIntegration wsRaspayIntegration;

    private final MailIntegration mailIntegration;

    PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository, WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
    }
    @Override
    public Boolean process(PaymentProcessDto dto) {
        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()){
            throw new NotFoundException("Usuario não encontrado");
        }
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())){
            throw new BusinessException("Pagamento não pode ser processado pois o usuario ja possui assinatura");
        }

        CustomerDto customer = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));

        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customer.getId(), dto));

        PaymentDto paymentDto = PaymentMapper.build(customer.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        Boolean sucessPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(sucessPayment)){
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);
            mailIntegration.send(user.getEmail(), "Usuario: "+ user.getEmail() +" - Senha: alunorasmoo", "Acesso Liberado");

            return true;
        }


        return false;
    }
}
