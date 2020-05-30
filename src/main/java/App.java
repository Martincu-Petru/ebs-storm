import bolts.FilterBolt;
import bolts.GoogleApp;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import spouts.GoogleInterestSpout;
import spouts.StockInterestSpout;
import spouts.StockSpout;
import utils.Filenames;
import utils.PublicationReader;
import utils.SubscriptionReader;

import static utils.ComponentIds.*;

public class App {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        LocalCluster cluster = new LocalCluster();

        // each subscriptions set has been generated with different constraints on the Company value (Yahoo, Microsoft, Google)
        StockInterestSpout yahooInterests = new StockInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS1).getSubscriptions());
        StockInterestSpout microsoftInterests = new StockInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS2).getSubscriptions());
        GoogleInterestSpout googleInterests = new GoogleInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS3).getSubscriptions());

        // set the publications spouts
        builder.setSpout(SPOUT_ONE_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS1).getPublications()));
        builder.setSpout(SPOUT_TWO_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS2).getPublications()));
        builder.setSpout(SPOUT_THREE_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS3).getPublications()));

        // set the subscriptions spouts
        builder.setSpout(YAHOO_INTEREST_ID, yahooInterests);
        builder.setSpout(MICROSOFT_INTEREST_ID, microsoftInterests);
        builder.setSpout(GOOGLE_INTEREST_ID, googleInterests);

        builder.setBolt(FILTER_BOLT_ID, new FilterBolt())
                .allGrouping(SPOUT_ONE_ID)
                .allGrouping(SPOUT_TWO_ID)
                .allGrouping(SPOUT_THREE_ID)
                .directGrouping(GOOGLE_APP_ID, "subs");

        builder.setBolt(GOOGLE_APP_ID, new GoogleApp())
                .directGrouping(FILTER_BOLT_ID, "pubs1")
                .directGrouping(GOOGLE_INTEREST_ID, "subs");



        // fine tuning
        config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE, 1024);
        config.put(Config.TOPOLOGY_DISRUPTOR_BATCH_SIZE, 1);
        config.put(Config.TOPOLOGY_DEBUG, false);

        StormTopology topology = builder.createTopology();
        cluster.submitTopology("count_topology", config, topology);
        // run for 20 seconds
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // shutdown topology
        cluster.killTopology("stocks_topology");
        cluster.shutdown();

    }
}
