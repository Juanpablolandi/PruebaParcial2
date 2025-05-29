import java.util.Random;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.util.List;

class Partido implements Callable<Resultado> {
    private final Jugador jugador1;
    private final Jugador jugador2;
    private final Random random = new Random();

    public Partido(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    @Override
    public Resultado call() {
        List<Jugador> sets = new ArrayList<>();
        int puntosJ1 = 0, puntosJ2 = 0;

        while (puntosJ1 < 2 && puntosJ2 < 2) {
            boolean ganaJ1 = random.nextBoolean();
            if (ganaJ1) {
                puntosJ1++;
                sets.add(jugador1);
            } else {
                puntosJ2++;
                sets.add(jugador2);
            }
        }

        Jugador ganador = (puntosJ1 == 2) ? jugador1 : jugador2;
        return new Resultado(jugador1, jugador2, sets, ganador);
    }
}
