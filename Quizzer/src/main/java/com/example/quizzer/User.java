package com.example.quizzer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.prefs.Preferences;

public class User {
    class Score{
        int value;
        String stamp;
        Score(int value){
            this.value = value;
            this.stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }


        public String toString()
        {
            return value+"_"+stamp;
        }
        Score(String s)
        {
            String[] vals=s.split("_");
            this.value=Integer.parseInt(vals[0]);
            this.stamp=vals[1];
        }
    }
    String userName;
    String uID;

    private Preferences pref= Preferences.userNodeForPackage(User.class);

    public User(String username) {
        this.userName = username;
        //get the uID associated with this user if any
        uID= pref.get(username, null);
        if ( uID== null) {//new user
            uID = UUID.randomUUID().toString();
            setUsername(username);
            setIcon("DINO.PNG");
        }
        //else {}//user already exists no need to do anything as the uID has already been loaded
    }

    //output stored data to the output stream, optionally nuke all stored data
    public void Debug(boolean clear)
    {
        try{
            System.out.println("--------Debug--------/n");
            for(String k:pref.keys())
                System.out.println(k+": "+pref.get(k,""));
            if (clear) pref.clear();
            System.out.println("--------End--------/n");
        }catch (Exception e){System.out.println(e.toString());}
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        System.out.println(username+"<<< this >>>>"+this.userName);

        pref.remove(this.userName);//remove old value if it exists (needed in case we're changing the username)
        pref.put(username, uID);//associate the new username with the ID
        this.userName = username;
    }

    public int getTotalScore() {
        List<Score> scores=getPreviousScores();
        int sum=0;
        for (Score s:scores)
            sum+=s.value;
        return sum;
    }


    public void AddScore(int score) {
        Score s = new Score(score);
        String old=pref.get(uID+"_scores", null);
        if (old == null)
            pref.put(uID+"_scores", s.toString());
        else
            pref.put(uID+"_scores", old+","+s.toString());
    }

    public List<Score> getPreviousScores() {
        String ss=pref.get(uID+"_scores", null);
        if(ss!=null)
            return Arrays.stream(ss.split(",")).map(Score::new).toList();
        return new ArrayList<>();
    }

    public void setIcon(String icon) {
        pref.put(uID+"_icon", icon);
    }


    public String getIcon() {
        return pref.get(uID+"_icon", "DINO.PNG");//return the stored icon or the default
    }
}
