package com.example.generaltemplate;

public class scorerPiece {
    private int x;
    private int y;
    private long startTime;
    private boolean borderBug;
    private String borderDirection;
    private int playerTurn;
    public scorerPiece(int x, int y,int[][] boardData,boolean borderBug, String borderDirection,int playerTurn){
        this.x = x;
        this.y = y;
        this.playerTurn=playerTurn;
        boardData[x][y]=playerTurn+1;
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
            boardData[x][y] = playerTurn+1;
        }
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
