package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class GreaterThanNode implements RobotConditionalNode {

    private RobotExpressionNode en1;
    private RobotExpressionNode en2;

    public GreaterThanNode(RobotExpressionNode en1, RobotExpressionNode en2) {
        this.en1 = en1;
        this.en2 = en2;
    }

    @Override
    public boolean evaluate(Robot robot) {
        return en1.evaluate(robot) > en2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "gt(" +
                en1.toString() + ", " + en2.toString() + ")";
    }
}