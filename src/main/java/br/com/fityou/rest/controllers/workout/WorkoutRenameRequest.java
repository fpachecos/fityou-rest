package br.com.fityou.rest.controllers.workout;

public class WorkoutRenameRequest {

    WorkoutRenameRequest(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}