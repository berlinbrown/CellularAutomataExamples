/**
 * Copyright (c) 2006-2011 Berlin Brown.  All Rights Reserved
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
 * Date: 12/15/2009 
 * Description: Extend this customizable Swing wrapper library.
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * Doing it wrong example, no refactoring
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.gol.sound

import javax.sound.midi._

/**
 * @author berlinbrown 
 */
class MidiSynth {

    
    var sequencer:Sequencer = null
    var sequence:Sequence = null
    var synthesizer:Synthesizer = null        
    var instruments:Array[Instrument] = null
    var channels:Array[MidiChannel] = null
    var cc:MidiChannel = null
    
    def open() = {
        println("Attempting to open midi system")
        try {
            if (synthesizer == null) {
                synthesizer = MidiSystem.getSynthesizer
                if (synthesizer == null) {
                    System.out.println("ERROR: getSynthesizer() failed!")                    
                }
            } 
            synthesizer.open
            sequencer = MidiSystem.getSequencer
            sequence = new Sequence(Sequence.PPQ, 10)
        } catch {
            case e:Exception => e.printStackTrace()
        }

        val sb = synthesizer.getDefaultSoundbank()
        if (sb != null) {
            instruments = synthesizer.getDefaultSoundbank().getInstruments()
            println("Instruments Count = " + instruments.length)
            synthesizer.loadInstrument(instruments(0));
        }        
        
        val midiChannels = synthesizer.getChannels  
        channels = new Array[MidiChannel](midiChannels.length)
        for (i <- 0 until midiChannels.length) {
            channels(i) = midiChannels(i)
        } 
        cc = channels(0)
        playTest        
    }
    
    def close() = {
        if (synthesizer != null) {
            synthesizer.close
        }
        if (sequencer != null) {
            sequencer.close
        }
        sequencer = null
        synthesizer = null
        instruments = null     
        println("Closing midi system")
    }
    
    def playTest() = {
        cc.noteOn(60, 30);
    }
    
    def play(noteNum:Int, vel:Int) = {
        cc.noteOn(noteNum, vel);
    }
    
} // End of Class //
