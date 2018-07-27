package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class AssignmentNode implements RobotProgramNode {

    VarNode vn = null;
    ExpressionNode en = null;

    public AssignmentNode(VarNode vn, ExpressionNode en) {
        this.vn = vn;
        this.en = en;
    }

    @Override
    public void execute(Robot robot) {
        vn.setValue(en.evaluate(robot));
    }

    @Override
    public String toString() {
        return vn.toString() + " = " + en.toString();
    }
}