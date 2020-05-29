package spouts;


import models.Publication;
import org.apache.storm.tuple.Fields;

import java.util.List;

public class StockSpout extends CycleSpout<Publication> {
    public StockSpout(List<Publication> publications) {
        super(publications, new Fields("stock"));
    }
}