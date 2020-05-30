import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import spouts.StockInterestSpout;
import spouts.StockSpout;
import utils.Filenames;
import utils.PublicationReader;
import utils.SubscriptionReader;

public class App {
    private static final String SPOUT_ONE_ID = "publishers_source_1";
    private static final String SPOUT_TWO_ID = "publishers_source_2";
    private static final String SPOUT_THREE_ID = "publishers_source_3";

    private static final String YAHOO_INTEREST_ID = "yahoo_subscription";
    private static final String MICROSOFT_INTEREST_ID = "microsoft_subscription";
    private static final String GOOGLE_INTEREST_ID = "google_subscription";

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        Config config = new Config();
        StormTopology topology = builder.createTopology();
        LocalCluster cluster = new LocalCluster();

        // each subscriptions set has been generated with different constraints on the Company value (Yahoo, Microsoft, Google)
        StockInterestSpout yahooInterests = new StockInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS1).getSubscriptions());
        StockInterestSpout microsoftInterests = new StockInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS2).getSubscriptions());
        StockInterestSpout googleInterests = new StockInterestSpout(new SubscriptionReader(Filenames.SUBSCRIPTIONS3).getSubscriptions());

        // set the publications spouts
        builder.setSpout(SPOUT_ONE_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS1).getPublications()));
        builder.setSpout(SPOUT_TWO_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS2).getPublications()));
        builder.setSpout(SPOUT_THREE_ID, new StockSpout(new PublicationReader(Filenames.PUBLICATIONS3).getPublications()));

        // set the subscriptions spouts
        builder.setSpout(YAHOO_INTEREST_ID, yahooInterests);
        builder.setSpout(MICROSOFT_INTEREST_ID, microsoftInterests);
        builder.setSpout(GOOGLE_INTEREST_ID, googleInterests);

        cluster.submitTopology("stocks_topology", config, topology);
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
