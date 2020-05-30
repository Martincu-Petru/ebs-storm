package bolts;

import models.Publication;
import models.Subscription;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class FilterBolt extends BaseRichBolt {
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    private void handleSubscription(Tuple tuple) {
        Subscription sub = (Subscription)tuple.getValueByField("subscription");
        System.out.println(sub);
        // TODO: route subscription to other nodes
    }

    private void handlePublication(Tuple tuple) {
        Publication pub = (Publication)tuple.getValueByField("stock");
        System.out.println(pub);
        // TODO: route publication to subscriptions stored in routing table
    }
    @Override
    public void execute(Tuple tuple) {
        String tupleSource = tuple.getSourceStreamId();
        if (tupleSource.equals("subs")) {
            this.handleSubscription(tuple);
        } else {
            this.handlePublication(tuple);
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("filters", true, new Fields("subscription"));
        declarer.declareStream("pub1", true, new Fields("stock"));
        declarer.declareStream("pub2", true, new Fields("stock"));
        declarer.declareStream("pub3", true, new Fields("stock"));
    }
}
