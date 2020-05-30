package utils;

import models.*;
import org.apache.storm.shade.org.apache.zookeeper.Op;
import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;
import org.apache.storm.shade.org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SubscriptionReader {

    private final String fileName;

    public ArrayList<Subscription> models;

    public SubscriptionReader(String fileName) {
        this.fileName = fileName;

        models = new ArrayList<>();
        this.parseSubscriptionsJson();
    }

    private void parseSubscriptionsJson() {
        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(fileName));

            for (Object o : jsonArray) {
                Subscription model = new Subscription();

                JSONObject obj = (JSONObject) o;

                if (obj.get("Company") != null) {
                    JSONObject compJson = (JSONObject) obj.get("Company");
                    Company comp = new Company();

                    comp.operator = this.getOperator(compJson.get("Operator").toString());
                    comp.value = compJson.get("Value").toString();

                    model.company = comp;
                }
                else {
                    model.company = null;
                }

                if (obj.get("Value") != null) {
                    JSONObject compJson = (JSONObject) obj.get("Value");
                    Value val = new Value();

                    val.operator = this.getOperator(compJson.get("Operator").toString());
                    val.value = Double.parseDouble(compJson.get("Value").toString());

                    model.value = val;
                }
                else {
                    model.value = null;
                }

                if (obj.get("Drop") != null) {
                    JSONObject compJson = (JSONObject) obj.get("Drop");
                    Drop drop = new Drop();

                    drop.operator = this.getOperator(compJson.get("Operator").toString());
                    drop.value = Double.parseDouble(compJson.get("Value").toString());

                    model.drop = drop;
                }
                else {
                    model.drop = null;
                }

                if (obj.get("Variation") != null) {
                    JSONObject compJson = (JSONObject) obj.get("Variation");
                    Variation variation = new Variation();

                    variation.operator = this.getOperator(compJson.get("Operator").toString());
                    variation.value = Double.parseDouble(compJson.get("Value").toString());

                    model.variation = variation;
                }
                else {
                    model.variation = null;
                }

                if (obj.get("Date") != null) {
                    JSONObject compJson = (JSONObject) obj.get("Date");
                    Date date = new Date();

                    date.operator = this.getOperator(compJson.get("Operator").toString());

                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    date.value = format.parse(compJson.get("Value").toString());

                    model.date = date;
                }
                else {
                    model.date = null;
                }

                models.add(model);
            }
        } catch (IOException | ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private Operator getOperator(String stringValue) {
        switch (stringValue) {
            case ">":
                return Operator.Greater;
            case ">=":
                return Operator.GreaterOrEqual;
            case "<":
                return Operator.Lower;
            case "<=":
                return Operator.LowerOrEqual;
            case "==":
                return Operator.Equal;
            case "!=":
                return Operator.NotEqual;
            default:
                return null;
        }
    }

    public ArrayList<Subscription> getSubscriptions() {
        return models;
    }
}
