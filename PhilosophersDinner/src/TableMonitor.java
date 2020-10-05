public class TableMonitor implements DinnerTable {

    private final Actions[] philosophersActions;
    private final Integer[] philosophers;
    private final int N;

    public TableMonitor(int N) {
        this.philosophersActions = new Actions[N];
        this.philosophers = new Integer[N];
        this.N = N;

        for (int i = 0; i < N; i++) {
            this.philosophersActions[i] = Actions.THINKING;
            this.philosophers[i] = new Integer(0);
        }
    }

    public int right(int id) {
        return (1 + id) % this.N;
    }

    public int left(int id) {
        return (this.N + id - 1) % this.N;
    }

    public void putForks(int id) {
        
        synchronized (philosophers[id]) {
            philosophersActions[id] = Actions.THINKING;
            this.test(left(id));
            this.test(right(id));
        }
    }

    public void takeForks(int id) {

        synchronized (philosophers[id]) {
            philosophersActions[id] = Actions.HUNGRY;
            this.test(id);

            while (philosophersActions[id] != Actions.EATING) {
                try {
                    this.philosophers[id].wait();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                }
            }
        }
    }

    private void test(int id) {
        if (philosophersActions[id] == Actions.HUNGRY && philosophersActions[this.left(id)] != Actions.EATING && philosophersActions[this.right(id)] != Actions.EATING) {
            philosophersActions[id] = Actions.EATING;
            System.out.println(String.format("The philosopher(%d) took the cutlery.", id));
            synchronized (philosophers[id]) {
                philosophers[id].notify();
            }
        }
    }
}
