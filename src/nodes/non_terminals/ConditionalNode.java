package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotConditionalNode;

/**
 * Created by Chris on 20/05/2017.
 */
public class ConditionalNode implements RobotConditionalNode {

    private RobotConditionalNode rcn = null;

    public ConditionalNode(RobotConditionalNode rcn) {this.rcn = rcn;}

    @Override
    public boolean evaluate(Robot robot) {
        return rcn.evaluate(robot);
    }

    @Override
    public String toString() {
        return rcn + "";
    }
}
