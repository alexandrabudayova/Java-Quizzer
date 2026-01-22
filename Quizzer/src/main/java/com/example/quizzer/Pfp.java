package com.example.quizzer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Pfp {
    static StackPane Create(String path)
    {
        ImageView icon = new ImageView(new Image(path));
        Circle icon_c = new Circle(25);
        icon_c.getStyleClass().add("circle");
        StackPane icons = new StackPane(icon_c,icon);
        return icons;
    }
}
