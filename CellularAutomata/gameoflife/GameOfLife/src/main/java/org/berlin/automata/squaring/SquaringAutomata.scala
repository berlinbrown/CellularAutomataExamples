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
 *  
 * Description: Extend this customizable Swing wrapper library.
 * Example scala and swing integration.
 * 
 * Doing it wrong example, no refactoring, imperative oriented.
 * 
 * Env: Eclipse IDE, Scala IDE, Scala 2.9
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life and Wolfram's Cellular Automata
 * Also see: http://mathworld.wolfram.com/ElementaryCellularAutomaton.html
 * 
 * 1D Cellular Automata.
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.automata.squaring

import java.util.Random
import org.berlin.automata.Rules
import org.berlin.automata.GeneralRules

/**
 * Squaring Automata Rules.
 * 
 * @author berlinbrown
 *
 */
class SquaringAutomata(rowCount:Int, colCount:Int, inputN:Int, initialValueCell:Int) {
    
    var nextGenerationRow = 0
    /**
     * Keep track of the input code color 1 and number of times
     * found on a particular row.
     */
    var countInputCode = 0
    
    /**
     * Cell requires a row and col, a cell can have an alive or dead state.
     */
    class Cell(val row:Int, val col:Int, val cellCode:Int) {              
        /**
         * Number of neighbors.
         * @param x$1
         */
        var neighbors:Int = 0        
        override def toString = "Cell:row=" + row + " col=" + col + " cellcode=" + cellCode    
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
            for (j1 <- 0 until rowCount) {
                for (i1 <- 0 until colCount) {
                    cells(j1)(i1) = new Cell(i1, j1, 0)
                }
            }
            // Create initial cell in the middle of the grid
            val middle = 10
            for (j <- 0 until inputN) {
                cells(0)(middle + j) = new Cell(0, middle + j, initialValueCell)
                println("Creating initial row: col:" + (middle + j))
            }
            cells(0)(middle + inputN) = new Cell(0, middle + inputN + 1, 3)
            nextGenerationRow = nextGenerationRow + 1
            this
        }
        
        /**
         * Use game of life rules to create next generation.
         */
        def nextGenerationRules() : CellGridSystem = {
            
            val squaringRule = new GeneralRules.SquaringRule()
            val newGrid = new CellGridSystem                 
            for (j <- 0 until rowCount) {
                for (i <- 0 until colCount) {              
                    newGrid.cells(j)(i) = new Cell(j, i,  cells(j)(i).cellCode)
                }
            }                                   
            if (nextGenerationRow <= (rowCount-2)) {
              for (i <- 0 until colCount) {         
                var state1 = 0
                var state2 = 0
                var state3 = 0                
                if (i == 0) {
                    state1 = 0
                } else if (i == (colCount-1)) {
                    state3 = 0
                } else {                    
                    state1 = cells(nextGenerationRow-1)(i-1).cellCode 
                    state2 = cells(nextGenerationRow-1)(i+0).cellCode
                    state3 = cells(nextGenerationRow-1)(i+1).cellCode             
                } // End of if - else                 
                val cellCodeOutput = squaringRule.rule((state1, state2, state3))
                newGrid.cells(nextGenerationRow)(i) = new Cell(nextGenerationRow, i, cellCodeOutput)                
              } // End of for //
                                         
              // Perform count on this particular row
              countInputCode = 0
              for (i <- 0 until colCount) {
                if (newGrid.cells(nextGenerationRow)(i).cellCode == 1) {
                    countInputCode = countInputCode + 1
                }
              } 
            } // End of if next gen                     
            nextGenerationRow = nextGenerationRow + 1            
            newGrid
        }
        
        /**
         * Get status of cell.
         * 
         * @param col x-position
         * @param row y-position
         * 
         * @return Cell Code
         */
        def getCell(col:Int, row:Int) : Rules.Output = {
            if (cells(row)(col) == null) 0 else cells(row)(col).cellCode
        }
        
    } // End of class
        
    def createGridSystem() : CellGridSystem = {
        new CellGridSystem().createInitialGrid()    
    } 
        
} // End of class