import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Arbol {
	String nombre;
	Nodo raiz;

	public Arbol(String s) {
		nombre = s;
		raiz = Nodo.nulo;
	}

	public void dibuja(Canvas c, int w, int h) {
		Graphics g = c.getGraphics();
		g.setColor(Color.black);
		g.drawString(nombre, 10, 10);
		int n = raiz.nLlaves();
		g.drawString("n=" + n + " h=" + raiz.altura() + " costo=" + raiz.costo(), 10, 25);

		int base = 40;
		dibnodo(raiz, g, w, base, (h - base) / 20);
	}

	public void dibnodo(Nodo n, Graphics g, int w, int y, int dy) {
		if (n instanceof NodoInt) {
			NodoInt ni = (NodoInt) n;
			int x = (int) (ni.info * (w - 1));
			punto(g, x, y);
			if (ni.izq instanceof NodoInt) {
				g.drawLine(x, y, (int) (((NodoInt) ni.izq).info * (w - 1)), y + dy);
			}
			if (ni.der instanceof NodoInt) {
				g.drawLine(x, y, (int) (((NodoInt) ni.der).info * (w - 1)), y + dy);
			}
			dibnodo(ni.izq, g, w, y + dy, dy);
			dibnodo(ni.der, g, w, y + dy, dy);
		}
	}

	public void punto(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.fillOval(x - 2, y - 2, 5, 5);
	}
}
