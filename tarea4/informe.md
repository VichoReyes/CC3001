# Introducción

Esta tarea busca solucionar un problema que surge en las universidades cuando se tienen varios cursos que se pueden tomar, pero algunos de ellos requieren haber tomado otros antes para poder seguir la materia sin muchos problemas.
Por ejemplo, no se puede seguir efectivamente Ecuaciones Diferenciales si no se ha cursado Cálculo Diferencial primero. Por lo tanto, un orden adecuado para tomar los cursos debe ser tal que Ecuaciones Diferenciales va después de Cálculo Diferencial.

El problema consiste en la generación de un ordenamiendo de cursos que cumpla con que cada vez que se toma un curso, ya se han tomado todos sus requisitos.
Dada una lista de cursos representados con identificadores numéricos, y dados los requisitos de cada curso, el programa escrito debe crear un orden para tomar los cursos, un orden que sea correcto en el sentido de que
en ningún momento se toma un curso para el que no se cumplan todos los requisitos y que además cada curso se cursa una sola vez.

Para solucionar este problema, se deben usar las estructuras de datos apropiadas que permitan la fácil implementación del algoritmo descrito en el enunciado, el cual será detallado en la sección siguiente.
Las estructuras de datos que se usan en este código son principalmente arreglos y, en el caso en que el tamaño de una colección varía mucho, listas enlazadas.

# Diseño de la solución

El algoritmo pedido consiste en cuatro pasos

1. Para cada curso `c` del _input_, crear un objeto que contenga sus datos. Entre ellos, deben incluirse punteros a los cursos que tienen a `c` como requisito, es decir, los cursos que `c` provee.

2. Almacenar todos los cursos en un arreglo `listas` que contenga listas enlazadas con los cursos ordenados por el número de requisitos que le faltan al estudiante para poder tomarlo, de tal manera que `listas[k]` sea una lista con todos los cursos que tienen `k` requisitos que el estudiante todavía no cursa. Así, se puede deducir que el estudiante tiene permitido tomar un curso si y solo si este se encuentra en `listas[0]`

3. Imprimir cada curso de `listas[0]` (con lo cual se da por cursado y pasado) y mover todos los cursos que lo tenían de requisito de `listas[n]` a `listas[n-1]`, ya que uno de los cursos que requerían se imprimió.

## Paso 1

En el proceso de crear una lista de punteros que contenga los cursos que tienen a `c` como requisito, hay un obstáculo que se debe enfrentar: Puede que estos cursos no hayan sido mencionados por el _input_ todavía. Es decir, si el _input_ es

```
2 1
1
```

entonces no es posible insertar el curso 2 en la lista de cursos que el curso 1 provee así como así, ya que el nodo correspondiente al curso 1 no ha sido creado todavía.
Por este motivo, se deben crear todos los objetos que representan un curso apenas sean vistos.

## Paso 2

Este paso simplemente requiere contar el número de elementos en la lista de requisitos de un curso, el cual se da en la línea que presenta al curso en el _input_. Por ejemplo, si se lee la línea `34 21 32 14` entonces el curso 34 debe ubicarse en `listas[3]`. Además, puede ser cómodo mantener el índice de `listas` en el que se encuentra el curso (en este caso 3) como información del nodo del curso, para que luego en el paso 3 este se pueda mover más fácilmente.

## Paso 3

En esta parte, se debe obtener algún curso de `listas[0]`[^1] siempre que haya alguno. Se imprime su id y luego se itera en la lista de los cursos que provee. Cada uno de estos cursos se saca de su lugar en `listas[k]` y se mueve a `listas[k-1]`, de forma que cuando sea hayan tomado todos sus requisitos, el curso se encuentre en `listas[0]`.

Para mover el curso, es necesario eliminarlo de `listas[k]` y luego añadirlo a `listas[k-1]`. Como esta es una operación muy común, es conveniente que para hacerla más rápida se sigan dos consejos:

1. Guardar en cada curso una variable **k** que indique en qué parte de `listas` se encuentra, para no tener que buscar por todo `listas` cada vez que se elimina un curso.

2. Que los cursos estén guardados en una lista doblemente enlazada, de forma que eliminar uno sea una operación $O(1)$.

[^1]: El primero, ya que es el más fácil de obtener.

