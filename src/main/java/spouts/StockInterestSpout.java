package spouts;


import models.Subscription;
import org.apache.storm.tuple.Fields;

import java.util.List;

public class StockInterestSpout extends CycleSpout<Subscription> {
    public StockInterestSpout(List<Subscription> subscriptions) {
        super(subscriptions, new Fields("subscription"));
    }

    @Override
    public void nextTuple() {
        super.nextTuple();
        // emit subscriptions with a lower frequency than publications
        try {
            Thread.sleep(99);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}