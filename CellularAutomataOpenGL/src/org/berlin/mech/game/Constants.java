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
 * Constants.java
 * Aug 28, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 * Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.game;

/**
 * @author bbrown
 *
 */
public class Constants {
 
    public static final int PLAYER_0        = 0;
    public static final float FIRE_ANT_SIZE   = 1.5f;

    // length of the vision
    public static final float PERSPECTIVE_Z   = 600.0f;

    // first person mode camera
    public static final float FIRST_PERSON_Z  = 4.3f;
    public static final float FIRST_HEIGHT    = 2.4f;

    public static final int DRAW_LINE_SIGHT = 1;

    public static final float LIGHT_HEIGHT_0  = 40.0f;
    public static final float LIGHT_HEIGHT_1  = 180.0f;

    public static final float MAX_BULLET_TRAVEL   = 600.0f;

    // bullet height
    public static final float BULLET_H1       = 1.8f;
    public static final float BULLET_H2       = 2.3f;

    public static final float BULLET_LEN      = 6.0f;

    // For the hex grid
    public static final float HEX_SIZE        = 12.0f;

    // the height should be 4 times othe size
    public static final float HEX_HEIGHT      = 35.0f;

    public static final float WORLD_X_MIN     = -300.0f;
    public static final float WORLD_X_MAX     = 300.0f;

    public static final float WORLD_Y_MIN     = -300.0f;
    public static final float WORLD_Y_MAX     = 300.0f;
    
} // End of the class //
