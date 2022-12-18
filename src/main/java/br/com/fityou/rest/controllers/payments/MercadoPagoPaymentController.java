package br.com.fityou.rest.controllers.payments;

import java.net.http.HttpClient;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

@RestController
public class MercadoPagoPaymentController {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${data.url}")
    private String dataUrl;

    @Value("${error.msg}")
    private String generalErrorMsg;

    @Value("${mercadopago.token}")
    private String mercadoPagoToken;

    @PostMapping("/mercadopago/pay")
    public PaymentResponse pay(@RequestBody() PaymentRequest paymentRequest) {

        MercadoPagoConfig.setAccessToken(mercadoPagoToken);

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest createPayment = PaymentCreateRequest.builder()
                .transactionAmount(paymentRequest.getGrossAmount())
                .token(paymentRequest.getToken())
                .description("Pagamento de servÃÂ§o de Nutricionista e/ou Personal")
                .installments(paymentRequest.getInstallments())
                .paymentMethodId(paymentRequest.getPaymentMethod())
                .payer(PaymentPayerRequest.builder()
                        .email(paymentRequest.getMail())
                        .firstName(paymentRequest.getName())
                        .build())
                .build();

        Payment payment = null;
        try {
            payment = client.create(createPayment);
        } catch (MPApiException ex) {
            return new PaymentResponse("Erro ao registrar o pagamento", ex.getApiResponse().getContent(),
                    false);
        } catch (MPException ex) {
            return new PaymentResponse("Erro inesperado ao registrar o pagamento", "",
                    false);
        }

        paymentRequest.setNetAmount(payment.getTransactionDetails().getNetReceivedAmount());

        try {

            SSLContextBuilder sslcontext = new SSLContextBuilder();
            sslcontext.loadTrustMaterial(null, new TrustSelfSignedStrategy());

            CloseableHttpClient httpClient = HttpClientBuilder.create().setSslcontext(sslcontext.build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);

            restTemplate.setRequestFactory(factory);

            return restTemplate.patchForObject(dataUrl.concat("payments/").concat(paymentRequest.getId().toString()),
                    paymentRequest, PaymentResponse.class);
        } catch (Exception e) {
            return new PaymentResponse(
                    "O pagamento foi criado mas não foi possível atualizar o mesmo em nossas bases.",
                    e.getMessage(),
                    false);
        }

    }

}
