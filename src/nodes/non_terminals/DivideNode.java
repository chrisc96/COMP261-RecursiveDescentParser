package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;
import nodes.interfaces.RobotOperationNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class DivideNode implements RobotOperationNode {

    RobotExpressionNode ren1 = null;
    RobotExpressionNode ren2 = null;

    public DivideNode(RobotExpressionNode ren1, RobotExpressionNode ren2) {
        this.ren1 = ren1;
        this.ren2 = ren2;
    }

    @Override
    public int evaluate(Robot robot) {
        if (ren2.evaluate(robot) == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return ren1.evaluate(robot) / ren2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "div(" +
                ren1 + ", " + ren2 + ")";
    }
}