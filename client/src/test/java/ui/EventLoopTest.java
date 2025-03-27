package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventLoopTest {

    @AfterEach
    void tearDown() {
    }

    @Test
    void run() {
        EventLoop.run();
    }
}