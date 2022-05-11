package persistence;

import org.json.JSONObject;

// Citation: Sampled from JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
