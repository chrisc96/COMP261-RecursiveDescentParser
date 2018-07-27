package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;
import nodes.interfaces.RobotOperationNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class AddNode implements RobotOperationNode {

    private RobotExpressionNode ren1 = null;
    private RobotExpressionNode ren2 = null;

    public AddNode(RobotExpressionNode ren1, RobotExpressionNode ren2) {
        this.ren1 = ren1;
        this.ren2 = ren2;
    }

    @Override
    public int evaluate(Robot robot) {
        return ren1.evaluate(robot) + ren2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "add(" +
                ren1 + ", " +  ren2 + ")";
    }
}