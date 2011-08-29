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
 * Lights.java
 * Aug 27, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 *  Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.gl;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

/**
 * @author bbrown
 *
 */
public class Lights {

    public static final float LIGHT_HEIGHT_0 = 24.0f;
    public static final float LIGHT_HEIGHT_1 = 180.0f;
    
    public final static float no_mat[] = { 0.0f, 0.0f, 0.0f, 1.0f };
    public final static float mat_ambient[] = { 0.9f, 0.9f, 0.9f, 1.0f };
    public final static float mat_diffuse[] = { 0.9f, 0.8f, 0.8f, 1.0f };
    public final static float mat_specular[] = { 0.0f, 1.0f, 1.0f, 1.0f };
    public final static float no_shininess[] = { 0.0f };
    public final static float low_shininess[] = { 5.0f };
    public final static float high_shininess[] = { 100.0f};
    public final static float mat_emission[] = {0.3f, 0.2f, 0.2f, 0.0f};    
    
    public final static float grey_ambient[] = { 0.0f, 0.3f, 0.4f, 1.0f };
    public final static float grey_diffuse[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    public final static float grey_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
    public final static float ino_shininess[] = { 0.0f };
    public final static float ilow_shininess[] = { 8.0f };
    public final static float ihigh_shininess[] = { 100.0f};
    public final static float imat_emission[] = {0.15f, 0.1f, 0.1f, 0.0f};
    
    private Light light = new Light();
    
    public static void setmaterial(final GL gl, final float amb[], final float diff[], final float spec[], final float shine[], final float emiss[]) {        
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, FloatBuffer.wrap(amb));
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, FloatBuffer.wrap(diff));
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, FloatBuffer.wrap(spec));
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, FloatBuffer.wrap(shine));
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, FloatBuffer.wrap(emiss));
    } // end of the function
    
    public void setLight(final GL gl) {    

        // Enable lighting
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);        
        
        // Setup the light
        gl.glLightfv(light.light_id, GL.GL_POSITION, FloatBuffer.wrap(light.position));
        gl.glDisable(GL.GL_LIGHTING);
        this.renderWirebox(gl);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0); 
        
    } // end of the function     
    
    public void renderWirebox(final GL gl) {    
        gl.glPushMatrix();
        gl.glTranslatef(light.position[0], light.position[1], light.position[2]);
        this.drawWirebox(gl);
        gl.glPopMatrix();
    } // end of the function 
    
    public void drawWirebox(final GL gl) {        
        float size = 0.4f;

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        // Front Face
        gl.glVertex3f(-size,  0.0f,  size);    // left bottom 
        gl.glVertex3f( size,  0.0f,  size);    // right bottom
        gl.glVertex3f( size,  size,  size);    // top right
        gl.glVertex3f(-size,  size,  size);    // top left
        // Back Face

        gl.glVertex3f(-size,  0.0f, -size);
        gl.glVertex3f(-size,  size, -size);
        gl.glVertex3f( size,  size, -size);
        gl.glVertex3f( size,  0.0f, -size);
     
        // Top Face
        gl.glVertex3f(-size,  size, -size);
        gl.glVertex3f(-size,  size,  size);
        gl.glVertex3f( size,  size,  size);
        gl.glVertex3f( size,  size, -size);

        // Bottom Face
        gl.glVertex3f(-size,  0.0f, -size);
        gl.glVertex3f( size,  0.0f, -size);
        gl.glVertex3f( size,  0.0f,  size);
        gl.glVertex3f(-size,  0.0f,  size);

        // Right face
        gl.glVertex3f( size,  0.0f, -size);
        gl.glVertex3f( size,  size, -size);
        gl.glVertex3f( size,  size,  size);
        gl.glVertex3f( size,  0.0f,  size);        

        // Left Face
        gl.glVertex3f(-size,  0.0f, -size);
        gl.glVertex3f(-size,  0.0f,  size);
        gl.glVertex3f(-size,  size,  size);
        gl.glVertex3f(-size,  size, -size);
        gl.glEnd();

    }     
    
} // End of the class //
