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
 * CoreObjects.java
 * Aug 28, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.mech.gl;

import javax.media.opengl.GL;

/**
 * @author bbrown
 *
 */
public class CoreObjects {
    
    private float rotqube = 0;
    
    /**
     * Render cube in center of scene.
     * @param gl
     */
    public void centerCube(final GL gl) {
        
        gl.glPushMatrix();
        gl.glRotatef(rotqube,0.0f,1.0f,0.0f);    // Rotate The cube around the Y axis        
        
        gl.glBegin(GL.GL_QUADS);     // Draw The Cube Using quads
        gl.glColor3f(0.0f,   1.0f,0.0f);  // Color Blue
        
        gl.glVertex3f( 1.0f, 9.0f,-1.0f);  // Top Right Of The Quad (Top)
        gl.glVertex3f(-1.0f, 9.0f,-1.0f);  // Top Left Of The Quad (Top)
        gl.glVertex3f(-1.0f, 9.0f, 1.0f);  // Bottom Left Of The Quad (Top)
        gl.glVertex3f( 1.0f, 9.0f, 1.0f);  // Bottom Right Of The Quad (Top)
        
        gl.glColor3f(1.0f,0.5f,0.0f);  // Color Orange        
        gl.glVertex3f( 1.0f,0.1f, 1.0f);  // Top Right Of The Quad (Bottom)
        gl.glVertex3f(-1.0f,0.1f, 1.0f);  // Top Left Of The Quad (Bottom)
        gl.glVertex3f(-1.0f,0.1f,-1.0f);  // Bottom Left Of The Quad (Bottom)
        gl.glVertex3f( 1.0f,0.1f,-1.0f);  // Bottom Right Of The Quad (Bottom)
        
        gl.glColor3f(1.0f, 0.0f,0.0f);  // Color Red        
        gl.glVertex3f( 1.0f, 9.0f, 1.0f);  // Top Right Of The Quad (Front)
        gl.glVertex3f(-1.0f, 9.0f, 1.0f);  // Top Left Of The Quad (Front)
        gl.glVertex3f(-1.0f,0.1f, 1.0f);  // Bottom Left Of The Quad (Front)
        gl.glVertex3f( 1.0f,0.1f, 1.0f);  // Bottom Right Of The Quad (Front)
        
        gl.glColor3f(1.0f,1.0f,0.0f);  // Color Yellow        
        gl.glVertex3f( 1.0f,0.1f,-1.0f);  // Top Right Of The Quad (Back)
        gl.glVertex3f(-1.0f,0.1f,-1.0f);  // Top Left Of The Quad (Back)
        gl.glVertex3f(-1.0f, 9.0f,-1.0f);  // Bottom Left Of The Quad (Back)
        gl.glVertex3f( 1.0f, 9.0f,-1.0f);  // Bottom Right Of The Quad (Back)
        
        gl.glColor3f(0.0f,0.0f,1.0f);  // Color Blue        
        gl.glVertex3f(-1.0f, 9.0f, 1.0f);  // Top Right Of The Quad (Left)
        gl.glVertex3f(-1.0f, 9.0f,-1.0f);  // Top Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, 0.1f,-1.0f);  // Bottom Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, 0.1f, 1.0f);  // Bottom Right Of The Quad (Left)
        
        gl.glColor3f(1.0f, 0.0f,1.0f);  // Color Violet
        gl.glVertex3f( 1.0f, 9.0f,-1.0f);  // Top Right Of The Quad (Right)
        gl.glVertex3f( 1.0f, 9.0f, 1.0f);  // Top Left Of The Quad (Right)
        gl.glVertex3f( 1.0f,0.1f, 1.0f);  // Bottom Left Of The Quad (Right)
        gl.glVertex3f( 1.0f,0.1f,-1.0f);  // Bottom Right Of The Quad (Right)
        gl.glEnd();          // End Drawing The Cube        
        rotqube +=0.9f;      // Increase Angle
        gl.glPopMatrix();
    }
    
    /**
     * Render cube in center of scene.
     * @param gl
     */
    public void cube(final GL gl, final float h, final float sz, float x, float y) {
                     
        gl.glBegin(GL.GL_QUADS);     // Draw The Cube Using quads
        gl.glColor3f(  0.0f, 1.0f, 0.0f);  // Color Blue
       
        gl.glVertex3f( sz+x, h,-sz+y);  // Top Right Of The Quad (Top)
        gl.glVertex3f(-sz+x, h,-sz+y);  // Top Left Of The Quad (Top)
        gl.glVertex3f(-sz+x, h, sz+y);  // Bottom Left Of The Quad (Top)
        gl.glVertex3f( sz+x, h, sz+y);  // Bottom Right Of The Quad (Top)
        
        gl.glColor3f(  sz+x,0.5f,0.0f);  // Color Orange
        
        gl.glVertex3f( sz+x,0.1f, sz+y);  // Top Right Of The Quad (Bottom)
        gl.glVertex3f(-sz+x,0.1f, sz+y);  // Top Left Of The Quad (Bottom)
        gl.glVertex3f(-sz+x,0.1f,-sz+y);  // Bottom Left Of The Quad (Bottom)
        gl.glVertex3f( sz+x,0.1f,-sz+y);  // Bottom Right Of The Quad (Bottom)
        
        gl.glColor3f(sz, 0.0f,0.0f);  // Color Red
        
        gl.glVertex3f( sz+x, h, sz+y);  // Top Right Of The Quad (Front)
        gl.glVertex3f(-sz+x, h, sz+y);  // Top Left Of The Quad (Front)
        gl.glVertex3f(-sz+x,0.1f, sz+y);  // Bottom Left Of The Quad (Front)
        gl.glVertex3f( sz+x,0.1f, sz+y);  // Bottom Right Of The Quad (Front)
        
        gl.glColor3f(sz+x,sz,0.0f);  // Color Yellow
        
        gl.glVertex3f( sz+x,0.1f,-sz+y);  // Top Right Of The Quad (Back)
        gl.glVertex3f(-sz+x,0.1f,-sz+y);  // Top Left Of The Quad (Back)
        gl.glVertex3f(-sz+x, h,-sz+y);  // Bottom Left Of The Quad (Back)
        gl.glVertex3f( sz+x, h,-sz+y);  // Bottom Right Of The Quad (Back)
        
        gl.glColor3f(0.0f,0.0f,sz);  // Color Blue
        
        gl.glVertex3f(-sz+x, h, sz+y);  // Top Right Of The Quad (Left)
        gl.glVertex3f(-sz+x, h,-sz+y);  // Top Left Of The Quad (Left)
        gl.glVertex3f(-sz+x, 0.1f,-sz+y);  // Bottom Left Of The Quad (Left)
        gl.glVertex3f(-sz+x, 0.1f, sz+y);  // Bottom Right Of The Quad (Left)
        
        gl.glColor3f(sz+x, 0.0f,sz);  // Color Violet
        
        gl.glVertex3f( sz+x, h,-sz+y);  // Top Right Of The Quad (Right)
        gl.glVertex3f( sz+x, h, sz+y);  // Top Left Of The Quad (Right)
        gl.glVertex3f( sz+x,0.1f, sz+y);  // Bottom Left Of The Quad (Right)
        gl.glVertex3f( sz+x,0.1f,-sz+y);  // Bottom Right Of The Quad (Right)
        
        gl.glEnd();          // End Drawing The Cube            
    }
    
} // End of the class //
