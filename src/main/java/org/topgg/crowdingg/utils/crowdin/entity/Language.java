package org.topgg.crowdingg.utils.crowdin.entity;

import org.json.JSONObject;
import org.topgg.crowdingg.Main;
import org.topgg.crowdingg.utils.crowdin.Crowdin;

import java.util.ArrayList;

public class Language {

    private final JSONObject data;

    public Language(JSONObject object) {
        this.data = object;
    }

    public static String getNameFromIdOnTargetLanguages(Project project, String id) {

        ArrayList<Language> languages = project.getTargetLanguages();

        for (Language language : languages) {
            if (language.getId().equalsIgnoreCase(id)) {
                return language.getName();
            }

        }

        return "null";
    }

    public static JSONObject getLanguageFromId(String langId) {
        System.out.println("checking ");
        JSONObject response = Crowdin.parseLanguageFromSupportedLanguagesArray(Main.crowdinSupportedLanguages, langId);

        // System.out.println(response.toString(3));
        return response;
    }

    public final String getName() {
        return this.data.getString("name");
    }

    public final String getId() {
        return this.data.getString("id");
    }

}
