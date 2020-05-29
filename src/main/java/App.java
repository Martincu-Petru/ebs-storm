import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class App
{
    public static void main(String[] args)
    {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        StormTopology topology = builder.createTopology();
        LocalCluster cluster = new LocalCluster();

        cluster.submitTopology("trucks_topology", config, topology);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("trucks_topology");
        cluster.shutdown();

    }
}
