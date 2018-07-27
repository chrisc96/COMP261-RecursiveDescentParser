package nodes.terminals.sensors;

import core.Robot;
import nodes.non_terminals.SensorNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class OppLRNode extends SensorNode {

    @Override
    public int evaluate(Robot robot) {
        return robot.getOpponentLR();
    }

    @Override
    public String toString() {
        return "oppLR";
    }
}
