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
 * Plane.java
 * Aug 28, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.game;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;

import org.berlin.mech.gl.Lights;
import org.berlin.mech.math.MathLib;

/**
 * @author bbrown
 * 
 */
public class Plane {

    public void renderPlane(final GL gl) {

        gl.glEnable(GL.GL_LIGHTING);
        gl.glDisable(GL.GL_TEXTURE_2D);
        // set the material for this object
        Lights.setmaterial(gl, Lights.grey_ambient, Lights.grey_diffuse, Lights.grey_specular, Lights.ilow_shininess, Lights.imat_emission);
        gl.glPushMatrix();
        drawHex(gl);
        gl.glPopMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);
    }

    public void drawHexplane(final GL gl , final float x_1, final float x_2, final float y_1, final float size) {

        /*
         #define N_0 CalcNormal(v[0], v[1], v[2], n)
         #define N_1 CalcNormal(v[1], v[0], v[2], n)
         #define N_2 CalcNormal(v[2], v[0], v[1], n)
         #define N_3 CalcNormal(v[0], v[2], v[1], n)

         #define GET_NORMAL glNormal3fv(n)

         #define CLR_0 glColor3f(0.23f, 0.67f, 0.79f)
         #define CLR_1 glColor3f(0.755f, 0.234f, 0.237f)
         #define CLR_2 glColor3f(0.5646f, 0.3453f, 0.753f)
         #define CLR_3 glColor3f(0.9646f, 0.9453f, 0.353f)
         */

        float h_2;        
        float v[][] = { 
                { 0, 0, 0 },
                { 0, 0, 0 },
                { 0, 0, 0 },
        };
        float n[] = { 0, 0, 0 };
        float tol = (size * 2.0f) * 1.1f;
        float x;

        h_2 = 1.8f * size;
        for (x = x_1; x < x_2; x += tol) {     

            // left bottom front
            v[0][0] = -size + x;
            v[0][1] = 0.0f;
            v[0][2] = size + y_1;

            v[1][0] = -size + x;
            v[1][1] = 0.0f;
            v[1][2] = -size + y_1;

            v[2][0] = size + x;
            v[2][1] = 0.0f;
            v[2][2] = -size + y_1;

            gl.glColor3f(0.23f, 0.67f, 0.79f);              
            MathLib.calcNormal(v[1], v[0], v[2], n);
            gl.glNormal3fv(FloatBuffer.wrap(n));       

            gl.glVertex3fv(FloatBuffer.wrap(v[0]));
            gl.glVertex3fv(FloatBuffer.wrap(v[1]));
            gl.glVertex3fv(FloatBuffer.wrap(v[2]));

            v[0][0] = size + x;
            v[0][1] = 0.0f;
            v[0][2] = -size + y_1;

            v[1][0] = size + x;
            v[1][1] = 0.0f;
            v[1][2] = size + y_1;

            v[2][0] = -size + x;
            v[2][1] = 0.0f;
            v[2][2] = size + y_1;

            gl.glColor3f(0.23f, 0.67f, 0.79f);
            
            // Calc normal and draw
            MathLib.calcNormal(v[1], v[0], v[2], n);
            gl.glNormal3fv(FloatBuffer.wrap(n));            

            gl.glVertex3fv(FloatBuffer.wrap(v[0]));
            gl.glVertex3fv(FloatBuffer.wrap(v[1]));
            gl.glVertex3fv(FloatBuffer.wrap(v[2]));    // triangle left bottom front

            // top triangle
            v[0][0] = size + x;
            v[0][1] = 0.0f;
            v[0][2] = -size + y_1;

            v[1][0] = -size + x;
            v[1][1] = 0.0f;
            v[1][2] = -size + y_1;

            v[2][0] = 0.0f + x;
            v[2][1] = 0.0f;
            v[2][2] = -h_2 + y_1;
            
            gl.glColor3f(0.23f, 0.67f, 0.79f);
            // Calc normal and draw            
            MathLib.calcNormal(v[1], v[0], v[2], n);
            gl.glNormal3fv(FloatBuffer.wrap(n));            

            gl.glVertex3fv(FloatBuffer.wrap(v[0]));
            gl.glVertex3fv(FloatBuffer.wrap(v[1]));
            gl.glVertex3fv(FloatBuffer.wrap(v[2]));    // triangle left bottom front

            // top triangle
            v[0][0] = size + x;
            v[0][1] = 0.0f;
            v[0][2] = size + y_1;

            v[1][0] = -size + x;
            v[1][1] = 0.0f;
            v[1][2] = size + y_1;

            v[2][0] = 0.0f + x;
            v[2][1] = 0.0f;
            v[2][2] = h_2 + y_1;
            
            gl.glColor3f(0.23f, 0.67f, 0.79f);
            // Calc normal and draw           
            MathLib.calcNormal(v[0], v[1], v[2], n);
            gl.glNormal3fv(FloatBuffer.wrap(n));            

            gl.glVertex3fv(FloatBuffer.wrap(v[0]));
            gl.glVertex3fv(FloatBuffer.wrap(v[1]));
            gl.glVertex3fv(FloatBuffer.wrap(v[2]));    // triangle left bottom front

        } // end of the for 

    } // end of the function

    public void drawHex(final GL gl) {
        
        float size = Constants.HEX_SIZE;
        float height = Constants.HEX_HEIGHT;

        float x_1 = Constants.WORLD_X_MIN;
        float x_2 = Constants.WORLD_X_MAX;

        float y_1 = Constants.WORLD_Y_MIN;
        float y_2 = Constants.WORLD_Y_MAX;

        float offset = 0.0f;
        boolean s_flag = false;

        float i = 0.0f;
                
        gl.glBegin(GL.GL_TRIANGLES);
        for (i = y_1; i < y_2; i += height) {
            drawHexplane(gl, x_1 - offset, x_2, i, size);
            if (s_flag == true) {
                offset = size;
                s_flag = false;
            } else {
                offset = 0.0f;
                s_flag = true;
            } // end of the if

        } // end of the for
        gl.glEnd();        

    } // end of the function

} // End of the Class //
