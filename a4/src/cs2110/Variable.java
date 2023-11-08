package cs2110;

import java.util.Set;

public class Variable implements Expression {

    /**
     * Name of variable in this expression.
     */
    private final String varName;

    /**
     * Create a node representing the variable `varName`.
     */
    public Variable(String varName) {
        assert varName != null;
        this.varName = varName;
    }

    /**
     * Return this node's value for the variable name. Throws UnboundVariableExpression if the
     * variable's value is not in `vars`.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        assert vars != null;
        if(!vars.contains(varName)) {
            throw new UnboundVariableException(varName);
        }
        return vars.get(varName);
    }

    /**
     * No operations are required to evaluate a variable's value.
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * Return the infix representation of this node's value.
     */
    @Override
    public String infixString() {
        return varName;
    }

    /**
     * Return the postfix representation of this node's value.
     */
    @Override
    public String postfixString() {
        return varName;
    }

    /**
     * Return a Constant that represents the variable replaced by the constant equal to its value
     * in `vars`.
     */
    @Override
    public Expression optimize(VarTable vars) {
        assert vars != null;
        try {
            return new Constant(vars.get(varName));
        }
        catch(UnboundVariableException exception) {
            return this;
        }
    }

    /**
     * Return a Set that contains the variable name.
     */
    @Override
    public Set<String> dependencies() {
        return Set.of(varName);
    }

    /**
     * Return whether `other` is a Variable of the same class with the same name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Variable var = (Variable) other;
        return varName.equals(var.varName);
    }

}
