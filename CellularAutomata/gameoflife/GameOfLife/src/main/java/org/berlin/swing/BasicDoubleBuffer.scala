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
 * Doing it wrong example, no refactoring
 * 
 * Env: Eclipse IDE, Scala IDE, Scala 2.9/2.8
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life and Wolfram's Cellular Automata
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.swing

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
 * Simple Double Buffer in Scala Example.
 * 
 * @author berlinbrown
 *
 */
object BasicDoubleBuffer extends SimpleSwingApplication {

  def maxWidth  = 900
  def maxHeight = 900  
  def maxGridRows:Int = 120
  def maxGridCols:Int = 120  
  def sizeCell:Int = 3
  
  /**
   * Canvas.
   */
  object lifeCanvas extends JPanel {
      
      private val timer = new Timer()
 
      private var counter = 0;
      private var offScreenImage:Image = null
      private var offScreenGraphics:Graphics = null 
      private var offScreenImageDrawed:Image = null
      private var offScreenGraphicsDrawed:Graphics = null      
      
      setPreferredSize(maxWidth, maxHeight)      
      timer.schedule(new AutomataTask(), 0, 100)      
      
      /** 
       * Use double buffering.
       * @see java.awt.Component#update(java.awt.Graphics)
       */
      override def update(g:Graphics) = {
          paint(g)
      }
            
      /**
       * Draw this generation.
       * @see java.awt.Component#paint(java.awt.Graphics)
       */
      override def paint(g:Graphics) = {    
          
          val d = getSize();
          if (offScreenImageDrawed == null) {   
              // Double-buffer: clear the offscreen image.                
              offScreenImageDrawed = createImage(d.width, d.height) 
          }          
          offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics()      
          offScreenGraphicsDrawed.setColor(Color.white)
          offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height)                           
          /////////////////////
          // Paint Offscreen //
          /////////////////////
          renderOffScreen(offScreenImageDrawed.getGraphics());
          g.drawImage(offScreenImageDrawed, 0, 0, null);
      }
      
      def renderOffScreen(g:Graphics) {
            g.setColor(Color.black)
            g.drawString("Test - " + counter, 100, 100)                                 
            println("INFO: paint - ")     
            counter = counter + 1;
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
      title = "Simple Double Buffer"      
      contents = new Panel {
          background = Color.white
          preferredSize = (maxWidth, maxHeight)
          focusable = true      
          peer.add(lifeCanvas)
          pack()
      }    
  }
} // End of Class //
