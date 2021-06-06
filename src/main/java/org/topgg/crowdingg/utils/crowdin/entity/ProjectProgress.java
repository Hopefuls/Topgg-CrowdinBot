package org.topgg.crowdingg.utils.crowdin.entity;

import org.json.JSONObject;

public class ProjectProgress {

    public final JSONObject data;


    public ProjectProgress(JSONObject statsData) {
        this.data = statsData.getJSONObject("data");
    }

    public final String getLanguageId() {
        return data.getString("languageId");
    }

    public final String getLetterCode() {
        return data.getString("twoLettersCode");
    }

    public final JSONObject getPhrasesCount() {
        return data.getJSONObject("phrases");
    }

    public final JSONObject getWordCount() {
        return data.getJSONObject("words");
    }

}
