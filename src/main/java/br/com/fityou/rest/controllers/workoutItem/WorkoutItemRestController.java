package br.com.fityou.rest.controllers.workoutItem;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WorkoutItemRestController {

        private RestTemplate restTemplate;

        WorkoutItemRestController() {
                restTemplate = new RestTemplate();
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        }

        @Value("${data.url}")
        private String dataUrl;

        @Value("${error.msg}")
        private String generalErrorMsg;

        @PostMapping("/workoutCreation")
        public WorkoutCreationResponse saveWorkout(@RequestBody WorkoutCreationRequest request) {

                for (WorkoutItemRequest item : request.getItems()) {
                        WorkoutItemResponse workItem = new WorkoutItemResponse();
                        if (item.getId() != null) {
                                workItem = restTemplate.patchForObject(
                                                dataUrl.concat("workoutItems/").concat(item.getId().toString()),
                                                item, WorkoutItemResponse.class);

                        } else {
                                workItem = restTemplate.postForObject(dataUrl.concat("workoutItems"), item,
                                                WorkoutItemResponse.class);

                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.valueOf("text/uri-list"));

                                HttpEntity<String> entity = new HttpEntity<>(
                                                dataUrl.concat("exercises/").concat(item.getExerciseId().toString()),
                                                headers);

                                restTemplate.exchange(
                                                dataUrl.concat("workoutItems/").concat(workItem.getId().toString())
                                                                .concat("/exercise"),
                                                HttpMethod.PUT, entity, WorkoutItemRequest.class);

                                entity = new HttpEntity<>(
                                                dataUrl.concat("exercises/").concat(workItem.getId().toString()),
                                                headers);

                                restTemplate.exchange(
                                                dataUrl.concat("workouts/").concat(request.getId().toString())
                                                                .concat("/workoutItems"),
                                                HttpMethod.PATCH, entity, WorkoutItemRequest.class);
                        }

                }

                WorkoutCreationResponse response = restTemplate.getForObject(
                                dataUrl.concat("workouts/").concat(request.getId().toString())
                                                .concat("?projection=workoutCompleteProjection"),
                                WorkoutCreationResponse.class);

                for (WorkoutItemResponse existingWorkoutItem : response.getWorkoutItems()) {
                        boolean exists = false;
                        for (WorkoutItemRequest requestedWorkoutItems : request.getItems()) {
                                if (existingWorkoutItem.getExercise().getId() == requestedWorkoutItems
                                                .getExerciseId()) {
                                        exists = true;
                                }
                        }
                        if (!exists) {
                                restTemplate.delete(dataUrl.concat("workouts/").concat(request.getId().toString())
                                                .concat("/workoutItems/")
                                                .concat(existingWorkoutItem.getId().toString()));
                        }
                }

                return restTemplate.getForObject(
                                dataUrl.concat("workouts/").concat(request.getId().toString())
                                                .concat("?projection=workoutCompleteProjection"),
                                WorkoutCreationResponse.class);

        }
}
