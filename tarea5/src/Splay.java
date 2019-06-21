/**
 * Algoritmo de insercion en Arbol Splay
 */
public class Splay {

    public static Nodo insertar(Nodo a, double x) {
        Nodo c = Nodo.clonar(a);
        return insertarHelper(x, c, null, null);
    }

    public static Nodo insertarHelper(double x, Nodo k, NodoInt p, NodoInt a) {
        if (k instanceof NodoExt) {
            return new NodoInt(Nodo.nulo, x, Nodo.nulo);
        }
        NodoInt b = (NodoInt) k;
        if (x < b.info) {
            b.izq = insertarHelper(x, b.izq, b, p);
            return splay((NodoInt) b.izq, b, p);
        } else if (x > b.info) {
            b.der = insertarHelper(x, b.der, b, p);
            return splay((NodoInt) b.der, b, p);
        } else {
            return b; // ignoramos insercion si es llave repetida
            // return splay(b, p, a);
        }
    }

    private static NodoInt splay(NodoInt k, NodoInt p, NodoInt a) {
        if (p == null)
            return k;
        if (a == null) {
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
        return k;
    }

    private static boolean eqInfo(Nodo x, Nodo y) {
        // return x.info == y.info;
        if (x instanceof NodoExt || y instanceof NodoExt)
            return false;
        return ((NodoInt) x).info == ((NodoInt) y).info;
    }
}
