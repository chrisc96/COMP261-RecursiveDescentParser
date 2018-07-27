package nodes.interfaces;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 21/05/2017.
 */
public interface RobotOperationNode extends RobotExpressionNode {

    @Override
    public int evaluate(Robot robot);

}
