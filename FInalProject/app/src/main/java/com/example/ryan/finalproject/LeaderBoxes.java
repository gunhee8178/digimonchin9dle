package com.example.ryan.finalproject;

/**
 * Created by darrentong on 5/2/16.
 */
public class LeaderBoxes {
    //Rank is calculated when leaderboard is generated
    //Stored_rank is stored in the database so there is something to compare
    //to when the leaderboard is generated
    public String name, rank, wins, losses, draws;
    public LeaderBoxes(String rank, String name, String wins, String losses, String draws){
        this.name = name;
        this.rank = rank;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
    }
    @Override
    public String toString(){
        return rank + " " + name + " " + wins + " " + losses + " " + draws;
    }
}