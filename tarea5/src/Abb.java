/**
 * Algoritmo de insercion en Arbol de busqueda binaria
 */
public class Abb {
	public static Nodo insertar(Nodo a, double x) {
		if (a instanceof NodoExt) {
			return new NodoInt(Nodo.nulo, x, Nodo.nulo);
		}
		NodoInt b = (NodoInt) a;
		if (x < b.info)
			return new NodoInt(insertar(b.izq, x), b.info, b.der);
		else if (x > b.info)
			return new NodoInt(b.izq, b.info, insertar(b.der, x));
		else
			return b; // ignoramos insercion si es llave repetida
	}
}
