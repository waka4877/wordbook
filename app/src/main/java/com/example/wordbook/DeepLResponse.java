package com.example.wordbook;

import java.util.List;

public class DeepLResponse {
    public List<Translation> translations;

    public static class Translation {
        public String text;
        public String detected_source_language;
    }
}