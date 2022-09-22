package br.com.fityou.rest.controllers.workout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.fityou.rest.controllers.workoutItem.WorkoutCreationResponse;
import br.com.fityou.rest.controllers.workoutItem.WorkoutItemRequest;
import br.com.fityou.rest.controllers.workoutItem.WorkoutItemResponse;

@RestController
public class WorkoutRestController {

    private RestTemplate restTemplate;

    WorkoutRestController() {
        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Value("${data.url}")
    private String dataUrl;

    @Value("${error.msg}")
    private String generalErrorMsg;

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/workouts")
    public List<WorkoutResponse> getWorkouts() {
        final List<WorkoutResponse> returnedList = (ArrayList<WorkoutResponse>) ((LinkedHashMap<?, ?>) restTemplate
                .getForObject(dataUrl.concat("workouts").concat("?sort=name,asc"), LinkedHashMap.class)
                .get("_embedded")).get("workout");
        return returnedList;
    }

    @GetMapping("/workouts/{id}")
    public WorkoutResponse getWorkout(@PathVariable() Long id) {
        final WorkoutResponse returned = restTemplate
                .getForObject(
                        dataUrl.concat("workouts").concat("/" + id).concat("?projection=workoutCompleteProjection"),
                        WorkoutResponse.class);
        return returned;
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
    @DeleteMapping("/workouts/{id}")
    public WorkoutResponse removeWorkout(@PathVariable() Long id) {
        try {
            WorkoutCreationResponse response = restTemplate.getForObject(
                    dataUrl.concat("workouts/").concat(id.toString())
                            .concat("?projection=workoutCompleteProjection"),
                    WorkoutCreationResponse.class);

            for (WorkoutItemResponse existingWorkoutItem : response.getWorkoutItems()) {
                restTemplate.delete(dataUrl.concat("workouts/").concat(id.toString())
                        .concat("/workoutItems/")
                        .concat(existingWorkoutItem.getId().toString()));

            }

            restTemplate.delete(dataUrl.concat("workouts").concat("/").concat(id.toString()));
            return new WorkoutResponse("Treino removido com sucesso.", null, true);
        } catch (Exception e) {
            return new WorkoutResponse("Erro inesperado ao remover esse novo treino.", e.getMessage(), false);
        }
    }

    @PatchMapping("/workouts/{id}")
    public WorkoutResponse renameWorkout(@PathVariable() Long id, @RequestBody WorkoutRequest request) {
        try {
            if (request.getName() != null && !"".equals(request.getName())) {
                return restTemplate.patchForObject(
                        dataUrl.concat("workouts").concat("/").concat(id.toString()),
                        new WorkoutRenameRequest(request.getName()),
                        WorkoutResponse.class);
            }
            if (request.getRestTime() != null && !"".equals(request.getRestTime())) {
                return restTemplate.patchForObject(
                        dataUrl.concat("workouts").concat("/").concat(id.toString()),
                        new WorkoutRestTimeUpdateRequest(request.getRestTime()),
                        WorkoutResponse.class);
            }
            return new WorkoutResponse("Ou o nome ou o tempo de descanço são obrigatórios para a atualização", null
                    , false);
        } catch (Exception e) {
            e.printStackTrace();
            return new WorkoutResponse("Houve alguns problema ao renomear esse treino", e.getMessage(), false);
        }
    }

}