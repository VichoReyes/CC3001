
// Anunciamos que usaremos la biblioteca de widgets de la AWT y Swing
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Arboles - Tarea CC3001
 */
public class Arboles extends Panel {
	Button clear, random;
	Label titulo;
	static Canvas regla, canvas1, canvas2, canvas3;
	static Arbol a1, a2, a3;
	static Random randomnumber;

	public Arboles() {
		randomnumber = new Random(); // crea el generador de numeros aleatorios
		setLayout(new BorderLayout()); // asigna el layout

		// crea los widgets
		random = new Button("random");
		titulo = new Label("Tarea CC3001: Comparacion de Arboles", Label.CENTER);

		Panel canvases = new Panel();
		canvases.setLayout(new GridLayout(1, 3));
		canvas1 = new MiCanvas(a1 = new Arbol("ABB"), Color.lightGray);
		canvas2 = new MiCanvas(a2 = new Arbol("AVL"), Color.orange);
		canvas3 = new MiCanvas(a3 = new Arbol("Splay"), Color.cyan);
		canvases.add(canvas1);
		canvases.add(canvas2);
		canvases.add(canvas3);

		regla = new Regla();

		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());

		// armamos la ventana

		add("North", titulo);

		add("Center", canvases);
		panel.add("West", random);
		panel.add("Center", regla);
		add("South", panel);

		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Arboles.insertall(Arboles.randomnumber.nextDouble());
			}
		});
	}

	/**
	 * Inserta una llave en los tres arboles
	 * 
	 * @param llave La llave que se inserta
	 */
	public static void insertall(double llave) {
		a1.raiz = Abb.insertar(a1.raiz, llave);
		a2.raiz = Avl.insertar(a2.raiz, llave);
		a3.raiz = Splay.insertar(a3.raiz, llave);
		canvas1.repaint();
		canvas2.repaint();
		canvas3.repaint();
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Window Listener");

		WindowListener listener = new WindowAdapter() {

			@Override

			public void windowClosing(WindowEvent w) {

				System.exit(0);

			}
		};
		f.addWindowListener(listener);
		Arboles a = new Arboles();
		f.add(a);
		f.pack();
		f.setVisible(true);

	}
}

/**
 * Areas de dibujo de arboles
 */
class MiCanvas extends Canvas {
	Arbol a;

	public MiCanvas(Arbol aa, Color c) {
		super();
		setBackground(c);
		a = aa;
	}

	public void paint(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;
		a.dibuja(this, w, h);
	}

	public Dimension getMinimumSize() {
		return new Dimension(150, 130);
	}

	public Dimension getPreferredSize() {
		return new Dimension(150, 200);
	}
}

/**
 * La regla que aparece abajo para insertar elementos
 */
class Regla extends Canvas {
	public Regla() {
		super();
		setBackground(Color.yellow);
		setSize(100, 5);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int w = getSize().width;
				double llave = (double) x / (w - 1);
				Arboles.insertall(llave);
			}
		});
	}

	public void paint(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;
		g.drawLine(0, h - 1, w - 1, h - 1);
		for (int i = 0; i <= 100; ++i) {
			int k = i * (w - 1) / 100;
			g.drawLine(k, h - 1, k, h - (i % 10 == 0 ? 7 : i % 5 == 0 ? 4 : 3));
		}
	}
}
