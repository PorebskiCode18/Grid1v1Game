package com.example.generaltemplate;

import java.util.ArrayList;

public class EXBug {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int explosionTimer=-1;
    private boolean mine;
    private int viewDistance;

    public EXBug(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,boolean mine,int viewDistance){
        this.x = x;
        this.y = y;
        this.viewDistance=viewDistance;
        this.mine=mine;
        boardData[x][y]=3;
        startTime = System.nanoTime();
        this.borderBug=borderBug;
        this.borderDirection=borderDirection;
    }

    public int getExplosionTimer() {
        return explosionTimer;
    }

    public void setExplosionTimer(int explosionTimer) {
        this.explosionTimer = explosionTimer;
    }
    public void decreaseExplosionTimer(){
        explosionTimer--;
    }

    public long getStartTime() {
        return startTime;
    }
    public void ResetStartTime(){
        startTime = System.nanoTime();
    }

    public boolean isBorderBug() {
        return borderBug;
    }

    public void changeLocation(int[][] boardData){
        if (borderBug) {
            int prevX = x;
            int prevyY = y;

            if (x+1<boardData[x].length && boardData[x+1][y]!=0&& boardData[x+1][y]!=4 && boardData[x+1][y]!=5&& borderDirection.equals("right")){
                borderBug=false;
                borderDirection="";
            }else if (y+1<boardData[x].length &&boardData[x][y+1]!=0&& boardData[x][y+1]!=4 && boardData[x][y+1]!=5&& borderDirection.equals("down")){
                borderBug=false;
                borderDirection="";
            }else if (x-1>-1 && boardData[x-1][y]!=0 && boardData[x-1][y]!=4 && boardData[x-1][y]!=5 && borderDirection.equals("left")){
                borderBug=false;
                borderDirection="";
            }else if (y-1>-1 && boardData[x][y-1]!=0&& boardData[x][y-1]!=4 && boardData[x][y-1]!=5 && borderDirection.equals("up")){
                borderBug=false;
                borderDirection="";
            }
            if (borderDirection.equals("right")) {
                x++;
            } else if (borderDirection.equals("left")) {
                x--;
            } else if (borderDirection.equals("down")) {
                y++;
            } else if (borderDirection.equals("up")) {
                y--;
            }

            if (x < 1) {
                x = 1;
                borderBug=false;
            }
            if (x > boardData.length-2) {
                borderBug=false;
                x = boardData.length-2;

            }
            if (y < 1) {
                y = 1;
                borderBug=false;
            }
            if (y > boardData.length-2) {
                y = boardData[0].length-2;
                borderBug=false;
            }
            boardData[prevX][prevyY] = 0;
            boardData[x][y] = 3;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine() {
        return mine;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public boolean explode(ArrayList<scorerPiece> scoringPieces, ArrayList<spStation> spcStations, int [][] boardData, ArrayList<scorerPiece> player1Owned, ArrayList<scorerPiece> player2Owned){
        boolean done = false;
        for (int i = scoringPieces.size() - 1; i > -1; i--) {
            if (scoringPieces.get(i).getX() >= x - viewDistance && scoringPieces.get(i).getX() <= x + viewDistance && scoringPieces.get(i).getY() >= y - viewDistance && scoringPieces.get(i).getY() <= y + viewDistance) {
                boardData[scoringPieces.get(i).getX()][scoringPieces.get(i).getY()] = 0;
                scorerPiece temp = scoringPieces.get(i);
                player1Owned.remove(temp);
                player2Owned.remove(temp);
                scoringPieces.remove(temp);
                done = true;
            }
        }
        for (int i = spcStations.size() - 1; i > -1; i--) {
            if (spcStations.get(i).getX() >= x - viewDistance && spcStations.get(i).getX() <= x + viewDistance && spcStations.get(i).getY() >= y - viewDistance && spcStations.get(i).getY() <= y + viewDistance) {
                boardData[spcStations.get(i).getX()][spcStations.get(i).getY()] = 0;
                spcStations.remove(spcStations.get(i));
                done = true;
            }
        }
        if (!mine){
            for (int i=x-viewDistance;i<x+viewDistance;i++){
                for (int j=y-viewDistance;j<y+viewDistance;j++){
                    if (i > 0 && j > 0 && i < boardData.length-1 && j < boardData[i].length-1 &&boardData[i][j]!=5&&boardData[i][j]!=0){
                        boardData[i][j]=0;
                        done = true;
                    }
                }
            }

        }
        return done;
    }
}
