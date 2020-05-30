package spouts;

import models.Subscription;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

import static utils.ComponentIds.YAHOO_APP_ID;

public class YahooInterestSpout extends CycleSpout<Subscription>
{
    private int yahooAppTask;

    public YahooInterestSpout(List<Subscription> subscriptions)
    {
        super(subscriptions, new Fields("subscription"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector)
    {
        super.open(conf, context, collector);
        this.yahooAppTask = context.getComponentTasks(YAHOO_APP_ID).get(0);
    }

    @Override
    protected void emit(Values values) {
        this.collector.emitDirect(yahooAppTask, "subs", values);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declareStream("subs", new Fields("subscription"));
    }
}
