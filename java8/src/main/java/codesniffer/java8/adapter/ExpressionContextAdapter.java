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

import codesniffer.*;
import codesniffer.api.expr.*;
import codesniffer.java8.*;

public class ExpressionContextAdapter implements Adapter<Expression, Java8Parser.ExpressionContext> {
    public Expression adapt(Java8Parser.ExpressionContext context, AdapterParameters adapterParameters) {
        Expression expression = null;

        if (context.methodReference() != null) {
            expression = Adapters.getMethodReferenceContextAdapter().adapt(context.methodReference(), adapterParameters);
        }

        if (context.assignmentOperator() != null) {
            // This is an assignment expression

            /*
                :   EQ          {$assignmentType = 1;}
                |   PLUSEQ      {$assignmentType = 2;}
                |   SUBEQ       {$assignmentType = 3;}
                |   STAREQ      {$assignmentType = 4;}
                |   SLASHEQ     {$assignmentType = 5;}
                |   AMPEQ       {$assignmentType = 6;}
                |   BAREQ       {$assignmentType = 7;}
                |   CARETEQ     {$assignmentType = 8;}
                |   PERCENTEQ   {$assignmentType = 9;}
                |   LT LT EQ    {$assignmentType = 10;}
                |   GT GT GT EQ {$assignmentType = 11;}
                |   GT GT EQ    {$assignmentType = 12;}
             */
            AssignExpr assignExpr = new AssignExpr();

            AssignExpr.Operator operator = null;
            switch (context.assignmentOperator().assignmentType) {
                case 1:
                    operator = AssignExpr.Operator.assign;
                    break;
                case 2:
                    operator = AssignExpr.Operator.plus;
                    break;
                case 3:
                    operator = AssignExpr.Operator.minus;
                    break;
                case 4:
                    operator = AssignExpr.Operator.star;
                    break;
                case 5:
                    operator = AssignExpr.Operator.slash;
                    break;
                case 6:
                    operator = AssignExpr.Operator.and;
                    break;
                case 7:
                    operator = AssignExpr.Operator.or;
                    break;
                case 8:
                    operator = AssignExpr.Operator.xor;
                    break;
                case 9:
                    operator = AssignExpr.Operator.rem;
                    break;
                case 10:
                    operator = AssignExpr.Operator.lShift;
                    break;
                case 11:
                    operator = AssignExpr.Operator.rUnsignedShift;
                    break;
                case 12:
                    operator = AssignExpr.Operator.rSignedShift;
                    break;
            }
            assignExpr.setOperator(operator);
            assignExpr.setValue(Adapters.getExpressionContextAdapter().adapt(context.expression(), adapterParameters));
            assignExpr.setTarget(expression);

            return assignExpr;
        }

        if (expression == null) {
            throw new ParseException("Unknown expression type");
        } else {
            return expression;
        }
    }
}
