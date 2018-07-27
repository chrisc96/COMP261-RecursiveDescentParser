package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class WhileNode implements RobotProgramNode {

    private RobotConditionalNode condition = null;
    private BlockNode whileBlock = null;

    public WhileNode(RobotConditionalNode condition, BlockNode block) {
        this.condition = condition;
        this.whileBlock = block;
    }

    @Override
    public void execute(Robot robot) {
        while (condition.evaluate(robot)) {
            whileBlock.execute(robot);
        }
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ")\n" + this.whileBlock.toString();
    }
}