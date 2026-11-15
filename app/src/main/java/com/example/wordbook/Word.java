//단어의 뜻을 담는 클래스.
package com.example.wordbook;

import java.io.Serializable;

public class Word implements Serializable {
    private String word;
    private String reading;
    private String meaning;

    public Word(String word, String reading, String meaning) {
        this.word = word;
        this.reading = reading;
        this.meaning = meaning;
    }

    public String getWord() { return word; }
    public String getReading() { return reading; }
    public String getMeaning() { return meaning; }
}