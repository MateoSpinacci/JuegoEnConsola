package juego.model;

public class FinalBoss extends Enemigo{

    private final String tipo;

    public FinalBoss(int dificultad, String nombre, int vida, int ataque, int defensa, boolean puedeCargar, int probabilidadCarga, String tipo) {
        super(dificultad, nombre, vida, ataque, defensa, puedeCargar, probabilidadCarga);
        this.tipo = tipo;
    }

    public FinalBoss copiar() {
        return new FinalBoss(this.getDificultadInt(), this.getNombre(), this.getVida(), this.getAtaque(), this.getDefensa(), this.isPuedeCargar(), this.getProbabilidadCarga(), this.tipo);
    }

    public String getTipo() {
        return tipo;
    }


}
