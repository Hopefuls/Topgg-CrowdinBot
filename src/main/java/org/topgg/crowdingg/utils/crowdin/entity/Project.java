package org.topgg.crowdingg.utils.crowdin.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.topgg.crowdingg.Variables;
import org.topgg.crowdingg.utils.crowdin.Crowdin;

import java.util.ArrayList;

public class Project {

    private final JSONObject projectData;

    public Project(JSONObject data) {
        this.projectData = data;
    }

    public final JSONObject getJSON() {
        return this.projectData;
    }

    public final String getProjectName() {
        return projectData.getString("name");
    }

    public final ArrayList<String> getProjectTargetLanguageIds() {
        ArrayList<String> languageIds = new ArrayList<>();

        projectData.getJSONArray("targetLanguageIds").forEach(o -> {
            String Id = (String) o;
            languageIds.add(Id);
        });

        return languageIds;
    }

    public final ArrayList<ProjectProgress> getProgress(String projectId) {

        ArrayList<ProjectProgress> progresses = new ArrayList<>();

        JSONObject responseData = Crowdin.getJSONFromEndpoint(Variables.ENDPOINT_PROJECT.value + "/" + projectId + "/languages/progress");

        responseData.getJSONArray("data").forEach(o -> {
            JSONObject languageProgress = (JSONObject) o;
            progresses.add(new ProjectProgress(languageProgress));
        });

        return progresses;

    }

    public final ArrayList<Language> getTargetLanguages() {

        ArrayList<Language> languages = new ArrayList<>();
        JSONArray array = this.projectData.getJSONArray("targetLanguages");

        for (int i = 0; i < array.length(); i++) {

            languages.add(new Language(array.getJSONObject(i)));

        }

        return languages;


    }

}
