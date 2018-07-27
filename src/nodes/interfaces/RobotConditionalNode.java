package nodes.interfaces;

import core.Robot;

/**
 * Interface for all nodes that need to be
 * evaluated
 */

public interface RobotConditionalNode {

    boolean evaluate(Robot robot);
}
