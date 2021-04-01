package repo;

import domain.NumarComplex;
import domain.Operation;

import java.util.Arrays;

public abstract class ComplexExpression {

    Operation operation;
    NumarComplex[] args = new NumarComplex[10];

    ComplexExpression(Operation op, NumarComplex[] args) {
        this.operation = op;
        this.args = args;
    }

    public abstract NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2);

    public NumarComplex execute() {
        NumarComplex result = executeOneOperation(args[0], args[1]);
        int i = 2;
        while (i < args.length) {
            result = executeOneOperation(result, args[i]);
            i++;
        }
        return result;
    }

    @Override
    public abstract String toString();

}
