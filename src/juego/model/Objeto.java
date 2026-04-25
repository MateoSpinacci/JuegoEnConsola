package juego.model;

public class Objeto {

    private final String nombre;
    private final String descripcion;
    private final String stats;
    private final int calidad;
    int hp;
    int dmg;
    int dfs;

    public Objeto(int calidad, String nombre, String descripcion, String stats, int hp, int dmg, int dfs) {
        this.calidad = calidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.stats = stats;
        this.hp = hp;
        this.dmg = dmg;
        this.dfs = dfs;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + "\nDescripción: " + this.descripcion + "\n" + this.stats + "\nCalidad: " + this.calidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDfs() {
        return dfs;
    }

    public int getDmg() {
        return dmg;
    }

    public int getHp() {
        return hp;
    }

    public int getCalidad() {
        return calidad;
    }
}
