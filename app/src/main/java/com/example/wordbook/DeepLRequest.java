package com.example.wordbook;

import java.util.ArrayList;
import java.util.List;

public class DeepLRequest {
    public List<String> text = new ArrayList<>();
    public String target_lang;
    public String source_lang;

    public DeepLRequest(String word, String targetLang, String sourceLang) {
        text.add(word);
        target_lang = targetLang;
        source_lang = sourceLang;
    }
}