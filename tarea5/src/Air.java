/**
 * Algoritmo de insercion en la ra�z (version provisoria)
 */
public class Air {
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
		int sentido = 0;
		int diferencia = (c.izq).altura() - (c.der).altura();
		if ((diferencia > -2 && diferencia < 2) || ((c.izq).altura() > 2) || ((c.der).altura() > 2))
			return c;
		else if (diferencia == 2) {
			sentido = (((NodoInt) c.izq).izq).altura() - (((NodoInt) c.izq).der).altura();
			if (sentido > 0) // subarbol zig-zig
				return new NodoInt(((NodoInt) c.izq).izq, ((NodoInt) c.izq).info,
						new NodoInt(Nodo.nulo, c.info, Nodo.nulo));
			else // subarbol zig-zag
				return new NodoInt(new NodoInt(Nodo.nulo, ((NodoInt) c.izq).info, Nodo.nulo),
						((NodoInt) ((NodoInt) c.izq).der).info, new NodoInt(Nodo.nulo, c.info, Nodo.nulo));
		} else {
			sentido = (((NodoInt) c.der).izq).altura() - (((NodoInt) c.der).der).altura();
			if (sentido > 0) // subarbol zag-zig
				return new NodoInt(new NodoInt(Nodo.nulo, c.info, Nodo.nulo), ((NodoInt) ((NodoInt) c.der).izq).info,
						new NodoInt(Nodo.nulo, ((NodoInt) c.der).info, Nodo.nulo));
			else // subarbol zag-zag
				return new NodoInt(new NodoInt(Nodo.nulo, c.info, Nodo.nulo), ((NodoInt) c.der).info,
						((NodoInt) c.der).der);
		}
	}

}
