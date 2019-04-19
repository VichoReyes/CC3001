---
title: "Informe Tarea 2"
author: [Vicente Reyes]
date: "2019-04-08"
subject: "Java"
keywords: [Backtracking, Recursión]
lang: "es"
titlepage: true

---

# Introducción

En esta tarea se pide escribir un programa solucionador de Sudokus. El Sudoku debe ser leído de la entrada estándar, siguiendo un formato detallado más adelante, y luego se deben imprimir:
* El Sudoku solucionado
* La cantidad de casillas "fáciles" que fueron rellenas (más detalles acerca de qué significa "fácil" más adelante)

Esta tarea tiene el objetivo de aprender a usar los conceptos de backtracking e invariante, y sentirse cómodo con la idea de programar mutando una matriz de datos.

# Implementación

Afortunadamente, los pasos a seguir están bastante claros en el PDF de las instrucciones. Lo único que faltaba era implementar este diseño

## Diseño de la solución

Directo del enunciado:

1. Leer el tablero desde la entrada estándar, éste consiste de 9 líneas, con 9 números
separados por espacios (se recomienda usar la clase Scanner y su función readLine
para esto) y guardarlo en una matriz de enteros (int[][]). Los ceros representan casillas
en blanco.
2. Crear otra matriz donde se indican los posibles candidatos para cada espacio (`int[][][]
candidatos`, donde `candidatos[i][j][valor]` indica si `valor` puede ir en el espacio (i,j) o si ya
está descartado).
3. Hacer una pasada por el tablero y para cada espacio que tenga un valor asignado,
descartar el valor correspondiente en todas las casillas de su misma fila, columna y caja
de 3x3.
4. Rellenar todos los espacios del tablero donde solo se pueda poner un número, y
descartar el número asignado de la fila, columna y caja como en el paso 3.
5. Repetir el paso 3 hasta que ya no queden espacios donde solo hay un número posible.
6. Usando backtracking, ir rellenando todos los espacios vacíos, probando con cada
candidato posible en cada espacio (es importante probar sólo con los candidatos
posibles y no con todos los números del uno al nueve). Use una función recursiva para
este paso.
7. Una vez completado el sudoku, imprimir en pantalla la solución, con el mismo formato
con el que se recibió, nueve líneas con nueve números cada una. Luego imprima en
pantalla otra línea indicando la cantidad de casillas rellenadas antes de entrar al paso
de backtracking. (Ver sección de ejemplos de entrada y salida).

## Del diseño al código

Para resolver este problema se siguieron las instrucciones datas por el enunciado de forma muy directa. Cada función llamada por main representa un paso específico. Para dar un claro ejemplo, la función `discard_when_possible` es el paso tres del enunciado.

_ _

```java
public static void main(String[] args) {
		get_sudoku();
		int filled_before_backtracking = 0;
		while (true) {
				discard_when_possible();
				int just_filled = fill_cells();
				if (just_filled == 0) {
						break;
				}
				filled_before_backtracking += just_filled;
		}
		backtracking(0);
		print_sudoku();
		System.out.println(filled_before_backtracking);
}
```

Cada una de las funciones que resuelven el Sudoku trabaja sobre dos estructuras globales que guardan información sobre el proceso de solucionado del Sudoku. Éstas son:
* `board`: Una matriz de enteros de 9×9 que representa el Sudoku, donde las casillas que tienen un 0 están vacías.
* `discarded`: Una "matriz 3D" de booleanos que guarda información sobre los candidatos posibles que quedan para cada casilla. Se usan 10 booleanos en lugar de 9 para evitar tener que restar 1 cada vez que se quiere comprobar un número. Es decir, en lugar de tener que decir algo del estilo `discarded[i][j][board[i][j]-1] = true;` basta con decir `discarded[i][j][board[i][j]] = true;`.

_ _ 

```java
static int board[][] = new int[9][9]; // first coordinate is row, second is column
static boolean discarded[][][] = new boolean[9][9][10]; // always skip the first one
```

La manera de leer el sudoku utilizada asume simplemente que en la entrada habrá 81 números, correspondiendo a cada casilla del Sudoku, con 0 representando las casillas vacías. Esta implementación, pese a no usar la función `readline` como era sugerido, es simple, clara y logra su cometido incluso cuando los fines de línea no funcionan al copiar y pegar.

_ _ 

```java
static void get_sudoku() {
		Scanner sc = new Scanner(System.in);

		for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
						board[i][j] = sc.nextInt();
				}
		}
		sc.close();
}
```

En los pasos 3 y 4 de las instrucciones (que corresponden a las funciones `discard_when_possible` y `fill_cells`) se utilizan loops a lo largo de la matriz para encontrar casillas que pueden solucionarse. Una valiosa invariante en `discard_when_possible` es que si un número ya está definido (es distinto de 0 en `board`) pero además tiene casillas descartadas, es porque ya está hecho todo el trabajo que se puede hacer con respecto a esa casilla, y por lo tanto no es necesario repetirlo (como dice el comentario en el siguiente fragmento de código).

_ _ 

```java
if (num != 0 && !discarded[i][j][num]) { // don't repeat work
		discard_column(i, j, num);
		discard_row(i, j, num);
		discard_block(i, j, num);
}
```

El paso 6, el backtracking, toma como argumento el número de la primera casilla que puede no haber sido trabajada todavía ya que así evita buscar un lugar vacío desde el principio cada vez que se llama a la función recursivamente.[^1]

