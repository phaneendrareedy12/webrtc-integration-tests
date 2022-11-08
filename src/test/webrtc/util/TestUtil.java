package util;

import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.HashMap;

public class TestUtil {

    public <T> ArrayList<T> getClients(JsonPath jp) {
        return jp.get();
    }

    public <T> HashMap<T,T> getErrorResponse(JsonPath jp) {
        return jp.get();
    }
}
