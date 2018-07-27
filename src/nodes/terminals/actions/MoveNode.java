package nodes.terminals.actions;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class MoveNode implements RobotProgramNode {

    RobotExpressionNode ren = null;

    public MoveNode(RobotExpressionNode ren) {
        this.ren = ren;
    }

    public MoveNode() {

    }

    @Override
    public void execute(Robot robot) {
        if (ren == null) {
            robot.move();
        }
        else {
            for (int i = 0; i < ren.evaluate(robot); i++) {
                robot.move();
            }
        }
    }

    @Override
    public String toString() {
        return "Move";
    }
}