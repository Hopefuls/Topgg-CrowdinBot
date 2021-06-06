package org.topgg.crowdingg.utils.crowdin;

import org.json.JSONObject;
import org.topgg.crowdingg.utils.crowdin.entity.Language;

public class FlagParser {


    public static String getByCode(String code) {

        JSONObject object = Language.getLanguageFromId(code);

        System.out.println(object.toString(3));

        code = object.getString("twoLettersCode");

        // offset between uppercase ascii and regional indicator symbols

        // validate code
        if (code == null) {
            return "";
        }

        System.out.println("Id is " + code);

        /*
        // fil to ph
        if (code.equalsIgnoreCase("fil")) {
            code = "ph";
        }

        //fix for uk -> gb
        if (code.equalsIgnoreCase("uk")) {
            code = "gb";

        }

        // fix for ja -> jp
        if (code.equalsIgnoreCase("ja")) {
            code = "jp";
        }

        // fix for ja -> dk
        if (code.equalsIgnoreCase("da")) {
            code = "dk";
        }

        // el to gr
        if (code.equalsIgnoreCase("el")) {
            code = "gr";
        }

        // fa to ir
        if (code.equalsIgnoreCase("fa")) {
            code = "ir";
        }

        // he to il
        if (code.equalsIgnoreCase("he")) {
            code = "il";
        }

        if (code.equalsIgnoreCase("hi")) {
            code = "in";
        }

        if (code.equalsIgnoreCase("ko")) {
            code = "kr";
        }

        if (code.equalsIgnoreCase("sco")) {
            code = "sc";
        }

        if (code.equalsIgnoreCase("sv")) {
            code = "se";
        }

         */

        switch (code.toLowerCase()) {
            case "fil":
                return parseToUnicode("ph");
            case "uk":
                return parseToUnicode("gb");
            case "ja":
                return parseToUnicode("jp");
            case "da":
                return parseToUnicode("dk");
            case "el":
                return parseToUnicode("gr");
            case "fa":
                return parseToUnicode("ir");
            case "he":
                return parseToUnicode("il");
            case "hi":
                return parseToUnicode("in");
            case "ko":
                return parseToUnicode("kr");
            case "sco":
                return ":scotland:";
            case "sv":
                return parseToUnicode("se");
            case "ku":
                return "<:flag_kurdish:849386493969694740>";
            default:
                return parseToUnicode(code.toLowerCase());
        }


    }

    public static String parseToUnicode(String code) {
        code = code.toUpperCase();
        int OFFSET = 127397;

        StringBuilder emojiStr = new StringBuilder();

        //loop all characters
        for (int i = 0; i < code.length(); i++) {
            emojiStr.appendCodePoint(code.charAt(i) + OFFSET);
        }

        // return emoji
        System.out.println("Final: " + code);
        return emojiStr.toString();
    }
}
