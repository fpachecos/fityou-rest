package br.com.fityou.rest.controllers.workout;

import java.util.List;

import br.com.fityou.rest.controllers.workoutItem.WorkoutItemResponse;

public class WorkoutResponse {

    public WorkoutResponse() {
        super();
        setIsSuccess(true);
    };

    public WorkoutResponse(String message, String technicalMessage, Boolean isSuccess) {
        this.message = message;
        this.technicalMessage = technicalMessage;
        this.isSuccess = isSuccess;
    }

    private Long id;

    private String name;

    private String restTime;

    private List<WorkoutItemResponse> workoutItems;

    private String message;

    private String technicalMessage;

    private Boolean isSuccess;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTechnicalMessage() {
        return technicalMessage;
    }

    public void setTechnicalMessage(String technicalMessage) {
        this.technicalMessage = technicalMessage;
    }

    public List<WorkoutItemResponse> getWorkoutItems() {
        return workoutItems;
    }

    public void setWorkoutItems(List<WorkoutItemResponse> workoutItems) {
        this.workoutItems = workoutItems;
    }

}