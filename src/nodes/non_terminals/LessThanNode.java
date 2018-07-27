package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class LessThanNode implements RobotConditionalNode {

    RobotExpressionNode en1 = null;
    RobotExpressionNode en2 = null;

    public LessThanNode(RobotExpressionNode en1, RobotExpressionNode en2) {
        this.en1 = en1;
        this.en2 = en2;
    }

    @Override
    public boolean evaluate(Robot robot) {
        return en1.evaluate(robot) < en2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "lt(" +
                en1 + ", " + en2 + ")";
    }
}