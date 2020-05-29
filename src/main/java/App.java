import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import spouts.StockSpout;

public class App
{
    private static final String SPOUT_ONE_ID = "publishers_source_1";
    private static final String SPOUT_TWO_ID = "publishers_source_2";
    private static final String SPOUT_THREE_ID = "publishers_source_3";

    public static void main(String[] args)
    {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        StormTopology topology = builder.createTopology();
        LocalCluster cluster = new LocalCluster();

        builder.setSpout(SPOUT_ONE_ID, new StockSpout(null));
        builder.setSpout(SPOUT_TWO_ID, new StockSpout(null));
        builder.setSpout(SPOUT_THREE_ID, new StockSpout(null));

        cluster.submitTopology("stocks_topology", config, topology);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("stocks_topology");
        cluster.shutdown();

    }
}
