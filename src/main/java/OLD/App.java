package OLD;

import bolts.CountBolt;
import models.Publication;
import models.Subscription;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import spouts.StockSpout;
import spouts.StockInterestSpout;
import utils.Filenames;
import utils.PublicationReader;
import utils.SubscriptionReader;

import java.util.List;

public class App {
    private static final String PUB_SPOUT_ID = "pub_spout_id";
    private static final String SUB_SPOUT_ID = "sub_spout_id";
    private static final String COUNT_SUB_BOLT_ID = "count_sub_bolt_id";
    private static final String COUNT_PUB_BOLT_ID = "count_pub_bolt_id";


    static List<Publication> readPublications() {
        PublicationReader reader = new PublicationReader(Filenames.PUBLICATIONS1);
        return reader.getPublications();
    }

    static List<Subscription> readSubscriptions() {
        SubscriptionReader reader = new SubscriptionReader(Filenames.SUBSCRIPTIONS1);
        return reader.getSubscriptions();
    }

    public static void main(String[] args) {
        List<Publication> publications = readPublications();
        List<Subscription> subscriptions = readSubscriptions();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(PUB_SPOUT_ID, new StockSpout(publications));
        builder.setSpout(SUB_SPOUT_ID, new StockInterestSpout(subscriptions));

        builder.setBolt(COUNT_PUB_BOLT_ID, new CountBolt("publications")).allGrouping(PUB_SPOUT_ID);
        builder.setBolt(COUNT_SUB_BOLT_ID, new CountBolt("subscriptions")).allGrouping(SUB_SPOUT_ID);

        Config config = new Config();

        LocalCluster cluster = new LocalCluster();
        StormTopology topology = builder.createTopology();


        cluster.submitTopology("count_topology", config, topology);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cluster.killTopology("count_topology");
        cluster.shutdown();

    }
}