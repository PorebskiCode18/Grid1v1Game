package com.example.generaltemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class player {
    private int money;
    private String playerName;
    private ArrayList<scorerPiece> scorerPiecesOwned = new ArrayList<>();
    private int bombLevel=1;
    private int plasmaBeamLevel=1;
    private int wallLevel=0;
    private int spaceStationLevel=1;
    private int colorSwapperLevel=0;

    public player(int money,String playerName){
        this.money=money;
        this.playerName=playerName;
    }

    public int getColorSwapperLevel() {
        return colorSwapperLevel;
    }

    public int getBombLevel() {
        return bombLevel;
    }

    public int getPlasmaBeamLevel() {
        return plasmaBeamLevel;
    }

    public int getWallLevel() {
        return wallLevel;
    }

    public int getSpaceStationLevel() {
        return spaceStationLevel;
    }
    public void incWallLevel(){
        wallLevel++;
    }
    public void incBombLevel(){
        bombLevel++;
    }
    public void incPlasmaBeamLevel(){
        plasmaBeamLevel++;
    }
    public void incSpaceStationLevel(){
        spaceStationLevel++;
    }
    public void incColorSwapperLevel(){
        colorSwapperLevel++;
    }
    public void addPiece(scorerPiece newPiece){
        scorerPiecesOwned.add(newPiece);
    }
    public void removePiece(scorerPiece rmvPiece){
        scorerPiecesOwned.remove(rmvPiece);
    }
    public int currentScore(){
        return scorerPiecesOwned.size();
    }
    public ArrayList<scorerPiece> getOwnedBasic() {
        return scorerPiecesOwned;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMoney() {
        return money;
    }

    public void decreaseMoney(int cost) {
        money-=cost;
}
    public void addMoney(int inc){
        money+=inc;
    }
}
