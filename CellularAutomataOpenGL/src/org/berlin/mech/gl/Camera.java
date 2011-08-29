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
 * Camera.java
 * Aug 27, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 * 
 *  Description: Java OpenGL framework, objects, camera movement, lighting.
 */
package org.berlin.mech.gl;

/**
 * @author bbrown
 */
public class Camera {
    
    public static float MAX_ZOOM  = 45.2f;

    public static float OFFSET_ROTATION = 8.0f;

    public static float PI_1 = 3.141592654f;

    public static float PI = 3.14159265358f;
    public static float PI_180 = PI_1 / 180.0f;
        
    public static int MAX_CAMERAS = 4;  

    public static final int CAMERA_STATIC = 0;
    public static final int CAMERA_WALKING = 1;
    
    public static final float LOOKAT_OFFSET = 5.0f;
    public static final float CAMERA_BOT_OFFSET = 8.0f;
    public static final float CAMERA_HEIGHT = 3.2f;
    
    // current location (Good Position : x=-19.0 y=27.0 z=46.00006)
    float position[] = { 4, 25, 58 };               
    // angle camera is pointing
    float angle[] = { 0, 0, 0 };    
    // rotation around the world
    float rotation[] = { 0, 0, 0 };    
    
    float centerx = 0;
    float centery = 0;
    float centerz = 0;

    float yaw;
    float pitch;
    float roll;

    float zoom_factor = 5.0f;
    float old_zoom;

    int id;
    int type;
           
    public String toString() {
        return String.format("[Camera Position : x=%s y=%s z=%s]", this.position[0], this.position[1], this.position[2]); 
    }
    
    /**
     * @return the position
     */
    public float[] getPosition() {
        return position;
    }
    /**
     * @param position the position to set
     */
    public void setPosition(float[] position) {
        this.position = position;
    }
    /**
     * @return the angle
     */
    public float[] getAngle() {
        return angle;
    }
    /**
     * @param angle the angle to set
     */
    public void setAngle(float[] angle) {
        this.angle = angle;
    }
    /**
     * @return the rotation
     */
    public float[] getRotation() {
        return rotation;
    }
    /**
     * @param rotation the rotation to set
     */
    public void setRotation(float[] rotation) {
        this.rotation = rotation;
    }
    /**
     * @return the centerx
     */
    public float getCenterx() {
        return centerx;
    }
    /**
     * @param centerx the centerx to set
     */
    public void setCenterx(float centerx) {
        this.centerx = centerx;
    }
    /**
     * @return the centery
     */
    public float getCentery() {
        return centery;
    }
    /**
     * @param centery the centery to set
     */
    public void setCentery(float centery) {
        this.centery = centery;
    }
    /**
     * @return the centerz
     */
    public float getCenterz() {
        return centerz;
    }
    /**
     * @param centerz the centerz to set
     */
    public void setCenterz(float centerz) {
        this.centerz = centerz;
    }
    /**
     * @return the yaw
     */
    public float getYaw() {
        return yaw;
    }
    /**
     * @param yaw the yaw to set
     */
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    /**
     * @return the pitch
     */
    public float getPitch() {
        return pitch;
    }
    /**
     * @param pitch the pitch to set
     */
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    /**
     * @return the roll
     */
    public float getRoll() {
        return roll;
    }
    /**
     * @param roll the roll to set
     */
    public void setRoll(float roll) {
        this.roll = roll;
    }
    /**
     * @return the zoom_factor
     */
    public float getZoom_factor() {
        return zoom_factor;
    }
    /**
     * @param zoomFactor the zoom_factor to set
     */
    public void setZoom_factor(float zoomFactor) {
        zoom_factor = zoomFactor;
    }
    /**
     * @return the old_zoom
     */
    public float getOld_zoom() {
        return old_zoom;
    }
    /**
     * @param oldZoom the old_zoom to set
     */
    public void setOld_zoom(float oldZoom) {
        old_zoom = oldZoom;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }    

} // End of the class //
