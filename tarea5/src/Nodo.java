public abstract class Nodo {
	public abstract int nLlaves(); // numero de llaves en este subarbol

	public abstract int altura(); // altura de este subarbol

	public abstract int costo(); // costo de accesar todos los elementos

	public static Nodo nulo = new NodoExt();

	public static Nodo clonar(Nodo a) {
		if (a instanceof NodoExt) {
			return a;
		}
		NodoInt b = (NodoInt) a;
		return new NodoInt(clonar(b.izq), b.info, clonar(b.der));
	}
}
