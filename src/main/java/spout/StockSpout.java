package spout;

import models.Publication;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class StockSpout extends BaseRichSpout
{
    private SpoutOutputCollector collector;
    private List<Publication> publications;
    private int currentStockIndex = 0;

    public StockSpout(List<Publication> publications)
    {
     this.publications = publications;
    }

    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector)
    {
        // TODO Auto-generated method stub
        this.collector = collector;

    }

    public void nextTuple() {
        // TODO Auto-generated method stub

        this.collector.emit(new Values(publications.get(currentStockIndex)));

        currentStockIndex ++;
        if (currentStockIndex >= publications.size()) {
            currentStockIndex = 0;
        }

        try
        {
            Thread.sleep(1);
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
        declarer.declare(new Fields("stocks"));

    }
}
