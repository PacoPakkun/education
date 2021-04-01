package repo;

import domain.NumarComplex;
import domain.Operation;

public class MultiplyExpression extends ComplexExpression {

    MultiplyExpression(Operation op, NumarComplex[] args) {
        super(op, args);
    }

    @Override
    public NumarComplex executeOneOperation(NumarComplex nr1, NumarComplex nr2) {
        return nr1.inmultire(nr2);
    }

    @Override
    public String toString() {
        String s = "" + args[0];
        String op = " * ";
        int i = 1;
        while (i < args.length) {
            s += op + args[i];
            i++;
        }
        return s;
    }
}
