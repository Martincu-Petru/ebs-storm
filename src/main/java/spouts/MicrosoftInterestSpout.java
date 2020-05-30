package spouts;

import models.Subscription;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

import static utils.ComponentIds.MICROSOFT_APP_ID;

public class MicrosoftInterestSpout extends CycleSpout<Subscription>
{

    private int microsoftAppTask;

    public MicrosoftInterestSpout(List<Subscription> subscriptions)
    {
        super(subscriptions, new Fields("subscription"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector)
    {
        super.open(conf, context, collector);
        this.microsoftAppTask = context.getComponentTasks(MICROSOFT_APP_ID).get(0);
    }

    @Override
    protected void emit(Values values) {
        this.collector.emitDirect(microsoftAppTask, "subs", values);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declareStream("subs", new Fields("subscription"));
    }
}
