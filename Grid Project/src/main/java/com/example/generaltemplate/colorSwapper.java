package com.example.generaltemplate;

import java.util.ArrayList;

public class colorSwapper {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int playerOwned;
    public colorSwapper(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,int playerOwned){
        this.x = x;
        this.y = y;
        this.playerOwned=playerOwned;
        boardData[x][y]=playerOwned+1;
        startTime = System.nanoTime();
        this.borderBug=borderBug;
        this.borderDirection=borderDirection;
    }

    public int getPlayerOwned() {
        return playerOwned;
    }


    public boolean isBorderBug() {
        return borderBug;
    }

    public void changePieces(int[][] boardData, ArrayList<scorerPiece> scoringPieces, ArrayList<scorerPiece> playerPieces,ArrayList<player> players){
        ArrayList<scorerPiece> connectingPieces = new ArrayList<>();

        //all this extra code is used to implement the upgrade system to the color swapper bug

        if (boardData[x+1][y]==(playerOwned+1)%2+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x+1 && playerPieces.get(i).getY()==y){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
        }
        if(boardData[x-1][y]==(playerOwned+1)%2+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x-1 && playerPieces.get(i).getY()==y){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
        }
        if (boardData[x][y+1]==(playerOwned+1)%2+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x && playerPieces.get(i).getY()==y+1){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
        }
        if (boardData[x][y-1]==(playerOwned+1)%2+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x && playerPieces.get(i).getY()==y-1){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
        }

        for (int p=0;p<players.get(playerOwned).getColorSwapperLevel();p++){
            ArrayList<scorerPiece> tempPieces = new ArrayList<>();
            for (int i=0;i<connectingPieces.size();i++){
                if (boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]==(playerOwned+1)%2+1){
                    for (int b=0;b<playerPieces.size();b++){
                        if (playerPieces.get(b).getX()==connectingPieces.get(i).getX()+1 && playerPieces.get(b).getY()==connectingPieces.get(i).getY()){
                            boolean equals=false;
                            for (int j=0;j<connectingPieces.size();j++){
                                if (connectingPieces.get(j).equals(playerPieces.get(b))){
                                    equals=true;
                                }
                            }
                            if (!equals) {
                                tempPieces.add(playerPieces.get(b));
                            }

                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]==(playerOwned+1)%2+1){
                    for (int b=0;b<playerPieces.size();b++){
                        if (playerPieces.get(b).getX()==connectingPieces.get(i).getX()-1 && playerPieces.get(b).getY()==connectingPieces.get(i).getY()){
                            boolean equals=false;
                            for (int j=0;j<connectingPieces.size();j++){
                                if (connectingPieces.get(j).equals(playerPieces.get(b))){
                                    equals=true;
                                }
                            }
                            if (!equals) {
                                tempPieces.add(playerPieces.get(b));
                            }
                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]==(playerOwned+1)%2+1){
                    for (int b=0;b<playerPieces.size();b++){
                        if (playerPieces.get(b).getX()==connectingPieces.get(i).getX() && playerPieces.get(b).getY()==connectingPieces.get(i).getY()+1){
                            boolean equals=false;
                            for (int j=0;j<connectingPieces.size();j++){
                                if (connectingPieces.get(j).equals(playerPieces.get(b))){
                                    equals=true;
                                }
                            }
                            if (!equals) {
                                tempPieces.add(playerPieces.get(b));
                            }
                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]==(playerOwned+1)%2+1){
                    for (int b=0;b<playerPieces.size();b++){
                        if (playerPieces.get(b).getX()==connectingPieces.get(i).getX() && playerPieces.get(b).getY()==connectingPieces.get(i).getY()-1){
                            boolean equals=false;
                            for (int j=0;j<connectingPieces.size();j++){
                                if (connectingPieces.get(j).equals(playerPieces.get(b))){
                                    equals=true;
                                }
                            }
                            if (!equals) {
                                tempPieces.add(playerPieces.get(b));
                            }
                        }
                    }
                }
            }
            connectingPieces.addAll(tempPieces);
        }
        for (int i=0;i<connectingPieces.size();i++){
            playerPieces.remove(connectingPieces.get(i));
            scoringPieces.remove(connectingPieces.get(i));
            scoringPieces.add(new scorerPiece(connectingPieces.get(i).getX(),connectingPieces.get(i).getY(),boardData,false,"",playerOwned));
            players.get(playerOwned).addPiece(scoringPieces.get(scoringPieces.size()-1));
            boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()]=playerOwned+1;
        }
    }
    public long getStartTime() {
        return startTime;
    }
    public void ResetStartTime(){
        startTime = System.nanoTime();
    }


    public void changeLocation(int[][] boardData){
        if (borderBug) {
            int prevX = x;
            int prevyY = y;

            if (x+1<boardData[x].length && boardData[x+1][y]!=0&& boardData[x+1][y]!=4 && borderDirection.equals("down")){
                borderBug=false;
                borderDirection="";
            }else if (y+1<boardData[x].length &&boardData[x][y+1]!=0&& boardData[x][y+1]!=4 && borderDirection.equals("right")){
                borderBug=false;
                borderDirection="";
            }else if (x-1>-1 && boardData[x-1][y]!=0 && boardData[x-1][y]!=4  && borderDirection.equals("up")){
                borderBug=false;
                borderDirection="";
            }else if (y-1>-1 && boardData[x][y-1]!=0&& boardData[x][y-1]!=4 && borderDirection.equals("left")){
                borderBug=false;
                borderDirection="";
            }
            if (borderDirection.equals("right")) {
                y++;
            } else if (borderDirection.equals("left")) {
                y--;
            } else if (borderDirection.equals("down")) {
                x++;
            } else if (borderDirection.equals("up")) {
                x--;
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
            boardData[x][y] = playerOwned+1;
        }
    }
}
