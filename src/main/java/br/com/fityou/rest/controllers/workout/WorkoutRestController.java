package br.com.fityou.rest.controllers.workout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WorkoutRestController {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${data.url}")
    private String dataUrl;

    @Value("${error.msg}")
    private String generalErrorMsg;

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/workouts")
    public List<WorkoutResponse> getExcercises() {
        final List<WorkoutResponse> returnedList = (ArrayList<WorkoutResponse>) ((LinkedHashMap<?, ?>) restTemplate
                .getForObject(dataUrl.concat("workouts").concat("?sort=name,asc"), LinkedHashMap.class)
                .get("_embedded")).get("workout");
        return returnedList;
    }

    /**
     * @param workoutRequest
     * @return
     */
    @PostMapping("/workouts")
    public WorkoutResponse addWorkout(@RequestBody WorkoutRequest workoutRequest) {

        try {
            return restTemplate.postForObject(dataUrl.concat("workouts"), workoutRequest,
                    WorkoutResponse.class);
        } catch (Exception e) {
            return new WorkoutResponse("Erro inesperado ao incluir esse novo treino.", e.getMessage(), false);
        }
    }

    /**
     * @param workoutRequest
     * @return
     */
    @DeleteMapping("/workouts")
    public WorkoutResponse removeWorkout(@RequestBody WorkoutRequest workoutRequest) {
        try {
            restTemplate.delete(dataUrl.concat("workouts").concat(workoutRequest.getId().toString()));
            return new WorkoutResponse("Treino removido com sucesso.", null, true);
        } catch (Exception e) {
            return new WorkoutResponse("Erro inesperado ao incluir esse novo treino.", e.getMessage(), false);
        }
    }

}