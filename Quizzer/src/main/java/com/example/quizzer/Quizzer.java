package com.example.quizzer;

import com.sun.javafx.scene.control.LabeledText;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Quizzer extends Application {
    private User s;
    private Stage primaryStage;




    @Override
    public void start(Stage Primarystage) throws IOException {
        this.primaryStage = Primarystage;
        StackPane root = new StackPane();
        root.getStyleClass().add("scene-background");

        Text introtxt2 = new Text("Welcome to the Quizzer!");
        introtxt2.getStyleClass().add("text_40");

        Text introusertxt = new Text("username:");
        introusertxt.getStyleClass().add("text_25");


        Text max_char = new Text("(maximum 8 characters)");
        max_char.getStyleClass().add("text_13");


        TextField introuserinput = new TextField();
        introuserinput.getStyleClass().add("text_20");
        introuserinput.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 8 ? change : null
        ));
        introuserinput.setMaxWidth(170);
        introuserinput.setMinHeight(40);

        Button introuserconf = new Button("confirm");
        introuserconf.getStyleClass().add("text_25");
        introuserconf.setPrefWidth(170);
        introuserconf.setMinHeight(40);



        introuserconf.setOnMouseClicked(mouseEvent -> {

            if (introuserinput.getText() != "") {
                s = new User(introuserinput.getText());
                open_Difficulty_window();
            }
        });

        VBox introuser = new VBox();
        introuser.getChildren().addAll(introusertxt,introuserinput,max_char);
        introuser.setAlignment(Pos.BOTTOM_CENTER);
        introuser.setSpacing(20);

        VBox introduction = new VBox();
        introduction.getChildren().addAll(introtxt2,introuser,introuserconf);
        introduction.setAlignment(Pos.BOTTOM_CENTER);
        introduction.setSpacing(10);
        introduction.setPadding(new Insets(0,0,100,0));


        root.getChildren().add(introduction);
        Scene scene = new Scene(root, 600, 800);
        scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());
        Primarystage.setResizable(false);
        Primarystage.setTitle("Quizzer");
        Primarystage.setScene(scene);
        Primarystage.show();
    }

    private void open_Difficulty_window() {
        primaryStage.setTitle("Choose Difficulty");
        Text welcname = new Text("Welcome, "+s.getUsername()+"!");
        welcname.getStyleClass().add("text_25");


        Text totsc = new Text("Total score: "+s.getTotalScore());
        totsc.getStyleClass().add("text_25");


        Text choose_diff = new Text("Choose your difficulty:");
        choose_diff.getStyleClass().add("text_25");


        VBox diff_buttons = new VBox();
        diff_buttons.getChildren().add(choose_diff);
        diff_buttons.setSpacing(15);
        diff_buttons.setAlignment(Pos.CENTER);


        for (TestManager.Difficulty d : TestManager.Difficulty.values()) {
            Button diffBtn = new Button(d.name());
            diffBtn.getStyleClass().add("text_45");
            diffBtn.setPrefWidth(380);
            diffBtn.setPrefHeight(40);
            diffBtn.setOnMouseClicked(e -> {
                open_questions_window(d== TestManager.Difficulty.Easy ? new EasyQuestionManager():d== TestManager.Difficulty.Intermediate ?new IntermediateQuestionManager():new HardQuestionManager());
            });
            diff_buttons.getChildren().add(diffBtn);
        }


        Button prev_scores = new Button("Previous scores");
        prev_scores.getStyleClass().add("text_25");
        prev_scores.setOnMouseClicked(e -> {
            open_previousScores_window();
        });
        Button settings = new Button("Settings");
        settings.getStyleClass().add("text_25");
        settings.setOnMouseClicked(e -> {
            open_settings_window();
        });


        HBox bottom_buttons = new HBox();
        bottom_buttons.getChildren().addAll(prev_scores,settings);
        bottom_buttons.setSpacing(30);
        bottom_buttons.setAlignment(Pos.BOTTOM_CENTER);
        bottom_buttons.setPadding(new Insets(0,0,30,0));

        HBox userInfo=new HBox();
        userInfo.setAlignment(Pos.CENTER);
        userInfo.setSpacing(30);
        userInfo.setPadding(new Insets(40,20,0,20));//top, right, bottom, left

        HBox pad=new HBox();
        HBox.setHgrow(pad,Priority.ALWAYS);
        userInfo.getChildren().addAll(welcname,pad,totsc,Pfp.Create(s.getIcon()));


        VBox begin_screen = new VBox();
        begin_screen.getChildren().addAll(userInfo,diff_buttons,bottom_buttons);
        begin_screen.setSpacing(50);
        VBox.setVgrow(diff_buttons, Priority.ALWAYS);

        Scene scene = new Scene(begin_screen, 600, 800);
        scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    private void open_questions_window(TestManager testManager) {
        primaryStage.setTitle(testManager.getDifficulty().name()+ " difficulty questions!");

        class QuestionScene {
            final GridPane grid = new GridPane();
            final Text questionSlot=new Text();
            final Text total_score=new Text("Total score:"+testManager.getTotalScore());

            final Text time = new Text("00:00");
            final Timer timer = new Timer(time);

            void updateQuestion(){
                grid.getChildren().clear();
                questionSlot.setText(testManager.CurrentQuestion.getQuestion());
                for (int i = 0; i < testManager.CurrentQuestion.getAnswers().length; i++) {
                    Button answerBtn = new Button(testManager.CurrentQuestion.getAnswers()[i]);
                    answerBtn.getStyleClass().add("answerButtons");
                    answerBtn.setMaxWidth(380);
                    answerBtn.setMinWidth(380);
                    int finalI = i;
                    answerBtn.setOnMouseClicked(e -> {
                        grid.setDisable(true);

                        boolean correct=testManager.Evaluate(finalI);
                        answerBtn.getStyleClass().add(correct? "correctAnswer":"wrongAnswer");
                        total_score.setText("Total score:"+testManager.getTotalScore());


                        new java.util.Timer().schedule(new java.util.TimerTask() {
                            @Override public void run() {
                                Platform.runLater(() -> {
                                    if(testManager.RemainingQuestions() > 0) {
                                        testManager.GetQuestion();
                                        grid.setDisable(false);
                                        updateQuestion();
                                    }else{
                                        timer.stop();
                                        s.AddScore(testManager.getTotalScore());
                                        endingScene(testManager.getTotalScore(), timer.getTime());
                                    }});}},1000);});

                    int row = i/2;
                    int col = i%2;

                    grid.add(answerBtn, col, row);
                }
            }

            QuestionScene(){
                //grid.setAlignment(Pos.CENTER);//not needed, check css
                grid.setMaxWidth(800);
                grid.setMinWidth(800);
                grid.getStyleClass().add("answerSlot");
                grid.setMinHeight(160);
                grid.setMinHeight(160);
                grid.setHgap(30);
                grid.setVgap(30);

                updateQuestion();

                HBox pad = new HBox();
                HBox.setHgrow(pad, Priority.ALWAYS);

                total_score.getStyleClass().add("text_25");

                time.getStyleClass().add("text_25");
                HBox top_bar = new HBox();
                //top_bar.setSpacing(50);//not needed with the growing filler element
                top_bar.setMaxWidth(800);
                top_bar.setMinWidth(800);


                top_bar.getChildren().addAll(time,pad,total_score);



                HBox questions = new HBox();
                TextFlow textFlow = new TextFlow(questionSlot);
                textFlow.setMaxWidth(780);
                textFlow.setMaxHeight(140);
                textFlow.setTextAlignment(TextAlignment.CENTER);
                VBox textVbox = new VBox(textFlow);
                textVbox.setAlignment(Pos.CENTER);
                textVbox.setPrefHeight(150);
                questions.setMaxWidth(800);
                questions.setMinWidth(800);
                questions.setMaxHeight(150);
                questions.setMinHeight(150);
                questions.setAlignment(Pos.CENTER);
                questions.getChildren().add(textVbox);

                questions.getStyleClass().addAll("questionSlot");


                Button exit = new Button("← Exit");
                exit.getStyleClass().add("text_25");
                exit.setOnMouseClicked(e ->{
                    timer.stop();
                    open_Difficulty_window();
                });

                timer.start();

                final boolean[] paused = {false};
                Button pauseResume = new Button("Pause");
                pauseResume.getStyleClass().add("text_25");
                pauseResume.setOnMouseClicked(e->{
                    paused[0] =!paused[0];
                    pauseResume.setText(paused[0] ?"resume":"Pause");
                    timer.start();
                    if (paused[0]) {
                        grid.setDisable(true);
                        timer.stop();
                    } else {
                        grid.setDisable(false);
                        timer.start();
                    }
                });



                Text explainTimer = new Text("if you need a break press pause! :)");
                explainTimer.getStyleClass().addAll("text_13");

                HBox paddingBox = new HBox();
                HBox.setHgrow(paddingBox, Priority.ALWAYS);

                HBox bottom_row = new HBox();
                bottom_row.getChildren().addAll(pauseResume,paddingBox,exit);

                VBox root = new VBox();
                root.getChildren().addAll(top_bar,questions,grid,explainTimer,bottom_row);

                root.setPadding(new Insets(20,50,20,50));
                root.setSpacing(20);


                Scene scene = new Scene(root, 900, 600);
                scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());

                primaryStage.setScene(scene);
                primaryStage.show();

            }
        }

        QuestionScene question = new QuestionScene();
    }

    private void endingScene(Integer score,String time){
        primaryStage.setTitle("Result");
        VBox endTime = new VBox();
        VBox endScore = new VBox();
        VBox endImg = new VBox();
        VBox goBack = new VBox();
        HBox endScreen = new HBox();


        Text yourTime = new Text("Your time:");
        yourTime.getStyleClass().add("text_25");

        Button yourTimer = new Button(time);

        yourTimer.setPrefWidth(130);
        yourTimer.setMinHeight(30);

        yourTimer.getStyleClass().add("text_25");



        endTime.getChildren().addAll(yourTime,yourTimer);
        endTime.setAlignment(Pos.CENTER);
        endTime.getStyleClass().add("text_25");


        Text yourScore = new Text("Your score:");
        yourScore.getStyleClass().add("text_25");




        Button btnScore = new Button( score.toString());
        btnScore.setPrefWidth(130);
        btnScore.setMinHeight(30);
        btnScore.getStyleClass().add("text_25");
        endScore.getChildren().addAll(yourScore,btnScore);
        endScore.setAlignment(Pos.CENTER);


        Image image = new Image("SMILE.PNG");
        ImageView dinoImg = new ImageView(image);
        endImg.getChildren().add(dinoImg);
        endImg.setPadding(new Insets(0,0,0,70));

        endImg.setAlignment(Pos.CENTER);
        Button leave= new Button("← Exit");
        leave.getStyleClass().add("text_25");
        leave.setOnMouseClicked(e ->{
            open_Difficulty_window();
        });
        goBack.getChildren().add(leave);
        goBack.setAlignment(Pos.BOTTOM_RIGHT);
        goBack.setPadding(new Insets(0,0,0,30));

        endScreen.getChildren().addAll(endTime,endScore,endImg,goBack);
        endScreen.maxHeight(900);
        endScreen.setAlignment(Pos.CENTER);
        endScreen.setPadding(new Insets(20,20,20,20));
        endScreen.setSpacing(50);
        endScreen.maxWidth(600);

        Scene scene = new Scene(endScreen, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private void open_settings_window() {
        primaryStage.setTitle("Settings");

        VBox settingScreen = new VBox();

        AtomicReference<String> SelectedIcon=new AtomicReference<>();
        SelectedIcon.set(s.getIcon());

        class components {
            private RadioButton createImageRadioButton(String imagePath, ToggleGroup group) {
                RadioButton radioButton = new RadioButton();
                radioButton.getStyleClass().remove("radio-button");
                radioButton.getStyleClass().add("custom-radio-button");

                radioButton.setToggleGroup(group);
                radioButton.setGraphic(Pfp.Create(imagePath));
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    SelectedIcon.set(imagePath);
                });
                return radioButton;
            }
        }

        HBox iconChangeBtn = new HBox();
        Button iconChangeTxt = new Button("Set your icon:");
        iconChangeBtn.getChildren().add(iconChangeTxt);
        GridPane iconChangeIcons = new GridPane();
        iconChangeIcons.setHgap(10);
        iconChangeIcons.setVgap(10);
        iconChangeBtn.setAlignment(Pos.CENTER);

        components c = new components();
        ToggleGroup groupIcons = new ToggleGroup();

        try{
            var lines = Files.readAllLines(new File(getClass().getResource("/icons.txt").getFile()).toPath());
            for (int i = 0; i < lines.size(); i++) {
                RadioButton icon = c.createImageRadioButton(lines.get(i), groupIcons);
                int row = i/5;
                int col=i%5;
                iconChangeIcons.add(icon,col,row);
                if(lines.get(i).equals(s.getIcon())) icon.setSelected(true);
            }
        }catch (IOException e){System.out.println(e.toString());}

        Button exit = new Button("Cancel");
        exit.getStyleClass().add("text_25");
        exit.setOnMouseClicked(e ->{

            open_Difficulty_window();
        });

        iconChangeIcons.setAlignment(Pos.CENTER);
        HBox unContainer=new HBox();
        Button userChangeBtn = new Button("Set your username:");

        TextField newUsername = new TextField(s.getUsername());
        newUsername.setTextFormatter(new TextFormatter<String>(change ->
                change.getControlNewText().length() <= 8 ? change : null
        ));
        newUsername.setMaxWidth(150);
        unContainer.getChildren().addAll(userChangeBtn,newUsername);
        unContainer.setAlignment(Pos.CENTER);
        unContainer.setPadding(new Insets(100,0,0,0));

        HBox changes = new HBox();
        Button saveChanges = new Button("Save");
        saveChanges.getStyleClass().add("text_25");
        saveChanges.setOnMouseClicked(e -> {
            s.setIcon(SelectedIcon.get());
            s.setUsername(newUsername.getText());
        });
        changes.getChildren().addAll(saveChanges,exit);
        changes.setPadding(new Insets(200,0,0,0));
        changes.setAlignment(Pos.BOTTOM_CENTER);

        settingScreen.getChildren().addAll(iconChangeBtn,iconChangeIcons,unContainer,changes);
        settingScreen.setAlignment(Pos.CENTER);
        settingScreen.getStyleClass().add("text_25");
        settingScreen.setSpacing(30);
        settingScreen.setPadding(new Insets(40,40,40,40));
        Scene scene = new Scene(settingScreen,600, 800);
        scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void open_previousScores_window(){
        primaryStage.setTitle("Previous Scores");
        VBox previousScoresScreen = new VBox();
        previousScoresScreen.setPadding(new Insets(40,40,40,40));


        VBox scores = new VBox();
        for(User.Score i:  s.getPreviousScores()) {
            HBox hb=new HBox();
            Text tx=new Text(i.value+"");
            HBox pad=new HBox();
            HBox.setHgrow(pad,Priority.ALWAYS);
            hb.getChildren().addAll(tx,pad,new Text(i.stamp));
            hb.setPadding(new Insets(5,20,5,20));
            scores.getChildren().add(hb);
            scores.getStyleClass().add("scores");
        }
        scores.setMinHeight(600);
        scores.setMinWidth(520);

        previousScoresScreen.getChildren().add(scores);
        Button exit = new Button("Cancel");
        exit.getStyleClass().add("text_25");
        exit.setOnMouseClicked(e ->{
            open_Difficulty_window();
        });
        previousScoresScreen.setSpacing(30);
        previousScoresScreen.getChildren().addAll(exit);

        Scene scene = new Scene(previousScoresScreen,600, 800);
        scene.getStylesheets().add(getClass().getResource("/quizzer.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}