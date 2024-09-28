package com.johnny.logic.concurrency;

import com.johnny.logic.facts.ExpressionFacts;

import java.util.concurrent.Executor;

// 逻辑运算表达式抽象类
public abstract class Expression {

    /**
     * 执行逻辑表达式
     *
     * @param facts    方法入参
     * @param executor 自定义线程池
     * @return 运算结果：true 或者 false
     */
    public abstract boolean evaluate(ExpressionFacts facts, Executor executor);
}
