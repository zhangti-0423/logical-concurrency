package com.johnny.logic.demo;

import com.johnny.logic.common.FactKey;
import com.johnny.logic.concurrency.Expression;
import com.johnny.logic.concurrency.LogicalExpressionParser;
import com.johnny.logic.facts.ExpressionFacts;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class DemoMulti {

    public static void main(String[] args) {
        String input = "A && ( B || C ) && !D";

        Map<String, Predicate<ExpressionFacts>> predicateMap = new HashMap<>();
        predicateMap.put("A", new ConditionA("factA"));
        predicateMap.put("B", new ConditionB());
        predicateMap.put("C", new ConditionC());
        predicateMap.put("D", new ConditionD());

        Expression expr = LogicalExpressionParser.parse(input, predicateMap);

        ExpressionFacts facts = new ExpressionFacts();
        facts.put(FactKey.TIME_OUT, 10000L);
        System.out.println("公共入参：" + facts);

        ExecutorService executor = Executors.newCachedThreadPool();
        boolean result = expr.evaluate(facts, executor);
        System.out.println("表达式结果: " + result);
    }
}

class ConditionA implements Predicate<ExpressionFacts> {

    /**
     * 逻辑A用到的属性
     */
    private String factA;

    public ConditionA(String factA) {
        this.factA = factA;
    }

    @Override
    public boolean test(ExpressionFacts expressionFacts) {
        try {
            System.out.println("线程A开始" + Thread.currentThread() + " 线程A独有属性：" + factA);
            TimeUnit.SECONDS.sleep(1);
            System.out.println("线程A结束" + Thread.currentThread());
            return true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

class ConditionB implements Predicate<ExpressionFacts> {

    @Override
    public boolean test(ExpressionFacts expressionFacts) {
        try {
            System.out.println("线程B开始" + Thread.currentThread());
            TimeUnit.SECONDS.sleep(3);
            System.out.println("线程B结束" + Thread.currentThread());
            return false;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

class ConditionC implements Predicate<ExpressionFacts> {

    @Override
    public boolean test(ExpressionFacts expressionFacts) {
        try {
            System.out.println("线程C开始" + Thread.currentThread());
            TimeUnit.SECONDS.sleep(3);
            System.out.println("线程C结束" + Thread.currentThread());
            return false;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

class ConditionD implements Predicate<ExpressionFacts> {

    @Override
    public boolean test(ExpressionFacts expressionFacts) {
        try {
            System.out.println("线程D开始" + Thread.currentThread());
            TimeUnit.SECONDS.sleep(1);
            System.out.println("线程D结束" + Thread.currentThread());
            return true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}