package juego.model;

import juego.logic.ManagerEnemigos;
import juego.logic.ManagerItems;
import juego.logic.ManagerJefes;

import java.util.Random;

public class Casillero {
    private final static Random rand = new Random();
    private char tipo;
    private Enemigo enemigo;
    private MiniJefes miniJefe;
    private FinalBoss finalBoss;
    private Objeto tesoro1;
    private Objeto tesoro2;
    private boolean limpiada;

    public Casillero() {
        this('.');
    }

    public Casillero(char tipo) {
        this.tipo = tipo;
        this.enemigo = null;
        this.tesoro1 = null;
        this.tesoro2 = null;
    }

    public void setEnemigo(ManagerEnemigos ME, int piso) {
        this.enemigo = ME.generarEnemigo(piso);
    }

    public void setJefe(ManagerJefes MB, int piso) {
        this.miniJefe = MB.generarMiniJefe(piso);
    }

    public void setJefeFinal(ManagerJefes MB) {
        this.finalBoss = MB.generarFinalBoss();
    }

    public FinalBoss getFinalBoss() {
        return finalBoss;
    }

    public MiniJefes getMiniJefe() {
        return miniJefe;
    }

    public void setObjetoTesoro1(ManagerItems MI, int piso) {
        this.tesoro1 = MI.generarItem(piso);
    }

    public void setObjetoTesoro2(ManagerItems MI, int piso) {
        this.tesoro2 = MI.generarItem(piso);
    }

    public Objeto getTesoro1() {
        return tesoro1;
    }

    public Objeto getTesoro2() {
        return tesoro2;
    }

    public Enemigo getEnemigo() {
        return enemigo;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public boolean isNotLimpiada() {
        return !limpiada;
    }

    public void setLimpiada(boolean limpiada) {
        this.limpiada = limpiada;
    }
}
