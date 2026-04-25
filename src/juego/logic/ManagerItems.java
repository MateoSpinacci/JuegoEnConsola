package juego.logic;

import juego.model.Objeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManagerItems {
    private static final List<Objeto> COMUNES = new ArrayList<>();
    private static final List<Objeto> RAROS = new ArrayList<>();
    private static final List<Objeto> EPICOS = new ArrayList<>();
    private static final List<Objeto> LEGENDARIOS = new ArrayList<>();

    public ManagerItems() {
        inicializarObjetos();
    }

    public Objeto generarItem(int piso) {
        Random rand = new Random();
        int roll = rand.nextInt(100);
        List<Objeto> lista;
        if (piso <= 3) {
            if (roll < 60) {
                lista = COMUNES;
            } else if (roll < 95) {
                lista = RAROS;
            } else {
                lista = EPICOS;
            }
        } else if (piso < 7) {
            if (roll < 40) {
                lista = COMUNES;
            } else if (roll < 75) {
                lista = RAROS;
            } else if (roll < 95) {
                lista = EPICOS;
            } else {
                lista = LEGENDARIOS;
            }
        } else if (piso < 10) {
            if (roll < 25) {
                lista = COMUNES;
            } else if (roll < 60) {
                lista = RAROS;
            } else if (roll < 85) {
                lista = EPICOS;
            } else {
                lista = LEGENDARIOS;
            }
        } else {
            if (roll < 10) {
                lista = COMUNES;
            } else if (roll < 30) {
                lista = RAROS;
            } else if (roll < 60) {
                lista = EPICOS;
            } else {
                lista = LEGENDARIOS;
            }
        }
        return lista.get(rand.nextInt(lista.size()));
    }

    public void quitarObjetoLista(Objeto objeto) {
        switch (objeto.getCalidad()) {
            case 1:
                COMUNES.remove(objeto);
                break;
            case 2:
                RAROS.remove(objeto);
                break;
            case 3:
                EPICOS.remove(objeto);
                break;
            case 4:
                LEGENDARIOS.remove(objeto);
                break;
        }
    }

    private static void inicializarObjetos() {
        COMUNES.add(new Objeto(1, "Jeringa Mutante", "Te inyectas y te sentís mas fuerte! Aunque también un poco perdido..."
                , "+ 10 DMG, -5 DFS", 0, 10, -5)); // DAÑO
        RAROS.add(new Objeto(2, "Espina Afilada", "Duele tenerla, pero duele más recibirla.",
                "+12 DMG, -5 HP", -5, 12, 0)); // DAÑO
        COMUNES.add(new Objeto(1, "Escudo Improvisado", "No es muy confiable... pero algo ayuda.",
                "+10 DFS, -3 DMG", 0, -3, 10)); // DEFENSA
        RAROS.add(new Objeto(2, "Armadura Pesada", "Te protege... pero te cuesta pegar.",
                "+15 DFS, -5 DMG", 0, -5, 15)); // DEFENSA
        EPICOS.add(new Objeto(3, "Ojo Maldito", "Ves más allá de lo normal... pero algo " +
                "te observa también.", "+12 DMG, -10 HP", -10, 12, 0)); // DAÑO
        COMUNES.add(new Objeto(1, "Vendaje Viejo", "No es muy higiénico, pero cumple.",
                "+8 HP, -2 DFS", 8, 0, -2)); // VIDA
        RAROS.add(new Objeto(2, "Máscara Agrietada", "Oculta tu miedo... pero también tu " +
                "visión.", "+10 DFS, -5 DMG", 0, -5, 10)); // DEFENSA
        RAROS.add(new Objeto(2, "Amuleto Roto", "Parece inútil, " +
                "pero algo de suerte queda.", "+10 HP, -5 DMG", 10, -5, 0)); // VIDA
        RAROS.add(new Objeto(2, "Café Sospechoso", "Esto no es café normal..." +
                " estás MUY despierto.", "+5 DMG, -5 HP", -5, 5, 0)); // DAÑO
        LEGENDARIOS.add(new Objeto(4, "Esencia Maldita", "Un poder prohibido " +
                "recorre tus venas.", "+20 HP, +20 DMG, -20 DFS", 20, 20, -20)); // VIDA Y DAÑO
        LEGENDARIOS.add(new Objeto(4, "Furia del Abismo", "Cada golpe libera una " +
                "violencia incontrolable.", "+30 DMG, -20 HP, -10 DFS", -20, 30, -10)); // MUCHO DAÑO
        LEGENDARIOS.add(new Objeto(4, "Carne Inmortal", "Tu cuerpo se rehúsa a morir..." +
                " pero duele existir.", "+50 HP, -10 DMG, -10 DFS", 50, -10, -10)); // MUCHA VIDA
        COMUNES.add(new Objeto(1, "Casco Viejo", "Algo desgastado, " +
                "pero protege.", "+6 DFS, -2 DMG", 0, -2, 6)); // DEFENSA
        COMUNES.add(new Objeto(1, "Piedra Pesada", "Golpea más fuerte " +
                "de lo que parece.", "+4 DMG, -2 DFS", 0, 4, -2)); // DAÑO
        RAROS.add(new Objeto(2, "Sangre Espesa", "Aguantás más, " +
                "pero te cuesta reaccionar.", "+20 HP, -6 DMG", 20, -6, 0)); // VIDA
        EPICOS.add(new Objeto(3, "Armadura Profunda", "Difícil de atravesar.",
                "+25 DFS, -8 DMG", 0, -8, 25)); // DEFENSA
        LEGENDARIOS.add(new Objeto(4, "Núcleo Radiante", "Una luz poderosa te fortalece.",
                "+30 HP, +20 DMG, +10 DFS", 30, 20, 10)); // TODO
        LEGENDARIOS.add(new Objeto(4, "Escudo Eterno", "Nada parece poder dañarte fácilmente."
                , "+50 DFS", 0, 0, 50)); // DEFENSA
        EPICOS.add(new Objeto(3, "Rabia Interna", "Un poder difícil de " +
                "controlar.", "+20 DMG, -5 DFS, -10 HP", -10, 20, -5)); // DAÑO
        EPICOS.add(new Objeto(3, "Corazón Negro", "Oscuro y " +
                "poderoso.", "+30 HP, -10 DFS", 30, 0, -10)); // VIDA
    }
}