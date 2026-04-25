package juego.model;

import juego.logic.Mapa;

import java.util.ArrayList;
import java.util.List;

public class Jugador extends Entidad {
    private int posicionX = 0;
    private int posicionY = 0;
    private int posicionXAnterior = 0;
    private int posicionYAnterior = 0;
    private int oro;
    private final List<Pocion> LISTA_POCIONES = new ArrayList<>();
    private final List<Objeto> LISTA_OBJETOS = new ArrayList<>();
    private int enemigosEliminados;
    private int jefesEliminados;
    private int oroTotal;
    private boolean defendiendose;


    public Jugador(String nombre, int vida, int ataque, int defensa) {
        super(nombre, vida, ataque, defensa);
        this.oro = 0;
        LISTA_POCIONES.add(new Pocion(25, "Pequeña"));
        LISTA_POCIONES.add(new Pocion(50, "Mediana"));
    }

    public void salirCorriendo(Mapa mapa) {
        mapa.moverJugador(posicionYAnterior, posicionXAnterior, posicionY, posicionX);
        int Y = posicionY;
        int X = posicionX;
        posicionY = posicionYAnterior;
        posicionX = posicionXAnterior;
        posicionYAnterior = Y;
        posicionXAnterior = X;
    }

    public char moverse(char dire, Mapa mapa) {
        char tipoAnterior = '.';
        switch (dire) {
            case 'W':
                posicionY--;
                if (mapa.dentroLimite(posicionY, posicionX)) {
                    posicionYAnterior = posicionY + 1;
                    posicionXAnterior = posicionX;
                    tipoAnterior = mapa.moverJugador(posicionY, posicionX, posicionYAnterior, posicionXAnterior);
                } else {
                    posicionY++;
                    System.out.println("NO TE SALGAS DEL MAPA");
                }
                break;
            case 'A':
                posicionX--;
                if (mapa.dentroLimite(posicionY, posicionX)) {
                    posicionXAnterior = posicionX + 1;
                    posicionYAnterior = posicionY;
                    tipoAnterior = mapa.moverJugador(posicionY, posicionX, posicionYAnterior, posicionXAnterior);
                } else {
                    posicionX++;
                    System.out.println("NO TE SALGAS DEL MAPA");
                }
                break;
            case 'S':
                posicionY++;
                if (mapa.dentroLimite(posicionY, posicionX)) {
                    posicionYAnterior = posicionY - 1;
                    posicionXAnterior = posicionX;
                    tipoAnterior = mapa.moverJugador(posicionY, posicionX, posicionYAnterior, posicionXAnterior);
                } else {
                    posicionY--;
                    System.out.println("NO TE SALGAS DEL MAPA");
                }
                break;
            case 'D':
                posicionX++;
                if (mapa.dentroLimite(posicionY, posicionX)) {
                    posicionXAnterior = posicionX - 1;
                    posicionYAnterior = posicionY;
                    tipoAnterior = mapa.moverJugador(posicionY, posicionX, posicionYAnterior, posicionXAnterior);
                } else {
                    posicionX--;
                    System.out.println("NO TE SALGAS DEL MAPA");
                }
                break;
        }
        return tipoAnterior;
    }

    public void tomarObjeto(Objeto objeto) {
        LISTA_OBJETOS.add(objeto);
        int hpObj = objeto.getHp();
        int dmgObj = objeto.getDmg();
        int dfsObj = objeto.getDfs();

        if ((this.getVida() + hpObj) <= 0) {
            this.setVivo(false);
        } else this.setVida(Math.min(this.getVida() + hpObj, 100));

        if ((this.getAtaque() + dmgObj) <= 0) {
            this.setAtaque(1);
        } else {
            this.setAtaque(this.getAtaque() + dmgObj);
        }

        if ((this.getDefensa() + dfsObj) <= 0) {
            this.setDefensa(0);
        } else this.setDefensa(Math.min(this.getDefensa() + dfsObj, 100));
    }

    public void agregarPocion(Pocion p) {
        LISTA_POCIONES.add(p);
    }

    public void curarse(int numPocion) {
        Pocion p = LISTA_POCIONES.get(numPocion);
        this.setVida(Math.min(this.getVida() + p.curacion(), 100));
        LISTA_POCIONES.remove(p);
    }

    public void resetearPosicion() {
         this.posicionX = 0;
         this.posicionY = 0;
         this.posicionXAnterior = 0;
         this.posicionYAnterior = 0;
    }

    public void setEnemigosEliminados() {
        this.enemigosEliminados++;
    }

    public int getEnemigosEliminados() {
        return this.enemigosEliminados;
    }

    public void setJefesEliminados() {
        this.jefesEliminados++;
    }

    public int getJefesEliminados() {
        return this.jefesEliminados;
    }

    public int getOro() {
        return this.oro;
    }

    public void recolectarOro(int oro) {
        this.oro += oro;
        this.oroTotal += oro;
    }

    public void quitarOro(int oro) {
        this.oro -= oro;
    }

    public int getOroTotal() {
        return this.oroTotal;
    }

    public List<Pocion> getLISTA_POCIONES() {
        return LISTA_POCIONES;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public List<Objeto> getListaObjetos() {
        return LISTA_OBJETOS;
    }

    public  int getPosicionX() {
        return posicionX;
    }

    public boolean isDefendiendose() {
        return defendiendose;
    }

    public void setDefendiendose(boolean defendiendose) {
        this.defendiendose = defendiendose;
    }
}
