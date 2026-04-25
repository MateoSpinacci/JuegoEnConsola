package juego.logic;

import juego.model.Enemigo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerEnemigos {

    private static final List<Enemigo> FACILES = new ArrayList<>();
    private static final List<Enemigo> NORMALES = new ArrayList<>();
    private static final List<Enemigo> DIFICILES = new ArrayList<>();
    private static final Random rand = new Random();

    public ManagerEnemigos() {
        inicializarEnemigos();
    }

    private void inicializarEnemigos() {
        FACILES.add(new Enemigo(1, "Araña", 50, 15, 0, false, 0));
        FACILES.add(new Enemigo(1, "Rata", 40, 12, 0, false, 0));
        FACILES.add(new Enemigo(1, "Murciélago", 30, 10, 0, true, 10));
        FACILES.add(new Enemigo(1, "Slime", 60, 8, 2, false, 0));
        FACILES.add(new Enemigo(1, "Zombie", 75, 5, 0, false, 0));
        FACILES.add(new Enemigo(1, "Lobo", 55, 12, 1, true, 15));

        NORMALES.add(new Enemigo(2, "Esqueleto", 60, 18, 5, true, 20));
        NORMALES.add(new Enemigo(2, "Fantasma", 80, 20, 10, true, 25));
        NORMALES.add(new Enemigo(2, "Duende", 90, 22, 5, true, 15));
        NORMALES.add(new Enemigo(2, "Araña Mutante", 100, 15, 15, true, 20));
        NORMALES.add(new Enemigo(2, "Chiqui Demo", 85, 25, 5, true, 30));
        NORMALES.add(new Enemigo(2, "Golem Pequeño", 120, 10, 20, true, 10));

        DIFICILES.add(new Enemigo(3, "Demonio", 110, 30, 10, true, 35));
        DIFICILES.add(new Enemigo(3, "Golem", 150, 15, 30, true, 15));
        DIFICILES.add(new Enemigo(3, "Bestia", 130, 35, 5, true, 40));
        DIFICILES.add(new Enemigo(3, "Caballero Gordo", 120, 25, 20, true, 20));
        DIFICILES.add(new Enemigo(3, "Gigante", 160, 20, 25, true, 25));
        DIFICILES.add(new Enemigo(3, "Sombra", 100, 40, 5, true, 45));
        //DIFICILES.add(new Enemigo(3, "TESTER", 100, 5, 5, true, 50));

        }

    public Enemigo generarEnemigo(int piso) {
        int roll = rand.nextInt(100);
        List<Enemigo> lista;
        if (piso <= 3) {
            if (roll < 80) {
                lista = FACILES;
            } else {
                lista = NORMALES;
            }
        } else if (piso <= 7) {
            if (roll < 50) {
                lista = FACILES;
            } else if (roll < 85) {
                lista = NORMALES;
            } else {
                lista = DIFICILES;
            }
        } else {
            if (roll < 30) {
                lista = NORMALES;
            } else {
                lista = DIFICILES;
            }
        }
        Enemigo base = lista.get(rand.nextInt(lista.size()));
        //Enemigo base = DIFICILES.get(6);
        return base.copiar();
    }
}

