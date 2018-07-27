/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assweeper;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.SwingUtilities;

/**
 *
 * @author komp
 */
public class Assweeper extends Application {
//    public int fix(int x, int n){
//        if(x<0){
//            x=0;
//        }
//        if(x>n){
//            x=n;
//        }
//        return x;
//    }
    
    private void colornumbers(Field item){
        if(item.getText().equals("1")){item.setStyle("-fx-text-fill: #0000ff; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("2")){item.setStyle("-fx-text-fill: #008000; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("3")){item.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("4")){item.setStyle("-fx-text-fill: #000080; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("5")){item.setStyle("-fx-text-fill: #800000; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("6")){item.setStyle("-fx-text-fill: #008080; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("7")){item.setStyle("-fx-text-fill: #000000; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("8")){item.setStyle("-fx-text-fill: #808080; -fx-background-color: #AAAAAA;");}
        if(item.getText().equals("0")){item.setStyle("-fx-text-fill: #AAAAAA; -fx-background-color: #AAAAAA;");}
    }
    
    public boolean arezeroesencircled(Field[][] buttons){
        boolean allzeroesencircled = true;
        for(int i=0; i<32; i++){
                for(int j=0; j<22; j++){
                    if(buttons[i][j].getText().equals("0")){
                        for(int g=-1; g<2; g++){
                            for(int h=-1; h<2; h++){
                                if(i+g<32 && i+g>-1 && j+h<22 && j+h>-1){
                                    if(buttons[i+g][j+h].getText().equals("u")){
                                        allzeroesencircled=false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        return allzeroesencircled;
    }
    
    public void zeros(Field item, Field[][] buttons){
        while(!arezeroesencircled(buttons)){
            for(int i=0; i<32; i++){
                for(int j=0; j<22; j++){
                    if(buttons[i][j].getText().equals("0")){
                        for(int g=-1; g<2; g++){
                            for(int h=-1; h<2; h++){
                                if(i+g<32 && i+g>-1 && j+h<22 && j+h>-1){
                                    buttons[i+g][j+h].setText("" + buttons[i+g][j+h].getNum());
                                    colornumbers(buttons[i+g][j+h]);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void checkIfWon(Field item, Field[][] buttons, Label time, Label mines){
        boolean won = true;
        for(int i=0; i<32; i++){
            for(int j=0; j<22; j++){
                if(!buttons[i][j].isMine()){
                    if(!buttons[i][j].getText().equals("" + buttons[i][j].getNum())){
                        won = false;
                    }
                }
            }
        }
        if(won){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("yo u won");
            alert.setHeaderText("congrats my nigga");
            alert.setContentText("👏👏👏👏👏👏👏👏👏👏👏👏👏👏👏👏👏");

            alert.showAndWait();
            reset(buttons, time, mines);
        }
    }
    
    public void reset(Field[][] buttons, Label time, Label mines){
        //set everything to null
        for(int i=0; i<32; i++){
            for(int j=0; j<22; j++){
                buttons[i][j].setMine(false);
                buttons[i][j].setText("u");
                buttons[i][j].setStyle("-fx-text-fill: #CCCCCC; -fx-background-color: #CCCCCC;");
            }
        }
        
        //place mines
        int thisi;
        int thisj;
        Random randi = new Random();
        Random randj = new Random();
        for(int a=0; a<128; a++){
            thisi = randi.nextInt(32);
            thisj = randj.nextInt(22);
            if(buttons[thisi][thisj].isMine()){
                a--;
            }
            else{
                buttons[thisi][thisj].setMine(true);
            }
        }
        
        //evertyhing gets a number
        int num;
        for(int i=0; i<32; i++){
            for(int j=0; j<22; j++){
                num = 0;
                for(int g=-1; g<2; g++){
                    for(int h=-1; h<2; h++){
                        if(/*right*/i+g<32 && i+g>-1 && j+h<22 && j+h>-1){
                            if(buttons[i+g][j+h].isMine()){
                                num++;
                            }
                        }
                        //else{System.exit(0);}
                    }
                }
                buttons[i][j].setNum(num);
                buttons[i][j].setText("u");
                buttons[i][j].setStyle("-fx-text-fill: #CCCCCC;");
            }
        }
        time.setText("0");
        mines.setText("128");
    }
    
    public void checkIfDead(Field item, Field[][] buttons,  Label time, Label mines) {
        if(item.isMine()){
            item.setText("X");
            item.setStyle("-fx-background-color: #FF4040;");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("nigga you dead");
            alert.setHeaderText("lmao xd");
            alert.setContentText("ripripripripripripripripripriprip");

            alert.showAndWait();
            reset(buttons, time, mines);
            //System.exit(0);
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        VBox bigvbox = new VBox();
        GridPane map = new GridPane();
        Field[][] buttons = new Field[32][22];
        for(int i=0; i<32; i++){
            for(int j=0; j<22; j++){
                buttons[i][j] = new Field(false, 0, i, j);
                GridPane.setConstraints(buttons[i][j], i, j);
                map.getChildren().addAll(buttons[i][j]);
            }
        }
        bigvbox.getChildren().addAll(map);
        
        int time = 0;
        Label lbltime = new Label("" + time);
        Label lblmines = new Label("128");
        HBox stats = new HBox();
//        GridPane.setConstraints(lbltime, 0, 22 + 1);
//        GridPane.setConstraints(lblmines, 3, 22 + 1);
//        map.getChildren().addAll(lbltime);
//        map.getChildren().addAll(lblmines);
        stats.getChildren().addAll(lbltime, lblmines);
        bigvbox.getChildren().addAll(stats);
        lbltime.setStyle("-fx-text-size: 40px;");
        lblmines.setStyle("-fx-text-size: 40px;");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            lbltime.setText("" + (Integer.valueOf(lbltime.getText())+1));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        reset(buttons, lbltime, lblmines);
        
        //on click
        for (Field[] buttons1 : buttons) {
            for (Field item : buttons1) {
                item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        MouseButton button = event.getButton();
                        if(button==MouseButton.PRIMARY){
                            if(!item.getText().equals("u") && !item.getText().equals("X")){
                                int x=item.getNum();
                                for(int g=-1; g<2; g++){
                                    for(int h=-1; h<2; h++){
                                        if(item.getIdi()+g<32 && item.getIdi()+g>-1 && item.getIdj()+h<22 && item.getIdj()+h>-1){
                                            if(buttons[item.getIdi()+g][item.getIdj()+h].getText().equals("X")){
                                                x--;
                                            }
                                        }
                                    }
                                }
                                if(x==0){
                                for(int g=-1; g<2; g++){
                                    for(int h=-1; h<2; h++){
                                        if(item.getIdi()+g<32 && item.getIdi()+g>-1 && item.getIdj()+h<22 && item.getIdj()+h>-1){
                                            if(!buttons[item.getIdi()+g][item.getIdj()+h].getText().equals("X")){
                                                buttons[item.getIdi()+g][item.getIdj()+h].setText("" + buttons[item.getIdi()+g][item.getIdj()+h].getNum());
                                                colornumbers(buttons[item.getIdi()+g][item.getIdj()+h]);
                                                if(buttons[item.getIdi()+g][item.getIdj()+h].getText().equals("0")){
                                                    colornumbers(item);
                                                    zeros(item, buttons);
                                                }
                                                checkIfDead(buttons[item.getIdi()+g][item.getIdj()+h], buttons, lbltime, lblmines);
                                            }
                                        }
                                    }
                                }
                            }}
                            if(!item.getText().equals("X")){
                                checkIfDead(item, buttons, lbltime, lblmines);
                                item.setText("" + item.getNum());
                            }
                            
                    
                            if(item.getText().equals("0")){
                                colornumbers(item);
                                zeros(item, buttons);
                            }
                            
                            checkIfWon(item, buttons, lbltime, lblmines);
                        }else if(button==MouseButton.SECONDARY){
                            if(item.getText().equals("u")){
                                item.setText("X");
                                item.setStyle("-fx-text-fill: red; -fx-background-color: #989898;");
                                lblmines.setText("" + (Integer.valueOf(lblmines.getText())-1));
                            }
                            else if(item.getText().equals("X")){
                                item.setText("u");
                                item.setStyle("-fx-text-fill: #CCCCCC; -fx-background-color: #CCCCCC;");
                                lblmines.setText("" + (Integer.valueOf(lblmines.getText())+1));
                            }
                        }
                        colornumbers(item);
                    }
                });
            }
        }
        
        
        StackPane root = new StackPane();
        root.getChildren().add(bigvbox);
        
        Scene scene = new Scene(root, 1300, 850);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());  
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
