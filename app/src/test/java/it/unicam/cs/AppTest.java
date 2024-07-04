package it.unicam.cs;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class AppTest {
    @BeforeAll
    static void initJfxRuntime(){
        Platform.startup(()->{}); // initializes JavaFX toolkit
    }


    @Test
    public void testApp()  {
        List list = new LinkedList<String>();

        list.add("Hello");
        list.add("World");
        list.add("!");
        list.add(2, "Java");
        System.out.println(list);


    }
}