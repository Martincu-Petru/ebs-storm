package utils;

import models.Publication;
import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;
import org.apache.storm.shade.org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PublicationReader {

    public ArrayList<Publication> models;

    public PublicationReader() {
        models = new ArrayList<Publication>();
        this.parsePublicationsJson();
    }

    private void parsePublicationsJson() {
        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader("./src/main/java/utils/publications.json"));

            for (Object o : jsonArray) {
                Publication model = new Publication();

                JSONObject obj = (JSONObject) o;
                model.color = obj.get("color").toString();
                model.make = obj.get("make").toString();
                model.latitude = Double.parseDouble(obj.get("latitude").toString());
                model.longitude = Double.parseDouble(obj.get("longitude").toString());
                model.speed = Double.parseDouble(obj.get("speed").toString());

                models.add(model);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Publication> getPublications() {
        return models;
    }
}
