package spout;


import models.Publication;
import org.apache.storm.tuple.Fields;

import java.util.List;

public class PublicationSpout extends CycleSpout<Publication> {
    public PublicationSpout(List<Publication> publications) {
        super(publications, new Fields("truck"));
    }
}