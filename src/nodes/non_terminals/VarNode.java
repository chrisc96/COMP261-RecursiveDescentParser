package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotExpressionNode;

/**
 * Created by Chris on 21/05/2017.
 */
public class VarNode implements RobotExpressionNode {

    private String varName;
    private int value;

    public VarNode(String varName, int value) {
        this.varName = varName;
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(Robot robot) {
        return value;
    }

    @Override
    public String toString() {
        return varName;
    }
}