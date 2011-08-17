package org.berlin.gol.sound

import java.util._
import javax.sound.sampled._

object NewWriteSound {

    def sampleRate = 8000.0f
    val newaf = new AudioFormat(sampleRate, 8, 1, true, false)
    val newsdl = AudioSystem.getSourceDataLine(newaf)
    
    def sound(af:AudioFormat, sdl:SourceDataLine, hz:Int, msecs:Int, vol:Double) : Unit = {
        this.synchronized {
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
            sdl.write(buf, 0, buf.length)            
        }
    }
    
    def soundThread(hz:Int, msecs:Int, vol:Double) : Unit = {
     
        object thread extends Runnable {
            override def run() : Unit = {
                this.synchronized {
                    sound(newaf, newsdl, hz, msecs, vol)
                    Thread.sleep(50)
                }
            }
        }
        new Thread(thread).start()       
    }
    
    def open() {
        newsdl.open(newaf)
        newsdl.start()
    }
    
    def close() {
        newsdl.drain()
        newsdl.close()           
    }

} // End of object
