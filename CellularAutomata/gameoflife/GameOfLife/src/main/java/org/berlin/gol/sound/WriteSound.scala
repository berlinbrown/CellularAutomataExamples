package org.berlin.gol.sound

import java.util._
import javax.sound.sampled._

object WriteSound {

    def sampleRate = 8000.0f
    var numberOfOpenThreads = 0
    
    def sound(hz:Int, msecs:Int, vol:Double) : Unit = {
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
                
            val af = new AudioFormat(sampleRate, 8, 1, true, false)
            val sdl = AudioSystem.getSourceDataLine(af)
            sdl.open(af)
            sdl.start()
            sdl.write(buf, 0, buf.length)         
            sdl.drain()
            sdl.close()            
        }
    }
    
    def soundThread(hz:Int, msecs:Int, vol:Double) : Unit = {
     
        object thread extends Runnable {
            override def run() : Unit = {
                this.synchronized {                                        
                    if (numberOfOpenThreads < 24) {
                        numberOfOpenThreads = numberOfOpenThreads + 1
                        sound(hz, msecs, vol)
                        Thread.sleep(140)
                        numberOfOpenThreads = numberOfOpenThreads - 1
                    }
                }
            }
        }
        new Thread(thread).start()
        
    }

} // End of object
