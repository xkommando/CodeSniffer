/*
 * Copyright (C) 2015 Julio Vilmar Gesser and Mike DeHaan
 *
 * This file is part of antlr-java-parser.
 *
 * antlr-java-parser is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * antlr-java-parser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with antlr-java-parser.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package codesniffer.java8.adapter;

import codesniffer.api.expr.*;
import codesniffer.java8.*;

import java.util.*;

public class RelationalExpressionContextAdapter implements Adapter<Expression, Java8Parser.RelationalExpressionContext> {
    public Expression adapt(Java8Parser.RelationalExpressionContext context, AdapterParameters adapterParameters) {

        List<Java8Parser.ShiftExpressionContext> contextList = context.shiftExpression();
        Expression expression = Adapters.getShiftExpressionContextAdapter().adapt(contextList.get(0), adapterParameters);

        // This expression represents more than one consecutive OR expression
        if (contextList.size() > 1) {
            BinaryExpr root = new BinaryExpr();

            root.setLeft(expression);
            root.setOperator(getOperator(context.relationalOp(0)));
            BinaryExpr currentExpression = root;

            for (int i = 1; i < contextList.size(); i++) {
                currentExpression.setRight(Adapters.getShiftExpressionContextAdapter().adapt(contextList.get(i), adapterParameters));

                // On the last one, do not create a tail.
                if (i < contextList.size() - 1) {
                    BinaryExpr binaryExpr = new BinaryExpr();
                    binaryExpr.setLeft(currentExpression);
                    binaryExpr.setOperator(getOperator(context.relationalOp(i)));
                    currentExpression = binaryExpr;
                }
            }

            return currentExpression;
        }

        return expression;
    }

    private BinaryExpr.Operator getOperator(Java8Parser.RelationalOpContext relationalOpContext) {
        switch (relationalOpContext.operatorType) {
            case 1:
                return BinaryExpr.Operator.lessEquals;
            case 2:
                return BinaryExpr.Operator.greaterEquals;
            case 3:
                return BinaryExpr.Operator.less;
            case 4:
                return BinaryExpr.Operator.greater;
        }

        return null;
    }
}
