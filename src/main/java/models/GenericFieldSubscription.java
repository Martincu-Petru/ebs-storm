package models;

import java.io.Serializable;

public class GenericFieldSubscription<X> implements Serializable {
    public X value;
    public Operator operator;

    @Override
    public String toString() {
        return  (X) getClass().toString() + "{" +
                "value: " + value +
                ", operator: " + operator +
                '}';
    }
}
