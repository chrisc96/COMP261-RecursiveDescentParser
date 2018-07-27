package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class SensorNode implements RobotExpressionNode {

    private RobotExpressionNode sn = null;

    @Override
    public int evaluate(Robot robot) {
        return sn.evaluate(robot);
    }
}
