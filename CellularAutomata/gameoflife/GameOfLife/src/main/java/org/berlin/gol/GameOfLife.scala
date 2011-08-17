/**
 * Copyright (c) 2006-2011 Berlin Brown.  All Rights Reserved
 *
 * http://www.opensource.org/licenses/bsd-license.php
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * * Neither the name of the Botnode.com (Berlin Brown) nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Date: 12/15/2009 
 * Description: Extend this customizable Swing wrapper library.
 * 
 * Doing it wrong example, no refactoring
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
/**
 * Created On: Jan 1, 2011
 * File: GameOfLife.scala
 */
package org.berlin.gol

import java.util.Random

/**
 * @author berlinbrown
 *
 */
class GameOfLife(rowCount:Int, colCount:Int) {
    
    /**
     * Cell requires a row and col, a cell can have an alive or dead state.
     */
    class Cell(val row:Int, val col:Int, val alive:Boolean) {
              
        /**
         * Number of neighbors.
         * @param x$1
         */
        var neighbors:Int = 0
        
        override def toString = "Cell:row=" + row + " col=" + col + " alive=" + alive    
    }    
    
    
    /**
     * Cell Grid contains all cells.
     */
    class CellGridSystem() {
        
        /**
         * Cells are represented by a scala multi-dimensional array.
         * @return
         */
        val cells = new Array[Array[Cell]](rowCount, colCount)
        
        /**
         * Create initial grid.
         */
        def createInitialGrid() : CellGridSystem = {
            val randomGenerator:Random = new Random(System.currentTimeMillis)
            for (j <- 0 until rowCount) {
                for (i <- 0 until colCount) {
                    // Randomly create alive or dead cell
                    val randNextInt = randomGenerator.nextInt(100)
                    if (randNextInt < 20) {
                        cells(j)(i) = new Cell(i, j, true)
                        println("Creating cell i=" + i + " j=" + j + " // " + randNextInt)
                    } else {
                        cells(j)(i) = new Cell(i, j, false)
                    }
                }
            } 
            this
        }
        
        /**
         * Use game of life rules to create next generation.
         */
        def nextGenerationRules() : CellGridSystem = {
            
            var totalCountAliveCells = 0
            val newGrid = new CellGridSystem
            for (j <- 0 until rowCount) {
                for (i <- 0 until colCount) {
                                        
                    val neighborCells = new Array[Cell](8);
                    neighborCells(0) = if ((j == 0) || (i == 0)) null else cells(j-1)(i-1)
                    neighborCells(1) = if (j == 0) null else cells(j-1)(i)
                    neighborCells(2) = if ((j == 0) || (i == colCount - 1)) null else cells(j-1)(i+1)
     
                    neighborCells(3) = if (i == 0) null else cells(j)(i-1)                    
                    neighborCells(4) = if (i == colCount - 1) null else cells(j)(i+1)
                    
                    neighborCells(5) = if ((j == rowCount - 1) || (i == 0)) null else cells(j+1)(i-1)
                    neighborCells(6) = if (j == rowCount - 1) null else cells(j+1)(i)
                    neighborCells(7) = if ((j == rowCount - 1) || (i == colCount - 1)) null else cells(j+1)(i+1)
                    
                    // Iterate through cells and add to neighbor count
                    for (z <- 0 until neighborCells.length) {
                        if ((neighborCells(z) != null) && neighborCells(z).alive) {                            
                            cells(j)(i).neighbors += 1
                        }
                    } // End for z
                    
                    // 1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
                    // 2. Any live cell with two or three live neighbours lives on to the next generation.
                    // 3. Any live cell with more than three live neighbours dies, as if by overcrowding.
                    // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                    if (cells(j)(i).alive) {
                       
                        if (cells(j)(i).neighbors < 2) {
                            newGrid.cells(j)(i) = new Cell(j, i, false)
                        } else if (cells(j)(i).neighbors == 2 || cells(j)(i).neighbors == 3) {
                            newGrid.cells(j)(i) = new Cell(j, i, true)
                        } else if (cells(j)(i).neighbors > 3) {
                            newGrid.cells(j)(i) = new Cell(j, i, false)
                        }
                            
                    } else {
                        if (cells(j)(i).neighbors == 3) {
                            newGrid.cells(j)(i) = new Cell(j, i, true)
                        }
                    }
                    
                    if (newGrid.cells(j)(i) == null) {
                        newGrid.cells(j)(i) = new Cell(j, i, false)
                    }
                } // End for i
            }                        
            newGrid
        }
        
        /**
         * Get status of cell (alive or dead).
         * 
         * @param col x-position
         * @param row y-position
         * 
         * @return living or not
         */
        def getCell(col:Int, row:Int) : Boolean = {
            cells(row)(col).alive
        }
        
    } // End of class
        
    def createGridSystem() : CellGridSystem = {
        new CellGridSystem().createInitialGrid()
    }    
} // End of class