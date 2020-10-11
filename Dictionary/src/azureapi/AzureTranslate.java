package azureapi;

/**
 * Sử dụng API Translate của Azure, code dựa vào source code hướng dẫn trong
 * forum docs.microsoft, đã được sửa theo ý hiểu và cách dùng của nhóm.
 * KEY của thành viên nhóm, free account nên có limit.
 */

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
            String jsonText = Post(Word);
            JsonParser parser = new JsonParser();
            Object object = parser.parse(jsonText);
            JsonObject jsonObject = (JsonObject) (((JsonArray) object).get(0));
            JsonArray jsonArray = (JsonArray) (jsonObject.get("translations"));
            JsonObject jsonObject1 = (JsonObject) jsonArray.get(0);
            String result = String.valueOf(jsonObject1.get("text"));
            result = result.substring(1, result.length() - 1);
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

}