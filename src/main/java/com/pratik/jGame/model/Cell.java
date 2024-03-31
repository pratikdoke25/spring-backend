package com.pratik.jGame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell {

    @JsonProperty("iCoordinate")
    private int iCoordinate;

    @JsonProperty("jCoordinate")
    private int jCoordinate;

    public Cell(Cell another) {
        this.iCoordinate = another.getICoordinate();
        this.jCoordinate = another.getJCoordinate();
    }

    @Override
    public boolean equals(Object another) {
        if(this==another) return true;

        if (another == null || getClass() != another.getClass()) return false;
        Cell cell = (Cell) another;
        return iCoordinate == cell.iCoordinate && jCoordinate == cell.jCoordinate;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "iCoordinate=" + iCoordinate +
                ", jCoordinate=" + jCoordinate +
                '}';
    }
}
