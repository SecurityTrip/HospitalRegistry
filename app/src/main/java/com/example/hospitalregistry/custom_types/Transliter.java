package com.example.hospitalregistry.custom_types;
import com.ibm.icu.text.Transliterator;
import java.util.Locale;

public class Transliter {
    private static final String  CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
    private static Locale currentLocale = Locale.getDefault();
    private static final String currentLanguage = currentLocale.getLanguage();

    private static String transliterate(String st){
         Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
         if (currentLanguage.equals(new Locale("ru").getLanguage())) {
             return st;
         } else {
             return toLatinTrans.transliterate(st);
         }
    }

    public static String[] translateArr(String[] st){
         String[] result = new String[st.length];
         for (int i = 0; i < st.length; i++) {
             result[i] = transliterate(st[i]);
         }
         return result;
    }
}
