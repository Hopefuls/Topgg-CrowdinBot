package org.topgg.crowdingg;

import com.vdurmont.emoji.EmojiParser;

public enum Variables {
    CROWDIN_API_URL("https://api.crowdin.com/api/v2/"),

    // ENDPOINTS
    ENDPOINT_GENERAL_LANGUAGES("languages"),
    ENDPOINT_PROJECT("projects"),
    PROJECT_ID_WEB("450156"),
    PROJECT_ID_BACKEND("456000"),

    // Emojis
    EMOJI_FINISHED("<:upvote:778416296630157333>"),
    EMOJI_PARTIALLY("<:neutral:782246935242604584>"),
    EMOJI_NOT_DONE("<:downvote:778416297347776533>"),
    PAGINATION_RIGHT(EmojiParser.parseToUnicode(":arrow_forward:")),
    PAGINATION_LEFT(EmojiParser.parseToUnicode(":arrow_backward:")),

    // Roles
    ROLE_ID_TRANSLATOR("842556283450425344");
    public String value;

    Variables(String val) {
        value = val;
    }
}
