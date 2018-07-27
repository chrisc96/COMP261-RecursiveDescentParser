package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 19/05/2017.
 */
public class BlockNode implements RobotProgramNode {

    private List<StmtNode> statements = new ArrayList<>();

    @Override
    public void execute(Robot robot) {
        for (StmtNode statement : statements) {
            statement.execute(robot);
        }
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        // \t indents because inside of a indented block structure - makes clearer
        String s = "\t";
        for (StmtNode n : statements) {
            s += n.toString() + "\n\t";
        }
        return s;
    }
}