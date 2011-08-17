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
 * Date: 8/15/2011
 *  
 * Description: Pure Java, Cellular Automata Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
 * iterations N^2 will appear.
 * 
 * The Java version is a port of the Scala version.
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life and Wolfram's Cellular Automata
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin3.java.squaring.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Cellular Automata Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
 * iterations N^2 will appear.
 * 
 * Swing based cellular grid application demonstrating this rule.
 *  
 */
public class JavaCellularAutomataSquare {
 
  public static final String TITLE = "Cellular Automata - Squaring Example";
  
  private int maxWidth = 700;
  private int maxHeight = 700;
  
  private int maxGridRows = 100;
  private int maxGridCols = 100;
  private int sizeCell = 3;
  
  private int N = 4;
  private int initialColorCode = 1;
  
  private JPanel lifeCanvas = new JavaCellularAutomataSquareCanvas();
  
  /**
   * Launch the 2D frame window.  
   */
  public void invokeLater() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        launch();
      }
    });
  }
  
  /**
   * Launch the 2D frame window.
   */
  protected void launch() {    
    final AutomataFrame frame = new AutomataFrame();
    frame.addWindowListener(frame);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocation(20, 20);    
    frame.setPreferredSize(new Dimension(maxWidth, maxHeight));
    frame.setResizable(false);
    frame.setFocusable(true);
    
    final JPanel panel = new JPanel();
    panel.setLocation(200, 200);
    panel.setVisible(true);
    panel.setPreferredSize(new Dimension(maxWidth, maxHeight));    
    panel.setFocusable(true);
    panel.setBackground(Color.white);
    
    lifeCanvas.setPreferredSize(new Dimension(maxWidth, maxHeight));
    panel.add(lifeCanvas);
    // Panel setup, toggle visibility on frame, set visible
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);    
  }
  
  /**
   * Canvas.
   */
  private class JavaCellularAutomataSquareCanvas extends JPanel {
            
    private static final long serialVersionUID = 1L;
    
    private Image offScreenImage = null;
    private Graphics offScreenGraphics = null; 
    private Image offScreenImageDrawed = null;
    private Graphics offScreenGraphicsDrawed = null;     
    
    private final Timer timer = new Timer();
    
    private final SquaringAutomata game = new SquaringAutomata(maxGridRows, maxGridCols, N, initialColorCode);
    private SquaringAutomata.CellGridSystem gridSystem = game.createGridSystem();
    
    public JavaCellularAutomataSquareCanvas() {
      timer.schedule(new AutomataTask(), 0, 60);
    }
    
    /**
     * Timer task, refresh canvas.
     */
    private class AutomataTask extends java.util.TimerTask {
      @Override
      public void run() {
            // Run thread on event dispatching thread
            if (!EventQueue.isDispatchThread()) {
                EventQueue.invokeLater(this);
            } else {
                if (lifeCanvas != null) {
                    lifeCanvas.repaint();
                }
            }
        }
    } // End of the Class //
        
    /**
     * Render the cell grid.
     */
    public void renderCellGrid(final Graphics g) {
          final int szrows = (maxGridRows * (sizeCell + 3));
          final int szcols = (maxGridCols * (sizeCell + 3));
          g.setColor(Color.black);
          for (int i = 0; i < maxGridRows + 1; i++) {
              g.drawLine((i * (sizeCell + 3)), 0, (i * (sizeCell + 3)), szrows);
              g.drawLine(0, (i * (sizeCell + 3)), szcols, (i * (sizeCell + 3)));
          } // End of the For //
    }        
    
    /**
     * Render all cells if alive.
     */
    public void renderGridSystem(final Graphics g) {                              
        for (int j = 0; j < maxGridRows; j++) {
            for (int i = 0; i < maxGridCols; i++) {
                
                if (gridSystem.getCell(i, j) > 0) {
                    final int xstart = (i * (sizeCell + 3));
                    final int ystart = (j * (sizeCell + 3));
                    if (gridSystem.getCell(i, j) == 1) {
                        g.setColor(Color.green);
                    } else if (gridSystem.getCell(i, j) == 2) {
                        g.setColor(Color.red);
                    } else if (gridSystem.getCell(i, j) == 3) {
                        g.setColor(Color.blue);
                    } else if (gridSystem.getCell(i, j) == 4) {
                        g.setColor(Color.cyan);
                    } else if (gridSystem.getCell(i, j) == 5) {
                        g.setColor(Color.magenta);
                    } else if (gridSystem.getCell(i, j) == 6) {
                        g.setColor(Color.orange);
                    } else if (gridSystem.getCell(i, j) == 7) {
                        g.setColor(new Color(200, 200, 0));
                    }
                    g.fillRect(xstart + 2, ystart + 2, sizeCell, sizeCell);
                }                  
            } // End of the for i              
        } // End of the for j 
    }
    
    /** 
     * Use double buffering.
     * @see java.awt.Component#update(java.awt.Graphics)
     */
    @Override
    public void update(final Graphics g) {
                  
        final Dimension d = getSize();
        if (offScreenImage == null) {
            offScreenImage = createImage(d.width, d.height);
            offScreenGraphics = offScreenImage.getGraphics();
        }
        paint(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, null);  
    }
          
    /**
     * Draw this generation.
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(final Graphics g) { 
        // Draw grid on background image, which is faster
        if (offScreenImageDrawed == null) {   
            final Dimension d = getSize();
            offScreenImageDrawed = createImage(d.width, d.height);              
            offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();      
            offScreenGraphicsDrawed.setColor(Color.white);
            offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height);  
            renderCellGrid(offScreenGraphicsDrawed);
        }                 
        g.drawImage(offScreenImageDrawed, 0, 0, null);                        
        renderGridSystem(g);
        gridSystem = gridSystem.nextGenerationRules();
        g.setColor(Color.black);
        g.drawString("General CA Squaring Rule", 20, 620);
        g.drawString("Initial Value - N = " + N, 20, 620 + 14);
        g.drawString("Current Square Value = " + game.countInputCode, 20, 620 + 28);
    }    
    
  } // End of the class //
    
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
  
  /**
   * Squaring Automata Rules.
   * 
   * @author berlinbrown
   *
   */
  private class SquaringAutomata {      
    
      private int nextGenerationRow = 0;
      
      private final int rowCount;
      private final int colCount;
      private final int inputN;
      private final int initialValueCell;
      
      /**
       * Keep track of the input code color 1 and number of times
       * found on a particular row.
       */
      private int countInputCode = 0;
      
      public SquaringAutomata(final int rowCount, final int colCount, final int inputN, final int initialValueCell) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.inputN = inputN;
        this.initialValueCell = initialValueCell;
      }
      
      /**
       * Cell requires a row and col, a cell can have an alive or dead state.
       */
      private class Cell {
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
      private class CellGridSystem {
        
          /**
           * Cells are represented by a scala multi-dimensional array.
           * @return
           */
          private Cell [][] cells = null;
          
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
              if (nextGenerationRow <= (rowCount-2)) {
                for (int i = 0; i < colCount; i++) {         
                  int state1 = 0;
                  int state2 = 0;
                  int state3 = 0;                        
                  if (cells[nextGenerationRow-1][i].cellCode > 0) {
                    System.out.println("==>" + cells[nextGenerationRow-1][i].cellCode  +  " // column=" + i);                    
                  }                  
                  if (i == 0) {
                      state1 = 0;
                  } else if (i == (colCount-1)) {
                      state3 = 0;
                  } else {                    
                      state1 = cells[nextGenerationRow-1][i-1].cellCode; 
                      state2 = cells[nextGenerationRow-1][i+0].cellCode;
                      state3 = cells[nextGenerationRow-1][i+1].cellCode;            
                  } // End of if - else                                   
                  
                  final int cellCodeOutput = squaringRule(state1, state2, state3);                  
                  newGrid.cells[nextGenerationRow][i] = new Cell(nextGenerationRow, i, cellCodeOutput);
                  if (newGrid.cells[nextGenerationRow][i].cellCode > 0) {
                    System.out.println("==> squaringRule=val=" + newGrid.cells[nextGenerationRow][i].cellCode  +  " // column=" + i);                    
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
           * @param col x-position
           * @param row y-position
           * 
           * @return Cell Code
           */
          public int getCell(final int col, final int row)  {
              if (cells[row][col] == null)  {
                return 0;
              } else { 
                return cells[row][col].cellCode;
              }
          }
          
      } // End of class
          
      public CellGridSystem createGridSystem() {
          return new CellGridSystem().createInitialGrid();    
      } 
          
  } // End of class
  
  /**
   * JFrame with window listener.
   */
  private class AutomataFrame extends JFrame implements WindowListener {    
    private static final long serialVersionUID = 1L;

    public AutomataFrame() {
      super(TITLE);
    }
    @Override
    public void windowOpened(WindowEvent e) {
      System.out.println("Frame Window Opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
      System.out.println("Frame Window Closing");
    }

    @Override
    public void windowClosed(WindowEvent e) {
      System.out.println("Frame Window Closed");      
    }

    @Override
    public void windowIconified(WindowEvent e) {
      System.out.println("Frame Window Minimized");      
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
      System.out.println("Frame Window Maximized");      
    }

    @Override
    public void windowActivated(WindowEvent e) {
      System.out.println("Frame Window Activated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
      System.out.println("Frame Window Deactivated");
      
    }
  } // End of the Class //
    
} // End of the Class //
