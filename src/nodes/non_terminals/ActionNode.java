package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;
import nodes.terminals.actions.*;

/**
 * Created by Chris on 19/05/2017.
 */
public class ActionNode implements RobotProgramNode {

    private RobotProgramNode an = null;

    public ActionNode(MoveNode mn) {
        an = mn;
    }
    public ActionNode(TurnLeftNode tln) {
        an = tln;
    }
    public ActionNode(TurnRightNode trn) {
        an = trn;
    }
    public ActionNode(TakeFuelNode tfn) {
        an = tfn;
    }
    public ActionNode(WaitNode wn) {
        an = wn;
    }
    public ActionNode(SetShieldOnNode sson) {an = sson;}
    public ActionNode(SetShieldOffNode ssoff) {an = ssoff;}
    public ActionNode(TurnAroundNode tan) {an = tan;}

    @Override
    public void execute(Robot robot) {
        an.execute(robot);
    }

    @Override
    public String toString() {
        return an.toString();
    }
}