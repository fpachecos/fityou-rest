package br.com.fityou.rest.controllers.payments;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentsController {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${data.url}")
    private String dataUrl;

    @Value("${error.msg}")
    private String generalErrorMsg;

    @GetMapping("/payments")
    public List<PaymentResponse> getPayments() {
        final List<PaymentResponse> returnedList = (List<PaymentResponse>) ((LinkedHashMap<?, ?>) restTemplate
                .getForObject(dataUrl.concat("payments").concat("?sort=name,asc"), LinkedHashMap.class)
                .get("_embedded")).get("payment");
        return returnedList;
    }

    @GetMapping("/payments/{id}")
    public PaymentResponse getPayments(@PathVariable Long id) {
        return restTemplate
                .getForObject(dataUrl.concat("payments/").concat(id.toString()), PaymentResponse.class);
    }

    @PostMapping("/payments/intention")
    public PaymentResponse createPaymentIntention(@RequestBody PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse;

        try {
            paymentResponse = restTemplate.postForObject(dataUrl.concat("payments"), paymentRequest,
                    PaymentResponse.class);
        } catch (Exception e) {
            return new PaymentResponse("Erro ao criar solicitação de pagamento.", e.getMessage(), false);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("text/uri-list"));

            HttpEntity<String> entity = new HttpEntity<>(
                    dataUrl.concat("people/").concat(paymentRequest.getPersonId().toString()),
                    headers);

            ResponseEntity<PaymentResponse> response = restTemplate.exchange(
                    dataUrl.concat("payments/").concat(paymentResponse.getId().toString()).concat("/person"),
                    HttpMethod.PUT, entity, PaymentResponse.class);

        } catch (Exception e) {
            restTemplate.delete(dataUrl.concat("payments/").concat(paymentResponse.getId().toString()));
            return new PaymentResponse(
                    "Erro ao criar solicitação de pagamento para o Aluno indicado.", 
                    e.getMessage(),
                    false);
        }
        return paymentResponse;
    }
}
