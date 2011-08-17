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
 * Example scala and swing integration.
 * 
 * Doing it wrong example, no refactoring
 * 
 * Env: Eclipse IDE, Scala IDE, Scala 2.9
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.gol.sound

import scala.swing.Swing._
import scala.swing.SimpleSwingApplication
import scala.swing.{ FlowPanel, MainFrame, Panel, SimpleGUIApplication }
import scala.swing.event._
import java.awt.{ Image, Color, Dimension, Graphics, Graphics2D, Point, geom }
import java.awt.EventQueue
import javax.swing.{ JPanel }

import java.util.TimerTask
import java.util.Timer
import java.awt.event.{ WindowListener, WindowEvent }

import org.berlin.gol.GameOfLife


/**
 * Test swing3, render grid.
 * 
 * @author berlinbrown
 *
 */
object GameOfLifeSoundApp extends SimpleSwingApplication {

  def maxWidth  = 500
  def maxHeight = 500 
  
  def maxGridRows = 40
  def maxGridCols = 40
  
  /**
   * Canvas.
   */
  object lifeCanvas extends JPanel {
            
      private val gameOfLife = new GameOfLife(maxGridRows, maxGridCols)
      private var gridSystem = gameOfLife.createGridSystem()
      
      private val timer = new Timer()
      
      private var offScreenImage:Image = null
      private var offScreenGraphics:Graphics = null 
      private var offScreenImageDrawed:Image = null
      private var offScreenGraphicsDrawed:Graphics = null      
      
      setPreferredSize(maxWidth, maxHeight)      
      timer.schedule(new GameOfLifeTask(), 0, 80)      
      
      val midi = new MidiSynth()
      midi.open
      
      def close() = {
        midi.close
      }        
      
      /**
       * Render the cell grid.
       */
      def renderCellGrid(g: Graphics) = {
            g.setColor(Color.black)
            for (i <- 0 until maxGridRows) {
                g.drawLine((i * 10), 0, (i * 10), maxHeight-50)
                g.drawLine(0, (i * 10), maxWidth-50, (i * 10))
            }                            
      }          
      
      /**
       * Render all cells if alive.
       */
      def renderGridSystem(g: Graphics) {          
          val size = 7
          g.setColor(Color.green)
          for (j <- 0 until maxGridRows) {
              for (i <- 0 until maxGridCols) {
                  if (gridSystem.getCell(i, j)) {
                      val xstart = (i * 10)
                      val ystart = (j * 10)
                      g.fillRect(xstart + 2, ystart + 2, size, size)
                      val hz = (((ystart * maxGridRows) + xstart) * 0.03).toInt + 300                      
                      WriteSound.soundThread(hz, 50, 0.2)
                  }
              }
          } // End of for, rows
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
              // TODO: fix double buffering.
              val d = getSize()         
              offScreenImageDrawed = createImage(d.width, d.height)
              
              offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics()      
              offScreenGraphicsDrawed.setColor(new Color(240, 240, 240))
              offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height)  
              renderCellGrid(offScreenGraphicsDrawed)
          }                 
          g.drawImage(offScreenImageDrawed, 0, 0, null)                        
          renderGridSystem(g)
          gridSystem = gridSystem.nextGenerationRules()
      }
      
      // Render All Cells
      
  } // End of Class
  
  /**
   * Timer task, refresh canvas.
   */
  class GameOfLifeTask extends java.util.TimerTask {
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
      title = "Game Of Life"
      peer.setLocation(200, 200)      
      contents = new Panel {
          background = Color.white
          preferredSize = (maxWidth, maxHeight)
          focusable = true      
          peer.add(lifeCanvas)
          pack()
      }
      
      peer.addWindowListener(new WindowListener() {
            override def windowClosed(we:WindowEvent) {
                println("Window close event occur [1]")
                lifeCanvas.close             
            }
            override def windowClosing(we:WindowEvent) { 
                println("Window closing event [2]")
                lifeCanvas.close                
            }
            override def windowActivated(we:WindowEvent) {  }            
            override def windowDeactivated(we:WindowEvent) { }
            override def windowDeiconified(we:WindowEvent) { }
            override def windowIconified(we:WindowEvent) { }
            override def windowOpened(we:WindowEvent) {  }
        });     
  }
} // End of Class //
