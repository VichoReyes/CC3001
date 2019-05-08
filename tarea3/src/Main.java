import java.util.Scanner;

class Main {
    static Curso[] listas;
    static Curso[] cursos;

    public static void main(String[] args) {
        CursosPorOrden();
    }

    static void CursosPorOrden() {
        inicializarEstructuras();
        while (listas[0] != null) {
            cursar(listas[0]);
        }
    }

    static void inicializarEstructuras() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        cursos = new Curso[n+1];
        listas = new Curso[n];
        for (int i = 0; i < n; i++) {
            GuardarCurso(sc);
        }
        sc.close();
    }

    static void GuardarCurso(Scanner sc) {
        String linea[] = sc.nextLine().split(" ");
        int id = Integer.parseInt(linea[0]);
        Curso.generar(id);
        for (int i = 1; i < linea.length; i++) {
            int requisito = Integer.parseInt(linea[i]);
            Curso.generar(requisito);
            Curso.insertarProvee(cursos[requisito], cursos[id]);
        }
        int k = linea.length - 1;
        cursos[id].requisitos = k;
        listas[k] = cursos[id].insertarEnLista(listas[k]);
    }

    static void cursar(Curso curso) {
        System.out.println(curso.id);
        for (ListaProvee c = curso.provee; c != null; c = c.sgte) {
            int k = c.valor.requisitos;
            listas[k] = c.valor.eliminarDeLista(listas[k]);
            listas[k-1] = c.valor.insertarEnLista(listas[k-1]);
            c.valor.requisitos--;
        }
        listas[0] = curso.eliminarDeLista(listas[0]);
    }
}

class ListaProvee {
    Curso valor;
    ListaProvee sgte;

    public ListaProvee(Curso valor, ListaProvee sgte) {
        this.valor = valor;
        this.sgte = sgte;
    }
}

class Curso {
    int id;
    ListaProvee provee;
    Curso siguiente;
    Curso anterior;
    int requisitos;

    private Curso(int id) {
        this.id = id;
    }

    public Curso insertarEnLista(Curso lista) {
        siguiente = lista;
        if (siguiente != null) {
            siguiente.anterior = this;
        }
        anterior = null;
        return this;
    }

    public Curso eliminarDeLista(Curso lista) {
        if (siguiente != null) {
            siguiente.anterior = anterior;
        }
        if (anterior != null) {
            anterior.siguiente = siguiente;
            return lista;
        } else {
            return siguiente;
        }
    }

    public static void generar(int id) {
        if (Main.cursos[id] != null)
            return;

        Main.cursos[id] = new Curso(id);
    }

    public static void insertarProvee(Curso requisito, Curso proveido) {
        requisito.provee = new ListaProvee(proveido, requisito.provee);
    }
}
