import java.util.*;
import java.util.concurrent.*;

class Jugador {
    private final int id;

    public Jugador(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Jugador " + id;
    }
}


