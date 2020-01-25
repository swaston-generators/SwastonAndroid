package com.ubermate.swastonandroid;

import java.util.Arrays;

public class SwastonGenerator {
    private static String Reverse(String s) {
        char[] charArray = s.toCharArray();
        return new StringBuilder(new String(charArray)).reverse().toString();
    }

    private static String Join(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int c = 0;

        for (char ch : s.toCharArray()) {
            if (c != 0) {
                sb.append(' ');
            }
            sb.append(ch);
            c++;
        }
        return sb.toString();
    }

    private static String Repeat(int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, ' ');
        return new String(chars);
    }

    public static String FromString(String s) {
        if (s.length() < 2) {
            return s;
        }
        String l_spaced_word = Join(s),
                r_spaced_word = Reverse(l_spaced_word),
                l_word = Reverse(s),
                r_word = s,
                center = String.format("%s%s\n", l_spaced_word, r_spaced_word.substring(1)),
                tab_pre = Repeat(r_spaced_word.length() - 2),
                tab_post = tab_pre + " ", // extended tab
                upper = "",
                lower = "";
        int length = l_word.length();
        for (int c = 0; c != length - 1; c++) {
            if (c == 0) { // case first row
                upper = upper.concat(String.format("%c%s%s\n", l_word.charAt(c), tab_pre, l_spaced_word));

            } else {
                upper = upper.concat(String.format("%c%s%c%s\n", l_word.charAt(c), tab_pre, r_word.charAt(c), tab_post));
            }
        }
        for (int c = 1; c != length; c++) { // lower side
            if (c == length - 1) { // case last row
                lower = lower.concat(String.format("%s%s%c\n", r_spaced_word, tab_pre, r_word.charAt(c)));
            } else {
                lower = lower.concat(String.format("%s%c%s%c\n", tab_post, l_word.charAt(c), tab_pre, r_word.charAt(c)));
            }
        }
        return String.format("%s%s%s", upper, center, lower);
    }
}
