package br.com.fityou.rest.controllers.mercadopago;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.customer.CustomerClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPSearchRequest;
import com.mercadopago.resources.customer.Customer;

@RestController
public class MercadoPagoRestController {

    @GetMapping("/mercadopago/cliente")
    public List<Customer> obterCliente(@RequestParam() String mail)
            throws MPException, MPApiException {

        MercadoPagoConfig.setAccessToken("TEST-1402136962917023-092210-5e7d14088ff4153c2653a6b403164fbf-29911661");

        CustomerClient client = new CustomerClient();

        Map<String, Object> filters = new HashMap<>();
        filters.put("email", mail);

        MPSearchRequest searchRequest = MPSearchRequest.builder().offset(0).limit(0).filters(filters).build();

        return client.search(searchRequest).getResults();
    }
}
