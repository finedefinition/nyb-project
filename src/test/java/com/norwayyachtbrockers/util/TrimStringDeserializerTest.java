package com.norwayyachtbrockers.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Order(750)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class TrimStringDeserializerTest {

    private TrimStringDeserializer trimStringDeserializer;
    private JsonParser jsonParser;
    private DeserializationContext deserializationContext;

    @BeforeEach
    void setUp() {
        trimStringDeserializer = new TrimStringDeserializer();
        jsonParser = mock(JsonParser.class);
        deserializationContext = mock(DeserializationContext.class);
    }

    @Test
    @DisplayName("deserialize - Should return trimmed string")
    void testDeserialize_shouldReturnTrimmedString() throws IOException {
        // Arrange
        String input = "  some string  ";
        when(jsonParser.getText()).thenReturn(input);

        // Act
        String result = trimStringDeserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertEquals("some string", result);
    }

    @Test
    @DisplayName("deserialize - Should return null for null input")
    void testDeserialize_shouldReturnNullForNullInput() throws IOException {
        // Arrange
        when(jsonParser.getText()).thenReturn(null);

        // Act
        String result = trimStringDeserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("deserialize - Should return empty string for input with only spaces")
    void testDeserialize_shouldReturnEmptyStringForInputWithOnlySpaces() throws IOException {
        // Arrange
        String input = "    ";
        when(jsonParser.getText()).thenReturn(input);

        // Act
        String result = trimStringDeserializer.deserialize(jsonParser, deserializationContext);

        // Assert
        assertEquals("", result);
    }
}
