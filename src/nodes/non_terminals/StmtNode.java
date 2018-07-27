package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

/**
 * Created by Chris on 19/05/2017.
 */
public class StmtNode implements RobotProgramNode {

    private RobotProgramNode rpn = null;

    public StmtNode(ActionNode an) {
        rpn = an;
    }
    public StmtNode(LoopNode ln) { rpn = ln; }
    public StmtNode(IfNode ifN) {rpn = ifN;}
    public StmtNode(WhileNode wN) {rpn = wN;}
    public StmtNode(AssignmentNode an) {rpn = an;}

    @Override
    public void execute(Robot robot) {
        rpn.execute(robot);
    }

    @Override
    public String toString() {
        return rpn.toString();
    }
}