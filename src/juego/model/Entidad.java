package juego.model;

public class Entidad {
    private final String nombre;
    private int vida = 100;
    private int ataque = 10;
    private int defensa = 0;
    private boolean vivo = true;

    public Entidad(String nombre, int vida, int ataque, int defensa) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public int atacar(Entidad receptor, boolean defendido, boolean carga) {
        int dmg;
        if (carga) {
            dmg = this.getAtaque() * 2 + 5;
        } else {
            dmg = this.getAtaque();
        }
        if (defendido) {
            dmg /= 2;
        }
        receptor.recibirDanio(dmg);
        return dmg;
    };

    public void recibirDanio(int danio) {
        if (this.defensa > 0) {
            this.defensa -= danio;
            if (this.defensa < 0) {
                this.vida = this.vida + this.defensa;
                this.defensa = 0;
            }
        } else {
            this.vida -= danio;
        }
        if (this.vida <= 0) {
            this.vivo = false;
            this.vida = 0;
        }
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
