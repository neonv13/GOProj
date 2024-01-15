package org.example;

import java.util.*;
import java.util.stream.IntStream;

class flipedBoxes {
    int grid;
    int firstBox;
    int secondBox;
    public flipedBoxes(int grid, int first, int second){
        this.grid = grid;
        this.firstBox = first;
        this.secondBox = second;
    }

}
class proposedSudoku {
    int [][][] sudoku;
    flipedBoxes boxes;

    public proposedSudoku(int[][][] sudoku, flipedBoxes boxes) {
        this.sudoku = sudoku;
        this.boxes = boxes;
    }
}
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
    public int tmpcount = 0;
    public int [][][] sudokuFix = Create9x3x3();
    public int [][][] copySudoku(int[][][] sudoku){
        int [][][] newSudoku = new int [9][3][3];
        for(int x= 0; x < 9; x++){
            for (int i = 0; i< 3; i++){
                for(int z = 0; z<3; z++){
                    newSudoku[x][i][z] = sudoku[x][i][z];
                }
            }
        }
        return newSudoku;
    }
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
        int[][] castSudoku = castTo2Dim(sudoku);
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
    public int[][] castTo2Dim(int[][][] sudoku){
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
        return castSudoku;
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

    public int calculateErrors(int[][][] sudoku){
        int errorsCount = 0;
        int[][] castSudoku = castTo2Dim(sudoku);
        for (int i = 0; i<9; i++){
            errorsCount = errorsCount + calculateErrorsRowColumn(castSudoku, i, i);
        }
        return errorsCount;
    }
    public int calculateErrorsRowColumn(int[][] sudoku, int colummn, int row){
        int[] colummnToArr = new int[9];
        for (int i= 0; i < sudoku[0].length; i++){
            colummnToArr[i] = sudoku[i][colummn];
        }
        int errorsVertical = IntStream.of(colummnToArr).distinct().toArray().length;
        int errorsHorizontal = IntStream.of(sudoku[row]).distinct().toArray().length;

        return (9 - errorsVertical) + (9 - errorsHorizontal);
    }

    public flipedBoxes choseBoxesToFlip(int grid){
        Random r = new Random();
        while(true){

            int rN1 = r.nextInt(9);
            int rN2;
            do {
                rN2 = r.nextInt(9);
            } while (rN2 == rN1);
            if( sudokuFix[grid][rN1/3][rN1%3] == 0 && sudokuFix[grid][rN2/3][rN2%3] == 0){
                return new flipedBoxes(grid, rN1, rN2);
            }
        }
    }
    public int [][][] flipBoxes(int[][][] sudoku, flipedBoxes boxes){
        int tmp = sudoku[boxes.grid][boxes.firstBox/3][boxes.firstBox%3];
        sudoku[boxes.grid][boxes.firstBox/3][boxes.firstBox%3] = sudoku[boxes.grid][boxes.secondBox/3][boxes.secondBox%3];
        sudoku[boxes.grid][boxes.secondBox/3][boxes.secondBox%3] = tmp;
        return sudoku;
    }
    public int sumOfOneBlock(int[][][] sudoku, int block){
        int sum = 0;
        for (int i = 0; i<3; i++){
            for(int x=0; x<3; x++){
                if(sudokuFix[block][i][x] != 0){
                    sum +=1;
                }
            }
        }

        return sum;
    }
    public int chooseNumberOfIterations() {
        int numberOfIterations = 0;
        for (int j = 0; j < 9; j++) {
            for(int x= 0; x < 3; x++){
                for (int i = 0; i< 3; i++){
                    if (sudokuFix[j][x][1] != 0) {
                        numberOfIterations++;
                    }
                }
            }

        }

        return numberOfIterations;
    }

    public proposedSudoku proposedState(int[][][] sudoku){
        Random r = new Random();
        int grid = r.nextInt(9);

        if(sumOfOneBlock(sudokuFix, grid)> 6){
            return new proposedSudoku(sudoku, new flipedBoxes(grid,1,1));
        }
        flipedBoxes boxes = choseBoxesToFlip(grid);
        proposedSudoku newsudoku = new proposedSudoku(flipBoxes(sudoku, boxes), boxes);


        return newsudoku;
    }

    public Object[] chooseNewState(int [][][] sudoku, double sigma){
        Object[] result = new Object[2];
        proposedSudoku propState = proposedState(copySudoku(sudoku));
        int[][][] newSudoku = propState.sudoku;
        flipedBoxes boxes = propState.boxes;
        int currentCost = calculateErrors(sudoku);
        int newCost = calculateErrors(newSudoku);
        double costDifference = newCost-currentCost;
        double rho = Math.exp(-costDifference / sigma);
        if (Math.random() < rho) {
            result[0] = newSudoku;
            result[1] = costDifference;
        } else {
            result[0] = sudoku;
            result[1] = 0.0;
        }
        return result;
    }


    public double calculateInitialSigma(int[][][] sudoku) {
        List<Double> listOfDifferences = new ArrayList<>();
        int[][][] tmpSudoku = sudoku.clone();

        for (int i = 1; i <= 9; i++) {
            proposedSudoku newSudoku = proposedState(tmpSudoku);
            listOfDifferences.add((double) calculateErrors(newSudoku.sudoku));
        }

        return calculateStandardDeviation(listOfDifferences);
    }

    private static double calculateStandardDeviation(List<Double> list) {
        double sum = 0;
        double mean = calculateMean(list);

        for (double value : list) {
            sum += Math.pow(value - mean, 2);
        }

        double variance = sum / list.size();
        return Math.sqrt(variance);
    }
    private static double calculateMean(List<Double> list) {
        double sum = 0;
        for (double value : list) {
            sum += value;
        }
        return sum / list.size();
    }




    public void solveSukoku(){
        int solutionFound = 0;
        while(solutionFound == 0){
            double decrFactor = 0.99;
            int stuckcount = 0;
            printSudoku(sudokutmp);
            int[][][] sudoku = Create9x3x3();
            int[][][] tmpSudoku = fillrandom9x3x3(sudoku);
            printSudoku9x3x3(tmpSudoku);
            double sigma = calculateInitialSigma(tmpSudoku);
            double score = calculateErrors(tmpSudoku);
            System.out.println(sigma);
            int itteration = chooseNumberOfIterations();
            if (score <= 0){
                solutionFound++;
            }
//            solutionFound++;
            while (solutionFound == 0){
                double previousScore = score;
                    for(int i=0; i< itteration; i++){
                        Object[] newState = chooseNewState(copySudoku(tmpSudoku) , sigma);
                        tmpSudoku = (int[][][]) newState[0];
                        score += (double) newState[1];
                        System.out.println(score);
                        if(score <=0){
                            break;
                        }
                    }
                sigma *=decrFactor;
                if(score <=0){
                    solutionFound = 1;
                    printSudoku9x3x3(tmpSudoku);
                    System.out.println(calculateErrors(tmpSudoku));
                    break;
                }
                if(score >= previousScore){
                    stuckcount++;
                }
                else{
                    stuckcount = 0;
                }
                if(stuckcount > 80){
                    sigma +=1;
                }

            }

        }
    }
}
