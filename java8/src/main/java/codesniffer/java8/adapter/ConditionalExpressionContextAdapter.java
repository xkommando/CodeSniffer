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

public class ConditionalExpressionContextAdapter implements Adapter<Expression, Java8Parser.ConditionalExpressionContext> {
    public Expression adapt(Java8Parser.ConditionalExpressionContext context, AdapterParameters adapterParameters) {
        Expression expression = Adapters.getConditionalOrExpressionContextAdapter().adapt(context.conditionalOrExpression(), adapterParameters);
        AdapterUtil.setComments(expression, context.expression(1), adapterParameters);
        AdapterUtil.setPosition(expression, context);

        if (context.QUES() != null) {
            Expression thenExpression = Adapters.getExpressionContextAdapter().adapt(context.expression(0), adapterParameters);
            Expression elseExpression = Adapters.getExpressionContextAdapter().adapt(context.expression(1), adapterParameters);

            ConditionalExpr conditionalExpr = new ConditionalExpr();
            conditionalExpr.setCondition(expression);
            conditionalExpr.setThenExpr(thenExpression);
            conditionalExpr.setElseExpr(elseExpression);

            return conditionalExpr;
        } else {
            return expression;
        }
    }
}
