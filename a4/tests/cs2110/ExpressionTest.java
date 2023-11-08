package cs2110;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantExpressionTest {

    @Test
    @DisplayName("A Constant node should evaluate to its value (regardless of var table)")
    void testEval() throws UnboundVariableException {
        Expression expr = new Constant(1.5);
        assertEquals(1.5, expr.eval(MapVarTable.empty()));
    }


    @Test
    @DisplayName("A Constant node should report that 0 operations are required to evaluate it")
    void testOpCount() {
        Expression expr = new Constant(1.5);
        assertEquals(0, expr.opCount());
    }


    @Test
    @DisplayName("A Constant node should produce an infix representation with just its value (as " +
            "formatted by String.valueOf(double))")
    void testInfix() {
        Expression expr = new Constant(1.5);
        assertEquals("1.5", expr.infixString());

        expr = new Constant(Math.PI);
        assertEquals("3.141592653589793", expr.infixString());
    }

    @Test
    @DisplayName("A Constant node should produce an postfix representation with just its value " +
            "(as formatted by String.valueOf(double))")
    void testPostfix() {
        Expression expr = new Constant(1.5);
        assertEquals("1.5", expr.postfixString());

        expr = new Constant(Math.PI);
        assertEquals("3.141592653589793", expr.postfixString());
    }


    @Test
    @DisplayName("A Constant node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Constant(1.5);
        // Normally `assertEquals()` is preferred, but since we are specifically testing the
        // `equals()` method, we use the more awkward `assertTrue()` to make that call explicit.
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("A Constant node should equal another Constant node with the same value")
    void testEqualsTrue() {
        Expression expr1 = new Constant(1.5);
        Expression expr2 = new Constant(1.5);
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("A Constant node should not equal another Constant node with a different value")
    void testEqualsFalse() {
        Expression expr1 = new Constant(1.5);
        Expression expr2 = new Constant(2.0);
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("A Constant node does not depend on any variables")
    void testDependencies() {
        Expression expr = new Constant(1.5);
        Set<String> deps = expr.dependencies();
        assertTrue(deps.isEmpty());
    }


    @Test
    @DisplayName("A Constant node should optimize to itself (regardless of var table)")
    void testOptimize() {
        Expression expr = new Constant(1.5);
        Expression opt = expr.optimize(MapVarTable.empty());
        assertEquals(expr, opt);
    }
}

class VariableExpressionTest {

    @Test
    @DisplayName("A Variable node should evaluate to its variable's value when that variable is " +
            "in the var map")
    void testEvalBound() throws UnboundVariableException {
        // variable's value exists in varTable
        Variable var1 = new Variable("x");
        VarTable table1 = new MapVarTable();
        table1.set("x", 2);
        assertEquals(2, var1.eval(table1));

        // varTable has multiple variables with values
        Variable var2 = new Variable("y");
        table1.set("y", 15);
        Variable var3 = new Variable("z");
        table1.set("z", -10);
        assertEquals(2, var1.eval(table1));
        assertEquals(15, var2.eval(table1));
        assertEquals(-10, var3.eval(table1));

        // changing variable value
        table1.set("y", 123);
        assertEquals(123, var2.eval(table1));

        // empty variable name with value
        Variable var4 = new Variable("");
        table1.set("", 7);
        assertEquals(7, var4.eval(table1));

    }

    @Test
    @DisplayName("A Variable node should throw an UnboundVariableException when evaluated if its " +
            "variable is not in the var map")
    void testEvalUnbound() {

        Expression expr = new Variable("x");
        assertThrows(UnboundVariableException.class, () -> expr.eval(MapVarTable.empty()));

    }


    @Test
    @DisplayName("A Variable node should report that 0 operations are required to evaluate it")
    void testOpCount() {

        // typical case for variable evaluation
        Variable var1 = new Variable("x");
        assertEquals(0, var1.opCount());

        // another typical case for variable evaluation
        Variable var2 = new Variable("y");
        assertEquals(0, var2.opCount());

        // longer variable name
        Variable var3 = new Variable("Long Variable Name");
        assertEquals(0, var3.opCount());

        // empty variable name
        Variable var4 = new Variable(" ");
        assertEquals(0, var4.opCount());

    }


    @Test
    @DisplayName("A Variable node should produce an infix representation with just its name")
    void testInfix() {
        // typical case for variable infix representation
        Variable var1 = new Variable("x");
        assertEquals("x", var1.infixString());

        // another typical case for variable infix representation
        Variable var2 = new Variable("y");
        assertEquals("y", var2.infixString());

        // longer variable name
        Variable var3 = new Variable("Long Variable Name");
        assertEquals("Long Variable Name", var3.infixString());

        // numbers and special characters
        Variable var4 = new Variable("@x_y123");
        assertEquals("@x_y123", var4.infixString());

        // empty variable name
        Variable var5 = new Variable(" ");
        assertEquals(" ", var5.infixString());

    }

    @Test
    @DisplayName("A Variable node should produce an postfix representation with just its name")
    void testPostfix() {
        // typical case for variable infix representation
        Variable var1 = new Variable("x");
        assertEquals("x", var1.postfixString());

        // another typical case for variable infix representation
        Variable var2 = new Variable("y");
        assertEquals("y", var2.postfixString());

        // longer variable name
        Variable var3 = new Variable("Long Variable Name");
        assertEquals("Long Variable Name", var3.postfixString());

        // numbers and special characters
        Variable var4 = new Variable("@x_y123");
        assertEquals("@x_y123", var4.postfixString());

        // empty variable name
        Variable var5 = new Variable(" ");
        assertEquals(" ", var5.postfixString());
    }


    @Test
    @DisplayName("A Variable node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Variable("x");
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("A Variable node should equal another Variable node with the same name")
    void testEqualsTrue() {
        // Force construction of new String objects to detect inadvertent use of `==`
        Expression expr1 = new Variable(new String("x"));
        Expression expr2 = new Variable(new String("x"));
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("A Variable node should not equal another Variable node with a different name")
    void testEqualsFalse() {

        // typical case of two different variable names
        Expression expr1 = new Variable(new String("x"));
        Expression expr2 = new Variable(new String("y"));
        assertFalse(expr1.equals(expr2));

        // abnormal variable names that are still different
        Expression expr3 = new Variable(new String(""));
        Expression expr4 = new Variable(new String(" "));
        assertFalse(expr3.equals(expr4));

        // comparing a Variable node with a random String
        Expression expr5 = new Variable(new String("a"));
        String notExpr6 = new String("a");
        assertFalse(expr5.equals(notExpr6));

        // comparing a non-null Variable node to a null Variable node
        Expression expr7 = new Variable(new String("b"));
        Expression expr8 = null;
        assertFalse(expr7.equals(expr8));

    }


    @Test
    @DisplayName("A Variable node only depends on its name")
    void testDependencies() {
        Expression expr = new Variable("x");
        Set<String> deps = expr.dependencies();
        assertTrue(deps.contains("x"));
        assertEquals(1, deps.size());
    }


    @Test
    @DisplayName("A Variable node should optimize to a Constant if its variable is in the var map")
    void testOptimizeBound() {
        Expression expr = new Variable("x");
        Expression opt = expr.optimize(MapVarTable.of("x", 1.5));
        assertEquals(new Constant(1.5), opt);
    }

    @Test
    @DisplayName("A Variable node should optimize to itself if its variable is not in the var map")
    void testOptimizeUnbound() {

        Expression expr1 = new Variable("x");
        VarTable table1 = new MapVarTable();
        assertEquals(expr1, expr1.optimize(table1));

    }

}

class OperationExpressionTest {

    @Test
    @DisplayName("An Operation node for ADD with two Constant operands should evaluate to their " +
            "sum")
    void testEvalAdd() throws UnboundVariableException {
        Expression expr = new Operation(new Constant(1.5), new Constant(2), Operator.ADD);
        assertEquals(3.5, expr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("An Operation node for ADD with a Variable for an operand should evaluate " +
            "to its operands' sum when the variable is in the var map")
    void testEvalAddBound() throws UnboundVariableException {
        Expression expr1 = new Operation(new Variable("x"), new Variable("y"), Operator.ADD);
        VarTable table1 = new MapVarTable();
        table1.set("x", 2);
        table1.set("y", 5);
        assertEquals(7, expr1.eval(table1));
    }

    @Test
    @DisplayName("An Operation node for ADD with a Variable for an operand should throw an " +
            "UnboundVariableException when evaluated if the variable is not in the var map")
    void testEvalAddUnbound() {
        Expression expr1 = new Operation(new Variable("x"), new Variable("y"), Operator.ADD);
        VarTable table1 = new MapVarTable();
        table1.set("x", 3);

        assertThrows(UnboundVariableException.class, () -> {
            expr1.eval(table1);
        });
    }


    @Test
    @DisplayName("An Operation node with leaf operands should report that 1 operation is " +
            "required to evaluate it")
    void testOpCountLeaves() {
        // Constant (leaf) nodes
        Expression expr1 = new Operation(new Constant(4), new Constant(3), Operator.POW);
        assertEquals(1, expr1.opCount());

        // Variable (leaf) nodes)
        Expression expr2 = new Operation(new Variable("x"), new Variable("y"), Operator.DIVIDE);
        assertEquals(1, expr2.opCount());

        // One Constant and One Variable (leaf) nodes
        Expression expr3 = new Operation(new Variable("x"), new Constant(8), Operator.SUBTRACT);
        assertEquals(1, expr3.opCount());

    }


    @Test
    @DisplayName("An Operation node with an Operation for either or both operands should report " +
            "the correct number of operations to evaluate it")
    void testOpCountRecursive() {
        Expression expr = new Operation(
                new Operation(new Constant(1.5), new Variable("x"), Operator.MULTIPLY),
                new Constant(2.0), Operator.ADD);
        assertEquals(2, expr.opCount());

        expr = new Operation(new Operation(new Constant(1.5), new Variable("x"),
                Operator.MULTIPLY), new Operation(new Constant(1.5), new Variable("x"),
                Operator.DIVIDE), Operator.SUBTRACT);
        assertEquals(3, expr.opCount());
    }


    @Test
    @DisplayName("An Operation node with leaf operands should produce an infix representation " +
            "consisting of its first operand, its operator symbol surrounded by spaces, and " +
            "its second operand, all enclosed in parentheses")
    void testInfixLeaves() {
        // Constant (leaf) nodes
        Expression expr1 = new Operation(new Constant(4), new Constant(3), Operator.POW);
        assertEquals("(4.0 ^ 3.0)", expr1.infixString());

        // Variable (leaf) nodes)
        Expression expr2 = new Operation(new Variable("x"), new Variable("y"), Operator.DIVIDE);
        assertEquals("(x / y)", expr2.infixString());

        // One Constant and One Variable (leaf) nodes
        Expression expr3 = new Operation(new Variable("x"), new Constant(8), Operator.SUBTRACT);
        assertEquals("(x - 8.0)", expr3.infixString());
    }

    @Test
    @DisplayName("An Operation node with an Operation for either operand should produce the " +
            "expected infix representation with parentheses around each operation")
    void testInfixRecursive() {
        Expression expr = new Operation(new Operation(new Constant(1.5), new Variable("x"),
                Operator.MULTIPLY), new Constant(2.0), Operator.ADD);
        assertEquals("((1.5 * x) + 2.0)", expr.infixString());

        expr = new Operation(new Constant(2.0), new Operation(new Constant(1.5), new Variable("x"),
                Operator.DIVIDE), Operator.SUBTRACT);
        assertEquals("(2.0 - (1.5 / x))", expr.infixString());
    }


    @Test
    @DisplayName("An Operation node with leaf operands should produce a postfix representation " +
            "consisting of its first operand, its second operand, and its operator symbol " +
            "separated by spaces")
    void testPostfixLeaves() {
        // Constant (leaf) nodes
        Expression expr1 = new Operation(new Constant(4), new Constant(3), Operator.POW);
        assertEquals("4.0 3.0 ^", expr1.postfixString());

        // Variable (leaf) nodes)
        Expression expr2 = new Operation(new Variable("x"), new Variable("y"), Operator.DIVIDE);
        assertEquals("x y /", expr2.postfixString());

        // One Constant and One Variable (leaf) nodes
        Expression expr3 = new Operation(new Variable("x"), new Constant(8), Operator.SUBTRACT);
        assertEquals("x 8.0 -", expr3.postfixString());
    }

    @Test
    @DisplayName("An Operation node with an Operation for either operand should produce the " +
            "expected postfix representation")
    void testPostfixRecursive() {
        // Nested operations on the left side of the main operation
        Expression expr1 = new Operation(new Operation(new Constant(1.5), new Variable("x"),
                Operator.MULTIPLY), new Constant(2.0), Operator.ADD);
        assertEquals("1.5 x * 2.0 +", expr1.postfixString());

        // Nested operations on the right side of the main operation
        Expression expr2 = new Operation(new Constant(2.0), new Operation(new Constant(1.5), new Variable("x"),
                Operator.DIVIDE), Operator.SUBTRACT);
        assertEquals("2.0 1.5 x / -", expr2.postfixString());

        // Nested operations on both sides of the main operation
        Expression expr3 = new Operation(new Operation(new Constant(1.5), new Variable("y"),
                Operator.ADD), new Operation(new Constant(2.0), new Variable("x"), Operator.MULTIPLY),
                Operator.SUBTRACT);
        assertEquals("1.5 y + 2.0 x * -", expr3.postfixString());
    }


    @Test
    @DisplayName("An Operation node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Operation(new Constant(1.5), new Variable("x"), Operator.ADD);
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("An Operation node should equal another Operation node with the same " +
            "operator and operands")
    void testEqualsTrue() {
        Expression expr1 = new Operation(new Constant(1.5), new Variable("x"), Operator.ADD);
        Expression expr2 = new Operation(new Constant(1.5), new Variable("x"), Operator.ADD);
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Operation node should not equal another Operation node with a different " +
            "operator")
    void testEqualsFalse() {
        Expression expr1 = new Operation(new Constant(11), new Constant(27), Operator.SUBTRACT);
        Expression expr2 = new Operation(new Constant(11), new Constant(27), Operator.ADD);
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("An Operation node depends on the dependencies of both of its operands")
    void testDependencies() {
        Expression expr = new Operation(new Variable("x"), new Variable("y"), Operator.ADD);
        Set<String> deps = expr.dependencies();
        assertTrue(deps.contains("x"));
        assertTrue(deps.contains("y"));
        assertEquals(2, deps.size());
    }


    @Test
    @DisplayName("An Operation node for ADD with two Constant operands should optimize to a " +
            "Constant containing their sum")
    void testOptimizeAdd() {
        Expression expr1 = new Operation(new Constant(1), new Constant(2), Operator.ADD);
        VarTable table1 = new MapVarTable();
        assertEquals(new Constant(3), expr1.optimize(table1));
    }

    @Test
    @DisplayName("An Operation node should optimize to an Operation node if at least one of its "
            + "operands' variable is not in the var map")
    void testOptimizeUnbound() {
        Expression expr = new Operation(new Variable("x"), new Variable("y"), Operator.ADD);

        VarTable table = new MapVarTable();
        table.set("x", 5);

        Expression opt = expr.optimize(table);
        assertInstanceOf(Operation.class, opt);

    }
}

class ApplicationExpressionTest {

    @Test
    @DisplayName("An Application node for SQRT with a Constant argument should evaluate to its " +
            "square root")
    void testEvalSqrt() throws UnboundVariableException {

        Application app1 = new Application(new Constant(25), UnaryFunction.SQRT);
        VarTable table1 = new MapVarTable();
        assertEquals(5, app1.eval(table1));

    }

    @Test
    @DisplayName("An Application node with a Variable for its argument should throw an " +
            "UnboundVariableException when evaluated if the variable is not in the var map")
    void testEvalAbsUnbound() {

        Application app1 = new Application(new Variable("x"), UnaryFunction.ABS);
        VarTable table1 = new MapVarTable();
        assertThrows(UnboundVariableException.class, () -> {
            app1.eval(table1);
        });

    }


    @Test
    @DisplayName("An Application node with a leaf argument should report that 1 operation is " +
            "required to evaluate it")
    void testOpCountLeaf() {

        // Constant (leaf) argument
        Application app1 = new Application(new Constant(25), UnaryFunction.SQRT);
        assertEquals(1, app1.opCount());

        // Variable (leaf) argument
        Application app2 = new Application(new Variable("x"), UnaryFunction.ABS);
        assertEquals(1, app2.opCount());

        // another unary function (leaf) argument
        Application app3 = new Application(new Constant(10), UnaryFunction.LOG);
        assertEquals(1, app3.opCount());

    }


    @Test
    @DisplayName("An Application node with non-leaf expressions for its argument should report " +
            "the correct number of operations to evaluate it")
    void testOpCountRecursive() {
        Expression expr = new Application(new Operation(new Constant(1.5), new Variable("x"),
                Operator.MULTIPLY), UnaryFunction.SQRT);
        assertEquals(2, expr.opCount());
    }


    @Test
    @DisplayName(
            "An Application node with a leaf argument should produce an infix representation " +
                    "consisting of its name, followed by the argument enclosed in parentheses.")
    void testInfixLeaves() {

        // Constant (leaf) argument
        Application app1 = new Application(new Constant(25), UnaryFunction.SQRT);
        assertEquals("sqrt(25.0)", app1.infixString());

        // Variable (leaf) argument
        Application app2 = new Application(new Variable("x"), UnaryFunction.ABS);
        assertEquals("abs(x)", app2.infixString());

        // another unary function (leaf) argument
        Application app3 = new Application(new Constant(10), UnaryFunction.LOG);
        assertEquals("log(10.0)", app3.infixString());

    }

    @Test
    @DisplayName("An Application node with an Operation for its argument should produce the " +
            "expected infix representation with redundant parentheses around the argument")
    void testInfixRecursive() {
        Expression expr = new Application(new Operation(new Constant(1.5), new Variable("x"),
                Operator.MULTIPLY), UnaryFunction.ABS);
        assertEquals("abs((1.5 * x))", expr.infixString());
    }


    @Test
    @DisplayName("An Application node with a leaf argument should produce a postfix " +
            "representation consisting of its argument, followed by a space, followed by its " +
            "function's name appended with parentheses")
    void testPostfixLeaves() {

        // Constant (leaf) argument
        Application app1 = new Application(new Constant(36), UnaryFunction.SQRT);
        assertEquals("36.0 sqrt()", app1.postfixString());

        // Variable (leaf) argument
        Application app2 = new Application(new Variable("y"), UnaryFunction.ABS);
        assertEquals("y abs()", app2.postfixString());

        // another unary function (leaf) argument
        Application app3 = new Application(new Constant(100), UnaryFunction.LOG);
        assertEquals("100.0 log()", app3.postfixString());
    }

    @Test
    @DisplayName("An Application node with an Operation for its argument should produce the " +
            "expected postfix representation")
    void testPostfixRecursive() {
        Application app1 = new Application(new Operation(new Variable("y"), new Constant(19.2),
                Operator.ADD), UnaryFunction.SQRT);
        assertEquals("y 19.2 + sqrt()", app1.postfixString());
    }

    @Test
    @DisplayName("An Application node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Application(new Constant(4.0), UnaryFunction.SQRT);
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("An Application node should equal another Application node with the same " +
            "function and argument")
    void testEqualsTrue() {
        Expression expr1 = new Application(new Constant(4.0), UnaryFunction.SQRT);
        Expression expr2 = new Application(new Constant(4.0), UnaryFunction.SQRT);
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Application node should not equal another Application node with a different " +
            "argument")
    void testEqualsFalseArg() {
        Expression expr1 = new Application(new Variable("x"), UnaryFunction.ABS);
        Expression expr2 = new Application(new Variable("y"), UnaryFunction.ABS);
        assertFalse(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Application node should not equal another Application node with a different " +
            "function")
    void testEqualsFalseFunc() {
        Expression expr1 = new Application(new Constant(4.0), UnaryFunction.SQRT);
        Expression expr2 = new Application(new Constant(4.0), UnaryFunction.ABS);
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("An Application node for SQRT with a Constant argument should optimize to a " +
            "Constant containing its square root")
    void testOptimizeConstant() {
        Expression expr1 = new Application(new Constant(50), UnaryFunction.SQRT);
        VarTable table1 = new MapVarTable();
        assertEquals(new Constant(Math.sqrt(50)), expr1.optimize(table1));
    }


    @Test
    @DisplayName("An Application node has the same dependencies as its argument")
    void testDependencies() {
        Expression arg = new Variable("x");
        Expression expr = new Application(arg, UnaryFunction.SQRT);
        Set<String> argDeps = arg.dependencies();
        Set<String> exprDeps = expr.dependencies();
        assertEquals(argDeps, exprDeps);
    }


    @Test
    @DisplayName("An Application node with an argument depending on a variable should optimize " +
            "to an Application node if the variable is unbound")
    void testOptimizeUnbound() {
        Expression expr = new Application(new Variable("x"), UnaryFunction.SQRT);
        Expression opt = expr.optimize(MapVarTable.empty());
        assertInstanceOf(Application.class, opt);
    }

    // Known missing coverage:
    // * When optimizing an Application that depends on an unbound variable, is the result's
    //   argument node optimized?
    @Test
    @DisplayName("When optimizing an Application that depends on an unbound variable, " +
            "the result's argument node should be optimized")
    void testOptimizeUnboundArgument() {

        Expression expr1 = new Application(
                new Operation(new Variable("x"), new Constant(5.0), Operator.ADD),
                UnaryFunction.SQRT
        );
        Expression opt = expr1.optimize(MapVarTable.empty());

        assertInstanceOf(Application.class, opt);

        // Verify the optimized state indirectly using the string representation
        assertEquals("sqrt((x + 5.0))", opt.infixString());
        assertEquals("x 5.0 + sqrt()", opt.postfixString());

    }

}