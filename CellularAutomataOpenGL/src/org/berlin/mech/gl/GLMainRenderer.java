/**
 *
 * Copyright (c) 2006-2007 Berlin Brown and botnode.com/Berlin Research  All Rights Reserved
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
 * Home Page: http://botnode.com/
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 * Simple Java OpenGL
 *  Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.gl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import org.berlin.mech.game.CellularAutomataSquaringRule;
import org.berlin.mech.game.Plane;
import org.berlin.mech.main.GLGridApp;

public class GLMainRenderer implements GLEventListener, MouseListener, MouseMotionListener, KeyListener {

    public static final int GL_WIDTH = GLGridApp.SCREEN_WIDTH;
    public static final int GL_HEIGHT = (int) (GLGridApp.SCREEN_HEIGHT * 0.9);

    /**
     * Number of spots on the board. !IMPORTANT! - key value for determining the
     * GL board size.
     */
    public static final float DEFAULT_N = 30.0f;

    private GLU glu = new GLU();
    
    private final Camera mainCamera = new Camera();
    private final CameraManager cameraManager = new CameraManager();
    private final CoreObjects coreObjects = new CoreObjects();
    private final CellularAutomataSquaringRule cellularAutomata = new CellularAutomataSquaringRule();     
    
    private long gameClock = 0;
    
    /**
     * Build a GL canvas.
     * 
     * @param args
     */
    public static GLCanvas buildCanvas(final GLGridApp frame) {
        
        final GLMainRenderer renderer = new GLMainRenderer();
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(renderer);        
        canvas.setSize(GL_WIDTH, GL_HEIGHT);        
        canvas.addKeyListener(renderer);
        return canvas;
    }

    public void init(GLAutoDrawable drawable) {

        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        GL gl = drawable.getGL();

        System.out.println("INIT GL IS: " + gl.getClass().getName());
        System.out.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

        gl.setSwapInterval(1);
                
        // Enable Smooth Shading
        // Black Background
        // Depth Buffer Setup
        gl.glShadeModel(GL.GL_SMOOTH);               
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);                    

        gl.glEnable(GL.GL_NORMALIZE);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

        gl.glShadeModel(GL.GL_SMOOTH);
        // Enables Depth Testing
        gl.glEnable(GL.GL_DEPTH_TEST);                
        // The Type Of Depth Testing To Do
        gl.glDepthFunc(GL.GL_LEQUAL);                 
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);  // Really Nice Perspective Calculations
        
        /****************************************
         * Continue with initialization
         ****************************************/ 
        gl.glColorMaterial (GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE|GL.GL_SPECULAR|GL.GL_EMISSION );
        /*
         try also:
           glMaterial ( GL_FRONT_AND_BACK, GL_EMISSION, ...colours... ) ; or
           glColorMaterial ( GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE ) ;
        */
        gl.glEnable(GL.GL_COLOR_MATERIAL);
        Lights.setmaterial(gl, Lights.no_mat, Lights.mat_diffuse, Lights.mat_specular, Lights.low_shininess, Lights.no_mat);
        
        cameraManager.setGl(gl);
        cameraManager.setCamera(mainCamera);
        cameraManager.setupCamera();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        GL gl = drawable.getGL();
        System.out.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.out.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.out.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
        if (height <= 0) {
            height = 1;
        }        
        // Reset The Current Viewport
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width,height);          
        // Select The Projection Matrix
        gl.glMatrixMode(GL.GL_PROJECTION);        
        // Reset The Projection Matrix
        gl.glLoadIdentity();                   

        // Calculate The Aspect Ratio Of The Window
        glu.gluPerspective(45.0f, h, 0.1f, 600.0f);
        // Select The Modelview Matrix
        gl.glMatrixMode(GL.GL_MODELVIEW);             
        // Reset The Modelview Matrix
        gl.glLoadIdentity();                                  
    }

    public void display(GLAutoDrawable drawable) {

        final GL gl = drawable.getGL();
        final Lights lights = new Lights();      
        final Plane plane = new Plane();        
        
        // Clear Screen And Depth Buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);        
        gl.glLoadIdentity();
        gl.glPushMatrix();
        
        cameraManager.thirdPersonModeStatic();
        lights.setLight(gl);
        
        plane.renderPlane(gl);        
        coreObjects.centerCube(gl);
        
        gl.glPushMatrix();        
        cellularAutomata.renderCellularAutomata(gl, gameClock); 
        gl.glPopMatrix();
        
        gl.glPopMatrix();
        // Flush the Buffer //
        gl.glFlush();
        gameClock++;
    }
       

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {

    }

    // Methods required for the implementation of MouseListener
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    // Methods required for the implementation of MouseMotionListener
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    @Override
    public void keyPressed(final KeyEvent e) {   
        System.out.println("Key Pressed : " + e.getKeyCode());
        
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("Moving Camera Backwards (-z) : " + cameraManager.getCamera());
            cameraManager.moveBackward();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Moving Camera Forward (z) : " + cameraManager.getCamera());
            cameraManager.moveForward();
            
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Moving Camera Left: " + cameraManager.getCamera());
            cameraManager.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            
            System.out.println("Moving Camera Right : " + cameraManager.getCamera());
            cameraManager.moveRight();
            
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            System.out.println("Moving Camera Vertically Up : " + cameraManager.getCamera());
            cameraManager.moveHigher();
            
        } else if (e.getKeyCode() == KeyEvent.VK_F) {            
            System.out.println("Moving Camera Vertically Down : " + cameraManager.getCamera());
            cameraManager.moveLower();
            
        } // End of the if - else //
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @Override
    public void keyReleased(final KeyEvent e) {

    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @Override
    public void keyTyped(final KeyEvent e) {            
    }
    
    /**
     * @return the glu
     */
    public GLU getGlu() {
        return glu;
    }

    /**
     * @param glu
     *            the glu to set
     */
    public void setGlu(GLU glu) {
        this.glu = glu;
    }

} // End of the Class //
