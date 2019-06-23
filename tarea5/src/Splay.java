import java.util.Stack;

/**
 * Algoritmo de insercion en Arbol Splay
 */
public class Splay {
    private static Stack<NodoInt> paraRotar;

    public static Nodo insertar(Nodo a, double x) {
        paraRotar = new Stack<NodoInt>();
        insertarHelper(x, a);
        a = splay();
        return Nodo.clonar(a);
    }

    private static void insertarHelper(double x, Nodo k) {
        if (k instanceof NodoExt) {
            k = new NodoInt(Nodo.nulo, x, Nodo.nulo);
            paraRotar.push((NodoInt) k);
            return;
        }
        NodoInt b = (NodoInt) k;
        paraRotar.push(b);
        if (x < b.info) {
            insertarHelper(x, b.izq);
        } else if (x > b.info) {
            insertarHelper(x, b.der);
        }
    }

    private static NodoInt splay() {
        NodoInt k, p, a, ba; // nodo, padre, abuelo, bisabuelo
        while (!paraRotar.isEmpty()) {
            k = paraRotar.pop();
            if (!paraRotar.isEmpty()) {
                p = paraRotar.pop();
            } else {
                return k;
            }
            if (!paraRotar.isEmpty()) {
                a = paraRotar.pop();
            } else {
                if (eqInfo(k, p.der)) {
                    p.der = k.izq;
                    k.izq = p;
                } else {
                    p.izq = k.der;
                    k.der = p;
                }
                return k;
            }
            if (a.izq instanceof NodoInt && eqInfo(k, ((NodoInt) a.izq).der)) {
                p.der = k.izq;
                a.izq = k.der;
                k.izq = p;
                k.der = a;
            } else if (a.der instanceof NodoInt && eqInfo(k, ((NodoInt) a.der).izq)) {
                p.izq = k.der;
                a.der = k.izq;
                k.der = p;
                k.izq = a;
            } else if (a.izq instanceof NodoInt && eqInfo(k, ((NodoInt) a.izq).izq)) {
                p.izq = k.der;
                a.izq = p.der;
                p.der = a;
                k.der = p;
            } else if (a.izq instanceof NodoInt && eqInfo(k, ((NodoInt) a.der).der)) {
                p.der = k.izq;
                a.der = p.izq;
                p.izq = a;
                k.izq = p;
            }
            if (!paraRotar.isEmpty()) {
                ba = paraRotar.pop();
                if (ba.info < k.info) {
                    ba.der = k;
                } else {
                    ba.izq = k;
                }
                paraRotar.push(ba);
            }
            paraRotar.push(k);
        }
        System.out.println("esto no debería pasar nunca");
        return null;
    }

    private static boolean eqInfo(Nodo x, Nodo y) {
        // return x.info == y.info;
        if (x instanceof NodoExt || y instanceof NodoExt)
            return false;
        return ((NodoInt) x).info == ((NodoInt) y).info;
    }
}
