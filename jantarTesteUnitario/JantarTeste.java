import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

class JantarTest {

    @Test
    @DisplayName("Deve configurar corretamente a topologia circular dos filósofos e locks")
    void testConfiguracaoInicialDoJantar() {
        Jantar jantar = new Jantar();

        jantar.iniciar();
        List<Filosofo> filosofos = jantar.getFilosofos();
        List<ReentrantLock> hashis = jantar.getHashis();

        assertNotNull(filosofos, "A lista de filósofos não pode ser nula");
        assertNotNull(hashis, "A lista de hashis não pode ser nula");
        assertEquals(5, filosofos.size(), "Devem existir 5 filósofos");
        assertEquals(5, hashis.size(), "Devem existir 5 hashis");

        
        for (int i = 0; i < 5; i++) {
            Filosofo atual = filosofos.get(i);
            
            ReentrantLock esperadoEsquerda = hashis.get(i);
            ReentrantLock esperadoDireita = hashis.get((i + 1) % 5);

            assertSame(esperadoEsquerda, atual.getHashiEsquerdo(), 
                "O Filósofo " + i + " tem o hashi esquerdo errado.");
                
            assertSame(esperadoDireita, atual.getHashiDireito(), 
                "O Filósofo " + i + " tem o hashi direito errado.");
        }
        
        assertNotSame(hashis.get(0), hashis.get(1), "Os hashis devem ser instâncias diferentes");
    }
}