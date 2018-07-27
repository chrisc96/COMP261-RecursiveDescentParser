package core;

import nodes.interfaces.RobotExpressionNode;
import nodes.non_terminals.AssignmentNode;
import nodes.interfaces.RobotConditionalNode;
import nodes.interfaces.RobotProgramNode;
import nodes.non_terminals.*;
import nodes.terminals.actions.*;
import nodes.terminals.sensors.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Chris on 20/05/2017.
 */

class ParserCore {

    /**
     * PROG ::= STMT+
     */
    static RobotProgramNode parseProgram(Scanner s) {
        ProgNode pg = new ProgNode();
        while (s.hasNext()) {
            pg.getStatements().add(parseStatement(s));
        }
        return pg;
    }

    /**
     * STMT ::= LOOP | ACT
     */
    private static StmtNode parseStatement(Scanner s) {
        if (s.hasNext(Parser.LOOP)) {
            // Parse a loop action
            return new StmtNode(parseLoop(s));
        }
        else if (s.hasNext(Parser.ACTIONS)) {
            StmtNode sn = new StmtNode(parseAction(s));
            Parser.require(Parser.SEMICOLON,"All actions have to end in a semi colon. You may have added a " +
                                            "parameter to an action that doesn't require one", s);

            return sn;
        }
        else if (s.hasNext(Parser.IF)) {
            return new StmtNode(parseIf(s));
        }
        else if (s.hasNext(Parser.WHILE)) {
            return new StmtNode(parseWhile(s));
        }
        else if (s.hasNext(Parser.VAR)) {
            return new StmtNode(parseAssign(s));
        }
        else {
            Parser.fail(s.next() + " is not a valid statement", s);
        }
        return null;
    }

    private static AssignmentNode parseAssign(Scanner s) {
        VarNode vn;
        ExpressionNode en = null;
        String nameOfVar = Parser.require(Parser.VAR, "No variable name found", s);

        Parser.require("=", "Expected equals after variable", s);

        en = parseExpression(s);

        Parser.require(";", "All assignments has to end with a \";\"", s);

        vn = Parser.variables.computeIfAbsent(nameOfVar, k -> new VarNode(nameOfVar, 0));
        return new AssignmentNode(vn, en);
    }

    private static WhileNode parseWhile(Scanner s) {
        if (!Parser.checkFor(Parser.WHILE, s)) {
            Parser.fail("Expected a while statement", s);
        }
        if (!Parser.checkFor(Parser.OPENPAREN, s)) {
            Parser.fail("Expected open parentheses", s);
        }

        RobotConditionalNode cn = parseConditional(s);

        if (!Parser.checkFor(Parser.CLOSEPAREN, s)) {
            Parser.fail("Expected closed parentheses", s);
        }

        BlockNode bn = parseBlock(s);

        return new WhileNode(cn, bn);
    }

    private static IfNode parseIf(Scanner s) {
        if (!Parser.checkFor(Parser.IF, s)) {
            Parser.fail("Expected an if statement", s);
        }
        if (!Parser.checkFor(Parser.OPENPAREN, s)) {
            Parser.fail("Expected open parentheses", s);
        }

        RobotConditionalNode cn = parseConditional(s);

        if (!Parser.checkFor(Parser.CLOSEPAREN, s)) {
            Parser.fail("Expected closed parentheses", s);
        }

        BlockNode bn = parseBlock(s);

        ArrayList<RobotConditionalNode> elifConds = new ArrayList<>();
        ArrayList<BlockNode> elifBlocks = new ArrayList<>();

        while (Parser.checkFor(Parser.ELIF, s)) {
            Parser.require(Parser.OPENPAREN, "Expected open parentheses", s);
            RobotConditionalNode elifCondition = parseConditional(s);
            Parser.require(Parser.CLOSEPAREN, "Expected closed parentheses", s);
            BlockNode elifBlock = parseBlock(s);
            elifConds.add(elifCondition);
            elifBlocks.add(elifBlock);
        }

        BlockNode elseBlock = null;
        if (Parser.checkFor(Parser.ELSE, s)) {
            elseBlock = parseBlock(s);
        }

        return new IfNode(cn, bn, elifConds, elifBlocks, elseBlock);
    }

