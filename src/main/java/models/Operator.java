package models;

public enum Operator
{
    Greater(">"),
    GreaterOrEqual(">="),
    Lower("<"),
    LowerOrEqual("<="),
    Equal("=="),
    NotEqual("!=")
    ;

    private final String op;

    Operator(final String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return op;
    }
}
