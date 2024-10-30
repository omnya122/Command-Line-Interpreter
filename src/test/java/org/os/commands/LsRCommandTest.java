package org.os.commands;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LsRCommandTest {
    @Test
    void testLsRecursive() {
        List<String> items = LsRCommand.listRecursive();
        assertTrue(items.size() > 0); // Ensure there is at least one file or directory
    }
}
