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
 * MathLib.java
 * Aug 28, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 *  Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.math;

/**
 * @author bbrown
 * 
 */
public class MathLib {

    /**
     * Reduces a normal vector specified as a set of three coordinates, to a
     * unit normal vector of length one.
     * 
     * @param vector
     */
    public static float [] reduceToUnit(final float vector[]) {
        float length;

        // Calculate the length of the vector
        length = (float) Math.sqrt((vector[0] * vector[0]) + (vector[1] * vector[1]) + (vector[2] * vector[2]));

        // Keep the program from blowing up by providing an accetable
        // value for vectors that may calculated too close to zero.
        if (length == 0.0f) {
            length = 1.0f;
        }

        // Dividing each element by the length will result in a
        // unit normal vector.
        vector[0] /= length;
        vector[1] /= length;
        vector[2] /= length;
        return vector;
    } // end of the function

    /**
     * Points p1, p2, & p3 specified in counter clock-wise order
     * 
     * @param v
     * @param out
     */
    public static float [] calcNormal(final float v[][], final float out[]) {
        float v1[] = { 0, 0, 0 };
        float v2[] = { 0, 0, 0 };
        final int x = 0;
        final int y = 1;
        final int z = 2;

        // Calculate two vectors from the three points
        v1[x] = v[0][x] - v[1][x];
        v1[y] = v[0][y] - v[1][y];
        v1[z] = v[0][z] - v[1][z];

        v2[x] = v[1][x] - v[2][x];
        v2[y] = v[1][y] - v[2][y];
        v2[z] = v[1][z] - v[2][z];

        // Take the cross product of the two vectors to get
        // the normal vector which will be stored in out
        out[x] = v1[y] * v2[z] - v1[z] * v2[y];
        out[y] = v1[z] * v2[x] - v1[x] * v2[z];
        out[z] = v1[x] * v2[y] - v1[y] * v2[x];

        // Normalize the vector (shorten length to one)
        return reduceToUnit(out);        

    } // end of the function

    /*
     * Normalize
     */
    public static float [] normalize(final float p[]) {
        float length;
        length = (float) Math.sqrt((p[0] * p[0]) + (p[1] * p[1]) + (p[2] * p[2]));
        if (length != 0) {
            p[0] /= length;
            p[1] /= length;
            p[2] /= length;
        } else {
            p[0] = 0;
            p[1] = 0;
            p[2] = 0;
        }
        return p;
    } // end of the function

    public static float [] calcNormal(final float p[], final float p1[], final float p2[], final float n[]) {

        float pa[] = { 0, 0, 0 };
        float pb[] = { 0, 0, 0 };

        pa[0] = p1[0] - p[0];
        pa[1] = p1[1] - p[1];
        pa[2] = p1[2] - p[2];

        pb[0] = p2[0] - p[0];
        pb[1] = p2[1] - p[1];
        pb[2] = p2[2] - p[2];

        n[0] = pa[1] * pb[2] - pa[2] * pb[1];
        n[1] = pa[2] * pb[0] - pa[0] * pb[2];
        n[2] = pa[0] * pb[1] - pa[1] * pb[0];
        
        return normalize(n);

    } // end of the function

} // End of the class //
