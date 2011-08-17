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
 * Rules.scala
 * Jan 10, 2011
 * bbrown
 * Contact: Berlin Brown <berlin dot brown at gmail.com>
 */
package org.berlin.automata

/**
 * Simple Cellular Automata Rules (see Wolfram's A New Kind of Science)
 * 
 * @author bbrown
 *
 */
object Rules {
   
    // Type Rule Input of Tuple of Ints
    type RuleInput = (Int, Int, Int)
    type Output = Int
    trait ElementaryRule extends Rule
    trait GeneralRule extends Rule
    
    trait Rule {
        def ruleId() : Int
        def rule(inputState:RuleInput) : Output 
        override def toString : String = "Rule:" + ruleId 
    }  
          
    class Rule132 extends Rule {
        def ruleId() = 132
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1, 1, 0) => 1
                case (0, 1, 0) => 1
                case _   => 0            
        }
    } // End of Rule 
          
    class Rule254() extends ElementaryRule {
        def ruleId = 254
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1, 1, 1) => 1
                case (1, 1, 0) => 1
                case (1, 0, 1) => 1
                case (1, 0, 0) => 1
                case (0, 1, 1) => 1
                case (0, 1, 0) => 1
                case (0, 0, 1) => 1
                case (0, 0, 0) => 0
                case _   => 0
        }                    
    } // End of Rule    
    
    class Rule250 extends ElementaryRule {
        def ruleId() = 250
        def rule(inputState:RuleInput) : Output = inputState match {
                case (0, 1, 0) => 0
                case (0, 0, 0) => 0
                case _   => 1
        }
    } // End of Rule     
    
    class Rule30 extends ElementaryRule {
        def ruleId() = 30
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1,  1, 1) => 0
                case (1,  1, 0) => 0
                case (1,  0, 1) => 0
                case (0,  0, 0) => 0
                case _   => 1
        }                    
    }
    
    class Rule110 extends ElementaryRule {
        def ruleId() = 110
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1,  1, 1) => 0
                case (1,  0, 0) => 0                
                case (0,  0, 0) => 0
                case _   => 1                       
        }
    }

    class Rule90 extends ElementaryRule {
        def ruleId() = 90
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1, 1, 1) => 0
                case (1, 0, 1) => 0  
                case (0, 1, 0) => 0
                case (0, 0, 0) => 0
                case _   => 1
        }                    
    }    
    class Rule190 extends ElementaryRule {
        def ruleId() = 190
        def rule(inputState:RuleInput) : Output = inputState match {
                case (1, 1, 0) => 0
                case (0, 0, 0) => 0
                case _   => 1
        }
    } // End of Rule
        
} // End Of Object