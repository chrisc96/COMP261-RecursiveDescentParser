package nodes.interfaces;

import core.Robot;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

public interface RobotProgramNode {

	void execute(Robot robot);

}