    private static LoopNode parseLoop(Scanner s) {
        if (!Parser.checkFor(Parser.LOOP, s)) {
            Parser.fail("Expected a loop", s);
        }
        return new LoopNode(parseBlock(s));
    }

    private static ConditionalNode parseConditional(Scanner s) {
        String label = Parser.require(Parser.CONDITIONALS, "Expected lt | gt | eq | and | or | not", s);
        Parser.require(Parser.OPENPAREN, "Expected open parentheses", s);

        ExpressionNode en1 = null;
        ExpressionNode en2 = null;
        ConditionalNode cn1 = null;
        ConditionalNode cn2 = null;

        if (label != null) {
            switch (label) {
                case "gt":
                case "lt":
                case "eq":
                    en1 = parseExpression(s);
                    Parser.require(Parser.COMMA, "Expected a comma inbetween two arguments", s);
                    en2 = parseExpression(s);
                    break;
                case "and":
                case "or":
                    cn1 = parseConditional(s);
                    Parser.require(Parser.COMMA, "Expected a comma inbetween two arguments", s);
                    cn2 = parseConditional(s);
                    break;
                case "not":
                    cn1 = parseConditional(s);
                    break;
            }

            Parser.require(Parser.CLOSEPAREN, "Expected closed parentheses", s);

            switch (label) {
                case "lt":
                    return new ConditionalNode(new LessThanNode(en1, en2));
                case "gt":
                    return new ConditionalNode(new GreaterThanNode(en1, en2));
                case "eq":
                    return new ConditionalNode(new EqualsNode(en1, en2));
                case "and":
                    return new ConditionalNode(new AndNode(cn1, cn2));
                case "or":
                    return new ConditionalNode(new OrNode(cn1, cn2));
                case "not":
                    return new ConditionalNode(new NotNode(cn1));
            }
        }
        return null;
    }

