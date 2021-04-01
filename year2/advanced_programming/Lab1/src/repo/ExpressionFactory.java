package repo;

import domain.NumarComplex;
import domain.Operation;

public class ExpressionFactory {

    private static ExpressionFactory instance = null;

    private ExpressionFactory() { }

    public static ExpressionFactory getInstance() {
        if (instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    public ComplexExpression createExpression(Operation operation, NumarComplex[] args) {
        ComplexExpression expr;
        switch (operation) {
            case SCADERE -> expr = new SubtractExpression(operation, args);
            case INMULTIRE -> expr = new MultiplyExpression(operation, args);
            case IMPARTIRE -> expr = new DivisionExpression(operation, args);
            default -> expr = new AdditionExpression(operation, args);
        }
        return expr;
    }

}
