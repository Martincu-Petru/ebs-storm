package models;

import java.io.Serializable;
import java.util.Date;

public class Publication implements Serializable
{
    public String company;
    public Double value;
    public Double drop;
    public Double variation;
    public Date date;

    @Override
    public String toString() {
        return "Publication{" +
                "company='" + company + '\'' +
                ", value=" + value +
                ", drop=" + drop +
                ", variation=" + variation +
                ", date=" + date +
                '}';
    }
}
