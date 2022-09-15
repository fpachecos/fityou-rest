package br.com.fityou.rest.controllers.exercise;

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
public class ExerciseRestController {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${data.url}")
	private String dataUrl;

	@Value("${error.msg}")
	private String generalErrorMsg;

	@SuppressWarnings("unchecked")
	@GetMapping("/exercises")
	public List<ResponseExercise> getExcercises() {
		List<ResponseExercise> returnedList = (ArrayList<ResponseExercise>) ((LinkedHashMap<?, ?>) restTemplate
				.getForObject(dataUrl.concat("exercises").concat("?sort=name,asc"), LinkedHashMap.class).get("_embedded")).get("exercise");
		return returnedList;
	}

	@PostMapping("/exercises")
	public ResponseExercise addExercise(@RequestBody RequestExercise exercise) {
		try {
			exercise.setVideoLink(convertYouTubeUrlIntoEmbedUrl(exercise.getVideoLink()));

			restTemplate.postForLocation(dataUrl.concat("exercises"), exercise);

			return new ResponseExercise("Excercício adicionado com sucesso.", null, true);
		} catch (Exception e) {
			return new ResponseExercise("[Erro] Problemas ao adicionar exercício.", e.getMessage(), false);
		}
	}

	@DeleteMapping("/exercises/{id}")
	public ResponseExercise remove(@PathVariable() Long id) {
		try {
		restTemplate.delete(dataUrl.concat("exercises").concat("/").concat(id.toString()));
		
		return new ResponseExercise("Exercício removido com sucesso. Ele foi removido também dos treinos vinculados.",
				null, true);
		}catch (Exception e) {
			return new ResponseExercise("Erro inesperado, tente novamente mais tarde.",
					e.getMessage(), true);
		}
	}

	/**
	 * Receive a url from Youtube and conver to a Embed Url format
	 * 
	 * @param url (Eg. https://youtu.be/e3bbeQgaxa4)
	 * @return Converted Embed format Url (Eg.
	 *         https://youtube.com/embed/e3bbeQgaxa4)
	 */
	private String convertYouTubeUrlIntoEmbedUrl(String url) {
		String params;
		String videoCode;

		if (url != null && !"".equals(url)) {
			if (url.contains("?v=")) {
				String[] videoUrlParts = url.split("\\?");
				String[] paramsWithVideo = videoUrlParts[videoUrlParts.length - 1].split("&");
				params = "?"
						.concat(videoUrlParts[videoUrlParts.length - 1].replace(paramsWithVideo[0].concat("&"), ""));
				videoCode = paramsWithVideo[0].replace("v=", "");
			} else {
				String[] videoUrlParts = url.split("\\?");
				params = videoUrlParts.length == 1 ? "" : "?".concat(videoUrlParts[videoUrlParts.length - 1]);
				String videoUrl = videoUrlParts[0];
				String[] videoUrlRootParts = videoUrl.split("/");
				videoCode = videoUrlRootParts[videoUrlRootParts.length - 1];
			}
			return "https://youtube.com/embed/".concat(videoCode).concat(params);
		}
		return url;
	}

}
