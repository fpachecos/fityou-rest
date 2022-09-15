/**
 * 
 */
package br.com.fityou.rest.controllers.person;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author fpach
 *
 */
@RestController
public class PersonRestController {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${data.url}")
	private String dataUrl;
	
	@Value("${error.msg}")
	private String generalErrorMsg;

	
	@PostMapping("/people/createPerson")
	public ResponsePerson addUser(@RequestBody RequestPerson newUser) {
		try {
			ResponsePerson responsePerson = restTemplate.postForObject(dataUrl.concat("people"), newUser, ResponsePerson.class);
			return responsePerson;
		}catch (Exception e) {
			if(e.getMessage().contains("not-null property references a null or transient") && e.getMessage().contains("Person.name")) {
				return new ResponsePerson("Nome da pessoa não fornecido.", e.getMessage());
			}
			if(e.getMessage().contains("not-null property references a null or transient") && e.getMessage().contains("Person.acceptRecieveMessages")) {
				return new ResponsePerson("É preciso aceitar ou negar receber mensagens.", e.getMessage());
			}
			return new ResponsePerson(generalErrorMsg, e.getMessage());
		}
	}
}
