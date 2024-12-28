package com.example.generaltemplate;

import java.util.ArrayList;

public class spStation {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int spawnTimer;
    private int playerOwned;
    public spStation(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,int playerOwned,int spLevel){
        this.x = x;
        this.y = y;
        if (spLevel==1){
            spawnTimer=300;
        }else if (spLevel==2){
            spawnTimer=200;
        }else if (spLevel==3){
            spawnTimer=100;
        }
        this.playerOwned=playerOwned;
        boardData[x][y]=7;
        startTime = System.nanoTime();
        this.borderBug=borderBug;
        this.borderDirection=borderDirection;
    }

    public int getPlayerOwned() {
        return playerOwned;
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public boolean isBorderBug() {
        return borderBug;
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }
    public void spawnPieces(int[][] boardData, ArrayList<scorerPiece> scoringPieces,ArrayList<scorerPiece> playerPieces){
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


        // this while loop is used to place all the pieces that are in connection to the space station or connected to a piece that is connected
        //in other words in the cluster of connected pieces to the space station


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

        for (int i=0;i<connectingPieces.size();i++){
            if (Math.random()>.75){
                int direction = (int)(Math.random()*4);
                if (direction==0 && boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]==0){
                    boardData[connectingPieces.get(i).getX()+1][connectingPieces.get(i).getY()]=playerOwned;
                    scoringPieces.add(new scorerPiece(connectingPieces.get(i).getX()+1,connectingPieces.get(i).getY(),boardData,false,"",playerOwned));
                    playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
                }else if (direction==1 && boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]==0){
                    boardData[connectingPieces.get(i).getX()-1][connectingPieces.get(i).getY()]=playerOwned;
                    scoringPieces.add(new scorerPiece(connectingPieces.get(i).getX()-1,connectingPieces.get(i).getY(),boardData,false,"",playerOwned));
                    playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
                }if (direction==2 && boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]==0){
                    boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()+1]=playerOwned;
                    scoringPieces.add(new scorerPiece(connectingPieces.get(i).getX(),connectingPieces.get(i).getY()+1,boardData,false,"",playerOwned));
                    playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
                }if (direction==3 && boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]==0){
                    boardData[connectingPieces.get(i).getX()][connectingPieces.get(i).getY()-1]=playerOwned;
                    scoringPieces.add(new scorerPiece(connectingPieces.get(i).getX(),connectingPieces.get(i).getY()-1,boardData,false,"",playerOwned));
                    playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
                }
            }
        }
        if (Math.random()>.75){
            int direction = (int)(Math.random()*4);
            if (direction==0 && boardData[x+1][y]==0){
                boardData[x+1][y]=playerOwned;
                scoringPieces.add(new scorerPiece(x+1,y,boardData,false,"",playerOwned));
                playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
            }else if (direction==1 && boardData[x-1][y]==0){
                boardData[x-1][y]=playerOwned;
                scoringPieces.add(new scorerPiece(x-1,y,boardData,false,"",playerOwned));
                playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
            }if (direction==2 && boardData[x][y+1]==0){
                boardData[x][y+1]=playerOwned;
                scoringPieces.add(new scorerPiece(x,y+1,boardData,false,"",playerOwned));
                playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
            }if (direction==3 && boardData[x][y-1]==0){
                boardData[x][y-1]=playerOwned;
                scoringPieces.add(new scorerPiece(x,y-1,boardData,false,"",playerOwned));
                playerPieces.add(scoringPieces.get(scoringPieces.size()-1));
            }
        }



    }

    public void decreaseSpawnTime(){
        spawnTimer--;
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
            boardData[x][y] = 7;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
