package com.taf.utils;

import javax.annotation.Nonnull;

/**
 * Created by kyle.rudd on 7/11/2016.
 */
public class StringUtils {
    /**
     * This will convert a string that is space delimited to PascalCase (camelCase with the first letter capitalized).
     * Note: If there were characters that were already captitalized in a word in input, those won't be changed.
     *
     * @param input - The string to be converted. Must be non-null.
     * @return - The PascalCase version of the string.
     */
    public static String convertSpaceDelimitedToPascalCase(@Nonnull final String input) {

        StringBuilder sb = new StringBuilder();
        char[] chars = input.toCharArray();
        int charArrayLength = chars.length;

        sb.append(Character.toUpperCase(chars[0]));
        for(int i=1, len=charArrayLength; i<len; i++) {
            char curr = chars[i];
            if (curr == ' ') {
                if (i+1 >= len) break;
                if (chars[i+1] == ' ') continue;

                curr = Character.toUpperCase(chars[i+1]);
                i++;
            }

            sb.append(curr);
        }

        return sb.toString();
    }

    public static String getPageNameFromString(String strPage) {
        strPage = convertSpaceDelimitedToPascalCase(strPage);
        if (!strPage.substring(strPage.length() - 4).equals("Page")) {
            strPage += "Page";
        }
        return strPage;
    }

    public static String convertSpaceDelimitedToCamelCase(@Nonnull final String input) {

        String output = convertSpaceDelimitedToPascalCase(input);
        char[] chars = output.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);

    }
}
