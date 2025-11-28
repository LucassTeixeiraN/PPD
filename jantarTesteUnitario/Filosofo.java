import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Filosofo implements Runnable {

    private final Random random = new Random();
    private final String nome;
    private final int id;
    private final ReentrantLock hashiEsquerdo;
    private final ReentrantLock hashiDireito;

    public Filosofo(String nome, int id, ReentrantLock hashiEsquerdo, ReentrantLock hashiDireito) {
        this.nome = nome;
        this.id = id;
        this.hashiEsquerdo = hashiEsquerdo;
        this.hashiDireito = hashiDireito;
    }

    public ReentrantLock getHashiEsquerdo() { return hashiEsquerdo; }
    public ReentrantLock getHashiDireito() { return hashiDireito; }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                this.pensar();
                this.pegarHashisEComer(i + 1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pegarHashisEComer(int turn) throws InterruptedException {
        boolean comeu = false;
        while (!comeu) {
            if (this.hashiEsquerdo.tryLock()) {
                try {
                    if (hashiDireito.tryLock()) {
                        try {
                            this.jantar(turn);
                            comeu = true; 
                        } finally {
                            hashiDireito.unlock();
                        }
                    } 
                } finally {
                    hashiEsquerdo.unlock();
                }
            }
            if (!comeu) {
                Thread.sleep(random.nextInt(100) + 50);
            }
        }
    }

    public void jantar(int turn) throws InterruptedException {
        Thread.sleep(10); 
    }

    public void pensar() throws InterruptedException {
        Thread.sleep(10);
    }
}