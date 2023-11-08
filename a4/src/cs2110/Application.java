package cs2110;

import java.util.Set;

public class Application implements Expression {

    /**
     * A non-empty subexpression in this expression.
     */
    private final Expression argument;

    /**
     * A function in this expression.
     */
    private final UnaryFunction function;

    /**
     * Create a node representing a subexpression `argument` and function `function`.
     */
    public Application(Expression argument, UnaryFunction function){
        assert argument != null;
        assert function != null;
        this.argument = argument;
        this.function = function;
    }

    /**
     * Return the result of applying the subexpression `argument` to `function`.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        assert vars != null;
        return function.apply(argument.eval(vars));
    }

    /**
     * Return the number of operations and unary functions contained in this expression. Calling
     * the function counts as one operation.
     */
    @Override
    public int opCount() {
        return 1 + argument.opCount();
    }

    /**
     * Return the infix representation of `argument` and `function`.
     */
    @Override
    public String infixString() {
        return function.name() + "(" + argument.infixString() + ")";
    }

    /**
     * Return the postfix representation of `argument` and `function`.
     */
    @Override
    public String postfixString() {
        return argument.postfixString() + " " + function.name() + "()";
    }

    /**
     * Return a Constant that replaces all variables by the constant equal to its value in
     * `vars`. Return an Application if variables can't be evaluated to a Constant value.
     * A function and a Constant are already fully optimized.
     */
    @Override
    public Expression optimize(VarTable vars) {
        assert vars != null;
        try{
            return new Constant(function.apply(argument.optimize(vars).eval(vars)));
        } catch(UnboundVariableException exception){
            return new Application(argument.optimize(vars), function);
        }
    }

    /**
     * Return a Set that contains all the variable names of this expression.
     */
    @Override
    public Set<String> dependencies() {
        return argument.dependencies();
    }

    /**
     * Return whether `other` is an Application of the same class with the same `argument` and
     * `function`.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        Application app = (Application) other;
        return argument.equals(app.argument) && function.equals(app.function);
    }

}