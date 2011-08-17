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
 * GeneralRules.scala
 * Jan 10, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.automata

/**
 * General Cellular Automata Rules.
 * 
 * @author bbrown
 *
 */
object GeneralRules {
       
    /**
     * Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many
     * iterations N^2 will appear.
     * 
     * Notebook File on Squaring Rules:
     * <code>
     * CellularAutomaton[{ 
     *  { 0, Blank[], 3} -> 0, 
     *  { Blank[], 2, 3} -> 3, 
     *  { 1, 1, 3 }      -> 4, 
     *  { Blank[], 1, 4} -> 4, 
     *  { Alternatives[1, 2] << 1 or 2 >>, 3, Blank[]} -> 5, 
     *  { Pattern[$CellContext`p, Alternatives[0, 1]], 4, Blank[]} -> 7 - $CellContext`p,
     *  { 7, 2, 6} -> 3, 
     *  { 7, Blank[], Blank[]} -> 7,  
     *  { Blank[], 7, Pattern[$CellContext`p, Alternatives[1, 2]]} -> $CellContext`p,
     *  { Blank[], Pattern[$CellContext`p, Alternatives[5, 6]], Blank[]} -> 7 - $CellContext`p,  
     *  { Alternatives[5, 6], Pattern[$CellContext`p, Alternatives[1, 2]], Blank[]} -> 7 - $CellContext`p,
     *  { Alternatives[5, 6], 0, 0} -> 1, 
     *  { Blank[], Pattern[$CellContext`p, Alternatives[1, 2]], Blank[]} -> $CellContext`p,
     *  { Blank[],  Blank[], Blank[]} -> 0}, {
     *  ...
     *  Append[Table[1, {$CellContext`n$$}], 3], 0}, 
     *  Table -> Expression to N
     *  Append -> Table to 3
     * </code>
     * 
     * From Wolfram's New Kind of Science 
     * <code>
     *  { 0, _, 3} -> 0, 
     *  { _, 2, 3} -> 3, 
     *  { 1, 1, 3} -> 4, 
     *  { _, 1, 4} -> 4, 
     *  { 1 | 2, 3, _ } -> 5, 
     *  { p 0 | 1, 4, _ } -> 7 - p.1,
     *  { 7, 2, 6} -> 3, 
     *  { 7, _, _ } -> 7,  
     *  { _, 7, p 1 | 2  } -> p.3,
     *  { _, p 5 | 6, _ } -> 7 - p,  
     *  { 5 | 6, p 1 | 2, _ } -> 7 - p.2,
     *  { 5 | 6, 0, 0} -> 1, 
     *  { _, p 1 | 2, _ } -> p,
     *  { _,  _, _ } -> 0}, {
      
     * </code>
     */
    class SquaringRule extends Rules.GeneralRule {
        def ruleId() = 132
        def rule(inputState:Rules.RuleInput) : Rules.Output = inputState match {
            case (0, _, 3) => 0
            case (_, 2, 3) => 3
            case (1, 1, 3) => 4
            case (_, 1, 4) => 4
            case (1 | 2, 3, _) => 5
            case (0 | 1, 4, _) => 7 - inputState._1 // Pattern, p, Alt[0,1]                       
            case (7, 2, 6) => 3
            case (7, _, _) => 7           
            case (_, 7, 1 | 2) => inputState._3
            case (_, 5 | 6, _) => 7 - inputState._2 // Alternatives[5, 6], Pattern[p, Alternatives[1, 2]], Blank[]} -> 7 - p           
            case (5 | 6, 1 | 2, _) => 7 - inputState._2
            case (5 | 6, 0, 0) => 1
            case (_, 1 | 2, _) => inputState._2
            case _   => 0            
        }
    } // End of Rule 
    
} // End of Class
//