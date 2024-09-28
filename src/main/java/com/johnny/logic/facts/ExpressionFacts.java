package com.johnny.logic.facts;

import java.util.*;

public class ExpressionFacts implements Iterable<ExpressionFact<?>> {

    private final Set<ExpressionFact<?>> facts = new HashSet<>();

    public <T> void put(String name, T value) {
        Objects.requireNonNull(name, "fact name must not be null");
        Objects.requireNonNull(value, "fact value must not be null");
        ExpressionFact<?> retrievedFact = getFact(name);
        if (retrievedFact != null) {
            remove(retrievedFact);
        }
        add(new ExpressionFact<>(name, value));
    }

    public <T> void add(ExpressionFact<T> fact) {
        Objects.requireNonNull(fact, "fact must not be null");
        ExpressionFact<?> retrievedFact = getFact(fact.getName());
        if (retrievedFact != null) {
            remove(retrievedFact);
        }
        facts.add(fact);
    }

    public void remove(String factName) {
        Objects.requireNonNull(factName, "fact name must not be null");
        ExpressionFact<?> fact = getFact(factName);
        if (fact != null) {
            remove(fact);
        }
    }

    public <T> void remove(ExpressionFact<T> fact) {
        Objects.requireNonNull(fact, "fact must not be null");
        facts.remove(fact);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String factName) {
        Objects.requireNonNull(factName, "fact name must not be null");
        ExpressionFact<?> fact = getFact(factName);
        if (fact != null) {
            return (T) fact.getValue();
        }
        return null;
    }

    public ExpressionFact<?> getFact(String factName) {
        Objects.requireNonNull(factName, "fact name must not be null");
        return facts.stream()
                .filter(fact -> fact.getName().equals(factName))
                .findFirst()
                .orElse(null);
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        for (ExpressionFact<?> fact : facts) {
            map.put(fact.getName(), fact.getValue());
        }
        return map;
    }

    @Override
    public Iterator<ExpressionFact<?>> iterator() {
        return facts.iterator();
    }

    public void clear() {
        facts.clear();
    }

    @Override
    public String toString() {
        Iterator<ExpressionFact<?>> iterator = facts.iterator();
        StringBuilder stringBuilder = new StringBuilder("[");
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().toString());
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }


}
