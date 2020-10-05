public class Philosopher implements Runnable {
    private int id;
    private int eatTime;
    private int thinkTime;
    private DinnerTable dinnerTable;

    public Philosopher(int philosopherId, int eatTime, int thinkTime, DinnerTable dinnerTable) {
        this.id = philosopherId;
        this.eatTime = eatTime;
        this.thinkTime = thinkTime;
        this.dinnerTable = dinnerTable;
        new Thread(this, "Philosopher" + philosopherId).start();
    }

    @Override
    public void run() {
        while(true) {
            think();
            takeForks();
            eat();
            putForks();
        }
    }

    private void think() {
        try {
            System.out.println(String.format("The philosopher(%d) is thinking.", this.id));
            Thread.sleep(this.thinkTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void takeForks() {
        System.out.println(String.format("The philosopher(%d) tries to pick up two forks.", this.id));
        dinnerTable.takeForks(this.id);
    }

    private void eat() {
        try {
            System.out.println(String.format("The philosopher(%d) is eating.", this.id));
            Thread.sleep(this.eatTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void putForks() {
        System.out.println(String.format("The philosopher(%d) finished eating and returned the forks.", this.id));
        dinnerTable.putForks(this.id);
    }
}
