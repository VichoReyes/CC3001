import java.util.Scanner;

class Main {
    static NodoCurso[] listas;
    static Curso[] cursos;

    public static void main(String[] args) {
        CursosPorOrden();
    }

    static void CursosPorOrden() {
        inicializarEstructuras();
        for (int i = 1; i < cursos.length; i++) {
            System.out.printf("el curso %d tiene flechas a: ", cursos[i].id);
            NodoCurso.imprimir("%d ", cursos[i].provee);
            System.out.println();
        }
    }

    static void inicializarEstructuras() {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        cursos = new Curso[n+1];
        listas = new NodoCurso[n];
        for (int i = 0; i < n; i++) {
            GuardarCurso(sc);
        }
        sc.close();

        for (int i = 1; i < cursos.length; i++) {
            marcarRequisitos(cursos[i]);
        }

        for (int i = 1; i < cursos.length; i++) {
            int len = NodoCurso.length(cursos[i].provee);
            System.out.println(len);
            listas[len] = NodoCurso.insertar(listas[len], cursos[i]);
        }
    }

    static void GuardarCurso(Scanner sc) {
        String linea[] = sc.nextLine().split(" ");
        int id = Integer.parseInt(linea[0]);
        int requisitos[] = new int[linea.length-1];
        for (int i = 0; i < requisitos.length; i++) {
            requisitos[i] = Integer.parseInt(linea[i+1]);
        }
        cursos[id] = new Curso(id, requisitos);
    }

    static void marcarRequisitos(Curso curso) {
        for (int i = 0; i < curso.requisitos.length; i++) {
            cursos[curso.requisitos[i]].insertarProvee(curso);
        }
    }
}

class NodoCurso {
    Curso valor;
    NodoCurso sgte;

    public NodoCurso(Curso valor, NodoCurso sgte) {
        this.valor = valor;
        this.sgte = sgte;
    }

    public static NodoCurso insertar(NodoCurso cola, Curso curso) {
        return new NodoCurso(curso, cola);
    }

    public static void imprimir(String fmt, NodoCurso cosas) {
        for (NodoCurso aux = cosas; aux != null; aux = aux.sgte) {
            System.out.printf(fmt, aux.valor.id);
        }
    }

    public static int length(NodoCurso lista) {
        int len = 0;
        for (NodoCurso aux = lista; aux != null; aux = aux.sgte) {
            len++;
        }
        return len;
    }
}

class Curso {
    int id;
    NodoCurso provee;
    int []requisitos;

    public Curso(int id, int []requisitos) {
        this.id = id;
        this.provee = null;
        this.requisitos = requisitos;
    }

    public void insertarProvee(Curso curso) {
        this.provee = NodoCurso.insertar(this.provee, curso);
    }
}
