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
 * CameraManager.java
 * Aug 27, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 *  Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.gl;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/**
 * @author bbrown
 * 
 */
public class CameraManager {

    /**
     * Active camera.
     */
    private Camera camera = null;
    private GL gl = null;
    private GLU glu = new GLU();

    public CameraManager() {
        super();
    }

    public CameraManager(final GL gl) {
        this.gl = gl;
    }

    /**
     * For each specific camera place in a particular position
     */
    public void setupCamera() {
        // start at zero        
        camera.type = Camera.CAMERA_WALKING;
        camera.rotation[1] = Camera.OFFSET_ROTATION;

    } // end of the if

    /**
     * Position camera setup the camera This should be called before all drawing
     * code
     */
    void setCamera() {

        gl.glRotatef(camera.angle[1], 0.0f, 1.0f, 0.0f);
        gl.glRotatef(camera.angle[0], 1.0f, 0.0f, 0.0f);
        gl.glRotatef(camera.angle[2], 0.0f, 0.0f, 1.0f);
        gl.glTranslatef(camera.position[0], camera.position[1], camera.position[2]);

        // move camera to according position
        gl.glRotatef(camera.rotation[1], 0.0f, 1.0f, 0.0f);
        gl.glRotatef(camera.rotation[0], 1.0f, 0.0f, 0.0f);
        gl.glRotatef(camera.rotation[2], 0.0f, 0.0f, 1.0f);

    } 

    void posCamera(final float x, final float y, final float z) {
        // move camera to according position
        camera.position[0] = x;
        camera.position[1] = y;
        camera.position[2] = z;
    } // end of the function

    /**
     * Set the rotation angles of the cameras
     */
    void setCameraRot(final float x, final float y, final float z) {
        // setup the camera rotations
        // The trick is figure out which rotation
        // to use (angle or rotation)
        camera.rotation[0] = x;
        camera.rotation[1] = y;
        camera.rotation[2] = z;

    } // end of the function

    void setCameraAngle(final float x, final float y, final float z) {
        camera.angle[0] = x;
        camera.angle[1] = y;
        camera.angle[2] = z;
    } // end of the function

    public void thirdPersonModeStatic() {
        float lookx, looky;      
        lookx = 0;
        looky = 0;
        glu.gluLookAt(camera.position[0], camera.position[1], camera.position[2], lookx, 0.0f, looky, 0.0f, 1.0f, 0.0f);

    } // end of the function

    /**
     * @param camera
     *            the camera to set
     */
    public void setCamera(final Camera camera) {
        this.camera = camera;
    }

    /**
     * @param gl
     *            the gl to set
     */
    public void setGl(final GL gl) {
        this.gl = gl;
    }

    /**
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }
    
    public void translateCamera(final float x, final float y, final float z) {    
        camera.position[0] += x;
        camera.position[1] += y;
        camera.position[2] += z;
    } // end of the function
    
    public void moveForward() {            
        translateCamera(0.0f, 0.0f, -1.2f);
    } 

    public void moveBackward() { 
        translateCamera(0.0f, 0.0f, 1.0f);
    }     
    
    public void moveLeft() {       
         translateCamera(-1.0f, 0.0f, 0.0f);     
    } 

    public void moveRight() {     
        translateCamera(1.0f, 0.0f, 0.0f);     
    } 
    
    public void moveHigher() {     
        translateCamera(0.0f, 1.0f, 0.0f);     
    }
    public void moveLower() {     
        translateCamera(0.0f, -1.0f, 0.0f);     
    }

} // End of the class //
