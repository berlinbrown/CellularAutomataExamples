/**
 * Copyright (c) 2006-2010 Berlin Brown and botnode.com  All Rights Reserved
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
 * CellularAutomataSquaringRule.java
 * Aug 28, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.mech.game;

import javax.media.opengl.GL;

import org.berlin.mech.gl.CoreObjects;

/**
 * @author bbrown
 * 
 */
public class CellularAutomataSquaringRule {
    
    private int nextGenerationRow = 0;

    private int maxGridRows = 55;
    private int maxGridCols = 55;
    
    private final int rowCount;
    private final int colCount;
    private final int inputN;
    private final int initialValueCell;
    
    private CellGridSystem gridSystem = null;
    
    /**
     * Keep track of the input code color 1 and number of times found on a
     * particular row.
     */
    private int countInputCode = 0;

    public CellularAutomataSquaringRule() {
        this.rowCount = maxGridRows;
        this.colCount = maxGridCols;
        this.inputN = 4;
        this.initialValueCell = 1;
        this.gridSystem = this.createGridSystem(); 
    }
    
    /**
     * Render all cells if alive.
     */
    public void renderCellularAutomata(final GL gl, final long clock) {
        
        final CoreObjects coreObjects = new CoreObjects();
        float x = -30.0f;
        float y = -30.0f;
        float h = 0.5f;
        for (int j = 0; j < maxGridRows; j++) {          
            for (int i = 0; i < maxGridCols; i++) { 
                if (gridSystem.getCell(i, j) == 1) {
                    h = 3.5f;
                } else if (gridSystem.getCell(i, j) == 2) {
                    h = 2.0f;                    
                } else if (gridSystem.getCell(i, j) == 3) {
                    h = 3.0f;                    
                } else if (gridSystem.getCell(i, j) == 4) {
                    h = 3.0f;                    
                } else if (gridSystem.getCell(i, j) == 5) {
                    h = 4.0f;                
                } else if (gridSystem.getCell(i, j) == 6) {
                    h = 5.0f;                    
                } else if (gridSystem.getCell(i, j) == 7) {
                    h = 6.0f;                    
                } else {                
                    h = 0.5f;
                }
                coreObjects.cube(gl, h, 0.5f, x, y);
                x += 1.6f;
            }                                             
            y += 1.6f;
            x = -30.0f;
        } // End of the for j
        
        if (clock % 10 == 0) {
            gridSystem = gridSystem.nextGenerationRules();
        }
    }
    
    /**
     * Cell requires a row and col, a cell can have an alive or dead state.
     */
    public class Cell {
        private final int row;
        private final int col;
        private final int cellCode;

        public Cell(final int row, final int col, final int cellCode) {
            this.row = row;
            this.col = col;
            this.cellCode = cellCode;
        }

        public String toString() {
            return "Cell:row=" + row + " col=" + col + " cellcode=" + cellCode;
        }
    } // End of class cell //

    /**
     * Cell Grid contains all cells.
     */
    public class CellGridSystem {

        /**
         * Cells are represented by a scala multi-dimensional array.
         * 
         * @return
         */
        private Cell[][] cells = null;

        /**
         * Create initial grid.
         */
        public CellGridSystem createInitialGrid() {
            // Scala: cells = new Array[Array[Cell]](rowCount, colCount);
            cells = new Cell[rowCount][colCount];
            for (int j1 = 0; j1 < rowCount; j1++) {
                for (int i1 = 0; i1 < colCount; i1++) {
                    cells[j1][i1] = new Cell(i1, j1, 0);
                }
            }
            // Create initial cell in the middle of the grid
            final int middle = 10;
            for (int j = 0; j < inputN; j++) {
                cells[0][middle + j] = new Cell(0, middle + j, initialValueCell);
            }
            cells[0][middle + inputN] = new Cell(0, middle + inputN + 1, 3);
            nextGenerationRow = nextGenerationRow + 1;
            return this;
        }

        /**
         * Use game of life rules to create next generation.
         */
        public CellGridSystem nextGenerationRules() {
            final CellGridSystem newGrid = new CellGridSystem();
            newGrid.cells = new Cell[rowCount][colCount];
            for (int j = 0; j < rowCount; j++) {
                for (int i = 0; i < colCount; i++) {
                    newGrid.cells[j][i] = new Cell(j, i, cells[j][i].cellCode);
                }
            }
            if (nextGenerationRow <= (rowCount - 2)) {
                for (int i = 0; i < colCount; i++) {
                    int state1 = 0;
                    int state2 = 0;
                    int state3 = 0;
                    if (cells[nextGenerationRow - 1][i].cellCode > 0) {
                        System.out.println("==>" + cells[nextGenerationRow - 1][i].cellCode + " // column=" + i);
                    }
                    if (i == 0) {
                        state1 = 0;
                    } else if (i == (colCount - 1)) {
                        state3 = 0;
                    } else {
                        state1 = cells[nextGenerationRow - 1][i - 1].cellCode;
                        state2 = cells[nextGenerationRow - 1][i + 0].cellCode;
                        state3 = cells[nextGenerationRow - 1][i + 1].cellCode;
                    } // End of if - else

                    final int cellCodeOutput = squaringRule(state1, state2, state3);
                    newGrid.cells[nextGenerationRow][i] = new Cell(nextGenerationRow, i, cellCodeOutput);
                    if (newGrid.cells[nextGenerationRow][i].cellCode > 0) {
                        System.out.println("==> squaringRule=val=" + newGrid.cells[nextGenerationRow][i].cellCode
                                + " // column=" + i);
                    }
                } // End of for //

                // Perform count on this particular row
                countInputCode = 0;
                for (int i = 0; i < colCount; i++) {
                    if (newGrid.cells[nextGenerationRow][i].cellCode == 1) {
                        countInputCode = countInputCode + 1;
                    }
                } // End of the for //
            } // End of if next gen
            nextGenerationRow = nextGenerationRow + 1;
            return newGrid;
        }

