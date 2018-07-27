package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class LoopNode implements RobotProgramNode {

    private BlockNode blockNode;

    public LoopNode(BlockNode bn) {
        blockNode = bn;
    }

    @Override
    public void execute(Robot robot) {
        while(true) {
            blockNode.execute(robot);
        }
    }

    @Override
    public String toString() {
        return "Loop:\n" + this.blockNode;
    }
}
