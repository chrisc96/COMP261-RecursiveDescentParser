package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotOperationNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class OperationNode implements RobotOperationNode {

    RobotOperationNode on = null;

    public OperationNode(RobotOperationNode on) {this.on = on;}

    @Override
    public int evaluate(Robot robot) {
        return on.evaluate(robot);
    }

    @Override
    public String toString() {
        return on + "";
    }
}