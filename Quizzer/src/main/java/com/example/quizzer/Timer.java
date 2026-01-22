package com.example.quizzer;


import javafx.scene.text.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Timer {
    private Text text;
    private long elapsedTime = 0;
    private boolean running = false;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> stopwatchTask;


    public Timer(Text text){
        this.text = text;
    }

    public void start() {
        if (!running ) {
            running = true;
            scheduler = Executors.newSingleThreadScheduledExecutor();
            stopwatchTask=scheduler.scheduleAtFixedRate(() -> {
                elapsedTime++;
                text.setText(getTime());
            }, 0, 1, TimeUnit.SECONDS);

        }
    }




    public String getTime() {

        long seconds = elapsedTime  % 60;
        long minutes = (elapsedTime / 60);

        return String.format("%02d:%02d", minutes, seconds);
    }

    public void stop() {
        if(running) {
            running = false;
            stopwatchTask.cancel(true);
        }
    }

    //not needed
    /*public void restart() {
        stop();
        elapsedTime = 0;
        running = false;
        start();
    }*/
}
