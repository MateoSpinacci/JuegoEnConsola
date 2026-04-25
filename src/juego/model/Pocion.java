package juego.model;

public record Pocion(int curacion, String tamanio) {

    @Override
    public String toString() {
        return "Poción " + this.tamanio + ": " + this.curacion;
    }
}
