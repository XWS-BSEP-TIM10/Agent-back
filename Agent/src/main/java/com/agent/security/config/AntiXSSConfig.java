package com.agent.security.config;

import com.fasterxml.jackson.core.JsonpCharacterEscapes;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AntiXSSConfig {

    @Autowired
    public void configJackson(ObjectMapper mapper) {
        mapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static class HTMLCharacterEscapes extends JsonpCharacterEscapes {

        @Override
        public int[] getEscapeCodesForAscii() {
            int[] asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
            return asciiEscapes;
        }

        @Override
        public SerializableString getEscapeSequence(int ch) {
            return switch (ch) {
                case '&' -> new SerializedString("&#38;");
                case '<' -> new SerializedString("&#60;");
                case '>' -> new SerializedString("&#62;");
                case '\"' -> new SerializedString("&#34;");
                case '\'' -> new SerializedString("&#39;");
                default -> super.getEscapeSequence(ch);
            };
        }
    }
}