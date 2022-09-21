package br.com.fityou.rest.controllers.workout;

public class WorkoutRestTimeUpdateRequest {

    WorkoutRestTimeUpdateRequest(String restTime) {
        this.restTime = restTime;
    }

    private String restTime;

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

}