package juego.logic;

import juego.model.*;

import java.util.Scanner;

public class Juego {

    private static final Scanner sc = new Scanner(System.in);
    private static final ManagerItems MI = new ManagerItems();
    private static final ManagerEnemigos ME = new ManagerEnemigos();
    private static final ManagerPersonajes MJ = new ManagerPersonajes();
    private static final ManagerJefes MB = new ManagerJefes();
    private static final Mapa MAPA = new Mapa(MI, ME, MB);
    private static final Verificador VR = new Verificador();
    private static final Combate combateClase = new Combate();
    private static Jugador JUGADOR;

    public void iniciar() {
        boolean jugando;
        JUGADOR = seleccionarPersonaje();
        bucleJugando:
        for (int i=0; i<10; i++) {
            jugando = true;
            System.out.println("========= PISO " + (i + 1) + " =========");
            JUGADOR.resetearPosicion();
            MAPA.generarMapa(i + 1);
            while (jugando) {
                MAPA.mostrarMapa();
                char opcion = VR.verificadorCaracter(sc, "[W] Arriba | [A] Izquierda | [D] Derecha | [S] Abajo");
                char tipoAnterior = JUGADOR.moverse(opcion, MAPA);
                Casillero casilla = MAPA.getMapa()[JUGADOR.getPosicionY()][JUGADOR.getPosicionX()];
                switch (tipoAnterior) {
                    case 'E':
                        Enemigo enemigo = MAPA.devolverEnemigo(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                        if (casilla.isNotLimpiada()) {
                            combate(enemigo);
                            if (!JUGADOR.isVivo()) {
                                System.out.println("MORISTE");
                                break bucleJugando;
                            } else if (!enemigo.isVivo()) {
                                MAPA.actualizarEnemigo(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                                System.out.println("¡Enemigo Eliminado!");
                            }
                        } else {
                            System.out.println("¡Enemigo ya eliminado... Tienes el paso libre!");
                        }
                        break;
                    case 'T':
                        if (casilla.isNotLimpiada()) {
                            System.out.println("========= SALA DEL TESORO =========");
                            System.out.println(casilla.getTesoro1());
                            if (casilla.getTesoro2() != null) {
                                System.out.println("\n" + casilla.getTesoro2());
                                int tomado = VR.verificarItem(sc, "¿Qué objeto tomas? (1|2|3(SALIR)): ");
                                if (tomado == 1) {
                                    Objeto objetoTomado = casilla.getTesoro1();
                                    JUGADOR.tomarObjeto(objetoTomado);
                                    MI.quitarObjetoLista(objetoTomado);
                                    casilla.setLimpiada(true);
                                } else if (tomado == 2) {
                                    Objeto objetoTomado = casilla.getTesoro2();
                                    JUGADOR.tomarObjeto(objetoTomado);
                                    MI.quitarObjetoLista(objetoTomado);
                                    casilla.setLimpiada(true);
                                }
                            } else {
                                String tomado = VR.verificadorSiNO(sc, "¿Tomar objeto? (si|no): ");
                                if (tomado.equals("SI")) {
                                    Objeto objetoTomado = casilla.getTesoro1();
                                    JUGADOR.tomarObjeto(objetoTomado);
                                    MI.quitarObjetoLista(objetoTomado);
                                    casilla.setLimpiada(true);
                                }
                            }
                            if (!JUGADOR.isVivo()) {
                                System.out.println("MORISTE");
                                break bucleJugando;
                            }
                        }
                        break;
                    case 'S':
                        if (MAPA.verificarMapaLimpiado()) {
                            if (MAPA.isTieneJefe() && MAPA.getPiso() != 10) {
                                MiniJefes miniJefe = MAPA.devolverMiniBoss(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                                combateMiniJefe(miniJefe);
                                if (!JUGADOR.isVivo()) {
                                    System.out.println("MORISTE");
                                    break bucleJugando;
                                } else if (!miniJefe.isVivo()) {
                                    MAPA.actualizarEnemigo(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                                    System.out.println("¡Jefe Eliminado!");
                                }
                            } else if (MAPA.isTieneJefe() && MAPA.getPiso() == 10) {
                                FinalBoss finalBoss = MAPA.devolverFinalBoss(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                                combateFinal(finalBoss);
                                if (!JUGADOR.isVivo()) {
                                    System.out.println("MORISTE");
                                    break bucleJugando;
                                } else if (!finalBoss.isVivo()) {
                                    MAPA.actualizarEnemigo(JUGADOR.getPosicionY(), JUGADOR.getPosicionX());
                                    System.out.println("¡Jefe Final Eliminado!");
                                    System.out.println("¡ESCAPASTE DE LA MAZMORRA | FELICIDADES!");
                                    break bucleJugando;
                                }
                            }
                            System.out.println("========= PISO COMPLETADO =========\n");
                            jugando = false;
                        } else {
                            System.out.println("Enemigos siguen vagando por el piso... Trae todas sus almas para abrir esta puerta...");
                        }
                }
            }
        }
        mostrarStats();
        System.out.println("Gracias Por Jugar :)");
    }

    private static Jugador seleccionarPersonaje() {
        int cantidad = 0;
        for (int i = 0; i < MJ.getJugadores().size(); i++) {
            cantidad++;
            Jugador jugador = MJ.getJugadores().get(i);
            System.out.println((i+1) + ". " + jugador.getNombre() + " (Vida: " + jugador.getVida() + " Defensa: " + jugador.getDefensa() + " Ataque: " + jugador.getAtaque() + ")");
        }
        return VR.verificarPersonaje(sc, "Seleccione un personaje: ", MJ.getJugadores(), cantidad);
    }

    private static void combate(Enemigo enemigo) {
        boolean esquivo;
        System.out.println("========= Iniciando combateClase =========");
        System.out.println("Enemigo: " + enemigo.getNombre() + " | Dificultad: " + enemigo.getDificultad());
        bucleCombate:
        while (enemigo.isVivo() && JUGADOR.isVivo()) {
            System.out.println("\nStats del enemigo:\n\tVida: " + enemigo.getVida() + "\n\tDefensa: " +
                    enemigo.getDefensa() + "\n\tAtaque: " + enemigo.getAtaque());
            System.out.println("\nTus Stats:\n\tVida: " + JUGADOR.getVida() + "\n\tDefensa: " +
                    JUGADOR.getDefensa() + "\n\tAtaque: " + JUGADOR.getAtaque());
            System.out.println("\n¡Tú turno!\n1. Atacar | 2. Defenderse | 3. Curarse (" + JUGADOR.getLISTA_POCIONES().size() + ") | 4. Correr");
            int jugada = VR.verificarCombate(sc, "Opción: ");
            switch (jugada) {
                case 1:
                    esquivo = combateClase.atacarJugador(enemigo, JUGADOR);
                    if (esquivo) {
                        System.out.println("¡El enemigo te esquivó! | No le haz hecho daño");
                    } else {
                        System.out.println("¡Ataque Completado!");
                    }
                    if (!enemigo.isVivo()) {
                        JUGADOR.setEnemigosEliminados();
                        JUGADOR.recolectarOro(enemigo.getRecompensaOro());
                        if (enemigo.getRecompensaPocion() != null) {
                            JUGADOR.agregarPocion(enemigo.getRecompensaPocion());
                        }
                        break bucleCombate;
                    }
                    break;
                case 2:
                    JUGADOR.setDefendiendose(true);
                    System.out.println("Te cubres para el siguiente golpe...");
                    break;
                case 3:
                    if (JUGADOR.getLISTA_POCIONES().isEmpty()) {
                        System.out.println("No te puedes curar, no cuentas con pociones en el inventario...");
                    } else {
                        opcionCurarse();
                    }
                    break;
                case 4:
                    JUGADOR.salirCorriendo(MAPA);
                    enemigo.curarMax();
                    enemigo.defensaMax();
                    System.out.println("Volviste a la sala anterior...");
                    break bucleCombate;
            }
            esquivo = combateClase.atacarEnemigo(enemigo, JUGADOR);
            if (esquivo) {
                System.out.println("¡Esquivaste al enemigo rápidamente, ni te tocó!");
            } else {
                int dmg = combateClase.getDanio();
                if (enemigo.isCarga()) {
                    System.out.println(enemigo.getNombre() + " está cargando un ataque...");
                } else {
                    if (JUGADOR.isDefendiendose()) {
                        System.out.println("¡El enemigo te ataca pero te defiendes! -" + dmg + " de vida");
                        JUGADOR.setDefendiendose(false);
                    } else {
                        System.out.println("¡El enemigo te ataca! -" + dmg + " de vida");
                    }
                }
            }
            if (JUGADOR.isDefendiendose()) {
                System.out.println("Dejas de defenderte...");
                JUGADOR.setDefendiendose(false);
            }
        }
    }

    private static void combateMiniJefe(MiniJefes miniJefe) {
        boolean esquivo;
        System.out.println("========= MINI JEFE | INICIANDO COMBATE =========");
        System.out.println("Enemigo: " + miniJefe.getNombre() + " | Tipo: " + miniJefe.getTipo());
        bucleCombate:
        while (miniJefe.isVivo() && JUGADOR.isVivo()) {
            System.out.println("\nStats del enemigo:\n\tVida: " + miniJefe.getVida() + "\n\tDefensa: " +
                    miniJefe.getDefensa() + "\n\tAtaque: " + miniJefe.getAtaque());
            System.out.println("\nTus Stats:\n\tVida: " + JUGADOR.getVida() + "\n\tDefensa: " +
                    JUGADOR.getDefensa() + "\n\tAtaque: " + JUGADOR.getAtaque());
            System.out.println("\n¡Tú turno!\n1. Atacar | 2. Defenderse | 3. Curarse (" + JUGADOR.getLISTA_POCIONES().size() + ")");
            int jugada = VR.verificarCombateJefe(sc, "Opción: ");
            switch (jugada) {
                case 1:
                    esquivo = combateClase.atacarJugador(miniJefe, JUGADOR);
                    if (esquivo) {
                        System.out.println("¡El enemigo te esquivó! | No le haz hecho daño");
                    } else {
                        System.out.println("¡Ataque Completado!");
                    }
                    if (!miniJefe.isVivo()) {
                        JUGADOR.setJefesEliminados();
                        JUGADOR.recolectarOro(miniJefe.getRecompensaOro());
                        if (miniJefe.getRecompensaPocion() != null) {
                            JUGADOR.agregarPocion(miniJefe.getRecompensaPocion());
                        }
                        break bucleCombate;
                    }
                    break;
                case 2:
                    JUGADOR.setDefendiendose(true);
                    System.out.println("Te cubres para el siguiente golpe...");
                    break;
                case 3:
                    if (JUGADOR.getLISTA_POCIONES().isEmpty()) {
                        System.out.println("No te puedes curar, no cuentas con pociones en el inventario...");
                    } else {
                        opcionCurarse();
                    }
                    break;
            }
            esquivo = combateClase.atacarJefe(miniJefe, JUGADOR);
            if (esquivo) {
                System.out.println("¡Esquivaste al enemigo rápidamente, ni te tocó!");
            } else {
                int dmg = combateClase.getDanio();
                if (miniJefe.isCarga()) {
                    System.out.println(miniJefe.getNombre() + " está cargando un ataque...");
                } else {
                    if (JUGADOR.isDefendiendose()) {
                        System.out.println("¡El enemigo te ataca pero te defiendes! -" + dmg + " de vida");
                        JUGADOR.setDefendiendose(false);
                    } else {
                        System.out.println("¡El enemigo te ataca! -" + dmg + " de vida");
                    }
                }
            }
            if (JUGADOR.isDefendiendose()) {
                System.out.println("Dejas de defenderte...");
                JUGADOR.setDefendiendose(false);
            }
        }
    }

    private static void combateFinal(FinalBoss finalBoss) {
        boolean esquivo;
        System.out.println("========= JEFE FINAL | INICIANDO COMBATE =========");
        System.out.println("Enemigo: " + finalBoss.getNombre() + " | Tipo: " + finalBoss.getTipo());
        bucleCombate:
        while (finalBoss.isVivo() && JUGADOR.isVivo()) {
            System.out.println("\nStats del enemigo:\n\tVida: " + finalBoss.getVida() + "\n\tDefensa: " +
                    finalBoss.getDefensa() + "\n\tAtaque: " + finalBoss.getAtaque());
            System.out.println("\nTus Stats:\n\tVida: " + JUGADOR.getVida() + "\n\tDefensa: " +
                    JUGADOR.getDefensa() + "\n\tAtaque: " + JUGADOR.getAtaque());
            System.out.println("\n¡Tú turno!\n1. Atacar | 2. Defenderse | 3. Curarse (" + JUGADOR.getLISTA_POCIONES().size() + ")");
            int jugada = VR.verificarCombateJefe(sc, "Opción: ");
            switch (jugada) {
                case 1:
                    esquivo = combateClase.atacarJugador(finalBoss, JUGADOR);
                    if (esquivo) {
                        System.out.println("¡El enemigo te esquivó! | No le haz hecho daño");
                    } else {
                        System.out.println("¡Ataque Completado!");
                    }
                    if (!finalBoss.isVivo()) {
                        JUGADOR.setJefesEliminados();
                        JUGADOR.recolectarOro(finalBoss.getRecompensaOro());
                        if (finalBoss.getRecompensaPocion() != null) {
                            JUGADOR.agregarPocion(finalBoss.getRecompensaPocion());
                        }
                        break bucleCombate;
                    }
                    break;
                case 2:
                    JUGADOR.setDefendiendose(true);
                    System.out.println("Te cubres para el siguiente golpe...");
                    break;
                case 3:
                    if (JUGADOR.getLISTA_POCIONES().isEmpty()) {
                        System.out.println("No te puedes curar, no cuentas con pociones en el inventario...");
                    } else {
                        opcionCurarse();
                    }
                    break;
            }
            esquivo = combateClase.atacarJefeFinal(finalBoss, JUGADOR);
            if (esquivo) {
                System.out.println("¡Esquivaste al enemigo rápidamente, ni te tocó!");
            } else {
                int dmg = combateClase.getDanio();
                if (finalBoss.isCarga()) {
                    System.out.println(finalBoss.getNombre() + " está cargando un ataque...");
                } else {
                    if (JUGADOR.isDefendiendose()) {
                        System.out.println("¡El enemigo te ataca pero te defiendes! -" + dmg + " de vida");
                        JUGADOR.setDefendiendose(false);
                    } else {
                        System.out.println("¡El enemigo te ataca! -" + dmg + " de vida");
                    }
                }
            }
            if (JUGADOR.isDefendiendose()) {
                System.out.println("Dejas de defenderte...");
                JUGADOR.setDefendiendose(false);
            }
        }
    }

    private static void opcionCurarse() {
        for (int j = 0; j < JUGADOR.getLISTA_POCIONES().size(); j++) {
            Pocion p = JUGADOR.getLISTA_POCIONES().get(j);
            System.out.println(j + 1 + ". " + p.toString());
        }
        int numPocion = VR.verificarEntero(sc, "¿Qué poción utilizaras?: ", JUGADOR.getLISTA_POCIONES().size());
        combateClase.curar(JUGADOR, numPocion);
        System.out.println("¡Te curaste perfectamente! | Tu nueva vida: " + JUGADOR.getVida());
    }

    private static void mostrarStats() {
        System.out.println("\n========= TUS ESTADÍSTICAS =========");
        System.out.println("Personaje: " + JUGADOR.getNombre());
        System.out.println("Llegaste al piso " + MAPA.getPiso());
        System.out.println("Eliminaste a " + JUGADOR.getEnemigosEliminados() + " enemigos");
        System.out.println("Eliminaste a " + JUGADOR.getJefesEliminados() + " jefes");
        System.out.println("Recolectaste un total de " + JUGADOR.getOroTotal() + " de oro");
        System.out.println("Tus objetos:");
        for (Objeto o : JUGADOR.getListaObjetos()) {
            System.out.println("-" + o.getNombre() + " | " + o.getCalidad());
        }
    }
}
