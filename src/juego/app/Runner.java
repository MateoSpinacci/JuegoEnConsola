package juego.app;

import juego.logic.Juego;

public class Runner {
    private static final Juego juego = new Juego();

    static void main(String[] args) throws InterruptedException {
        juego.iniciar();
    }
}
