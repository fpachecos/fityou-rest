/**
 * 
 */
package br.com.fityou.rest.controllers.person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("/people")
	public ResponsePerson addUser(@RequestBody RequestPerson newUser) {
		try {
			ResponsePerson responsePerson = restTemplate.postForObject(dataUrl.concat("people"), newUser,
					ResponsePerson.class);
			return responsePerson;
		} catch (Exception e) {
			if (e.getMessage().contains("not-null property references a null or transient")
					&& e.getMessage().contains("Person.name")) {
				return new ResponsePerson("Nome da pessoa não fornecido.", e.getMessage(),  false);
			}
			if (e.getMessage().contains("not-null property references a null or transient")
					&& e.getMessage().contains("Person.acceptRecieveMessages")) {
				return new ResponsePerson("É preciso aceitar ou negar receber mensagens.", e.getMessage(), false);
			}
			return new ResponsePerson(generalErrorMsg, e.getMessage(), false);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/people")
	public List<ResponsePerson> getPeople() {
		List<ResponsePerson> returnedList = (ArrayList<ResponsePerson>) ((LinkedHashMap<?, ?>) restTemplate
				.getForObject(dataUrl.concat("people").concat("?sort=name,asc"), LinkedHashMap.class)
				.get("_embedded")).get("person");
		return returnedList;
	}

	@DeleteMapping("/people/{id}")
	public ResponsePerson remove(@PathVariable() Long id) {
		try {
			restTemplate.delete(dataUrl.concat("people").concat("/").concat(id.toString()));

			return new ResponsePerson(
					"Aluno/a removido/a com sucesso.",
					null, true);
		} catch (Exception e) {
			return new ResponsePerson("Erro inesperado, tente novamente mais tarde.",
					e.getMessage(), false);
		}
	}
}
