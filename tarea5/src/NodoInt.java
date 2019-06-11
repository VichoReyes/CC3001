public class NodoInt extends Nodo {
	public double info; // llave
	public Nodo izq, der; // hijos
	int n; // numero de nodos
	int h; // altura
	int c; // costo de accesar todos los nodos del subarbol

	public NodoInt(Nodo i, double x, Nodo d) {
		info = x;
		izq = i;
		der = d;
		n = 1 + i.nLlaves() + d.nLlaves();
		h = 1 + Math.max(i.altura(), d.altura());
		c = i.costo() + d.costo() + n;
	}

	public int nLlaves() {
		return n;
	}

	public int altura() {
		return h;
	}

	public int costo() {
		return c;
	}
}
