package cs2110;

import java.util.HashSet;
import java.util.Set;

public class Operation implements Expression {

    /**
     * The left operand in this expression.
     */
    private final Expression leftOperand;

    /**
     * The right operand in this expression.
     */
    private final Expression rightOperand;

    /**
     * The binary operator (addition (+), subtraction (-), multiplication (*), division (/), and
     * exponentiation (^)) for the `leftOperand` and `rightOperand`.
     */
    private final Operator binaryOp;

    public Operation(Expression leftOperand, Expression rightOperand, Operator binaryOp) {
        assert leftOperand != null;
        assert rightOperand != null;
        assert binaryOp != null;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.binaryOp = binaryOp;
    }

    /**
     * Return the result of combining the values of the `leftOperand` and `rightOperand` with the
     * binary operator `binaryOp`.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        assert vars != null;
        return binaryOp.operate(leftOperand.eval(vars), rightOperand.eval(vars));
    }

    /**
     * Return the number of operations and unary functions contained in this expression.
     */
    @Override
    public int opCount() {
        return 1 + leftOperand.opCount() + rightOperand.opCount();
    }

    /**
     * Return the infix representation of `leftOperand`, `rightOperand`, and `binaryOp`.
     */
    @Override
    public String infixString() {
        return "(" + leftOperand.infixString() + " " + binaryOp.symbol() + " "
                + rightOperand.infixString() + ")";
    }

    /**
     * Return the postfix representation of `leftOperand`, `rightOperand`, and `binaryOp`.
     */
    @Override
    public String postfixString() {
        return leftOperand.postfixString() + " " + rightOperand.postfixString() + " "
                + binaryOp.symbol();
    }

    /**
     * Return a Constant that replaces all variables with constants by the constant equal to its
     * value in `vars`. Return an Operation if variables can't be evaluated to a Constant value.
     * Functions and Constants are already fully optimized.
     */
    @Override
    public Expression optimize(VarTable vars) {
        assert vars != null;
        try {
            return new Constant(binaryOp.operate(leftOperand.eval(vars), rightOperand.eval(vars)));
        } catch (UnboundVariableException exception) {
            return new Operation(leftOperand.optimize(vars), rightOperand.optimize(vars), binaryOp);
        }

    }

    /**
     * Return a Set that contains all the variable names of this expression.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> variableNames = new HashSet<>();
        variableNames.addAll(leftOperand.dependencies());
        variableNames.addAll(rightOperand.dependencies());
        return variableNames;
    }

    /**
     * Return whether `other` is an Operation of the same class with the same operands and
     * operator.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Operation op = (Operation) other;
        return leftOperand.equals(op.leftOperand) && rightOperand.equals(op.rightOperand)
                && binaryOp.equals(op.binaryOp) && binaryOp.symbol().equals(op.binaryOp.symbol());
    }

}