## Estructuras de Datos

Por las razones mencionadas en el [paso 3][Paso 3], es conveniente que el arreglo `listas` contenga listas doblemente enlazadas.

Se debe mencionar que como es necesario eliminar los cursos de esta lista, se deben guardar no solamente los cursos sino que los nodos de esta lista que contienen los cursos, ya que para eliminarlos es necesario tener punteros al próximo elemento y el anterior. Por lo tanto, hay dos posibles formas de enfrentar este problema:

1. Que en la lista que cada curso `c` tiene que contiene los cursos para los que `c` es requisito, se guarden los nodos de los cursos con respecto a `listas`.

2. Que cada elemento de clase `Curso` contenga de por sí información sobre los nodos siguiente y anterior.

Por simplicidad, se eligió la segunda opción, aunque en un proyecto real convendría más la primera ya que mantiene mejor separados los distintos problemas y por lo tanto deriva en código más fácil de mantener.

# Implementación

La estructura general de la solución es

```java
static void CursosPorOrden() {
    inicializarEstructuras();
    while (listas[0] != null) {
        cursar(listas[0]);
    }
}
```

De forma que la función `inicializarEstructuras` cumple los pasos [1][Paso 1] y [2][Paso 2] del diseño de la solución, y el loop junto con la función `cursar` cumplen el paso [3][Paso 3].

## Estructuras de Datos

Se crean dos arrays estáticos de la clase `Main` que contienen los cursos:

```java
static Curso[] listas;
static Curso[] cursos;
```

Son dos porque esto permite acceder al mismo curso de dos maneras: Usando el id del curso con `cursos[id]` o usando la cantidad de requisitos que quedan para poder tomarlo con `listas[cantidadDeRequisitos]`, aunque en ese último caso lo que se encuentra representa una lista.

Para poder crear los cursos con listas de esta manera, se define la clase cursos así:

```java
class Curso {
    int id;
    ListaProvee provee;
    Curso siguiente;
    Curso anterior;
    int requisitos;
```

Entonces, cada curso apunta al siguiente y anterior curso que tiene la misma cantidad de requisitos en los atributos `siguiente` y `anterior`, y esta cantidad de requisitos es además accesible usando el atributo `requisitos`.

`provee` es una lista de simple implementación que guarda la información de los cursos que se abren al `cursar(this)`, de manera que se pueda iterar sobre ellos de la manera descrita en el [paso 3][Paso 3].

## Inicialización de Estructuras

Por cada línea que describe un curso, la función `inicializarEstructuras` llama a `GuardarCurso`, la cual es la responsable de interpretar esta línea.

```java
static void GuardarCurso(Scanner sc) {
    String linea[] = sc.nextLine().split(" ");
    int id = Integer.parseInt(linea[0]);
    Curso.generar(id);
    // continúa después
```

Con eso se puede generar el objeto `Curso` correspondiendo al id del de la línea que se acaba de leer. Sin embargo, puede que este id ya haya sido mencionado antes, por lo que el `Curso` ya habría sido generado. Es por esta razón que la función `generar` está implementada de forma que no hace nada si el curso ya existe.

El resto de la función `GuardarCurso` se ocupa de poblar el resto de los atributos y guardarlo en el lugar apropiado de `listas`, además de añadir el curso al atributo `provee` de todos sus requisitos:

```java
    // continuación
    for (int i = 1; i < linea.length; i++) {
        int requisito = Integer.parseInt(linea[i]);
        Curso.generar(requisito);
        Curso.insertarProvee(cursos[requisito], cursos[id]);
    }
    int k = linea.length - 1;
    cursos[id].requisitos = k;
    listas[k] = cursos[id].insertarEnLista(listas[k]);
}
```

En conclusión, cuando se ha ejecutado `GuardarCurso` para todas las líneas, se tienen todas las estructuras inicializadas correctamente.

## Cursado de los cursos

Una vez inicializadas las estructuras, y habiendo creado funciones `Curso.insertarEnLista` y `Curso.eliminarDeLista`, la función `Main.cursar` resulta ser bastante directa del algoritmo descrito en el [paso 3][Paso 3].

Y con esto, cuando no hay ningún curso en `listas[0]` se da por cursado todo lo que se puede cursar, y termina la ejecución del programa.

# Anexo: Código