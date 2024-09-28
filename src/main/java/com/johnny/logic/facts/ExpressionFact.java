package com.johnny.logic.facts;

import java.util.Objects;

public class ExpressionFact<T> {

    private final String name;
    private final T value;

    public ExpressionFact(String name, T value) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(value, "value must not be null");
        this.name = name;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionFact<?> fact = (ExpressionFact<?>) o;
        return name.equals(fact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
