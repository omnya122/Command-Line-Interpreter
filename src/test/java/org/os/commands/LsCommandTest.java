package org.os.commands;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LsCommandTest {
    @Test
    void testLs() {
        List<String> items = LsCommand.list();
        assertTrue(items.size() > 0); // Ensure the current directory has contents
    }
}
