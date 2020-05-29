package bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;

public class CountBolt extends BaseRichBolt {

    private int count;
    private String label;

    public CountBolt(String label) {
        this.label = label;
    }


    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        count = 0;
    }

    public void execute(Tuple input) {
        // TODO Auto-generated method stub
        if (input.getValues() != null) {
            count++;
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

    public void cleanup() {
        System.out.println("[COUNTER] " + label + " " + count);
    }

}