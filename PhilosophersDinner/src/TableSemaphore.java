import java.util.concurrent.Semaphore;

public class TableSemaphore implements DinnerTable {

    private Semaphore mutex;
    private Actions[] philosophersActions;
    private Semaphore[] s;
    private int N;

    public TableSemaphore(int N) {
        this.mutex = new Semaphore(1);
        this.philosophersActions = new Actions[N];
        this.s = new Semaphore[N];
        this.N = N;

        for (int i = 0; i < N; i++) {
            this.philosophersActions[i] = Actions.THINKING;
            this.s[i] = new Semaphore(0);
        }
    }

    public int right(int id){
        return (1 + id) % this.N;
    }

    public int left(int id){
        return (this.N + id - 1) % this.N;
    }

    public void putForks(int id) {

        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }

        philosophersActions[id] = Actions.THINKING;

        this.test(left(id));
        this.test(right(id));

        this.mutex.release();
    }

    public void takeForks(int id) {

        try {
            this.mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }

        philosophersActions[id] = Actions.HUNGRY;

        this.test(id);

        this.mutex.release();

        try {
            s[id].acquire();
            System.out.println(String.format("The philosopher(%d) took the cutlery.", id));
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }
    }

    private void test(int id){
       if(philosophersActions[id] == Actions.HUNGRY && philosophersActions[this.left(id)] != Actions.EATING && philosophersActions[this.right(id)] != Actions.EATING){
           philosophersActions[id] = Actions.EATING;
           s[id].release();
       }
    }
}
