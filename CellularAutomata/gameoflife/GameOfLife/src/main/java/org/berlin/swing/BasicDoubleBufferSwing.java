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
 * BasicDoubleBufferSwing.java
 * Jan 10, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Basic Double Buffer Swing Example .
 * 
 * @author bbrown
 *
 */
public class BasicDoubleBufferSwing {
    
    public static class Canvas extends JPanel {
        
        private static final long serialVersionUID = 1L;
                
        private Image offScreenImage = null;
        private Graphics offScreenGraphics = null;
        private Image offScreenImageDrawed = null;
        private Graphics offScreenGraphicsDrawed = null;              
        private Timer timer = new Timer();
        private int counter = 0;
        
        public Canvas() {
            timer.schedule(new AutomataTask(), 0, 100);
            this.setPreferredSize(new Dimension(800, 600));
            this.setBackground(Color.white);
        }
        /** 
         * Use double buffering.
         * @see java.awt.Component#update(java.awt.Graphics)
         */
        @Override
        public void update(Graphics g) {                                
            paint(g);
            System.out.println("update called ----------->");
        }
              
        /**
         * Draw this generation.
         * @see java.awt.Component#paint(java.awt.Graphics)
         */
        @Override
        public void paint(final Graphics g) {

            final Dimension d = getSize();
            if (offScreenImageDrawed == null) {   
                // Double-buffer: clear the offscreen image.                
                offScreenImageDrawed = createImage(d.width, d.height);   
            }          
            offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();      
            offScreenGraphicsDrawed.setColor(Color.white);
            offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height) ;                           
            /////////////////////
            // Paint Offscreen //
            /////////////////////
            renderOffScreen(offScreenImageDrawed.getGraphics());
            g.drawImage(offScreenImageDrawed, 0, 0, null);
        }
        
        public void renderOffScreen(final Graphics g) {
            g.setColor(Color.black);
            g.drawString("Test - " + counter, 100, 100);                                 
            System.out.println("INFO: paint - " + counter);
            counter++;
        }
        
        private class AutomataTask extends java.util.TimerTask {
            public void run() {
                // Run thread on event dispatching thread
                if (!EventQueue.isDispatchThread()) {
                    EventQueue.invokeLater(this);
                } else {
                    if (Canvas.this != null) {
                        Canvas.this.repaint();                        
                    }
                }
                
            } // End of Run
        }        
    }
    
    public static void main(final String [] args) {
        
        System.out.println("Running");
        final JFrame frame = new JFrame("Simple Double Buffer") {
            private static final long serialVersionUID = 1L;
            public void processWindowEvent(java.awt.event.WindowEvent e) {
                super.processWindowEvent(e);
                if (e.getID() == java.awt.event.WindowEvent.WINDOW_CLOSING) {
                    System.exit(-1);
                }
              }
        };
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setBackground(Color.white);
        frame.add(new Canvas());
        frame.pack();
        frame.setVisible(true);
      
    }
    
} // End of the Class //
