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
package org.berlin.automata

import java.util.Random

/**
 * Cellular Automata Application.
 * 
 * @author berlinbrown
 *
 */
class Automata(rowCount:Int, colCount:Int) {
    
    var nextGenerationRow = 0
    var rule:Rules.Rule = null
    var rules = createRules    
    
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
            
            for (j1 <- 0 until rowCount) {
                for (i1 <- 0 until colCount) {
                    cells(j1)(i1) = new Cell(i1, j1, false)                    
                }
            }
            // Create initial cell in the middle of the grid
            val middle = (colCount / 2).toInt 
            cells(0)(middle) = new Cell(0, middle, true)
            nextGenerationRow = nextGenerationRow + 1
            this
        }
        
        /**
         * Use game of life rules to create next generation.
         */
        def nextGenerationRules() : CellGridSystem = {                        
            val newGrid = new CellGridSystem
            
            // Move to next generation                          
            for (j <- 0 until rowCount) {
                for (i <- 0 until colCount) {                    
                    newGrid.cells(j)(i) = new Cell(j, i,  cells(j)(i).alive)                                       
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
                    state1 = if (cells(nextGenerationRow-1)(i-1).alive) 1 else 0 
                    state2 = if (cells(nextGenerationRow-1)(i+0).alive) 1 else 0
                    state3 = if (cells(nextGenerationRow-1)(i+1).alive) 1 else 0             
                }                
                if (rule.rule((state1, state2, state3)) == 1) {
                    newGrid.cells(nextGenerationRow)(i) = new Cell(nextGenerationRow, i, true)
                } else {
                    newGrid.cells(nextGenerationRow)(i) = new Cell(nextGenerationRow, i, false)
                }
              } // End of for //
            }
            nextGenerationRow = nextGenerationRow + 1
            if (nextGenerationRow >= rowCount) {
                if (rules.isEmpty) {
                    rules = createRules
                }
                rule = rules.dequeue               
                nextGenerationRow = 0
                createInitialGrid
            } else {
                newGrid
            }
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
            if (cells(row)(col) == null) false else cells(row)(col).alive
        }
        
    } // End of class
        
    def createGridSystem() : CellGridSystem = {
        new CellGridSystem().createInitialGrid()    
    } 
    
    def init = {        
        rule = rules.dequeue        
    }
    
    ///////////////////////////////////
    // Rules
    ///////////////////////////////////
    
    def createRules() : scala.collection.mutable.Queue[Rules.Rule] = {
        val queue = new scala.collection.mutable.Queue[Rules.Rule]
        queue += new Rules.Rule132
        queue += new Rules.Rule190
        queue += new Rules.Rule90
        queue += new Rules.Rule254
        queue += new Rules.Rule250
        queue += new Rules.Rule30
        queue += new Rules.Rule110        
        queue
    }
    
} // End of class