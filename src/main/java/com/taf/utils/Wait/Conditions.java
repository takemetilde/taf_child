package com.taf.utils.Wait;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by AF41814 on 7/25/2017.
 */
public class Conditions implements List<Condition> {
    private List<Condition> conditionList;
    public Wait.ConditionChainType chainType;

    public Conditions(Wait.ConditionChainType conditionChainType) {
        conditionList = new ArrayList<>();
        chainType = conditionChainType;
    }

    public Conditions(List<Condition> conditions, Wait.ConditionChainType conditionChainType) {
        conditionList = conditions;
        chainType = conditionChainType;
    }

    @Override
    public int size() {
        return conditionList.size();
    }

    @Override
    public boolean isEmpty() {
        return conditionList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return conditionList.contains(o);
    }

    @NotNull
    @Override
    public Iterator<Condition> iterator() {
        return conditionList.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return conditionList.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return conditionList.toArray(a);
    }

    @Override
    public boolean add(Condition condition) {
        return conditionList.add(condition);
    }

    @Override
    public boolean remove(Object o) {
        return conditionList.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return conditionList.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends Condition> c) {
        return conditionList.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends Condition> c) {
        return conditionList.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return conditionList.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return conditionList.retainAll(c);
    }

    @Override
    public void clear() {
        conditionList.clear();
    }

    @Override
    public Condition get(int index) {
        return conditionList.get(index);
    }

    @Override
    public Condition set(int index, Condition element) {
        return conditionList.set(index, element);
    }

    @Override
    public void add(int index, Condition element) {
        conditionList.add(index, element);
    }

    @Override
    public Condition remove(int index) {
        return conditionList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return conditionList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return conditionList.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<Condition> listIterator() {
        return conditionList.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<Condition> listIterator(int index) {
        return conditionList.listIterator(index);
    }

    @NotNull
    @Override
    public List<Condition> subList(int fromIndex, int toIndex) {
        return conditionList.subList(fromIndex, toIndex);
    }
}
