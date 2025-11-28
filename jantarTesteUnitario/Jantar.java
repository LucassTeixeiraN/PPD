import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Jantar {
    
    private List<ReentrantLock> hashis;
    private List<Filosofo> filosofos;
    private final int NUM_FILOSOFOS = 5;

    public void iniciar() {
        hashis = new ArrayList<>();
        filosofos = new ArrayList<>();

        // 1. Cria os Hashis (Locks)
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            hashis.add(new ReentrantLock());
        }

        // 2. Cria os Filósofos e distribui os Hashis
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            ReentrantLock esquerda = hashis.get(i);
            ReentrantLock direita = hashis.get((i + 1) % NUM_FILOSOFOS); // Lógica Circular

            Filosofo f = new Filosofo("F" + (i + 1), i, esquerda, direita);
            filosofos.add(f);
        }
    }

    // Getters necessários para o Teste Unitário inspecionar a mesa
    public List<ReentrantLock> getHashis() {
        return hashis;
    }

    public List<Filosofo> getFilosofos() {
        return filosofos;
    }
}