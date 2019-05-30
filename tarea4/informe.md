---
header-includes:
    - \usepackage{setspace}
---

# Introducción

Esta tarea busca evaluar la construcción y manipulación de árboles binarios.
En particular, se debe crear un programa que al recibir el recorrido de un árbol en postorden,
imprima el mismo árbol, pero esta vez recorrido en preorden.

Por ejemplo, dado un `string` como el siguiente:

```
. . . E D . B . . C A
```

se debe generar este _output:_

```
A B D . E . . . C . .
```

Ya que son respectivamente los recorridos en postorden y preorden del árbol de la figura 1:

![Árbol de Ejemplo](ejemplo.png){ width=40% }

La estrategia seguida para solucionar este problema se describirán en la sección "[Diseño de la Solución]"
y los detalles de su implementación serán explicados en la sección "[Implementación]"

# Diseño de la Solución

Es claro que para convertir el recorrido en postorden de un árbol en un recorrido en preorden,
la forma más clara de hacerlo es primero generar el árbol a partir del recorrido en postorden,
y luego teniendo este árbol, se debe imprimir recorriéndolo en preorden.
El primero de estos pasos es naturalmente traducido a una función con firma

```java
Nodo generaArbol(String postorden)
```

y el segundo a una función con firma

```java
String generaPreorden(Nodo raiz)
```

## Generación del Árbol

Una forma de implementar la función `generaArbol` es usar una pila y luego iterar por los elementos del string.
De esta manera, se puede crear el árbol si se se hace lo siguiente para cada elemento:

- Si es un punto, añadir un nodo externo a la pila.
- Si es una letra, se sacan dos nodos de la pila y se crea un nuevo nodo con la letra como valor y los nodos sacados como hijos. Luego este nuevo nodo se añade a la pila.

Al terminar de leer el string, si éste es un recorrido en postorden válido, se debería haber generado el árbol correspondiente en el primer y único lugar de la pila.

Es conveniente implementar la pila usando una lista enlazada ya que ello permite que la pila no tenga un límite de tamaño apreciable.

## Generación del Preorden

La representación en preorden de un árbol se puede generar fácilmente de forma recursiva, añadiendo partes a un string acumulador en cada llamada a la función.

Se debe añadir el valor del nodo antes que nada, y luego añadir el preorden de las ramas izquierda y derecha respectivamente. En caso de que se encuentre un árbol `null`, se debe añadir un punto.

# Implementación

Para generar el árbol se creó una clase `Pila` (implementada con listas enlazadas) con las funciones `push` y `pop`.
También se escribió la función `vacia` pero no fue necesario usarla, ya que al generar un árbol basado en un postorden válido usando el algoritmo descrito en [Generación del Árbol], se garantiza que cada vez que se quiera usar `pop` haya un elemento en la pila.
Para una solución más robusta del problema, sí sería necesario usar `vacia` para evitar errores del tipo `NullPointerException` cuando el _input_ no es un postorden válido.

Luego, creada la clase `Pila`, se separan los elementos del _input_ y se crea el árbol correspondiente siguiendo las instrucciones de [Generación del Árbol].

Una vez obtenido el árbol y en particular su raíz, la generación del preorden se implementó con una función recursiva directa de la descripción dada en [Generación del Preorden].

# Análisis de Resultados

Se probó el código con los casos de prueba entregados en u-cursos.
A continuación se presenta una tabla con algunos de los
_inputs_ y _outputs_ correspondientes, generados por el programa.

\tiny
| **Input**                                           | **Output**                                          |
| --------------------------------------------------- | --------------------------------------------------- |
| `. . . E D . B . . C A`                             | `A B D . E . . . C . .`                             |
| `. . D . . E B . . F . . G C A`                     | `A B D . . E . . C F . . G . .`                     |
| `. . . A E . . . B F I . . . . L C G . . . D H J K` | `K I E . A . . F . B . . J G . C . L . . H . D . .` |
| `. . O . L . L . E . . D . L . R . O . W H`         | `H E L L O . . . . . W O R L D . . . . . .`         |
| `. . A . . E . K L . . E . S . A O`                 | `O L A . . K E . . . A S E . . . .`                 |
| `.`                                                 | `.`                                                 |
\normalsize

Si se revisan los _outputs_ entregados, se verá que son casi los mismos,
la única diferencia es que los generados por el programa tienen un fin de línea.

Nota: Para revisar esto, se puede correr el siguiente código en una carpeta en que contiene la carpeta `input`, la carpeta `output` y los archivos `.class` generados por `javac Main.java`.

```bash
# para ver los outputs generados por el programa
for i in $(ls input); do
    java Main <input/$i;
done | md5sum

# y luego, para ver los outputs que se buscaban
for i in $(ls output); do
    # awk 1 añade el salto de línea que falta
    awk 1 output/$i;
done | md5sum
```

Como ambos scripts generan el mismo checksum, los _outputs_ están correctos.

# Anexo: Código

\small

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String postorden = sc.nextLine();
        sc.close();

        Nodo arbol = generaArbol(postorden);
        String preorden = generaPreorden(arbol);
        System.out.println(preorden);
    }

    static Nodo generaArbol(String postorden) {
        Pila pila = new Pila();
        String[] elementos = postorden.split(" ");
        for (String s : elementos) {
            if (s.equals(".")) {
                pila.push(null);
            } else {
                Nodo der = pila.pop();
                Nodo izq = pila.pop();
                pila.push(new Nodo(s, izq, der));
            }
        }
        return pila.pop();
    }

    static String generaPreorden(Nodo raiz) {
        return preordenHelper(raiz, "").substring(1);
    }

    static String preordenHelper(Nodo n, String acum) {
        if (n == null) {
            return acum + " .";
        } else {
            acum += " " + n.val;
            acum = preordenHelper(n.izq, acum);
            acum = preordenHelper(n.der, acum);
            return acum;
        }
    }
}

class Nodo {
    String val;
    Nodo izq;
    Nodo der;

    public Nodo(String valor, Nodo izquierda, Nodo derecha) {
        val = valor;
        izq = izquierda;
        der = derecha;
    }
}

class Pila {
    private Lista pila;

    Nodo pop() {
        Nodo ret = pila.valor;
        pila = pila.sgte;
        return ret;
    }

    void push(Nodo n) {
        pila = new Lista(n, pila);
    }

    boolean vacia() {
        return pila == null;
    }

    class Lista {
        Nodo valor;
        Lista sgte;

        public Lista(Nodo valor, Lista sgte) {
            this.valor = valor;
            this.sgte = sgte;
        }
    }
}
```