package persistence;

import org.json.JSONObject;

public interface Writable {
    //EFFECTS: return this as JSONObject
    JSONObject toJason();
}
