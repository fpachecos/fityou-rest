package br.com.fityou.rest.controllers.workoutItem;

import java.util.List;

public class WorkoutCreationRequest {

    private Long id;

    private String name;

    private String restTime;

    private List<WorkoutItemRequest> items;

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

    public List<WorkoutItemRequest> getItems() {
        return items;
    }

    public void setItems(List<WorkoutItemRequest> items) {
        this.items = items;
    }

}
