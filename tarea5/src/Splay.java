/**
 * Algoritmo de insercion en Arbol Splay
 */
public class Splay {

    public static Nodo insertar(Nodo a, double x) {
        if (a instanceof NodoExt) {
            return new NodoInt(Nodo.nulo, x, Nodo.nulo);
        }
        NodoInt b = (NodoInt) a;
        if (x < b.info) {
            NodoInt retorno = new NodoInt(insertar(b.izq, x), b.info, b.der);
            return splay(retorno);
        } else if (x > b.info) {
            NodoInt retorno = new NodoInt(b.izq, b.info, insertar(b.der, x));
            return splay(retorno);
        } else
            return b; // ignoramos insercion si es llave repetida
    }

    private static NodoInt splay(NodoInt k, NodoInt p, NodoInt a) {
        if (p == null)
            return k;
        if (a == null) {
            if (eqInfo(k, p.der))
                k = new NodoInt(new NodoInt(p.izq, p.info, k.izq), k.info, k.der);
            else
                k = new NodoInt(k.izq, k.info, new NodoInt(k.der, p.info, p.der));
            return k;
        }
        if (eqInfo(k, a.izq.der)) { // NullPointerException here

        }
    }

    private static boolean eqInfo(NodoInt x, NodoInt y) {
        return x.info == y.info;
    }
}
