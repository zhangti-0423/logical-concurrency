package com.johnny.logic.concurrency;

import com.johnny.logic.facts.ExpressionFacts;

import java.util.concurrent.Executor;

// 逻辑“非”操作
public class NotExpression extends Expression {

    /**
     * 参与逻辑“非”运算的条件
     */
    private final Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate(ExpressionFacts facts, Executor executor) {
        return !expression.evaluate(facts, executor);
    }
}
