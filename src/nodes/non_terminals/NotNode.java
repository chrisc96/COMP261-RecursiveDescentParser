package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class NotNode implements RobotConditionalNode {

    private ConditionalNode c1 = null;

    public NotNode(ConditionalNode c1) {
        this.c1 = c1;
    }

    @Override
    public boolean evaluate(Robot robot) {
        return !c1.evaluate(robot);
    }

    @Override
    public String toString() {
        return "not(" + c1.toString() + ")";
    }
}