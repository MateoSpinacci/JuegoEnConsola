package juego.logic;

import juego.model.Enemigo;
import juego.model.FinalBoss;
import juego.model.Jugador;
import juego.model.MiniJefes;

import java.util.Random;

public class Combate {

    private final Random rand = new Random();
    private int danioProvocado;
    private int turnos = 0;
    private boolean etapaSinActivar = true;

    public boolean atacarJefe(MiniJefes minijefe, Jugador jugador) {
        if (!minijefe.isCarga() && rand.nextInt(100) < 5) {
            return true;
        }
        switch (minijefe.getTipo()) {
            case "CARGADOR":
                if (minijefe.isCarga()) {
                    System.out.println(minijefe.getNombre() + " lanza un ataque cargado!");
                    this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), true);
                    minijefe.setCarga(false);
                    return false;
                }
                if (minijefe.puedeCargar() && rand.nextInt(100) < minijefe.getProbabilidadCarga()) {
                    minijefe.setCarga(true);
                    return false;
                }
                this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
            case "BERSERKER":
                if (turnos == 2) {
                    minijefe.setAtaque(minijefe.getAtaque() + 5);
                    turnos = 0;
                    System.out.println(minijefe.getNombre() + " aumentó su ataque en 5");
                } else {
                    turnos++;
                }
                this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
            case "REGENERADOR":
                if (turnos == 2) {
                    System.out.println(minijefe.getNombre() + " regenero " + (Math.min(5, (minijefe.getDefensaMax() - minijefe.getDefensa())) + " de escudo"));
                    minijefe.setDefensa(Math.min(minijefe.getDefensa() + 5, minijefe.getDefensaMax()));
                    turnos = 0;
                } else {
                    turnos++;
                }
                this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
            case "ESQUIVADOR":
                this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
            case "VAMPIRO":
                this.danioProvocado = minijefe.atacar(jugador, jugador.isDefendiendose(), false);
                minijefe.curarse((this.danioProvocado * 25) / 100);
                System.out.println(minijefe.getNombre() + " se curo " + ((this.danioProvocado * 25) / 100));
                return false;
        }
        return false;
    }

    public boolean atacarEnemigo(Enemigo enemigo, Jugador jugador) {
        if (!enemigo.isCarga() && rand.nextInt(100) < 5) {
            return true;
        }
        if (enemigo.puedeCargar() && rand.nextInt(100) < enemigo.getProbabilidadCarga()) {
            enemigo.setCarga(true);
            return false;
        }
        if (enemigo.isCarga()) {
            System.out.println(enemigo.getNombre() + " lanza un ataque cargado!");
            this.danioProvocado = enemigo.atacar(jugador, jugador.isDefendiendose(), true);
            enemigo.setCarga(false);
            return false;
        }
        this.danioProvocado = enemigo.atacar(jugador, jugador.isDefendiendose(), false);
        return false;
    }

    public boolean atacarJugador(Enemigo enemigo, Jugador jugador) {
        if (enemigo.getDificultadInt() == 4) {
            MiniJefes minijefe = (MiniJefes) enemigo;
            if (minijefe.getTipo().equals("ESQUIVADOR") && rand.nextInt(100) < 30) {
                return true;
            }
        } else if (rand.nextInt(100) < 5) {
            return true;
        }
        jugador.atacar(enemigo, false, false);
        return false;
    }

    public boolean atacarJefeFinal(FinalBoss jefeFinal, Jugador jugador) {
        if (!jefeFinal.isCarga() && rand.nextInt(100) < 5) {
            return true;
        }
        switch (jefeFinal.getTipo()) {
            case "SATAN":
                if ((jefeFinal.getVida() <= jefeFinal.getVidaMax() / 2) && etapaSinActivar) {
                    System.out.println(jefeFinal.getNombre() + " se ha enfurecido... Busca atacarte con fuerza... ¡Cuidado!");
                    jefeFinal.setAtaque(jefeFinal.getAtaque() + 10);
                    jefeFinal.setProbabilidadCarga(60);
                    etapaSinActivar = false;
                }
                if (turnos == 3) {
                    turnos = 0;
                    System.out.println(jefeFinal.getNombre() + " lanza un ataque cargado!");
                    this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), true);
                    jefeFinal.setCarga(false);
                    return false;
                }
                if (jefeFinal.isCarga()) {
                    System.out.println(jefeFinal.getNombre() + " lanza un ataque cargado!");
                    this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), true);
                    jefeFinal.setCarga(false);
                    return false;
                }
                if (jefeFinal.puedeCargar() && rand.nextInt(100) < jefeFinal.getProbabilidadCarga()) {
                    jefeFinal.setCarga(true);
                    turnos++;
                    return false;
                }
                this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), false);
                turnos++;
                return false;
            case "TANQUE":
                if ((jefeFinal.getVida() <= jefeFinal.getVidaMax() / 2) && etapaSinActivar) {
                    System.out.println(jefeFinal.getNombre() + " se ha enfurecido... Busca atacarte con fuerza... ¡Cuidado!");
                    jefeFinal.setAtaque(jefeFinal.getAtaque() * 2);
                    etapaSinActivar = false;
                } else if ((jefeFinal.getVida() > jefeFinal.getVidaMax() / 2)) {
                    if (turnos == 2) {
                        System.out.println(jefeFinal.getNombre() + " regenero " + (Math.min(10, (jefeFinal.getDefensaMax() - jefeFinal.getDefensa())) + " de escudo"));
                        jefeFinal.setDefensa(Math.min(jefeFinal.getDefensa() + 10, jefeFinal.getDefensaMax()));
                        turnos = 0;
                    } else {
                        turnos++;
                    }
                }
                this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
            case "REY":
                if (jefeFinal.isCarga()) {
                    System.out.println(jefeFinal.getNombre() + " lanza un ataque cargado!");
                    this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), true);
                    jefeFinal.setCarga(false);
                    return false;
                }
                if (jefeFinal.puedeCargar() && rand.nextInt(100) < jefeFinal.getProbabilidadCarga()) {
                    jefeFinal.setCarga(true);
                    return false;
                }
                jefeFinal.setAtaque(jefeFinal.getAtaque() + 3);
                System.out.println(jefeFinal.getNombre() + " aumentó su ataque en 3");
                this.danioProvocado = jefeFinal.atacar(jugador, jugador.isDefendiendose(), false);
                return false;
        }
        return false;
    }

    public int getDanio() {
        return this.danioProvocado;
    }

    public void curar(Jugador jugador, int numPocion) {
        jugador.curarse(numPocion);
    }
}