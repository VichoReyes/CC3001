import java.util.Scanner;

class Main {
    static NodoCurso[] listas;
    static Curso[] cursos;

    public static void main(String[] args) {
        CursosPorOrden();
    }

    static void CursosPorOrden() {
        inicializarEstructuras();
        while (listas[0] != null) {
            cursar(listas[0].valor);
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
            int len = cursos[i].requisitos.length;
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

    static void cursar(Curso curso) {
        System.out.println(curso.id);
        for (NodoCurso c = curso.provee; c != null; c = c.sgte) {
            for (int i = 1; i < listas.length; i++) {
                for (NodoCurso d = listas[i]; d != null ; d = d.sgte) {
                    if (c.valor.id == d.valor.id) {
                        listas[i-1] = NodoCurso.insertar(listas[i-1], d.valor);
                        listas[i] = NodoCurso.eliminar(listas[i], d.valor);
                        break;
                    }
                }
            }
        }
        listas[0] = NodoCurso.eliminar(listas[0], curso);
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

    public static NodoCurso eliminar(NodoCurso lista, Curso curso) {
        if (lista == null) {
            return null;
        }
        if (lista.valor.id == curso.id) {
            return lista.sgte;
        }
        for (NodoCurso aux = lista; aux.sgte != null; aux = aux.sgte) {
            if (aux.sgte.valor.id == curso.id) {
                aux.sgte = aux.sgte.sgte;
                return lista;
            }
        }
        return lista;
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
