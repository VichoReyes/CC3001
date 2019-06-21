/**
 * Algoritmo de insercion en Arbol AVL (version provisoria)
 */
public class Avl {

	public static Nodo insertar(Nodo a, double x) {
		if (a instanceof NodoExt) {
			return new NodoInt(Nodo.nulo, x, Nodo.nulo);
		}
		NodoInt b = (NodoInt) a;
		if (x < b.info) {
			NodoInt retorno = new NodoInt(insertar(b.izq, x), b.info, b.der);
			return Revisar(retorno);
		} else if (x > b.info) {
			NodoInt retorno = new NodoInt(b.izq, b.info, insertar(b.der, x));
			return Revisar(retorno);
		} else
			return b; // ignoramos insercion si es llave repetida
	}

	public static NodoInt Revisar(NodoInt c) {
		int subarbol_mayor = (c.izq).altura() - (c.der).altura();
		int sentido_mayor = 0;
		NodoInt sub_m;
		if (subarbol_mayor > 1)
			sentido_mayor = (((NodoInt) c.izq).izq).altura() - (((NodoInt) c.izq).der).altura();
		else if (subarbol_mayor < -1)
			sentido_mayor = (((NodoInt) c.der).izq).altura() - (((NodoInt) c.der).der).altura();

		if (subarbol_mayor * sentido_mayor > 1) // ins. externa <=> Rot. simple
			return RotSimple(c, sentido_mayor);
		else if (subarbol_mayor * sentido_mayor < -1) // ins. interna <=> Rot. doble
			return RotDoble(c, sentido_mayor);
		else
			return c; // arbol balanceado AVL
	}

	public static NodoInt RotSimple(NodoInt p, int sentido) {
		if (sentido >= 1) {
			NodoInt pp = new NodoInt(((NodoInt) p.izq).der, p.info, p.der);
			return new NodoInt(((NodoInt) p.izq).izq, ((NodoInt) p.izq).info, pp);
		} else {
			NodoInt pp = new NodoInt(p.izq, p.info, ((NodoInt) p.der).izq);
			return new NodoInt(pp, ((NodoInt) p.der).info, ((NodoInt) p.der).der);
		}
	}

	public static NodoInt RotDoble(NodoInt p, int sentido) {
		if (sentido <= -1) {
			NodoInt izquierda = new NodoInt(((NodoInt) p.izq).izq, ((NodoInt) p.izq).info,
					((NodoInt) ((NodoInt) p.izq).der).izq);
			NodoInt pp = new NodoInt(((NodoInt) ((NodoInt) p.izq).der).der, p.info, p.der);
			return new NodoInt(izquierda, ((NodoInt) ((NodoInt) p.izq).der).info, pp);
		} else {
			NodoInt derecha = new NodoInt(((NodoInt) ((NodoInt) p.der).izq).der, ((NodoInt) p.der).info,
					((NodoInt) p.der).der);
			NodoInt pp = new NodoInt(p.izq, p.info, ((NodoInt) ((NodoInt) p.der).izq).izq);
			return new NodoInt(pp, ((NodoInt) ((NodoInt) p.der).izq).info, derecha);
		}
	}

}
