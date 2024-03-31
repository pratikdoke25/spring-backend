package com.pratik.jGame.model;

import lombok.Data;

import java.util.LinkedList;

@Data
public class TraveledPathRequest {
    private LinkedList<LinkedList<Integer>> visitedCells;

}
