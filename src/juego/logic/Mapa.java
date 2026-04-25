package juego.logic;

import juego.model.Casillero;
import juego.model.Enemigo;
import juego.model.FinalBoss;
import juego.model.MiniJefes;

import java.util.Random;

public class Mapa {

    private final Random rand = new Random();
    private char tipoAnterior = '.';
    private final ManagerItems MI;
    private final ManagerEnemigos ME;
    private final ManagerJefes MB;
    private int piso;
    private int cantEnemigos;
    private static int numDesdePisosJefe;
    private boolean tieneJefe;

    public Mapa(ManagerItems MI, ManagerEnemigos ME, ManagerJefes MB) {
        this.MI = MI;
        this.ME = ME;
        this.MB = MB;
    }

    private Casillero[][] mapaBase;

    public void generarMapa(int numPiso) {
        mapaBase = new Casillero[][]{
                {new Casillero('P'), new Casillero(), new Casillero(), new Casillero()},
                {new Casillero(), new Casillero(), new Casillero(), new Casillero()},
                {new Casillero(), new Casillero(), new Casillero(), new Casillero()},
                {new Casillero(), new Casillero(), new Casillero(), new Casillero('S')}
        };
        tipoAnterior = '.';
        this.piso = numPiso;
        if (numDesdePisosJefe == 3 || this.piso == 10) {
            this.generarJefe();
            this.tieneJefe = true;
            numDesdePisosJefe = 0;
        } else if (numDesdePisosJefe == 2 && rand.nextInt(2) == 1) {
            numDesdePisosJefe = 0;
            this.generarJefe();
            this.tieneJefe = true;
        } else {
            this.tieneJefe = false;
            numDesdePisosJefe++;
        }
        // if (numPiso % 2 == 1) this.generarTienda();
        this.generarTesoro();
        this.generarEnemigos(ME);
    }

    public char moverJugador(int fila, int columna, int filaAnterior, int columnaAnterior) {
        mapaBase[filaAnterior][columnaAnterior].setTipo(tipoAnterior);
        tipoAnterior = mapaBase[fila][columna].getTipo();
        mapaBase[fila][columna].setTipo('P');
        return tipoAnterior;
    }

    public void generarEnemigos(ManagerEnemigos ME) {
        cantEnemigos = rand.nextInt(3) + 2;
        for (int i = 0; i < cantEnemigos; i++) {
            boolean loop = true;
            int fila = rand.nextInt(mapaBase.length);
            int columna = rand.nextInt(mapaBase[0].length);
            while (loop) {
                if (this.mapaBase[fila][columna].getTipo() == 'E' || this.mapaBase[fila][columna].getTipo() == 'M' || this.mapaBase[fila][columna].getTipo() == 'S' || this.mapaBase[fila][columna].getTipo() == 'P' || this.mapaBase[fila][columna].getTipo() == 'T') {
                    fila = rand.nextInt(mapaBase.length);
                    columna = rand.nextInt(mapaBase[0].length);
                } else {
                    loop = false;
                }
            }
            this.mapaBase[fila][columna].setTipo('E');
            this.mapaBase[fila][columna].setEnemigo(ME, this.piso);
        }
    }

    public void generarJefe() {
        if (this.piso == 10) {
            this.mapaBase[mapaBase.length - 1][mapaBase[0].length - 1].setJefeFinal(MB);
        } else {
            this.mapaBase[mapaBase.length - 1][mapaBase[0].length - 1].setJefe(MB, this.piso);
        }
    }


    /*private void generarTienda() {
        boolean loop = true;
        int fila = rand.nextInt(mapaBase.length);
        int columna = rand.nextInt(mapaBase[0].length);
        while (loop) {
            if (this.mapaBase[fila][columna].getTipo() == 'E' || this.mapaBase[fila][columna].getTipo() == 'S' || this.mapaBase[fila][columna].getTipo() == 'P' || this.mapaBase[fila][columna].getTipo() == 'T') {
                fila = rand.nextInt(mapaBase.length);
                columna = rand.nextInt(mapaBase[0].length);
            } else {
                loop = false;
            }
        }
        this.mapaBase[fila][columna].setTipo('M');
    }*/

    public boolean verificarMapaLimpiado() {
        int contador = 0;
        for (int j = 0; j < mapaBase.length; j++) {
            for (int i = 0; i < mapaBase[j].length; i++) {
                if (mapaBase[i][j].getTipo() == 'E') {
                    if (!mapaBase[i][j].getEnemigo().isVivo()) {
                        contador++;
                    }
                }
            }
        }
        return contador == cantEnemigos;
    }

    public void generarTesoro() {
        boolean loop = true;
        int fila = rand.nextInt(mapaBase.length);
        int columna = rand.nextInt(mapaBase[0].length);
        while (loop) {
            if (this.mapaBase[fila][columna].getTipo() == 'E' || this.mapaBase[fila][columna].getTipo() == 'M' || this.mapaBase[fila][columna].getTipo() == 'S' || this.mapaBase[fila][columna].getTipo() == 'P' || this.mapaBase[fila][columna].getTipo() == 'T') {
                fila = rand.nextInt(mapaBase.length);
                columna = rand.nextInt(mapaBase[0].length);
            } else {
                loop = false;
            }
        }
        this.mapaBase[fila][columna].setTipo('T');
        this.mapaBase[fila][columna].setObjetoTesoro1(MI, this.piso);
        if (this.piso >= 5) {
            do {
                this.mapaBase[fila][columna].setObjetoTesoro2(MI, this.piso);
            } while (this.mapaBase[fila][columna].getTesoro2() == this.mapaBase[fila][columna].getTesoro1());
        }
    }

    public void mostrarMapa() {
        System.out.println("\n=======================");
        for (int i = 0; i < this.mapaBase.length; i++) {
            System.out.print("        ");
            for (int j = 0; j < this.mapaBase[i].length; j++) {
                System.out.print(this.mapaBase[i][j].getTipo() + " ");
            }
            System.out.print("\n");
        }
        System.out.println("=======================");
        System.out.println("\nP. Personaje | E. Enemigo\nT. Tesoro | S. Salida\n");
    }

    public boolean dentroLimite(int fila, int columna) {
        return fila >= 0 && columna >= 0 && fila <= this.mapaBase.length - 1 && columna <= this.mapaBase[0].length - 1;
    }

    public Enemigo devolverEnemigo(int fila, int columna) {
        return this.mapaBase[fila][columna].getEnemigo();
    }

    public MiniJefes devolverMiniBoss(int fila, int columna) {
        return this.mapaBase[fila][columna].getMiniJefe();
    }

    public FinalBoss devolverFinalBoss(int fila, int columna) {
        return this.mapaBase[fila][columna].getFinalBoss();
    }

    public void actualizarEnemigo(int fila, int columna) {
        mapaBase[fila][columna].setLimpiada(true);
    }

    public Casillero[][] getMapa() {
        return mapaBase;
    }

    public boolean isTieneJefe() {
        return tieneJefe;
    }

    public int getPiso() {
        return piso;
    }
}