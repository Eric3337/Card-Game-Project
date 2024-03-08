package persistence;

import org.json.JSONObject;

// Writable interface was inspired by the JsonSpecializationDemo project
public interface Writable {
    // EFFECTS: return this as a JSON object
    JSONObject toJson();
}
