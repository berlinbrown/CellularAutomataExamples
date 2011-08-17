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
 * Home Page: http://code.google.com/u/berlin.brown/
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.gol.sound

import scala.swing.Swing._
import scala.swing.SimpleSwingApplication
import scala.swing.{ MainFrame, Panel, SimpleGUIApplication }
import scala.swing.event._
import java.awt.{ Color, Dimension, Graphics, Graphics2D, Point, geom }
import java.awt.event.{ WindowListener, WindowEvent }

/**
 * Test swing3, render grid.
 * 
 * @author berlinbrown
 *
 */
object TestMidi extends SimpleSwingApplication {

  def maxWidth  = 600
  def maxHeight = 600 
    
  lazy val ui = new Panel {
     
      
    background = Color.white
    preferredSize = (maxWidth, maxHeight)
    focusable = true
    
    val midi = new MidiSynth()
    midi.open()
    
    /**
     * Render the cell grid.
     */
    def renderCellGrid(g: Graphics2D) = {
        g.setColor(Color.black)
        for (i <- 0 until 10) {
            g.drawLine((i * 10), 0, (i * 10), maxHeight)
            g.drawLine(0, (i * 10), maxWidth, (i * 10))
        }                
    }
    
    /**
     * Paint component.
     */
    override def paintComponent(g: Graphics2D) = {
      super.paintComponent(g)         
      g.setColor(Color.black)
      renderCellGrid(g)
    }
    
    def close() = {
        midi.close
    }
    
  } // End of Object

  /**
   * Main Frame, entry point.
   */
  def top = new MainFrame {
      peer.setLocation(200, 200)
      title = "Game Of Life with Midi Sound"          
      contents = ui      
      peer.addWindowListener(new WindowListener() {
            override def windowClosed(we:WindowEvent) {
                println("Window close event occur [1]")
                ui.close
            }
            override def windowClosing(we:WindowEvent) { 
                println("Window closing event [2]")
                ui.close
            }
            override def windowActivated(we:WindowEvent) {  }            
            override def windowDeactivated(we:WindowEvent) { }
            override def windowDeiconified(we:WindowEvent) { }
            override def windowIconified(we:WindowEvent) { }
            override def windowOpened(we:WindowEvent) {  }
        });              
  }  
} // End of Class //
