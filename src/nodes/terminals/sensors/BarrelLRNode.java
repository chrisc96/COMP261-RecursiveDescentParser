package nodes.terminals.sensors;

import core.Robot;
import nodes.non_terminals.ExpressionNode;
import nodes.non_terminals.SensorNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class BarrelLRNode extends SensorNode {

    ExpressionNode en = null;

    public BarrelLRNode(){}
    public BarrelLRNode(ExpressionNode en) {
        this.en = en;
    }

    @Override
    public int evaluate(Robot robot) {
        if (en != null) {
            return robot.getBarrelFB(en.evaluate(robot));
        }
        else {
            return robot.getClosestBarrelFB();
        }
    }

    @Override
    public String toString() {
        String str = "BarrelLR";
        if (en != null) {
            str += "(" + en.toString() + ")";
        }
        return str;
    }
}