    private static SensorNode parseSensor(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.FUELLEFT.toString())) {return new FuelLeftNode();}
        else if (next.equals(Parser.OPPLR.toString())) {return new OppLRNode();}
        else if (next.equals(Parser.OPPFB.toString())) {return new OppFBNode();}
        else if (next.equals(Parser.NUMBARRELS.toString())) {return new NumBarrelsNode();}
        else if (next.equals(Parser.BARRELFB.toString())) {
            ExpressionNode exp;
            if (Parser.checkFor(Parser.OPENPAREN, s)) {
                exp = parseExpression(s);
                Parser.require(Parser.CLOSEPAREN, "Expected closed parentheses", s);
                return new BarrelFBNode(exp);
            }
            return new BarrelFBNode();
        }
        else if (next.equals(Parser.BARRELLR.toString())) {
            ExpressionNode exp;
            if (Parser.checkFor(Parser.OPENPAREN, s)) {
                exp = parseExpression(s);
                Parser.require(Parser.CLOSEPAREN, "Expected closed parentheses", s);
                return new BarrelLRNode(exp);
            }
            return new BarrelLRNode();
        }
        else if (next.equals(Parser.WALLDIST.toString())) {return new WallDistNode();}
        else {
            Parser.fail(next + " is not a valid sensor", s);
        }
        return null;
    }

    private static OperationNode parseOperation(Scanner s) {
        if (s.hasNext(Parser.ADD)) return new OperationNode(parseAdd(s));
        else if (s.hasNext(Parser.SUB)) return new OperationNode(parseSub(s));
        else if (s.hasNext(Parser.MULTIPLY)) return new OperationNode(parseMult(s));
        else if (s.hasNext(Parser.DIVIDE)) return new OperationNode(parseDiv(s));
        else {
            Parser.fail(s.next() + " is an unknown operation", s);
        }
        return null;
    }

    private static AddNode parseAdd(Scanner s) {
        ExpressionNode en1, en2;

        Parser.require(Parser.ADD, "Expecting ADD" , s);
        Parser.require(Parser.OPENPAREN, "Expecting Open Parentheses" , s);

        en1 = parseExpression(s);
        Parser.require(Parser.COMMA, "Expecting a comma in between two statements", s);
        en2 = parseExpression(s);

        Parser.require(Parser.CLOSEPAREN, "Expecting Closed Parentheses", s);

        return new AddNode(en1, en2);
    }

    private static SubtractNode parseSub(Scanner s) {
        ExpressionNode en1, en2;

        Parser.require(Parser.SUB, "Expecting SUB" ,s);
        Parser.require(Parser.OPENPAREN, "Expecting Open Parentheses" ,s);

        en1 = parseExpression(s);
        Parser.require(Parser.COMMA, "Expecting a comma in between two statements", s);
        en2 = parseExpression(s);

        Parser.require(Parser.CLOSEPAREN, "Expecting Closed Parentheses", s);

        return new SubtractNode(en1, en2);
    }

    private static MultiplyNode parseMult(Scanner s) {
        ExpressionNode en1, en2;

        Parser.require(Parser.MULTIPLY, "Expecting MULT" ,s);
        Parser.require(Parser.OPENPAREN, "Expecting Open Parentheses" ,s);

        en1 = parseExpression(s);
        Parser.require(Parser.COMMA, "Expecting a comma in between two statements", s);
        en2 = parseExpression(s);

        Parser.require(Parser.CLOSEPAREN, "Expecting Closed Parentheses", s);

        return new MultiplyNode(en1, en2);
    }

    private static DivideNode parseDiv(Scanner s) {
        ExpressionNode en1, en2;

        Parser.require(Parser.DIVIDE, "Expecting DIV" ,s);
        Parser.require(Parser.OPENPAREN, "Expecting Open Parentheses" ,s);

        en1 = parseExpression(s);
        Parser.require(Parser.COMMA, "Expecting a comma in between two statements", s);
        en2 = parseExpression(s);

        Parser.require(Parser.CLOSEPAREN, "Expecting Closed Parentheses", s);

        return new DivideNode(en1, en2);
    }

    private static NumNode parseNumber(Scanner s) {
        if (s.hasNext(Parser.NUM)) {
            String value = s.next(Parser.NUM);
            return new NumNode(Integer.parseInt(value));
        }
        else {
            Parser.fail("number fail. expecting: " + Parser.NUM.toString(), s);
        }
        return null;
    }

    private static ActionNode parseAction(Scanner s) {
        if (s.hasNext(Parser.MOVE)) return new ActionNode(parseMove(s));
        else if (s.hasNext(Parser.TURNLEFT)) return new ActionNode(parseTurnL(s));
        else if (s.hasNext(Parser.TURNRIGHT)) return new ActionNode(parseTurnR(s));
        else if (s.hasNext(Parser.TURNAROUND)) return new ActionNode(parseTurnAround(s));
        else if (s.hasNext(Parser.TAKEFUEL)) return new ActionNode(parseTakeFuel(s));
        else if (s.hasNext(Parser.WAIT)) return new ActionNode(parseWait(s));
        else if (s.hasNext(Parser.SHIELDON)) return new ActionNode(parseShieldOn(s));
        else if (s.hasNext(Parser.SHIELDOFF)) return new ActionNode(parseShieldOff(s));
        else {
            Parser.fail("Expected action node", s);
        }
        return null;
    }

    private static BlockNode parseBlock(Scanner s) {
        BlockNode bn = new BlockNode();

        // Checking for open brace
        Parser.require(Parser.OPENBRACE, "Block should begin with open brace", s);

        while (s.hasNext() && !s.hasNext(Parser.CLOSEBRACE)) {
            bn.getStatements().add(parseStatement(s));
        }

        if (bn.getStatements().isEmpty()) {
            Parser.fail("Block cannot be empty", s);
        }

        // Checking for closed brace
        Parser.require(Parser.CLOSEBRACE, "Block should end with closed brace", s);
        return bn;
    }

    private static ExpressionNode parseExpression(Scanner s) {
        if (s.hasNext(Parser.NUM)) {
            return new ExpressionNode(parseNumber(s));
        }
        else if (s.hasNext(Parser.OPERATIONS)) {
            return new ExpressionNode(parseOperation(s));
        }
        else if (s.hasNext(Parser.SENSORS)) {
            return new ExpressionNode(parseSensor(s));
        }
        else if (s.hasNext(Parser.VAR)) {
            return new ExpressionNode(parseVariable(s));
        }
        else {
            Parser.fail(s.next() + " is an Unknown Expression", s);
        }
        return null;
    }

    private static RobotExpressionNode parseVariable(Scanner s) {
        String varName = Parser.require(Parser.VAR, "Invalid variable name", s);

        VarNode var = Parser.variables.computeIfAbsent(varName, k -> new VarNode(varName, 0));
        return var;
    }

    private static MoveNode parseMove(Scanner s) {
        String next = s.next();
        MoveNode tmp1 = null;
        MoveNode tmp2;

        if(next.equals(Parser.MOVE.toString())){
            tmp1 = new MoveNode();
        }
        else{
            Parser.fail("Expected Move", s);
        }

        if(s.hasNext(Parser.OPENPAREN)){
            if(!Parser.checkFor(Parser.OPENPAREN, s)){
                Parser.fail("expected OPENPAREN for MOVE", s);
            }

            tmp2 = new MoveNode(parseExpression(s));
            if(!Parser.checkFor(Parser.CLOSEPAREN, s)){
                Parser.fail("expected CLOSEPAREN for MOVE", s);
            }
            return tmp2;
        }
        return tmp1;
    }

    private static TurnLeftNode parseTurnL(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.TURNLEFT.toString())) {
            return new TurnLeftNode();
        }
        else {
            Parser.fail("Expected turn left", s);
        }
        return null;
    }

    private static TurnRightNode parseTurnR(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.TURNRIGHT.toString())) {
            return new TurnRightNode();
        }
        else {
            Parser.fail("Expected turn right", s);
        }
        return null;
    }

    private static TurnAroundNode parseTurnAround(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.TURNAROUND.toString())) {
            return new TurnAroundNode();
        }
        else {
            Parser.fail("Expected turn around", s);
        }
        return null;
    }

    private static WaitNode parseWait(Scanner s) {
        String next = s.next();
        WaitNode tmp1 = null;
        WaitNode tmp2;

        if(next.equals(Parser.WAIT.toString())){
            tmp1 = new WaitNode();
        }
        else{
            Parser.fail("Expected Move", s);
        }

        if(s.hasNext(Parser.OPENPAREN)){
            if(!Parser.checkFor(Parser.OPENPAREN, s)){
                Parser.fail("expected OPENPAREN for MOVE", s);
            }

            tmp2 = new WaitNode(parseExpression(s));
            if(!Parser.checkFor(Parser.CLOSEPAREN, s)){
                Parser.fail("expected CLOSEPAREN for MOVE", s);
            }
            return tmp2;
        }
        return tmp1;
    }

    private static TakeFuelNode parseTakeFuel(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.TAKEFUEL.toString())) {
            return new TakeFuelNode();
        }
        else {
            Parser.fail("Expected takeFuel", s);
        }
        return null;
    }

    private static SetShieldOffNode parseShieldOff(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.SHIELDOFF.toString())) {
            return new SetShieldOffNode();
        }
        else {
            Parser.fail("Expected shieldOff", s);
        }
        return null;
    }

    private static SetShieldOnNode parseShieldOn(Scanner s) {
        String next = s.next();
        if (next.equals(Parser.SHIELDON.toString())) {
            return new SetShieldOnNode();
        }
        else {
            Parser.fail("Expected shieldOn", s);
        }
        return null;
    }
}
