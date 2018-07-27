package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class NumNode implements RobotExpressionNode {

    private int value;

    public NumNode(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(Robot robot) {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
