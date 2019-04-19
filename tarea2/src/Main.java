// tarea de Vicente Reyes
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
