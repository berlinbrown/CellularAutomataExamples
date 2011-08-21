
<img src="https://github.com/berlinbrown/CellularAutomataExamples/raw/master/media/screenshot_squaring_1.png" />
Introduction
Squaring Rule from Wolfram's New Kind of Science, given an input N, after so many iterations N^2 will appear.

This is an example of emergent behavior forming from a simple set of input rules. The program is interesting because we didn't program the squaring rule into the system. We didn't implement a multiplier function using procedural logic. The 'solution' to our problem emerged based on a simple set of rules. Those rules emerged after so many iterations through the cell grid.

Also see: http://mathworld.wolfram.com/CellularAutomaton.html
<pre>
Scala Code : Squaring Rules Implementation
class SquaringRule extends Rules.GeneralRule {
        def ruleId() = 132
        def rule(inputState:Rules.RuleInput) : Rules.Output = inputState match {
            case (0, _, 3) => 0
            case (_, 2, 3) => 3
            case (1, 1, 3) => 4
            case (_, 1, 4) => 4
            case (1 | 2, 3, _) => 5
            case (0 | 1, 4, _) => 7 - inputState._1 
            case (7, 2, 6) => 3
            case (7, _, _) => 7           
            case (_, 7, 1 | 2) => inputState._3
            case (_, 5 | 6, _) => 7 - inputState._2          
            case (5 | 6, 1 | 2, _) => 7 - inputState._2
            case (5 | 6, 0, 0) => 1
            case (_, 1 | 2, _) => inputState._2
            case _   => 0            
        }
    } // End of Rule 
</pre>
The code block above is a snippet for the Scala programming language. The input pattern is a tuple of integers. The first parameter represents the integer value of the left cell, the second parameter represents the center cell, and the third parameter represents the right cell. The integer result of the match on the input pattern will establish the cell state for the next line of the grid.
