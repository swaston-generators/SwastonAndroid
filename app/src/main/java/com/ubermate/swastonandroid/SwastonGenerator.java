package com.ubermate.swastonandroid;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwastonGenerator {

    private static int GraphemesLen(String s) {
        BreakIterator boundary = BreakIterator.getCharacterInstance();
        boundary.setText(s);
        int c = 0;
        while (boundary.next() != BreakIterator.DONE) c++;
        return c;
    }

    private static String[] SplitByGraphemes(String s) {
        List<String> sv = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getCharacterInstance();
        boundary.setText(s);
        for (int start = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; ) {
            String chunk = s.substring(start, end);
            sv.add(chunk);

            start = end;
            end = boundary.next();
        }
        return sv.toArray(new String[0]);
    }

    private static String GraphemesToString(String[] s) {
        StringBuilder sb = new StringBuilder();
        for (String ch : s) sb.append(ch);
        return sb.toString();
    }

    private static String Reverse(String s) {
        String[] ls = SplitByGraphemes(s);
        Collections.reverse(Arrays.asList(ls));
        return GraphemesToString(ls);
    }

    private static String Join(String sep, String s) {
        String[] sv = SplitByGraphemes(s);

        if (s.isEmpty() || sep.isEmpty() || sv.length == 1) return s;

        StringBuilder sb = new StringBuilder();

        sb.append(sv[0]);
        for (int i = 1; i < sv.length; i++) {
            sb.append(sep);
            sb.append(sv[i]);
        }
        return sb.toString();
    }

    private static String Repeat(String s, int length) {
        StringBuilder sb = new StringBuilder();
        while (length-- > 0) sb.append(s);
        return sb.toString();
    }

    private static String Substr(String s, int begin, int end) {
        String[] sv = SplitByGraphemes(s);
        return GraphemesToString(Arrays.copyOfRange(sv, begin, end == -1 ? sv.length : end));
    }

    public static String FromString(String s) {
        if (GraphemesLen(s) < 2) {
            return s;
        }
        String l_spaced_word = Join(" ", s),
                r_spaced_word = Reverse(l_spaced_word),
                l_word = Reverse(s),
                r_word = s,
                center = String.format("%s%s\n", l_spaced_word, Substr(r_spaced_word, 1, -1)),
                tab_pre = Repeat(" ", GraphemesLen(r_spaced_word) - 2),
                tab_post = tab_pre + " ", // extended tab
                upper = "",
                lower = "";

        String[]
                r_vec = SplitByGraphemes(r_word),
                l_vec = SplitByGraphemes(l_word); // convert utf-8 string to vector of strings

        int length = l_vec.length;
        for (int c = 0; c != length - 1; c++) {
            if (c == 0) { // case first row
                upper = upper.concat(String.format("%s%s%s\n", l_vec[c], tab_pre, l_spaced_word));
            } else {
                upper = upper.concat(String.format("%s%s%s%s\n", l_vec[c], tab_pre, r_vec[c], tab_post));
            }
        }
        for (int c = 1; c != length; c++) { // lower side
            if (c == length - 1) { // case last row
                lower = lower.concat(String.format("%s%s%s\n", r_spaced_word, tab_pre, r_vec[c]));
            } else {
                lower = lower.concat(String.format("%s%s%s%s\n", tab_post, l_vec[c], tab_pre, r_vec[c]));
            }
        }
        return String.format("%s%s%s", upper, center, lower);
    }
}
