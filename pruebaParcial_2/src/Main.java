import java.util.Random;

import java.util.*;
import java.util.concurrent.*;

public static void main(String[] args) {
    int numCores = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(numCores);

    List<Jugador> jugadores = new ArrayList<>();
    for (int i = 1; i <= 16; i++) {
        jugadores.add(new Jugador(i));
    }

    try {
        jugarRonda("OCTAVOS DE FINAL", jugadores, executor);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        executor.shutdown();
    }
}




public static List<Resultado> jugarRonda(String nombreRonda, List<Jugador> jugadores, ExecutorService executor) throws InterruptedException, ExecutionException {
    System.out.println("===== " + nombreRonda + " =====");

    List<Future<Resultado>> futuros = new ArrayList<>();
    for (int i = 0; i < jugadores.size(); i += 2) {
        futuros.add(executor.submit(new Partido(jugadores.get(i), jugadores.get(i + 1))));
    }

    List<Resultado> resultados = new ArrayList<>();
    List<Jugador> ganadores = new ArrayList<>();

    for (Future<Resultado> futuro : futuros) {
        Resultado r = futuro.get();
        resultados.add(r);
        ganadores.add(r.ganador());
    }

    for (Resultado r : resultados) {
        System.out.println(r.jugador1() + " vs " + r.jugador2());
        for (int i = 0; i < r.sets().size(); i++) {
            System.out.println("Set " + (i + 1) + ": " + r.sets().get(i));
        }
        System.out.println("Ganador del partido: " + r.ganador() + "\n");
    }

    if (ganadores.size() == 1) {
        System.out.println("🏆 ¡Campeón del torneo: " + ganadores.get(0) + "!");
    } else {
        String siguienteRonda = switch (ganadores.size()) {
            case 8 -> "CUARTOS DE FINAL";
            case 4 -> "SEMIFINAL";
            case 2 -> "FINAL";
            default -> "RONDA";
        };
        jugarRonda(siguienteRonda, ganadores, executor);
    }

    return resultados;
}

