package azureapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class AzureTranslate {
    private static String subscriptionKey = "de58cb609ea746478e5f9dc9da8677e8";
    private static String endpoint = "https://api.cognitive.microsofttranslator.com/";
    private static String region = "southeastasia";
    private static String url = endpoint + "/translate?api-version=3.0&to=en";

    public static void setUrl(String language) {
        url = endpoint + "/translate?api-version=3.0&to=" + language;
    }

    static OkHttpClient client = new OkHttpClient();

    public static String Post(String translate) throws IOException {
        String content = "[{\n\t\"Text\": \"" + translate + "\"\n}]";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", region)
                .addHeader("Content-type", "application/json").build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String Translate(String Word) {
        try {
            String json_text = Post(Word);
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(json_text);
            JsonObject obj2 = (JsonObject) (((JsonArray) obj).get(0));
            JsonArray x = (JsonArray) (obj2.get("translations"));
            JsonObject obj3 = (JsonObject) x.get(0);
            String ret = String.valueOf(obj3.get("text"));
            ret = ret.substring(1, ret.length() - 1);
            return ret;
        } catch (IOException e) {
            return "-1";
        }
    }

}