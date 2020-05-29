package utils;

import models.Publication;
import org.apache.storm.shade.org.json.simple.JSONArray;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.parser.JSONParser;
import org.apache.storm.shade.org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PublicationReader {

    public ArrayList<Publication> models;

    private final String fileName;

    public PublicationReader(String fileName) {
        this.fileName = fileName;

        models = new ArrayList<>();
        this.parsePublicationsJson();
    }

    private void parsePublicationsJson() {
        try {
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(new FileReader(fileName));

            for (Object o : jsonArray) {
                Publication model = new Publication();

                JSONObject obj = (JSONObject) o;
                model.company = obj.get("company").toString();
                model.value = Double.parseDouble(obj.get("value").toString());
                model.drop = Double.parseDouble(obj.get("drop").toString());
                model.variation = Double.parseDouble(obj.get("variation").toString());
                model.date = this.getDate(obj.get("date").toString());

                models.add(model);
            }
        } catch (IOException | ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    private Date getDate(String value) throws java.text.ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(value);
    }

    public ArrayList<Publication> getPublications() {
        return models;
    }
}
