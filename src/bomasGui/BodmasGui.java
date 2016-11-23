/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodmas;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author adescode
 */
public class BodmasGui extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        TextArea text = new TextArea();
        text.setMaxSize(5, 2);
        text.setMinWidth(249);
         Button b1 = new Button("1");
         b1.getStyleClass().add("button2");
         b1.setPrefSize(35, 35);
         //b1.setDefaultButton(true);
         Button b2 = new Button("2");
         b2.getStyleClass().add("button2");
         b2.setPrefSize(35, 35);
         //b2.setDefaultButton(true);
         Button b3 = new Button("3");
         b3.getStyleClass().add("button2");
         b3.setPrefSize(35, 35);
         //b3.setDefaultButton(true);
         Button b4 = new Button("4");
         b4.getStyleClass().add("button2");
         b4.setPrefSize(35, 35);
         //b4.setDefaultButton(true);
         Button b5 = new Button("5");
         b5.getStyleClass().add("button2");
         b5.setPrefSize(35, 35);
         //b5.setDefaultButton(true);
         Button b6 = new Button("6");
         b6.getStyleClass().add("button2");
         b6.setPrefSize(35, 35);
         //b6.setDefaultButton(true);
         Button b7 = new Button("7");
         b7.getStyleClass().add("button2");
         b7.setPrefSize(35, 35);
         //b7.setDefaultButton(true);
         Button b8 = new Button("8");
         b8.getStyleClass().add("button2");
         b8.setPrefSize(35, 35);
         //b8.setDefaultButton(true);
         Button b9 = new Button("9");
         b9.getStyleClass().add("button2");
         b9.setPrefSize(35, 35);
         //b9.setDefaultButton(true);
         Button b0 = new Button("0");
         b0.getStyleClass().add("button1");
         b0.setPrefSize(35, 35);
        // b0.setDefaultButton(true);
         Button bdot = new Button(".");
         bdot.getStyleClass().add("button2");
         bdot.setPrefSize(35, 35);
        // bdot.setDefaultButton(true);
         Button beq = new Button("=");
         beq.getStyleClass().add("button1");
         beq.setPrefSize(35, 35);
        // beq.setDefaultButton(true);
         Button badd = new Button("+");
         badd.getStyleClass().add("button1");
         badd.setPrefSize(35, 35);
         //badd.setDefaultButton(true);
         Button bsub = new Button("-");
         bsub.getStyleClass().add("button1");
         bsub.setPrefSize(35, 35);
         //bsub.setDefaultButton(true);
         Button bdiv = new Button("/");
         bdiv.getStyleClass().add("button1");
         bdiv.setPrefSize(35, 35);
         //bdiv.setDefaultButton(true);
         Button bmul = new Button("*");
         bmul.getStyleClass().add("button1");
         bmul.setPrefSize(35, 35);
         //bmul.setDefaultButton(true);
         Button bpow = new Button("^");
         bpow.getStyleClass().add("button1");
         bpow.setPrefSize(35, 35);
         //bpow.setDefaultButton(true);
         Button broot = new Button("√");
         broot.getStyleClass().add("button1");
         broot.setPrefSize(35, 35);
         //broot.setDefaultButton(true);
         Button bclear = new Button("C");
         bclear.getStyleClass().add("button1");
         bclear.setPrefSize(35, 35);
         //bclear.setDefaultButton(true);
         Button bback = new Button("<-");
         bback.getStyleClass().add("button1");
         bback.setPrefSize(35, 35);
         //bback.setDefaultButton(true);
         Button bbrkopn = new Button("(");
         bbrkopn.getStyleClass().add("button1");
         bbrkopn.setPrefSize(35, 35);
         //bbrkopn.setDefaultButton(true);
         Button bbrkcl = new Button(")");
         bbrkcl.getStyleClass().add("button1");
         bbrkcl.setPrefSize(35, 35);
         ///bbrkcl.setDefaultButton(true);
         Button bmem = new Button("m");
         bmem.getStyleClass().add("button1");
         bmem.setPrefSize(35, 35);
         //bmem.setDefaultButton(true);
         Button bmem2 = new Button("m");
         bmem2.getStyleClass().add("button1");
         bmem2.setPrefSize(35, 35);
         //bmem2.setDefaultButton(true);
         
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(b1,b2,b3);
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(b4,b5,b6);
        HBox hbox3 = new HBox();
        hbox3.getChildren().addAll(b7,b8,b9);
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(bdot,b0,beq);
        HBox hbox5 = new HBox();
        hbox5.getChildren().addAll(badd,bsub,bbrkopn);
        HBox hbox6 = new HBox();
        hbox6.getChildren().addAll(bdiv,bmul,bbrkcl);
        HBox hbox7 = new HBox();
        hbox7.getChildren().addAll(bpow,broot,bmem);
        HBox hbox8 = new HBox();
        hbox8.getChildren().addAll(bclear,bback,bmem2);
        
        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(hbox1,hbox2,hbox3,hbox4);
        Insets padH = new Insets(5.0,0.0,2.0,5.5);
               vbox1.setPadding(padH);
        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(hbox5,hbox6,hbox7,hbox8);
        Insets padH1 = new Insets(5.0,1.0,2.0,0.0);
               vbox2.setPadding(padH1);
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox1,vbox2);
        
        HBox htext = new HBox();
        htext.getChildren().add(text);
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(htext,hbox);
        
        pane.getChildren().add(vbox);
        Scene scene = new Scene(pane, 250, 250);
       // primaryStage.setMaxHeight(250);
        //primaryStage.setMaxWidth(250);
        primaryStage.setTitle("Bodmas Calculator");
        primaryStage.setScene(scene);
        
        scene.getStylesheets().add(BodmasGui.class.getResource("cssFile.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
