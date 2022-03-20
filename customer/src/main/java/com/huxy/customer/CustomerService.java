package com.huxy.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class  CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO : Check if email is valid
        // TODO : Check if email not taken
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-chec k/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
                );

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("Customer a fraudster.");
        }
        // TODO : Send notification.
    }
}
