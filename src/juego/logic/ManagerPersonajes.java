package juego.logic;

import juego.model.Jugador;

import java.util.ArrayList;
import java.util.List;

public class ManagerPersonajes {

    private static final List<Jugador> jugadores = new ArrayList<>();

    public ManagerPersonajes() {
        inicializarPersonajes();
    }

    private void inicializarPersonajes() {
        jugadores.add(new Jugador("Guerrero", 100, 20, 10));
        jugadores.add(new Jugador("Tanque", 120, 12, 25));
        jugadores.add(new Jugador("Mago", 80, 30, 5));
        jugadores.add(new Jugador("Asesino", 90, 24, 8));
        jugadores.add(new Jugador("Tester", 1000, 135, 1));
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
}
