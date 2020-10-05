public enum Actions {
    THINKING(0), HUNGRY(1), EATING(2);

    private int description;

    Actions(int description) {
        this.description = description;
    }

    public int getDescription() {
        return description;
    }
}
