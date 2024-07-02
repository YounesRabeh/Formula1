package it.unicam.cs;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class AppTest {
    @BeforeAll
    static void initJfxRuntime(){
        Platform.startup(()->{}); // initializes JavaFX toolkit
    }


    @Test
    public void testApp()  {
        System.out.println("Test App");

    }
}