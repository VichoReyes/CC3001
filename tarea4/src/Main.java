
// Tarea 4 de Vicente Reyes
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String postorden = sc.nextLine();
        sc.close();

        Nodo arbol = generaArbol(postorden);
        String preorden = generaPreorden(arbol);
        // Si este println se cambia por un print, calza
        // mejor con los casos de prueba ya que no tienen
        // fin de línea, pero queda más feo al correr varias
        // veces el programa
        System.out.println(preorden);
    }

    static Nodo generaArbol(String postorden) {
        Pila pila = new Pila();
        String[] elementos = postorden.split(" ");
        for (String s : elementos) {
            if (s.equals(".")) {
                pila.push(null);
            } else {
                Nodo der = pila.pop();
                Nodo izq = pila.pop();
                pila.push(new Nodo(s, izq, der));
            }
        }
        return pila.pop();
    }

    static String generaPreorden(Nodo raiz) {
        return preordenHelper(raiz, "").substring(1);
    }

    static String preordenHelper(Nodo n, String acum) {
        if (n == null) {
            return acum + " .";
        } else {
            acum += " " + n.val;
            acum = preordenHelper(n.izq, acum);
            acum = preordenHelper(n.der, acum);
            return acum;
        }
    }
}

class Nodo {
    String val;
    Nodo izq;
    Nodo der;

    public Nodo(String valor, Nodo izquierda, Nodo derecha) {
        val = valor;
        izq = izquierda;
        der = derecha;
    }
}

class Pila {
    private Lista pila;

    Nodo pop() {
        Nodo ret = pila.valor;
        pila = pila.sgte;
        return ret;
    }

    void push(Nodo n) {
        pila = new Lista(n, pila);
    }

    boolean vacia() {
        return pila == null;
    }

    class Lista {
        Nodo valor;
        Lista sgte;

        public Lista(Nodo valor, Lista sgte) {
            this.valor = valor;
            this.sgte = sgte;
        }
    }
}