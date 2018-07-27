package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;
import nodes.interfaces.RobotProgramNode;

import java.util.ArrayList;

/**
 * Created by Chris on 19/05/2017.
 */
public class IfNode implements RobotProgramNode {

    private RobotConditionalNode condition = null;
    private BlockNode ifBlock = null;
    private BlockNode elseBlock = null;
    private ArrayList<RobotConditionalNode> elifConditions;
    private ArrayList<BlockNode> elifBlocks;


    public IfNode(RobotConditionalNode cn, BlockNode ifB, ArrayList<RobotConditionalNode> elifConditions, ArrayList<BlockNode> elifBlocks, BlockNode elseBlock) {
        this.condition = cn;
        this.ifBlock = ifB;
        this.elseBlock = elseBlock;
        this.elifConditions = elifConditions;
        this.elifBlocks = elifBlocks;
    }

    @Override
    public void execute(Robot robot) {
        // If condition is true
        if (condition.evaluate(robot)) {
            ifBlock.execute(robot);
        }
        // Else if execution
        else if (!elifBlocks.isEmpty() && !elifConditions.isEmpty()) {
            for (int i = 0; i < elifConditions.size(); i++) {
                if (elifConditions.get(i).evaluate(robot)) {
                    elifBlocks.get(i).execute(robot);
                }
            }
        }
        // Else condition
        if (elseBlock != null) {
            elseBlock.execute(robot);
        }
    }

    @Override
    public String toString() {
        String str;
        str = "\tif(" + condition.toString() + ")\n\t" + this.ifBlock.toString();
        if (!elifBlocks.isEmpty() && !elifConditions.isEmpty()) {
            for (int i = 0; i < elifBlocks.size(); i++) {
                str += "elif(" + elifConditions.get(i).toString() + ")\n\t" + elifBlocks.get(i).toString();
            }
        }
        if (elseBlock != null) {
            str += "else\n\t" + this.elseBlock;
        }
        return str;
    }
}