[^1]: Se usa un solo argumento en lugar de dos porque así aumentarlo es trivial en comparación con la alternativa, que sería revisar si se llegó al final de una fila para reiniciar ESE contador y aumentar el de las columnas A MENOS QUE se haya terminado el de las columnas también en cuyo caso termina el programa. La complejidad de este enfoque es claramente mayor, y por lo tanto se eligió el de aumentar un sólo número, del 0 al 81.

# Resultados y conclusiones

Aunque en cantidad de líneas de código la solución es larga, escribirla resulta ser simple si se hace de manera ordenada, probando que cada nuevo paso funcione por separado antes de escribir el siguiente. Por otro lado, existe un aspecto negativo de esta manera de trabajar, que es que tiende a crear programas más largos, con funciones muy similares entre ellas, ya que cuando una parte funciona bien, uno se siente reacio a cambiarla para poder reutilizar una función en otro paso del problema. Como resultado, el programa tiene funciones que claramente hacen trabajos muy similares (por ejemplo, la mayoría de las líneas de algo fueron copiadas de origen).

Hablando sobre las técnicas particulares de programación utilizadas, podemos concluir que el backtracking es una técnica muy poderosa y no necesariamente ineficiente, sobre todo cuando es complementada por técnicas no recursivas que restringen las posibilidades.

Notemos que este programa solo funciona para Sudokus válidos, ya que:
1. `main` ni siquiera revisa el valor retornado por `backtracking(0)`, sólo asume que el sudoku está resuelto.
2. `read_sudoku` asume que recibirá 81 números en la entrada estándar, sin ningún tipo de corrección de errores si encuentra EOF.

Afortunadamente, añadir mejor reporte de errores no debería ser muy difícil. 

Lo que sí podría ser un poco más de trabajo sería hacer que el algoritmo averiguara el número de soluciones posibles para un tablero determinado. Habría que cambiar la firma de `backtracking` y sumar todos los casos posibles, lo cual significaría analizarlos todos por lo que la potencia del computador podría llegar a ser un limitante.

# Anexo: Código

El código está en inglés porque el lenguaje también lo está, y usar siempre el mismo idioma reduce la carga cognitiva.

_ _

```java
import java.util.Scanner;

class Main {
    static int board[][] = new int[9][9]; // first coordinate is row, second is column
    static boolean discarded[][][] = new boolean[9][9][10]; // always skip the first one

    public static void main(String[] args) {
        get_sudoku();
        int filled_before_backtracking = 0;
        while (true) {
            discard_when_possible();
            int just_filled = fill_cells();
            if (just_filled == 0) {
                break;
            }
            filled_before_backtracking += just_filled;
        }
        backtracking(0);
        print_sudoku();
        System.out.println(filled_before_backtracking);
    }

    static void discard_when_possible () {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                if (num != 0 && !discarded[i][j][num]) { // don't repeat work
                    discard_column(i, j, num);
                    discard_row(i, j, num);
                    discard_block(i, j, num);
                }
            }
        }
    }

    static void discard_row(int row, int column, int num) {
        for (int i = 0; i < 9; i++) {
            discarded[row][i][num] = true;
        }
    }

    static void discard_column(int row, int column, int num) {
        for (int i = 0; i < 9; i++) {
            discarded[i][column][num] = true;
        }
    }

    static void discard_block(int row, int column, int num) {
        // I only care about the start of the blocks
        row = row - row%3;
        column = column - column%3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                discarded[row+i][column+j][num] = true;
            }
        }
    }

    static int fill_cells() {
        int filled = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    if (replace_if_possible(i, j)) {
                        filled++;
                    }
                }
            }
        }
        return filled;
    }

    // checks if the spot has only one possible answer left
    // in that case, sets the spot to that answer and returns true
    // otherwise, returns false
    static boolean replace_if_possible(int row, int column) {
        int candidate = 0; // silence "might now have been initialized" error
        boolean found_candidate = false;
        for (int i = 1; i <= 9; i++) {
           if (!discarded[row][column][i] && found_candidate) {
               return false;
           } else if (!discarded[row][column][i]) {
               candidate = i;
               found_candidate = true;
           }
        }
        if (!found_candidate) {
            System.out.println("there was a problem");
            System.exit(1);
        }
        board[row][column] = candidate;
        return true;
    }

    static void get_sudoku() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = sc.nextInt();
            }
        }
        sc.close();
    }

    static void print_sudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.printf("%d ", board[i][j]);
            }
            System.out.println();
        }
    }

    static boolean backtracking(int cur_index) {
        // first, find a cell to try to fill
        // each call to the function will be in charge of one cell
        while (cur_index < 9*9) {
            int row = cur_index/9;
            int column = cur_index%9;
            if (board[row][column] != 0) {
                cur_index++;
                continue;
            }
            for (int i = 1; i <= 9; i++) {
                if (discarded[row][column][i]) {
                    continue;
                }
                board[row][column] = i;
                if (check(row, column) && backtracking(cur_index+1)) {
                    return true;
                }
            }
            board[row][column] = 0;
            return false;
        }

        // couldn't find an empty cell => finished
        return true;
    }

    static boolean check(int row, int column) {
        int num = board[row][column];

        // check the row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                if (i != column) {
                    return false;
                }
            }
        }

        // check the column
        for (int i = 0; i < 9; i++) {
            if (board[i][column] == num) {
                if (i != row) {
                    return false;
                }
            }
        }

        // check the block
        int base_row = row - row%3;
        int base_column = column - column%3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[base_row+i][base_column+j] == num) {
                    if (base_row+i != row || base_column+j != column) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
```
