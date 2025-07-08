module com.utils { 
    exports com.utils; 
}


package com.utils;

public class StringUtil {
    public static String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    public static String reverse(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return new StringBuilder(text).reverse().toString();
    }
}


module com.greetings { 
    requires com.utils; 
}


package com.greetings;

import com.utils.StringUtil; 

public class Main {
    public static void main(String[] args) {
        String original = "hello madesh!";
        String capitalized = StringUtil.capitalize(original); 
        String reversed = StringUtil.reverse(capitalized); 

        System.out.println("Original: " + original);
        System.out.println("Capitalized: " + capitalized);
        System.out.println("Reversed: " + reversed);
    }
}