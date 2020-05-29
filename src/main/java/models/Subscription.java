package models;

import java.io.Serializable;

public class Subscription implements Serializable
{
    public Company company;
    public Date date;
    public Drop drop;
    public Value value;
    public Variation variation;
}
