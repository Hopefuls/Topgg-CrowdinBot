package org.topgg.crowdingg;

public enum Variables {
    CROWDIN_API_URL("https://api.crowdin.com/api/v2/"),

    // ENDPOINTS
    ENDPOINT_GENERAL_LANGUAGES("languages"),
    ENDPOINT_PROJECT("projects"),
    PROJECT_ID_WEB("450156"),
    PROJECT_ID_BACKEND("456000"),

    // Emojis
    EMOJI_FINISHED("<:finished:848022208763920434>"),
    EMOJI_PARTIALLY("<:partially:848027954926846002>"),
    EMOJI_NOT_DONE("<:not_done:848026313024602132>");

    public String value;

    Variables(String val) {
        value = val;
    }
}
