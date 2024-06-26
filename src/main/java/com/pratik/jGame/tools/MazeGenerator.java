package com.pratik.jGame.tools;

import com.pratik.jGame.exception.MazeGenerationException;
import com.pratik.jGame.model.Cell;
import com.pratik.jGame.model.MazeInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class MazeGenerator {

    public static boolean[][] generateMaze(MazeInitializer mazeInitializer) {

        //For secure reasons, size cannot be less than 1
        if (mazeInitializer.getSize() < 1) {
            throw new MazeGenerationException("Size of maze must be positive");
        }

        if (mazeInitializer.getDifficulty() < 1 || mazeInitializer.getDifficulty() > 10) {
            throw new MazeGenerationException("Difficulty must be between 1 and 10");
        }

        int width = mazeInitializer.getSize() * 2 - 1;
        int height = mazeInitializer.getSize() * 2 - 1;

        int maxDifficulty = 10;
        int numberOfCircles = (maxDifficulty + 1 - mazeInitializer.getDifficulty()) * (mazeInitializer.getSize() / 4);

        boolean[][] maze = new boolean[height][width];

        //Filling array with true values
        for (int i = 0; i < maze.length; i++) {
            Arrays.fill(maze[i], true);
        }

        createTypicalMaze(maze);
        createCirclesInMaze(numberOfCircles, width, height, maze);

        return maze;
    }
    private static void createCirclesInMaze(int countOfCircles, int width, int height, boolean[][] maze) {
        //Count of erased walls
        int alreadyErased = 0;

        Random randomGenerator = new Random();

        //We make a loop until we delete selected count of walls and make this count of circles
        while (alreadyErased < countOfCircles) {

            //We have to delete either even row and odd column or odd row an even column
            int randomICoordinate, randomJCoordinate;

            //And here we choose it
            boolean isEvenRow = randomGenerator.nextBoolean();
            if (isEvenRow) {
                randomICoordinate = randomGenerator.nextInt((height + 1) / 2) * 2;
                randomJCoordinate = (randomGenerator.nextInt((width - 1) / 2) * 2) + 1;
            }
            else {
                randomICoordinate = (randomGenerator.nextInt((height - 1) / 2) * 2) + 1;
                randomJCoordinate = randomGenerator.nextInt((width + 1) / 2) * 2;
            }

            //Deleting a wall if there was it
            if (maze[randomICoordinate][randomJCoordinate]) {
                alreadyErased++;
                maze[randomICoordinate][randomJCoordinate] = false;
            }
        }

    }

    /**
     * Creates typical maze. Typical means that it does have only one solution
     * @param maze array to form a maze
     */
    private static void createTypicalMaze(boolean[][] maze) {
        /* The algorithm is DFS with random choosing element */

        //A stack of visited cells
        LinkedList<Cell> visitedCells = new LinkedList<>();

        Random randomGenerator = new Random();

        //We start from top-left corner
        visitedCells.addFirst(new Cell(0, 0));
        maze[0][0] = false;

        boolean canLastMove = true;

        //We go until we fill an entire array
        while (!visitedCells.isEmpty()) {
            //Choosing random way
            //1 - top, 2 - right, 3 - down, 4 - left

            Cell currentCell;
            if (canLastMove) {
                currentCell = visitedCells.getFirst();
            }
            else {
                currentCell = visitedCells.get(randomGenerator.nextInt(visitedCells.size()));
            }

            //Defining accessible ways to pick from
            ArrayList<Integer> possibleWays = getPossibleWays(currentCell, maze);

            //If we have where to go from current cell
            if (!possibleWays.isEmpty()) {

                //Choosing where to go
                int way = possibleWays.get(randomGenerator.nextInt(possibleWays.size()));

                switch (way) {
                    case 1 -> { //Go up
                        maze[currentCell.getICoordinate() - 1][currentCell.getJCoordinate()] = false;
                        maze[currentCell.getICoordinate() - 2][currentCell.getJCoordinate()] = false;
                        visitedCells.addFirst(new Cell(currentCell.getICoordinate() - 2, currentCell.getJCoordinate()));
                    }
                    case 2 -> { //Go right
                        maze[currentCell.getICoordinate()][currentCell.getJCoordinate() + 1] = false;
                        maze[currentCell.getICoordinate()][currentCell.getJCoordinate() + 2] = false;
                        visitedCells.addFirst(new Cell(currentCell.getICoordinate(), currentCell.getJCoordinate() + 2));
                    }
                    case 3 -> { //Go down
                        maze[currentCell.getICoordinate() + 1][currentCell.getJCoordinate()] = false;
                        maze[currentCell.getICoordinate() + 2][currentCell.getJCoordinate()] = false;
                        visitedCells.addFirst(new Cell(currentCell.getICoordinate() + 2, currentCell.getJCoordinate()));
                    }
                    case 4 -> { //Go left
                        maze[currentCell.getICoordinate()][currentCell.getJCoordinate() - 1] = false;
                        maze[currentCell.getICoordinate()][currentCell.getJCoordinate() - 2] = false;
                        visitedCells.addFirst(new Cell(currentCell.getICoordinate(), currentCell.getJCoordinate() - 2));
                    }
                }

                canLastMove = true;
            }
            //If we don't have where to go from current cell, then we delete it from visited cells
            else {
                visitedCells.remove(currentCell);
                canLastMove = false;
            }
        }
    }

    /**
     * Calculates possible ways to go from current cell
     * @param currentCell the cell to calculate ways from
     * @param maze maze in which we calculate
     * @return list of ways, where 1 is up, 2 is right, 3 is down, 4 is left
     */
    private static ArrayList<Integer> getPossibleWays(Cell currentCell, boolean[][] maze) {
        ArrayList<Integer> possibleWays = new ArrayList<>();
        //If we can go up
        if (currentCell.getICoordinate() != 0
                && maze[currentCell.getICoordinate() - 2][currentCell.getJCoordinate()]) {
            possibleWays.add(1);
        }
        //If we can go right
        if (currentCell.getJCoordinate() != (maze[0].length - 1)
                && maze[currentCell.getICoordinate()][currentCell.getJCoordinate() + 2]) {
            possibleWays.add(2);
        }
        //If we can go down
        if (currentCell.getICoordinate() != (maze.length - 1)
                && maze[currentCell.getICoordinate() + 2][currentCell.getJCoordinate()]) {
            possibleWays.add(3);
        }
        //If we can go left
        if (currentCell.getJCoordinate() != 0
                && maze[currentCell.getICoordinate()][currentCell.getJCoordinate() - 2]) {
            possibleWays.add(4);
        }

        return possibleWays;
    }
}
