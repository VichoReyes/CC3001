/**
 * Algoritmo de insercion en Arbol de busqueda binaria
 */
public class Abb {
	public static Nodo insertar(Nodo a, double x) {
		a = insertarHelper(a, x);
		return clonar(a);
	}

	private static Nodo insertarHelper(Nodo a, double x) {
		if (a instanceof NodoExt) {
			return new NodoInt(Nodo.nulo, x, Nodo.nulo);
		}
		NodoInt b = (NodoInt) a;
		if (x < b.info) {
			b.izq = insertarHelper(b.izq, x);
			return b;
		} else if (x > b.info) {
			b.der = insertarHelper(b.der, x);
			return b;
		} else {
			return b; // ignoramos insercion si es llave repetida
		}
	}

	private static Nodo clonar(Nodo a) {
		if (a instanceof NodoExt) {
			return a;
		}
		NodoInt b = (NodoInt) a;
		return new NodoInt(clonar(b.izq), b.info, clonar(b.der));
	}
}
