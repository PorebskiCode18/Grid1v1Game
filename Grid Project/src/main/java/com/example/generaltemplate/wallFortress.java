package com.example.generaltemplate;

import java.util.ArrayList;

public class wallFortress {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int playerOwned;
    public wallFortress(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,int playerOwned){
        this.x = x;
        this.y = y;
        this.playerOwned=playerOwned;
        boardData[x][y]=8;
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
    public void spawnWalls(int[][] boardData, ArrayList<scorerPiece> playerPieces){
        ArrayList<scorerPiece> connectingPieces = new ArrayList<>();
        boolean isConnectingPieces=true;
        boolean around=false;
        if (boardData[x+1][y]==playerOwned+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x+1 && playerPieces.get(i).getY()==y){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
            around=true;
        }
        if(boardData[x-1][y]==playerOwned+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x-1 && playerPieces.get(i).getY()==y){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
            around=true;
        }
        if (boardData[x][y+1]==playerOwned+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x && playerPieces.get(i).getY()==y+1){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
            around=true;
        }
        if (boardData[x][y-1]==playerOwned+1){
            for (int i=0;i<playerPieces.size();i++){
                if (playerPieces.get(i).getX()==x && playerPieces.get(i).getY()==y-1){
                    connectingPieces.add(playerPieces.get(i));
                }
            }
            around=true;
        }
        if (!around){
            isConnectingPieces=false;
        }

        while (isConnectingPieces){
            int numHaveConnecting=0;
            ArrayList<scorerPiece> tempPieces = new ArrayList<>();
            for (int i=0;i<connectingPieces.size();i++){
                boolean hasConnection=false;
                if (boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]==playerOwned+1){
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
                                hasConnection = true;
                            }

                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]==playerOwned+1){
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
                                hasConnection = true;
                            }
                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]==playerOwned+1){
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
                                hasConnection = true;
                            }
                        }
                    }
                }
                if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]==playerOwned+1){
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
                                hasConnection = true;
                            }
                        }
                    }
                }
                if (hasConnection){
                    numHaveConnecting++;
                }
            }
            connectingPieces.addAll(tempPieces);
            if (numHaveConnecting==0){
                isConnectingPieces=false;
            }
        }
        // code below used to randomly decide if the colony will expand and what direction
        boardData[x][y]=0;
        for (int i=0;i<connectingPieces.size();i++){
            if (boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]==0){
                boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]=6;
            }
            if (boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]==0){
                boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]=6;
            }
            if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]==0){
                boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]=6;
            }
            if (boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]==0){
                boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]=6;
            }
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

            if (x+1<boardData[x].length && boardData[x+1][y]!=0&& boardData[x+1][y]!=4 && boardData[x+1][y]!=5&& borderDirection.equals("down")){
                borderBug=false;
                borderDirection="";
            }else if (y+1<boardData[x].length &&boardData[x][y+1]!=0&& boardData[x][y+1]!=4 && boardData[x][y+1]!=5&& borderDirection.equals("right")){
                borderBug=false;
                borderDirection="";
            }else if (x-1>-1 && boardData[x-1][y]!=0 && boardData[x-1][y]!=4 && boardData[x-1][y]!=5 && borderDirection.equals("up")){
                borderBug=false;
                borderDirection="";
            }else if (y-1>-1 && boardData[x][y-1]!=0&& boardData[x][y-1]!=4 && boardData[x][y-1]!=5 && borderDirection.equals("left")){
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
            boardData[x][y] = 8;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
