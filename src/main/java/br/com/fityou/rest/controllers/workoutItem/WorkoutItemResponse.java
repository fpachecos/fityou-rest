package br.com.fityou.rest.controllers.workoutItem;

public class WorkoutItemResponse {
    private Long id;

    private String repetitions;

    private Integer series;

    private String comments;

    private ExerciseRespose exercise;

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseRespose getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseRespose exercise) {
        this.exercise = exercise;
    }

}
