package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;
import nodes.interfaces.RobotOperationNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class MultiplyNode implements RobotOperationNode {

    RobotExpressionNode ren1 = null;
    RobotExpressionNode ren2 = null;

    public MultiplyNode(RobotExpressionNode ren1, RobotExpressionNode ren2) {
        this.ren1 = ren1;
        this.ren2 = ren2;
    }

    @Override
    public int evaluate(Robot robot) {
        return ren1.evaluate(robot) * ren2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "mult(" +
                ren1  + ", " + ren2 + ")";
    }
}
