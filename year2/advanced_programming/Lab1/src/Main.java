import domain.NumarComplex;
import repo.ComplexExpression;
import service.ExpressionParser;

public class Main {

    public static void main(String[] args) {

        ExpressionParser parser = new ExpressionParser(args);
        ComplexExpression expr = parser.parseExpression();
        if (expr != null) {
            System.out.println(expr.toString()+" = ");
            NumarComplex nr = expr.execute();
            System.out.println(nr.toString());
        } else
            System.out.println("Expresie invalida!");

    }
}


