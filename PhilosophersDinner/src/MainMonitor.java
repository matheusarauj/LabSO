public class MainMonitor {
    private static int PHILOSOPHERS = 5;
    private static int[] TIME_TO_THINK = {100, 100, 100, 100, 100};
    private static int[] TIME_TO_EAT = {100, 100, 100, 100, 100};

    public static void main(String[] args) {
        DinnerTable dinner = new TableMonitor(PHILOSOPHERS);

        for (int i = 0; i < PHILOSOPHERS; i++) {
            new Philosopher(i, TIME_TO_EAT[i], TIME_TO_THINK[i], dinner);
        }
    }
}
