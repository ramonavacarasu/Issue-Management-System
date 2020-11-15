package org.jee8ng.ims.tasks.boundary;

import org.jee8ng.ims.tasks.entity.Issue;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.stream.JsonCollectors;
import javax.json.stream.JsonParser;

@Singleton
@Startup
public class JsonDemo {

    @PostConstruct
    public void init() {
        simple();
        parser();
        inAarray();
        pointer();
        patch();
        mergePatch();
        jsonStreamFilter();
        jsonStreamGroup();
        jsonB();
    }

    private void simple() {
        JsonObject json = Json.createObjectBuilder()
                .add("name", "Raise alert on failure")
                .add("id", Long.valueOf(2003))
                .build();
        String result = json.toString();
        System.out.println("JsonObject simple : " + result);
    }


    private void parser() {
        JsonParser parser = Json.createParser(JsonDemo.class.getResourceAsStream("/sample.json"));

        while (parser.hasNext()) {
            JsonParser.Event e = parser.next();
            System.out.print(e.name());
            switch (e) {
                case KEY_NAME:
                case VALUE_STRING:
                case VALUE_NUMBER:
                    System.out.println(" - " + parser.getString());
                    break;
            }
            System.out.println();
        }
    }

    private void array() {
        JsonArray array = Json.createArrayBuilder().add("java").add("ruby").build();

        JsonArray transformedArray = Json.createArrayBuilder(array)
                .add(0, "python") //puts in first, shifting others
                .remove(2) // removes the 3rd element
                .build();
        System.out.println("transformedArray: " + transformedArray);
    }

    private void inAarray() {
        JsonArray array = Json.createArrayBuilder().add("java").add("ruby").build();

        JsonArray transformedArray = Json.createArrayBuilder(array)
                .add(0, "python") //puts in first, shifting others
                .remove(2) // removes the 3rd element
                .build();
        System.out.println("transformedArray: " + transformedArray);
    }

    private void pointer() {
        JsonReader reader = Json.createReader(JsonDemo.class.getResourceAsStream("/sample.json"));
        JsonArray issuesJson = reader.readArray();

        JsonPointer pointer = Json.createPointer("/1");
        JsonValue value = pointer.getValue(issuesJson);

        System.out.println(value.toString());

        pointer = Json.createPointer("/0/name");
        value = pointer.getValue(issuesJson);

        System.out.println("pointer: " + value.toString());
    }

    private void patch() {
        JsonReader reader = Json.createReader(JsonDemo.class.getResourceAsStream("/sample.json"));
        JsonArray issuesJson = reader.readArray();

        JsonPatch patch = Json.createPatchBuilder()
                .remove("/2")
                .replace("/1/name", "Fix alert not firing")
                .build();

        JsonArray result = patch.apply(issuesJson);
        System.out.println("patch result: " + result);
    }

    private void mergePatch() {
        JsonReader reader = Json.createReader(JsonDemo.class.getResourceAsStream("/sample.json"));
        JsonObject json = reader.readArray().getJsonObject(0);

        //Create the Patch
        JsonObject removeName = Json.createObjectBuilder()
                .add("name", JsonValue.NULL)
                .build();

        JsonMergePatch mergePatch = Json.createMergePatch(removeName);
        JsonValue result = mergePatch.apply(json);

        System.out.println("mergePatch result: " + result);
    }

    private void jsonStreamFilter() {
        JsonReader reader = Json.createReader(JsonDemo.class.getResourceAsStream("/sample_priority.json"));
        JsonArray jsonarray = reader.readArray();

        JsonArray result = jsonarray.getValuesAs(JsonObject.class)
                .stream()
                .filter(j -> "High".equals(j.getString("priority")))
                .collect(JsonCollectors.toJsonArray());
        System.out.println("Stream Filter: " + result);
    }

    private void jsonStreamGroup() {
        JsonReader reader = Json.createReader(JsonDemo.class.getResourceAsStream("/sample_priority.json"));
        JsonArray jsonarray = reader.readArray();

        JsonObject result = jsonarray.getValuesAs(JsonObject.class)
                .stream()
                .collect(JsonCollectors.groupingBy(
                        x -> ((JsonObject) x).getJsonString("priority")
                                .getString()
                ));
        System.out.println("Stream Group: " + result);

    }

    private void jsonB() {
        Jsonb jsonb = JsonbBuilder.create();

        Issue newIssue = jsonb.fromJson("{\"id\":1123,\"name\":\"Implement feature X\",\"priority\":\"High\"}",
                Issue.class);
        System.out.println("JSON-B Issue: "+ newIssue);

        JsonbConfig config = new JsonbConfig().withFormatting(true);
        Jsonb jsonbFormatted = JsonbBuilder.create(config);

        System.out.println("JSON-B formatted output: " + jsonbFormatted.toJson(newIssue));
    }

}
