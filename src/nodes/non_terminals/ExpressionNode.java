package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class ExpressionNode implements RobotExpressionNode {

    RobotExpressionNode ren = null;

    public ExpressionNode(RobotExpressionNode ren) {
        this.ren = ren;
    }

    @Override
    public int evaluate(Robot robot) {
        return ren.evaluate(robot);
    }

    @Override
    public String toString() {
        return ren + "";
    }
}
