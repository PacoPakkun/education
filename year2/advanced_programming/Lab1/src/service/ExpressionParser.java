package service;

import domain.NumarComplex;
import domain.Operation;
import repo.ComplexExpression;
import repo.ExpressionFactory;

public class ExpressionParser {

    private String[] args;

    public ExpressionParser(String[] args) {
        this.args = args;
    }

    private boolean isValid() {
        if (args.length < 3)
            return false;
        if (args.length % 2 != 1)
            return false;
        if (!args[1].matches("[+\\-*/]"))
            return false;
        String op = args[1];
        int i = 0;
        while (i < args.length) {
            if (i % 2 == 0) {
                if (!args[i].matches("^(?=[iI.\\d+-])([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?![iI.\\d]))?([+-]?(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)?[iI])?$"))
                    return false;
            } else {
                if (!args[i].equals(op))
                    return false;
            }
            i++;
        }
        return true;
    }

    private NumarComplex[] buildArgs() {
        NumarComplex[] nrs = new NumarComplex[args.length / 2 + 1];
        int i = 0;
        while (i < args.length) {
            String[] s = args[i].split("(?=[\\-+])");
            String re,im;
            if (s.length == 2) {
                re = s[0];
                im = s[1].substring(0,s[1].length()-1);
            } else {
                re = s[1];
                im = s[2].substring(0,s[1].length()-1);
            }
            nrs[i / 2] = new NumarComplex(Float.parseFloat(re), Float.parseFloat(im));
            i += 2;
        }
        return nrs;
    }

    public ComplexExpression parseExpression() {
        if (isValid()) {
            Operation operation;
            switch (args[1]) {
                case "-" -> operation = Operation.SCADERE;
                case "*" -> operation = Operation.INMULTIRE;
                case "/" -> operation = Operation.IMPARTIRE;
                default -> operation = Operation.ADUNARE;
            }
            NumarComplex[] nrs = buildArgs();
            ExpressionFactory factory = ExpressionFactory.getInstance();
            return factory.createExpression(operation, nrs);
        }
        return null;
    }
}
