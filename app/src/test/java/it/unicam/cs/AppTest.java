package it.unicam.cs;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class AppTest {
    @BeforeAll
    static void initJfxRuntime(){
        Platform.startup(()->{}); // initializes JavaFX toolkit
    }


    @Test
    public void testApp()  {


    }

}