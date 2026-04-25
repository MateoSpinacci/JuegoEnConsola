package juego.logic;

import juego.model.FinalBoss;
import juego.model.MiniJefes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerJefes {

    private static final List<MiniJefes> MINI_JEFES_EARLY = new ArrayList<>();
    private static final List<MiniJefes> MINI_JEFES_MID = new ArrayList<>();
    private static final List<MiniJefes> MINI_JEFES_LATE = new ArrayList<>();
    private static final List<FinalBoss> FINAL_BOSSES = new ArrayList<>();
    private static final Random rand = new Random();

    public ManagerJefes() {
        inicializarJefes();
    }

    private void inicializarJefes() {

        MINI_JEFES_EARLY.add(new MiniJefes(4, "Cargador Brutal", 120, 18, 10, true, 50, "CARGADOR"));
        MINI_JEFES_EARLY.add(new MiniJefes(4, "Guardián de Piedra", 130, 14, 22, false, 0, "REGENERADOR"));
        MINI_JEFES_EARLY.add(new MiniJefes(4, "Berserker Oscuro", 100, 16, 8, false, 0, "BERSERKER"));

        MINI_JEFES_MID.add(new MiniJefes(4, "Espectro Errante", 90, 22, 5, false, 0, "ESQUIVADOR"));
        MINI_JEFES_MID.add(new MiniJefes(4, "Devorador", 130, 20, 10, false, 0, "VAMPIRO"));
        MINI_JEFES_MID.add(new MiniJefes(4, "Caballero Maldito", 120, 24, 15, true, 50, "CARGADOR"));
        MINI_JEFES_MID.add(new MiniJefes(4, "Chamán Oscuro", 110, 18, 12, false, 0, "REGENERADOR"));

        MINI_JEFES_LATE.add(new MiniJefes(4, "Señor de las Sombras", 110, 28, 5, true, 50, "CARGADOR"));
        MINI_JEFES_LATE.add(new MiniJefes(4, "Coloso Maldito", 160, 20, 30, true, 20, "REGENERADOR"));
        MINI_JEFES_LATE.add(new MiniJefes(4, "Bestia Desatada", 140, 35, 5, false, 0, "BERSERKER"));
        MINI_JEFES_LATE.add(new MiniJefes(4, "Espectro Supremo", 100, 28, 8, false, 0, "ESQUIVADOR"));

        FINAL_BOSSES.add(new FinalBoss(5, "Mega Satan", 200, 25, 20, true, 40, "SATAN"));
        FINAL_BOSSES.add(new FinalBoss(5, "El Inmortal", 260, 18, 40, false, 0, "TANQUE"));
        FINAL_BOSSES.add(new FinalBoss(5, "Rey Fantasma", 180, 30, 10, true, 25, "REY"));
    }

    public MiniJefes generarMiniJefe(int piso) {
        List<MiniJefes> lista;
        if (piso <= 3) {
            lista = MINI_JEFES_EARLY;
        } else if (piso <= 7) {
            lista = MINI_JEFES_MID;
        } else {
            lista = MINI_JEFES_LATE;
        }
        int numJefe = rand.nextInt(lista.size());
        MiniJefes base = lista.get(numJefe);
        //MiniJefes base = MINI_JEFES_MID.get(0); // PROBAR EL ENEMIGO QUE QUIERAS
        lista.remove(numJefe);
        return base.copiar();
    }

    public FinalBoss generarFinalBoss() {
        int numJefe = rand.nextInt(FINAL_BOSSES.size());
        FinalBoss base = FINAL_BOSSES.get(numJefe);
        //FinalBoss base = FINAL_BOSSES.get(1); PROBAR EL JEFE QUE QUIERAS
        FINAL_BOSSES.remove(numJefe);
        return base.copiar();
    }

}
