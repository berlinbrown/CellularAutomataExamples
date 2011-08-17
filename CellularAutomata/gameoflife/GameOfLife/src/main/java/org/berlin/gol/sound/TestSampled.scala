package org.berlin.gol.sound

import java.util._
import javax.sound.sampled._

object TestSampled {

    def sampleRate = 8000.0f
    
    def sound(hz:Int, msecs:Int, vol:Double) : Unit = {
        
        var szBuf = sampleRate.toInt * msecs / 1000;
        val buf = new Array[Byte](szBuf);
        
        for (i <- 0 until buf.length) {
             val angle = i / (sampleRate / hz) * 2.0 * Math.Pi;
             buf(i) = (Math.sin(angle) * 127.0 * vol).toByte;
        } 
        
        var z = 0;        
        while (z < (sampleRate / 100.0).toInt && (z < buf.length / 2)) {
            buf(z) = (buf(z) * z / (sampleRate / 100.0)).toByte
            buf(buf.length - 1) = (buf(buf.length-1-z) * z / (sampleRate / 100.0)).toByte
            z += 1
        }
            
        val af = new AudioFormat(sampleRate, 8, 1, true, false)
        val sdl = AudioSystem.getSourceDataLine(af)
        sdl.open(af)
        sdl.start()
        sdl.write(buf, 0, buf.length)
        sdl.drain()
        sdl.close() 
        Thread.sleep(10)
    }
    
              
    def main(args : Array[String]) : Unit = {
      
      println("Running TestSampled [Math Sound]")
      for (i <- 0 until 10) {
          sound(80 * i, 500, 0.3)
      }      
    }
          
} // End of object
