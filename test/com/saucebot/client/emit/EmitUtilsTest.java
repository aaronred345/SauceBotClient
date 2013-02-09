package com.saucebot.client.emit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class EmitUtilsTest {

    @Test
    public void testBasicParse() {
        Emit emit = EmitUtils.parse("{\"cmd\":\"error\",\"data\":{}}");
        assertNotNull(emit);
        assertEquals(EmitType.ERROR, emit.getCmd());
    }

    @Test
    public void testParseMessage() {
        Emit emit = EmitUtils.parse("{\"cmd\":\"msg\",\"data\":{\"a\":1,\"b\":2,\"c\":\"asdf\"}}");
        assertEquals(EmitType.MESSAGE, emit.getCmd());
        assertEquals("1", emit.get("a"));
        assertEquals("2", emit.get("b"));
        assertEquals("asdf", emit.get("c"));
    }

    @Test
    public void testGenerate() {
        Emit emit = new Emit(EmitType.PRIVATE_MESSAGE);
        emit.put("user", "ravn");
        String json = EmitUtils.serialize(emit);
        Emit upd = EmitUtils.parse(json);

        assertEquals(EmitType.PRIVATE_MESSAGE, upd.getCmd());
        assertEquals("ravn", upd.get("user"));
    }
}
