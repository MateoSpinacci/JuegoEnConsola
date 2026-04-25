package juego.logic;

import juego.model.Jugador;

import java.util.List;
import java.util.Scanner;

public class Verificador {

    public char verificadorCaracter(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine().toUpperCase();
            if (variable.isBlank() || !(variable.equals("W") || variable.equals("A") || variable.equals("D") || variable.equals("S"))) {
                System.out.println("Ingrese un valor correcto");
            } else {
                return variable.charAt(0);
            }
        }
    }

    public String verificadorSiNO(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine().trim().toUpperCase();
            if (variable.isBlank() || !(variable.equals("SI") || variable.equals("NO"))) {
                System.out.println("Ingrese un valor correcto");
            } else {
                return variable;
            }
        }
    }

    public Jugador verificarPersonaje(Scanner sc, String mensaje, List<Jugador> lista, int cantidad) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine();
            if (variable.isBlank()) {
                System.out.println("Ingrese un valor correcto");
            } else {
                for (int i=0; i<cantidad; i++) {
                    String num = String.valueOf(i+1);
                    if (variable.equals(num)) {
                        return lista.get(Integer.parseInt(variable.trim()) - 1);
                    }
                }
                System.out.println("Ingrese un valor correcto");
            }
        }
    }

    public int verificarItem(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine();
            if (variable.isBlank() || !(variable.equals("0") || variable.equals("1") || variable.equals("3"))) {
                System.out.println("Ingrese un valor correcto");
            } else {
                return Integer.parseInt(variable.trim());
            }
        }
    }

    public int verificarCombate(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine();
            if (variable.isBlank() || !(variable.equals("1") || variable.equals("2") || variable.equals("3") || variable.equals("4"))) {
                System.out.println("Ingrese un valor correcto");
            } else {
                return Integer.parseInt(variable.trim());
            }
        }
    }

    public int verificarCombateJefe(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String variable = sc.nextLine();
            if (variable.isBlank() || !(variable.equals("1") || variable.equals("2") || variable.equals("3"))) {
                System.out.println("Ingrese un valor correcto");
            } else {
                return Integer.parseInt(variable.trim());
            }
        }
    }

    public int verificarEntero(Scanner sc, String mensaje, int maximo) {
        while (true) {
            System.out.println(mensaje);
            try {
                int variable = Integer.parseInt(sc.nextLine().trim());
                if (variable > maximo || 0 >= variable) {
                    System.out.println("Ingrese un valor correcto");
                } else {
                    return variable - 1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número valido");
            }
        }
    }
}
