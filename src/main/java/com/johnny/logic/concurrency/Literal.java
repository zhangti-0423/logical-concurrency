package com.johnny.logic.concurrency;

import com.johnny.logic.facts.ExpressionFacts;

import java.util.concurrent.Executor;
import java.util.function.Predicate;

// 表示逻辑运算表达式的一个条件
public class Literal extends Expression {

    /**
     * 逻辑表达式中每个条件的具体实现
     */
    private final Predicate<ExpressionFacts> predicate;

    public Literal(Predicate<ExpressionFacts> predicate) {
        this.predicate = predicate;
    }

    /**
     * 获取该条件的处理结果
     *
     * @param facts    方法入参
     * @param executor 自定义线程池
     * @return 处理结果：true 或者 false
     */
    @Override
    public boolean evaluate(ExpressionFacts facts, Executor executor) {
        return predicate.test(facts);
    }
}
