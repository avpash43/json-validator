import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.examples.Utils;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.*;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.examples.Utils;

import java.io.IOException;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, ProcessingException {
        //parseEverit();
        parseOld();


    }

    private static void parseEverit() {
        try {
            JSONObject jsonSchema = new JSONObject(new JSONTokener(new FileInputStream(new File("src/main/resources/fstab.json"))));
            //JSONObject jsonSchema = new JSONObject(new JSONTokener(Main.class.getResourceAsStream("/fstab.json")));
            JSONObject jsongood = new JSONObject(new JSONTokener(Main.class.getResourceAsStream("/fstab-good.json")));
            JSONObject jsongood2 = new JSONObject(new JSONTokener(Main.class.getResourceAsStream("/fstab-good-with-test.json")));
            JSONObject jsonbad = new JSONObject(new JSONTokener(Main.class.getResourceAsStream("/fstab-bad.json")));
            JSONObject jsonbad2 = new JSONObject(new JSONTokener(Main.class.getResourceAsStream("/fstab-bad2.json")));

            Schema schema = SchemaLoader.load(jsonSchema);
            schema.validate(jsongood);
            schema.validate(jsongood2);
            //schema.validate(jsonbad);
            //schema.validate(jsonbad2);
        } catch(RuntimeException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void parseOld() throws IOException, ProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        final JsonNode fstabSchema = mapper.readTree(new File("src/main/resources/fstab.json"));
        final JsonNode good = mapper.readTree(new File("src/main/resources/fstab-good.json"));
        final JsonNode goodWithTest = mapper.readTree(new File("src/main/resources/fstab-good-with-test.json"));
        final JsonNode bad = mapper.readTree(new File("src/main/resources/fstab-bad.json"));
        final JsonNode bad2 = mapper.readTree(new File("src/main/resources/fstab-bad2.json"));

        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

        final JsonSchema schema = factory.getJsonSchema(fstabSchema);

        ProcessingReport report;

        report = schema.validate(good);
        System.out.println(report);
        System.out.println(report.isSuccess());

        report = schema.validate(goodWithTest);
        System.out.println(report);
        System.out.println(report.isSuccess());

        report = schema.validate(bad);
        System.out.println(report);
        System.out.println(report.isSuccess());

        report = schema.validate(bad2);
        System.out.println(report);
        System.out.println(report.isSuccess());
    }
}