        /**
         * Get status of cell.
         * 
         * @param col
         *            x-position
         * @param row
         *            y-position
         * 
         * @return Cell Code
         */
        public int getCell(final int col, final int row) {
            if (cells[row][col] == null) {
                return 0;
            } else {
                return cells[row][col].cellCode;
            }
        }

    } // End of class

    public CellGridSystem createGridSystem() {
        return new CellGridSystem().createInitialGrid();
    }
    
    
    /**
     * Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
     * iterations N^2 will appear.   
     * 
     * From Wolfram's New Kind of Science 
     * <code>
     *  { 0, _, 3} -> 0, 
     *  { _, 2, 3} -> 3, 
     *  { 1, 1, 3} -> 4, 
     *  { _, 1, 4} -> 4, 
     *  { 1 | 2, 3, _ } -> 5, 
     *  { p 0 | 1, 4, _ } -> 7 - p.1,
     *  { 7, 2, 6} -> 3, 
     *  { 7, _, _ } -> 7,  
     *  { _, 7, p 1 | 2  } -> p.3,
     *  { _, p 5 | 6, _ } -> 7 - p,  
     *  { 5 | 6, p 1 | 2, _ } -> 7 - p.2,
     *  { 5 | 6, 0, 0} -> 1, 
     *  { _, p 1 | 2, _ } -> p,
     *  { _,  _, _ } -> 0}, {
      
     * </code>
     */  
    public int squaringRule(final int _i_1, final int _i_2, final int _i_3) {
                            
      if ((_i_1 == 0) && (_i_3 == 3)) {
        /* case (0, _, 3) => 0 */
        return 0;
        
      } else if ((_i_2 == 2) && (_i_3 == 3)) {
        /* case (_, 2, 3) => 3 */
        return 3;
        
      } else if ((_i_1 == 1) && (_i_2 == 1) && (_i_3 == 3)) {
        /* case (1, 1, 3) => 4 */
        return 4;
        
      } else if ((_i_2 == 1) && (_i_3 == 4)) {
        /* case (_, 1, 4) => 4 */
        return 4;
        
      } else if ((_i_1 == 1 || _i_1 == 2) && (_i_2 == 3)) {
        /* case (1 | 2, 3, _) => 5 */
        return 5;
        
      } else if (((_i_1 == 0) || (_i_1 == 1)) && (_i_2 == 4)) {
        /* case (0 | 1, 4, _) => 7 - inputState._1 // Pattern, p, Alt[0,1] */
        return 7 - _i_1;
        
      } else if ((_i_1 == 7) && (_i_2 == 2) && (_i_3 == 6)) {
        /* case (7, 2, 6) => 3 */
        return 3;
        
      } else if (_i_1 == 7) {
        /* case (7, _, _) => 7 */
        return 7;      
        
      } else if (_i_2 == 7 && ((_i_3 == 1) || (_i_3 == 2))) {
        /* case (_, 7, 1 | 2) => inputState._3 */
        return _i_3;
        
      } else if ((_i_2 == 5) || (_i_2 == 6)) {
        /* case (_, 5 | 6, _) => 7 - inputState._2 // Alternatives[5, 6], Pattern[p, Alternatives[1, 2]], Blank[]} -> 7 - p */
        return 7 - _i_2;
        
      } else if (((_i_1 == 5) || (_i_1 == 6)) && ((_i_2 == 1) || (_i_2 == 2))) {
        /* case (5 | 6, 1 | 2, _) => 7 - inputState._2 */
        return 7 - _i_2;
        
      } else if (((_i_1 == 5) || (_i_1 == 6)) && (_i_2 == 0) && (_i_3 == 0)) {
        /* case (5 | 6, 0, 0) => 1 */
        return 1;
      
      } else if ((_i_2 == 1) || (_i_2 == 2)) {
        /* case (_, 1 | 2, _) => inputState._2 */
        return _i_2;
      } else {
        /* case _   => 0 */
        return 0;
      } // End of the if - else //        
    }
    
    
    
} // End of the class //

