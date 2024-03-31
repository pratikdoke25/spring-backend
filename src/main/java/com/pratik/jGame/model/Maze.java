package com.pratik.jGame.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import static com.pratik.jGame.tools.MazeGenerator.generateMaze;

@Data
@NoArgsConstructor
public class Maze {


    private boolean[][] grid;

    private int size;

    public Maze(MazeInitializer mazeInitializer){
        this.grid = generateMaze(mazeInitializer);
        this.size = mazeInitializer.getSize();
    }


}
