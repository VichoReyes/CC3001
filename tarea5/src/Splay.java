/**
 * Algoritmo de insercion en Arbol Splay
 */
public class Splay {

    public static Nodo insertar(Nodo a, double x) {
        return insertarHelper(x, a, null, null);
    }

    public static Nodo insertarHelper(double x, Nodo k, NodoInt p, NodoInt a) {
        if (k instanceof NodoExt) {
            if (p == null) {
                return new NodoInt(Nodo.nulo, x, Nodo.nulo);
            } else if (p.info > x) {
                p.izq = new NodoInt(Nodo.nulo, x, Nodo.nulo);
                return p.izq;
            } else {
                p.der = new NodoInt(Nodo.nulo, x, Nodo.nulo);
                return p.izq;
            }
        }
        NodoInt b = (NodoInt) k;
        if (x < b.info) {
            insertarHelper(x, b.izq, b, p);
            return splay((NodoInt) b.izq, b, p);
        } else if (x > b.info) {
            insertarHelper(x, b.der, b, p);
            return splay((NodoInt) b.der, b, p);
        } else
            return b; // ignoramos insercion si es llave repetida
    }

    private static NodoInt splay(NodoInt k, NodoInt p, NodoInt a) {
        if (p == null)
            return k;
        if (a == null) {
            if (p.der instanceof NodoInt && eqInfo(k, (NodoInt) p.der))
                k = new NodoInt(new NodoInt(p.izq, p.info, k.izq), k.info, k.der);
            else
                k = new NodoInt(k.izq, k.info, new NodoInt(k.der, p.info, p.der));
            return k;
        }
        if (a.izq instanceof NodoInt && eqInfo(k, (NodoInt) ((NodoInt) a.izq).der)) {
            return new NodoInt(new NodoInt(p.izq, p.info, k.izq), k.info, new NodoInt(k.der, a.info, a.der));
        }
        if (a.der instanceof NodoInt && eqInfo(k, (NodoInt) ((NodoInt) a.der).izq)) {
            return new NodoInt(new NodoInt(k.izq, a.info, a.izq), k.info, new NodoInt(p.der, p.info, k.der));
        }
        if (a.izq instanceof NodoInt && eqInfo(k, (NodoInt) ((NodoInt) a.izq).izq)) {
            return new NodoInt(k.izq, k.info, new NodoInt(k.der, p.info, new NodoInt(p.der, a.info, a.der)));
        }
        if (a.izq instanceof NodoInt && eqInfo(k, (NodoInt) ((NodoInt) a.der).der)) {
            return new NodoInt(new NodoInt(new NodoInt(p.izq, a.info, a.izq), p.info, k.izq), k.info, k.der);
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
