package utils;

import models.Subscription;
import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;
import org.apache.storm.shade.org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SubscriptionReader {

    public ArrayList<Subscription> models;

    public SubscriptionReader() {
        models = new ArrayList<Subscription>();
        this.parseSubscriptionsJson();
    }

    private void parseSubscriptionsJson() {
        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader("./src/main/java/utils/subscriptions.json"));

            for (Object o : jsonArray) {
                Subscription model = new Subscription();

                JSONObject obj = (JSONObject) o;
                model.key = obj.get("key").toString();
                model.operator = obj.get("operator").toString();
                model.value = obj.get("value").toString();

                models.add(model);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Subscription> getSubscriptions() {
        return models;
    }
}
