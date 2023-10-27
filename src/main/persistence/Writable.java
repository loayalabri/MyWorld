package persistence;

import org.json.JSONObject;

/*
This interface is modeled on Writable interface from JsonSerializationDemo from GitHub
that can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writable {
    //EFFECTS: return this as JSONObject
    JSONObject toJason();
}
