package com.example.generaltemplate;

import java.util.ArrayList;

public class plasmaBeam {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int wallBreaks;

    public plasmaBeam(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,int wallBreaks){
        this.x = x;
        this.y = y;
        boardData[x][y]=5;
        this.wallBreaks=wallBreaks;
        startTime = System.nanoTime();
        this.borderBug=borderBug;
        this.borderDirection=borderDirection;
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

    public void changeLocation(int[][] boardData, ArrayList<player>players,ArrayList<scorerPiece> allScorers){
        if (borderBug) {
            if (x+1<boardData[x].length && boardData[x+1][y]!=0 && boardData[x+1][y]!=5&& boardData[x+1][y]!=4 && boardData[x+1][y]!=5&& borderDirection.equals("right")){
                if (wallBreaks==0) {
                    borderBug = false;
                    borderDirection = "";
                    for (int i=x;i>=0;i--){
                        boardData[i][y]=0;
                    }
                }else{
                    if (boardData[x+1][y]==6){
                        wallBreaks-=1;
                    }
                }
            }else if (y+1<boardData[x].length &&boardData[x][y+1]!=0&&boardData[x][y+1]!=5&& boardData[x][y+1]!=4 && boardData[x][y+1]!=5&& borderDirection.equals("down")){
                if (wallBreaks==0) {
                    borderBug = false;
                    borderDirection = "";
                    for (int i=y;i>=0;i--){
                        boardData[x][i]=0;
                    }
                }else{
                    if (boardData[x][y+1]==6){
                        wallBreaks-=1;
                    }
                }
            }else if (x-1>-1 && boardData[x-1][y]!=0 && boardData[x-1][y]!=5&& boardData[x-1][y]!=4 && boardData[x-1][y]!=5 && borderDirection.equals("left")){
                if (wallBreaks==0) {
                    borderBug = false;
                    borderDirection = "";
                    for (int i=x;i<=boardData.length-1;i++){
                        boardData[i][y]=0;
                    }
                }else{
                    if (boardData[x-1][y]==6){
                        wallBreaks-=1;
                    }
                }
            }else if (y-1>-1 && boardData[x][y-1]!=0&&boardData[x][y-1]!=5&& boardData[x][y-1]!=4 && boardData[x][y-1]!=5 && borderDirection.equals("up")){
                if (wallBreaks==0) {
                    borderBug = false;
                    borderDirection = "";
                    for (int i=y;i<=boardData[0].length-1;i++){
                        boardData[x][i]=0;
                    }
                }else{
                    if (boardData[x][y-1]==6){
                        wallBreaks-=1;
                    }
                }
            }

            if (x+1<boardData[x].length && (boardData[x+1][y]==1|| boardData[x+1][y]==2)&& borderDirection.equals("right")){
                for (int i=0;i<allScorers.size();i++){
                    if (allScorers.get(i).getX()==x+1&& allScorers.get(i).getY()==y){
                        allScorers.remove(allScorers.get(i));
                        players.get(boardData[x+1][y]-1).removePiece(allScorers.get(i));
                    }
                }
            }else if (y+1<boardData[x].length && (boardData[x][y+1]==1|| boardData[x][y+1]==2)&& borderDirection.equals("down")){
                for (int i=0;i<allScorers.size();i++){
                    if (allScorers.get(i).getX()==x&& allScorers.get(i).getY()==y+1){
                        allScorers.remove(allScorers.get(i));
                        players.get(boardData[x][y+1]-1).removePiece(allScorers.get(i));
                    }
                }
            }else if (x-1>-1 && (boardData[x-1][y]==1|| boardData[x-1][y]==2)&& borderDirection.equals("left")){
                for (int i=0;i<allScorers.size();i++){
                    if (allScorers.get(i).getX()==x-1&& allScorers.get(i).getY()==y){
                        allScorers.remove(allScorers.get(i));
                        players.get(boardData[x-1][y]-1).removePiece(allScorers.get(i));
                    }
                }
            }else if (y-1>-1 && (boardData[x][y-1]==1|| boardData[x][y-1]==2)&& borderDirection.equals("up")){
                for (int i=0;i<allScorers.size();i++){
                    if (allScorers.get(i).getX()==x&& allScorers.get(i).getY()==y-1){
                        players.get(boardData[x][y-1]-1).removePiece(allScorers.get(i));
                        allScorers.remove(allScorers.get(i));
                    }
                }
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
                for (int i=x;i<=boardData.length-1;i++){
                    boardData[i][y]=0;
                }
            }
            if (x > boardData.length-2) {
                borderBug=false;
                x = boardData.length-2;
                for (int i=x;i>=0;i--){
                    boardData[i][y]=0;
                }
            }
            if (y < 1) {
                y = 1;
                borderBug=false;
                for (int i=y;i<=boardData[0].length-1;i++){
                    boardData[x][i]=0;
                }
            }
            if (y > boardData.length-2) {
                y = boardData[0].length-2;
                borderBug=false;
                for (int i=y;i>=0;i--){
                    boardData[x][i]=0;
                }

            }
            boardData[x][y] = 5;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
