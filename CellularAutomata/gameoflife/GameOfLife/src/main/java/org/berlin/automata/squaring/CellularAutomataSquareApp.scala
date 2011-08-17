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
 *   Example scala and swing integration. 
 *   Doing it wrong example, no refactoring
 * 
 * Env: Eclipse IDE, Scala IDE, Scala 2.9
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life and Wolfram's Cellular Automata
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.automata.squaring

import scala.swing.Swing._
import scala.swing.SimpleSwingApplication
import scala.swing.{ FlowPanel, MainFrame, Panel, SimpleGUIApplication }
import scala.swing.event._
import java.awt.{ Image, Color, Dimension, Graphics, Graphics2D, Point, geom }
import java.awt.EventQueue
import javax.swing.{ JPanel }

import java.util.TimerTask
import java.util.Timer

/**
 * Cellular Automata Squaring Demo.
 * 
 * @author berlinbrown
 *
 */
object CellularAutomataSquareApp extends SimpleSwingApplication {

  def maxWidth  = 700
  def maxHeight = 700  
  def maxGridRows:Int = 100
  def maxGridCols:Int = 100
  def sizeCell:Int = 3
  
  def N = 4
  def initialColorCode = 1
  
  /**
   * Canvas.
   */
  object lifeCanvas extends JPanel {
                  
      val game = new SquaringAutomata(maxGridRows, maxGridCols, N, initialColorCode)
      private var gridSystem = game.createGridSystem()
      
      private val timer = new Timer()
      
      private var offScreenImage:Image = null
      private var offScreenGraphics:Graphics = null 
      private var offScreenImageDrawed:Image = null
      private var offScreenGraphicsDrawed:Graphics = null      
      
      setPreferredSize(maxWidth, maxHeight)      
      timer.schedule(new AutomataTask(), 0, 30)      
      
      /**
       * Render the cell grid.
       */
      def renderCellGrid(g: Graphics) = {
            val szrows = (maxGridRows * (sizeCell + 3))
            val szcols = (maxGridCols * (sizeCell + 3))
            g.setColor(Color.black)
            for (i <- 0 until (maxGridRows + 1)) {
                g.drawLine((i * (sizeCell + 3)), 0, (i * (sizeCell + 3)), szrows)
                g.drawLine(0, (i * (sizeCell + 3)), szcols, (i * (sizeCell + 3)))
            }                            
      }          
      
      /**
       * Render all cells if alive.
       */
      def renderGridSystem(g: Graphics) {                              
          for (j <- 0 until maxGridRows) {
              for (i <- 0 until maxGridCols) {
                  
                  if (gridSystem.getCell(i, j) > 0) {
                      val xstart = (i * (sizeCell + 3))
                      val ystart = (j * (sizeCell + 3))
                      if (gridSystem.getCell(i, j) == 1) {
                          g.setColor(Color.green)
                      } else if (gridSystem.getCell(i, j) == 2) {
                          g.setColor(Color.red)
                      } else if (gridSystem.getCell(i, j) == 3) {
                          g.setColor(Color.blue)
                      } else if (gridSystem.getCell(i, j) == 4) {
                          g.setColor(Color.cyan)
                      } else if (gridSystem.getCell(i, j) == 5) {
                          g.setColor(Color.magenta)
                      } else if (gridSystem.getCell(i, j) == 6) {
                          g.setColor(Color.orange)
                      } else if (gridSystem.getCell(i, j) == 7) {
                          g.setColor(new Color(200, 200, 0))
                      }
                      g.fillRect(xstart + 2, ystart + 2, sizeCell, sizeCell)
                  }                  
              } // End of the for i              
          } // End of the for j 
      }
      
      /** 
       * Use double buffering.
       * @see java.awt.Component#update(java.awt.Graphics)
       */
      override def update(g:Graphics) = {
                    
          val d = getSize()
          if (offScreenImage == null) {
              offScreenImage = createImage(d.width, d.height);
              offScreenGraphics = offScreenImage.getGraphics()
          }
          paint(offScreenGraphics);
          g.drawImage(offScreenImage, 0, 0, null);  
      }
            
      /**
       * Draw this generation.
       * @see java.awt.Component#paint(java.awt.Graphics)
       */
      override def paint(g:Graphics) = {
                   
          // Draw grid on background image, which is faster
          if (offScreenImageDrawed == null) {   
              val d = getSize()         
              offScreenImageDrawed = createImage(d.width, d.height)              
              offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics()      
              offScreenGraphicsDrawed.setColor(Color.white)
              offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height)  
              renderCellGrid(offScreenGraphicsDrawed)
          }                 
          g.drawImage(offScreenImageDrawed, 0, 0, null)                        
          renderGridSystem(g)
          gridSystem = gridSystem.nextGenerationRules()
          g.setColor(Color.black)
          g.drawString("General CA Squaring Rule", 20, 620)
          g.drawString("Initial Value - N = " + N, 20, 620 + 14)
          g.drawString("Current Square Value = " + game.countInputCode, 20, 620 + 28)
      }
      
      // Render All Cells
      
  } // End of Class
  
  /**
   * Timer task, refresh canvas.
   */
  class AutomataTask extends java.util.TimerTask {
      override def run() {
          // Run thread on event dispatching thread
          if (!EventQueue.isDispatchThread()) {
              EventQueue.invokeLater(this)
          } else {
              if (lifeCanvas != null) {
                  lifeCanvas.repaint()
              }
          }
      }
  }
  
  /**
   * Main Frame, entry point.
   */
  def top = new MainFrame  {
      peer.setLocation(20, 20)
      title = "Cellular Automata - Squaring Example"      
      contents = new Panel {          
          background = Color.white
          preferredSize = (maxWidth, maxHeight)
          focusable = true      
          peer.add(lifeCanvas)
          pack()
      }    
  }
} // End of Class //
