package com.pratik.jGame.service;

import com.pratik.jGame.exception.MazeGenerationException;
import com.pratik.jGame.exception.ShortestPathCalculationException;
import com.pratik.jGame.exception.StatsCalculationException;
import com.pratik.jGame.model.Cell;
import com.pratik.jGame.model.Maze;
import com.pratik.jGame.model.MazeInitializer;
import com.pratik.jGame.model.TraveledPathRequest;
import com.pratik.jGame.tools.MazeGenerator;
import com.pratik.jGame.tools.MazeSolver;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@Getter
public class MazeService {

    private final Maze maze;
    public MazeService() {
        this.maze = new Maze();
    }

    public boolean[][] generateMaze(MazeInitializer mazeInitializer) {

        try {
            maze.setGrid(MazeGenerator.generateMaze(mazeInitializer));
            maze.setSize(mazeInitializer.getSize());
            return maze.getGrid();
        } catch (Exception e) {
            throw new MazeGenerationException("Error generating maze", e);
        }

    }

    /**
     * Gets the shortest path in the maze using BFS algorithm.
     *
     * @return A list of coordinates representing the shortest path.
     */
    public LinkedList<LinkedList<Integer>> getShortestPath() {

        try {

            LinkedList<Cell> solvedLinkedListOfCells = MazeSolver.solveMazeBFS(
                    maze,
                    new Cell(0, 0),
                    new Cell(maze.getSize() * 2 - 2, maze.getSize() * 2 - 2));
            LinkedList<LinkedList<Integer>> solvedLinkedListOfLinkedLists = new LinkedList<>();

            for (Cell cell : solvedLinkedListOfCells) {
                LinkedList<Integer> solvedLinkedList = new LinkedList<>();
                solvedLinkedList.add(cell.getICoordinate());
                solvedLinkedList.add(cell.getJCoordinate());
                solvedLinkedListOfLinkedLists.add(solvedLinkedList);
            }
            return solvedLinkedListOfLinkedLists;

        } catch (Exception e) {
            throw new ShortestPathCalculationException("Error calculating shortest path", e);
        }
    }

    /**
     * Calculates and returns the percentage of the traveled path's length compared to the shortest path's length.
     *
     * @param traveledPathRequest The request containing the traveled path.
     * @return The percentage of traveled path's length compared to the shortest path's length.
     */
    public Double getStats(TraveledPathRequest traveledPathRequest) {

        try {

            int traveledPathLength = traveledPathRequest.getVisitedCells().size();
            int shortestPathLength = getShortestPath().size();

            return ((double) traveledPathLength / shortestPathLength) * 100;

        } catch (StatsCalculationException e) {
            throw new StatsCalculationException("Error calculating stats", e);
        }

    }
}

