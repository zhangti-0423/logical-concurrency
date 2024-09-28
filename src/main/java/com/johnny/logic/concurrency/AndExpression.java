package com.johnny.logic.concurrency;

import com.johnny.logic.common.FactKey;
import com.johnny.logic.facts.ExpressionFacts;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

// 多条件逻辑“与”表达式
public class AndExpression extends Expression {

    /**
     * 加入逻辑“与”运算的子表达式
     */
    private final List<Expression> expressions;

    public AndExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    /**
     * 实现 A && B && C && ... 快速失败
     *
     * @param facts    方法入参
     * @param executor 自定义线程池
     * @return A && B && C && ... 的最终结果
     */
    @Override
    public boolean evaluate(ExpressionFacts facts, Executor executor) {
        // result 为该方法返回的最终结果，使用 Atomic 以保证变量的原子性操作
        AtomicBoolean result = new AtomicBoolean(true);
        // 终止线程，用来根据不同任务的执行结果决定是否要提前返回
        CompletableFuture<Void> terminationFuture = new CompletableFuture<>();

        CompletableFuture.anyOf(terminationFuture, CompletableFuture.allOf(expressions.stream()
                .map(expression -> CompletableFuture.runAsync(() -> {
                            if (!result.get()) {
                                return; // 如果已经有规则不符合，直接返回
                            }
                            boolean ruleResult = expression.evaluate(facts, executor); // 不同任务执行的结果
                            if (!ruleResult && result.compareAndSet(true, false)) {
                                terminationFuture.complete(null); // 完成终止线程，提前返回多线程结果
                            }
                        }, executor)
                        .orTimeout(facts.get(FactKey.TIME_OUT), TimeUnit.MILLISECONDS) // JDK 9 引入的异步超时处理方法
                        .exceptionally(ex -> {
                            // 捕获异常，将异常结果算作 false，触发终止线程，提前返回
                            if (result.compareAndSet(true, false)) {
                                terminationFuture.complete(null);
                            }
                            return null;
                        })).toArray(CompletableFuture[]::new))).join();

        return result.get();
    }
}
