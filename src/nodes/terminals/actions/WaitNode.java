package nodes.terminals.actions;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class WaitNode implements RobotProgramNode {

    RobotExpressionNode ren = null;

    public WaitNode(RobotExpressionNode ren) {
        this.ren = ren;
    }
    public WaitNode() {}

    @Override
    public void execute(Robot robot) {
        if (ren == null) robot.idleWait();
        else {
            for (int i = 0; i < ren.evaluate(robot); i++) {
                robot.idleWait();
            }
        }
    }

    @Override
    public String toString() {
        return "Wait";
    }
}