package org.topgg.crowdingg.utils.crowdin;

import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.topgg.crowdingg.Main;
import org.topgg.crowdingg.Variables;
import org.topgg.crowdingg.utils.crowdin.entity.Language;
import org.topgg.crowdingg.utils.crowdin.entity.Project;

import java.io.IOException;
import java.util.ArrayList;

public class Crowdin {


    public static Project getProjectFromId(String projectId) {
        JSONObject response = getJSONFromEndpoint(Variables.ENDPOINT_PROJECT.value + "/" + projectId);

        return new Project(response.getJSONObject("data"));

    }

    public static ArrayList<Language> getSupportedLanguages() {
        //  https://api.crowdin.com/api/v2/languages/
        ArrayList<Language> languages = new ArrayList<>();


        JSONArray dataArray = Main.crowdinSupportedLanguages;

        dataArray.forEach(langObject -> {
            JSONObject lang = (JSONObject) langObject;
            Language language = new Language(lang.getJSONObject("data"));

            languages.add(language);
        });

        return languages;

    }

    public static JSONArray getSupportedLanguagesInJSONArray() {
        JSONObject response = getJSONFromEndpoint(Variables.ENDPOINT_GENERAL_LANGUAGES.value);
        JSONArray dataArray = response.getJSONArray("data");
        return dataArray;

    }


    public static JSONObject getJSONFromEndpoint(String endpoint) {
        Request request = new Request.Builder()
                .url(Variables.CROWDIN_API_URL.value + endpoint)
                .addHeader("Authorization", "Bearer " + Main.crowdinBearerToken)
                .build();

        try {
            Response response = Main.okHttpClient.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
