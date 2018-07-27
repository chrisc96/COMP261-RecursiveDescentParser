package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class OrNode implements RobotConditionalNode {

    ConditionalNode c1 = null;
    ConditionalNode c2 = null;

    public OrNode(ConditionalNode c1, ConditionalNode c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public boolean evaluate(Robot robot) {
        return c1.evaluate(robot) || c2.evaluate(robot);
    }

    @Override
    public String toString() {
        return "or(" + c1.toString() + ", " + c2.toString() + ")";
    }
}
