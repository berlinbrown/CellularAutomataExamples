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
 * Date: 8/15/2011
 *  
 * Description: Pure Java, Cellular Automata Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
 * iterations N^2 will appear.
 * 
 * The Java version is a port of the Scala version.
 * 
 * Home Page: http://code.google.com/u/berlin.brown/
 * https://github.com/berlinbrown/CellularAutomataExamples
 * 
 * Also see: http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life and Wolfram's Cellular Automata
 * 
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin3.java.squaring.app;

/**
 * Cellular Automata Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
 * iterations N^2 will appear.
 * 
 * Swing based cellular grid application demonstrating this rule.
 *  
 */
public class JavaCellularAutomataSquareApp {

  public static void main(final String [] args) {
    System.out.println(">>> Running application");
    final JavaCellularAutomataSquare automata = new JavaCellularAutomataSquare();
    automata.invokeLater();
  }
  
} // End of the Class //
