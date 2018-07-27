package nodes.non_terminals;

import core.Robot;
import nodes.interfaces.RobotProgramNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 19/05/2017.
 */
public class ProgNode implements RobotProgramNode {

    private List<StmtNode> statements = new ArrayList<>();

    @Override
    public void execute(Robot robot) {
        // Execute all the statements
        for (StmtNode stmt : statements) {
            stmt.execute(robot);
        }
    }

    public List<StmtNode> getStatements() {
        return statements;
    }

    @Override
    public String toString() {
        String s = "";
        for (StmtNode n : statements) {
            s += n.toString() + '\n';
        }
        return s;
    }
}