package com.github.xuejike.query.core.po;

/**
 * @author xuejike
 * @date 2020/12/18
 */
public class BetweenObj {
    private Object first;
    private Object second;


    public BetweenObj() {
    }
    public BetweenObj(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() {
        return first;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public Object getSecond() {
        return second;
    }

    public void setSecond(Object second) {
        this.second = second;
    }
}
