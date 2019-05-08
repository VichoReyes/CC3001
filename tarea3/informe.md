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

# Implementación

# Resultados y conclusiones

# Anexo: Código