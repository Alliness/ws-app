package alliness.zzone.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * DTO - Object mapper
 * Created by zhitnikov on 7/4/2017.
 */
public class Serializable {

    private static final Logger log = Logger.getLogger(Serializable.class);

    /**
     * Deserialize json to DTO
     *
     * @param data target object
     * @param type ClassType
     * @param <T>  class type ref
     * @return instance of <T>
     */
    public static <T> T deserialize(Object data, Class<T> type) {
        GsonBuilder gson = new GsonBuilder();
        gson.disableHtmlEscaping();
        try {
            return gson.create().fromJson(data.toString(), type);
        } catch (JsonSyntaxException e) {
            log.error(e.getMessage());
            log.warn(data.toString());
            return null;
        }
    }

    /**
     * Convert Object (Serializable) to JSON
     *
     * @param object target Object
     * @return JSON object of instance
     */
    public static JSONObject serialize(Object object) {
        return new JSONObject(object);
    }

    /**
     * Convert Object (Serializable) to JSON
     *
     * @param serializeNulls is require to show nulls values (default=false)
     * @return JSON object of instance
     */
    public static JSONObject serialize(Object object, boolean serializeNulls) {

        GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) {
            builder.serializeNulls();
        }
        Gson gson = builder.create();
        return new JSONObject(gson.toJson(object));
    }

    @Override
    public String toString() {
        return serialize(this).toString();
    }

    public JSONObject serialize() {
        return serialize(this);
    }
}
