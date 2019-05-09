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

3. Imprimir algún curso de `listas[0]` (con lo cual se da por cursado y pasado) y mover todos los cursos que lo tenían de requisito de `listas[n]` a `listas[n-1]`, ya que uno de los cursos que requerían se imprimió.

4. Sacar el curso recién impreso de `listas[0]` y repetir la operación del paso 3 hasta que no queden más cursos.

## Paso 1

En el proceso de crear una lista de punteros que contenga los cursos que tienen a `c` como requisito, hay un obstáculo que se debe enfrentar: Puede que estos cursos no hayan sido mencionados por el _input_ todavía. Es decir, si el _input_ es

```
2 1
1
```

entonces no es posible insertar el curso 2 en la lista de cursos que el curso 1 provee así como así, ya que el nodo correspondiente al curso 1 no ha sido creado todavía. Por lo tanto, se debe crear el curso 1 aunque no se tenga toda la información al respecto.
En general, al procesar cada línea se deben crear todos los cursos mencionados en caso de que no existan, ya que no se tiene la garantía de que ninguno de ellos haya sido mencionado antes. Luego se deben añadir cursos a la lista de cursos que los requieren dinámicamente a medida que se lee el resto del _input_.

## Paso 2

Este paso simplemente requiere contar el número de elementos en la lista de requisitos de un curso, el cual se da en la línea que presenta al curso en el _input_. Por ejemplo, si se lee la línea `34 21 32 14` entonces el curso 34 debe ubicarse en `listas[3]`. Además, puede ser cómodo mantener el índice de `listas` en el que se encuentra el curso (en este caso 3) como información del nodo del curso, para que luego en el paso 3 este se pueda mover más fácilmente.

Al terminar de leer el _input_, si este es válido, todos los cursos serán accesibles en alguna parte de `listas`, y todos tendrán el número que indica en qué parte de `listas` se encuentran.

Como no es posible saber con anticipación cuál es el máximo número de requisitos que llegará a contener un curso, la variable `listas` se inicializa con el máximo imaginable: El número de cursos menos 1.

## Paso 3

En esta parte, se debe obtener algún curso de `listas[0]`[^1] siempre que haya alguno. Se imprime su id y luego se itera en la lista de los cursos que provee. Cada uno de estos cursos se saca de su lugar en `listas[k]` y se mueve a `listas[k-1]`, de forma que cuando sea hayan tomado todos sus requisitos, el curso se encuentre en `listas[0]`.

Para mover el curso, es necesario eliminarlo de `listas[k]` y luego añadirlo a `listas[k-1]`. Como esta es una operación muy común, es conveniente que para hacerla más rápida se sigan dos consejos:
1. Guardar en cada curso una variable **k** que indique en qué parte de `listas` se encuentra, para no tener que buscar por todo `listas` cada vez que se elimina un curso.
2. Que los cursos estén guardados en una lista doblemente enlazada, de forma que eliminar uno sea una operación $O(1)$.

[^1]: El primero, ya que es el más fácil de obtener.

## Paso 4

Como se menciona arriba, se debe eliminar el curso recién impreso de `listas[0]` y repetir la operación del paso 3 hasta que no queden más cursos, lo cual si el _input_ es válido debería ocurrir cuando `listas[0]` es una lista vacía.

## Estructuras de Datos

Por las razones mencionadas en la subsección del paso 3, es conveniente que el arreglo `listas` contenga listas doblemente enlazadas. En cambio, las listas de cursos que cada curso provee no requieren ser eliminadas, por lo que las listas pueden ser de un solo enlace sin perjudicar el rendimiento del programa.

Se debe mencionar que en el paso 3 es necesario eliminar los cursos de una lista doblemente enlazada, es necesario guardar no solamente los cursos sino que los nodos de esta lista que contienen los cursos, ya que para eliminarlos es necesario tener punteros al próximo elemento y el anterior. Por lo tanto, hay dos posibles formas de enfrentar este problema:

1. Que en la lista que cada curso `c` tiene que contiene los cursos para los que `c` es requisito, se guarden los nodos de los cursos con respecto a `listas`.

2. Que cada elemento de clase `Curso` contenga de por sí información sobre los nodos siguiente y anterior.

Por simplicidad, se eligió la segunda opción, aunque en un proyecto real convendría más la primera ya que mantiene mejor separados los distintos problemas y por lo tanto deriva en código más fácil de mantener.

# Implementación

## Paso 1

## Paso 2

## Paso 3

## Paso 4

# Resultados y conclusiones

# Anexo: Código