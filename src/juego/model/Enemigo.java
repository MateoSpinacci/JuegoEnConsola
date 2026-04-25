package juego.model;

import java.util.Random;

public class Enemigo extends Entidad {

    private int recompensaOro = 0;
    private Pocion recompensaPocion = null;
    private final int dificultad;
    private final int vidaMax;
    private final int defensaMax;
    private final boolean puedeCargar;
    private int probabilidadCarga;
    private boolean cargando;

    public Enemigo(int dificultad, String nombre, int vida, int ataque, int defensa, boolean puedeCargar, int probabilidadCarga) {
        super(nombre, vida, ataque, defensa);
        this.dificultad = dificultad;
        this.vidaMax = vida;
        if (this.getDificultadInt() == 4 ||  this.getDificultadInt() == 5) {
            this.defensaMax = defensa + (defensa / 2);
        } else {
            this.defensaMax = defensa;
        }
        this.puedeCargar = puedeCargar;
        this.probabilidadCarga = probabilidadCarga;
        Random rand = new Random();
        switch (dificultad) {
            case 1:
                if (rand.nextInt(100) < 90) {
                    this.recompensaOro = rand.nextInt(2) + 3;
                } else {
                    this.recompensaPocion = new Pocion(25, "Pequeña");
                }
                break;
            case 2:
                if (rand.nextInt(100) < 85) {
                    this.recompensaOro = rand.nextInt(5) + 5;
                } else {
                    this.recompensaPocion = new Pocion(50, "Mediana");
                }
                break;
            case 3:
                if (rand.nextInt(100) < 70) {
                    this.recompensaOro = rand.nextInt(10) + 10;
                } else {
                    this.recompensaPocion = new Pocion(100, "Grande");
                }
                break;
        }
    }

    public void curarse(int cantidad) {
        this.setVida(this.getVida() + cantidad);
    }

    public int getProbabilidadCarga() {
        return probabilidadCarga;
    }

    public void setProbabilidadCarga(int probabilidadCarga) {
        this.probabilidadCarga = probabilidadCarga;
    }

    public void curarMax() {
        this.setVida(this.vidaMax);
    }

    public void defensaMax() {
        this.setDefensa(this.defensaMax);
    }

    public int getDefensaMax() {
        return defensaMax;
    }

    public int getVidaMax() {
        return vidaMax;
    }

    public int getDificultadInt() {
        return dificultad;
    }

    public String getDificultad() {
        if (this.dificultad == 1) {
            return "Fácil";
        } else if (this.dificultad == 2) {
            return "Normal";
        } else {
            return "Difícil";
        }
    }

    public Enemigo copiar() {
        return new Enemigo(this.dificultad, this.getNombre(), this.getVida(), this.getAtaque(), this.getDefensa(), this.puedeCargar, this.probabilidadCarga);
    }

    public int getRecompensaOro() {
        return recompensaOro;
    }

    public boolean isCarga() {
        return cargando; // Verifica si ya está cargando
    }

    public void setCarga(boolean cargando) {
        this.cargando = cargando; // Pone si empieza a cargar
    }

    public boolean isPuedeCargar() {
        return puedeCargar;
    }

    public Pocion getRecompensaPocion() {
        return recompensaPocion;
    }

    public boolean puedeCargar() {
        return puedeCargar;
    }
}
