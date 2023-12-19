package org.example;

import java.util.*;

public class Sudoku {
    public int[][] sudokutmp = {
            {0, 2, 4, 0, 0, 7, 0, 0, 0},
            {6, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 8, 0, 4, 1, 5},
            {4, 3, 1, 0, 0, 5, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 3, 2},
            {7, 9, 0, 0, 0, 0, 0, 6, 0},
            {2, 0, 9, 7, 1, 0, 8, 0, 0},
            {0, 4, 0, 0, 9, 3, 0, 0, 0},
            {3, 1, 0, 0, 0, 4, 7, 5, 0}
    };
    public void printSudoku(int[][] sudoku) {
        System.out.println();
        for (int i = 0; i < sudoku.length; i++) {
            String line = "";
            if (i == 3 || i == 6) {
                System.out.println("---------------------");
            }
            for (int j = 0; j < sudoku[i].length; j++) {
                if (j == 3 || j == 6) {
                    line += "| ";
                }
                line += sudoku[i][j] + " ";
            }
            System.out.println(line);
        }
    }
    public void printSudoku9x3x3(int[][][] sudoku){
        int[][] castSudoku = new int[9][9];
        for(int x= 0; x < 3; x++){
            for (int i = 0; i< 3; i++){
                castSudoku[x][i] = sudoku[0][x][i];
                castSudoku[x][i+3] = sudoku[1][x][i];
                castSudoku[x][i+6] = sudoku[2][x][i];
                castSudoku[x+3][i] = sudoku[3][x][i];
                castSudoku[x+3][i+3] = sudoku[4][x][i];
                castSudoku[x+3][i+6] = sudoku[5][x][i];
                castSudoku[x+6][i] = sudoku[6][x][i];
                castSudoku[x+6][i+3] = sudoku[7][x][i];
                castSudoku[x+6][i+6] = sudoku[8][x][i];
            }
        }
        printSudoku(castSudoku);

    }
    public int[][][] Create9x3x3(){
        int[][][] sudokuGrid = new int[9][3][3];

        for(int x= 0; x < 3; x++){
            for (int i = 0; i< 3; i++){
                sudokuGrid[0][x][i] = sudokutmp[x][i];
                sudokuGrid[1][x][i] = sudokutmp[x][i+3];
                sudokuGrid[2][x][i] = sudokutmp[x][i+6];
                sudokuGrid[3][x][i] = sudokutmp[x+3][i];
                sudokuGrid[4][x][i] = sudokutmp[x+3][i+3];
                sudokuGrid[5][x][i] = sudokutmp[x+3][i+6];
                sudokuGrid[6][x][i] = sudokutmp[x+6][i];
                sudokuGrid[7][x][i] = sudokutmp[x+6][i+3];
                sudokuGrid[8][x][i] = sudokutmp[x+6][i+6];
            }
        }

        return sudokuGrid;
    }
    public int[][][] fillrandom9x3x3(int[][][] sudoku){
        Random random = new Random();
        for(int g=0; g<9; g++){
            for(int x= 0; x < 3; x++){
                for (int i = 0; i< 3; i++){
                    if(sudoku[g][x][i] == 0){
                        boolean tmp = true;
                        while (tmp){
                            int rInt = random.nextInt(10);
                            if(!Arrays.stream(sudoku[g][0]).anyMatch(num -> num == rInt)&&
                                    !Arrays.stream(sudoku[g][1]).anyMatch(num -> num == rInt)&&
                                    !Arrays.stream(sudoku[g][2]).anyMatch(num -> num == rInt)){
                                tmp = false;
                                sudoku[g][x][i] = rInt;
                            }
                        }

                    }
                }
            }
        }
    return sudoku;
    }




    public void solveSukoku(){
        printSudoku(sudokutmp);
        int [][][] sudokuTest = Create9x3x3();
        sudokuTest = fillrandom9x3x3(sudokuTest);

        printSudoku9x3x3(sudokuTest);
    }
}
