package nodes.terminals.actions;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class TurnLeftNode implements RobotProgramNode {

    @Override
    public void execute(Robot robot) {
        robot.turnLeft();
    }

    @Override
    public String toString() {
        return "TurnLeft";
    }
}
