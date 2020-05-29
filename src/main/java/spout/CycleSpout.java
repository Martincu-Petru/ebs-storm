package spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class CycleSpout<T> extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private int i = 0;
    private List<T> items;
    private Fields fields;

    CycleSpout(List<T> items, Fields declaredFields) {
        this.items = items;
        fields = declaredFields;
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        // TODO Auto-generated method stub
        this.collector = collector;
    }

    public void nextTuple() {

        if (i == items.size()) {
            i = 0;
        }
        this.collector.emit(new Values(items.get(i)));
        i++;

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(fields);
    }

}