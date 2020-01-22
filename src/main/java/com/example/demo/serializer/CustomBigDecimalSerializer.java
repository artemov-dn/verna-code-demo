package com.example.demo.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by den on 21.01.20.
 */
public class CustomBigDecimalSerializer extends StdSerializer<BigDecimal> {

    public CustomBigDecimalSerializer() {
        this(null);
    }

    public CustomBigDecimalSerializer(Class<BigDecimal> t) {
        super(t);
    }

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(bigDecimal.toString());
    }

}
