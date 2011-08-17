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
 * Doing it wrong example, no refactoring
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.gol

import scala.swing.Swing._
import scala.swing.SimpleSwingApplication
import scala.swing.{ MainFrame, Panel, SimpleGUIApplication }
import scala.swing.event._
import java.awt.{ Color, Dimension, Graphics, Graphics2D, Point, geom }

object TestSwing2 extends SimpleSwingApplication {

  lazy val ui = new Panel {
    background = Color.white
    preferredSize = (200, 200)

    focusable = true
    listenTo(mouse.clicks, mouse.moves, keys)

    reactions += {
      case e: MousePressed =>
        moveTo(e.point)
        requestFocusInWindow()
      case e: MouseDragged => lineTo(e.point)
      case e: MouseReleased => lineTo(e.point)
      case KeyTyped(_, 'c', _, _) =>
        path = new geom.GeneralPath
        repaint()
      case _: FocusLost => repaint()
    }
    var path = new geom.GeneralPath

    def lineTo(p: Point) { path.lineTo(p.x, p.y); repaint() }
    def moveTo(p: Point) { path.moveTo(p.x, p.y); repaint() }

    override def paintComponent(g: Graphics2D) = {
      super.paintComponent(g)
      g.setColor(new Color(100, 100, 100))
      g.drawString("Press left mouse button and drag to paint." + (if (hasFocus) " Press 'c' to clear." else ""), 10, size.height - 10)
      g.setColor(Color.black)
      g.draw(path)
    }
  }

  def top = new MainFrame {
    title = "Simple Line Painting Demo"
    contents = ui
  }
} // End of Class //
