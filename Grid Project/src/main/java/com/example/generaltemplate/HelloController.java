package com.example.generaltemplate;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class HelloController {

    @FXML
    public GridPane gpane;
    public ListView lstPieces;
    public Label lblTurn;
    public Label lblTimeLeft;
    public Label lblPlayer1;
    public Label lblScore2;
    public Label lblMoney2;
    public Label lblPlayer2;
    public Label lblScore1;
    public Label lblMoney1;
    public ListView lstUpgrades;
    private FileInputStream rr, bb, backk,exx,grr,expp,walll,station,forceBall;
    private Image r, b, back,ex,gr,exp,wall,stat,frcBall;

    private Button[][] board;
    private int[][] boardData;
    private ArrayList<player> players = new ArrayList<>();
    private ArrayList<scorerPiece> basicScoringPieces = new ArrayList<>();
    private ArrayList<EXBug> exBugs = new ArrayList<>();
    private ArrayList<spStation> spaceStations= new ArrayList<>();
    private ArrayList<wallFortress> wallFortresses = new ArrayList<>();
    private ArrayList<plasmaBeam> plasmaBeams = new ArrayList<>();
    private ArrayList<scorerTrail>scorerTrails= new ArrayList<>();
    private ArrayList<colorSwapper>colorSwappers = new ArrayList<>();
    private String selectedBug="";
    private int turn;
    private double timeLeft=11;
    private long time=System.nanoTime();


    public void handleStart(ActionEvent actionEvent) {
        lstPieces.getItems().add("Scorer Piece");
        lstPieces.getItems().add("Walls Vertical ($5)");
        lstPieces.getItems().add("Walls Horizontal ($5)");
        lstPieces.getItems().add("Exploding Orb ($7)");
        lstPieces.getItems().add("Color Swapper ($10)");
        lstPieces.getItems().add("Plasma Beam ($15)");
        lstPieces.getItems().add("Scorer Trail ($15)");
        lstPieces.getItems().add("Wall Fortress ($40)");
        lstPieces.getItems().add("Space Station ($50)");
        lstPieces.getItems().add("Nuke ($70)");

        lstUpgrades.getItems().add("Walls: Level 1 ($5)");
        lstUpgrades.getItems().add("Exploding Orb: Level 1 ($10)");
        lstUpgrades.getItems().add("Color Swapper: Level 1 ($15)");
        lstUpgrades.getItems().add("Plasma Beam: Level 1 ($20)");
        lstUpgrades.getItems().add("Space Station: Level 1 ($30)");
        turn = (int)(Math.random()*2);
        players.add(new player(1000,"Player 1"));
        players.add(new player(1000,"Player 2"));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Button();
                board[i][j].setMinHeight(20);
                board[i][j].setMinWidth(20);
                board[i][j].setMaxHeight(20);
                board[i][j].setMaxWidth(20);

                ImageView dummy = new ImageView(back);
                dummy.setFitHeight(20);
                dummy.setPreserveRatio(true);
                board[i][j].setGraphic(dummy);
                board[i][j].setStyle("-fx-border-color: #ff0000; -fx-border-width: 1px;");
                gpane.add(board[i][j],j,i);
            }
        }
        for (int i = 1; i < board.length-1; i++) {
            for (int j = 1; j < board[0].length - 1; j++) {
                double wallChance= Math.random()*1;
                if (wallChance>.98){
                    boardData[i][j]=6;
                }
            }
        }
        updateGame();
        gpane.setGridLinesVisible(true);
        EventHandler z =new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                row = GridPane.getRowIndex((Button) event.getSource());
                col = GridPane.getColumnIndex((Button) event.getSource());
                System.out.println(row + "," + col);
                selectedBug=lstPieces.getSelectionModel().getSelectedItems().toString().substring(1,lstPieces.getSelectionModel().getSelectedItems().toString().length()-1);
                System.out.println(selectedBug);
                if (selectedBug.equals("Scorer Piece") && (row==0 && boardData[1][col]==0||col==boardData.length-1&&boardData[row][boardData.length-2]==0||col==0 && boardData[row][1]==0||row==boardData[0].length-1&&boardData[boardData[0].length-2][col]==0)){
                    players.get(turn).addMoney(players.get(turn).currentScore());
                    if (row==0){
                        basicScoringPieces.add(new scorerPiece(row,col,boardData,true,"down",turn));
                        players.get(turn).addPiece(basicScoringPieces.get(basicScoringPieces.size()-1));
                    }else if(col== boardData.length-1){
                        basicScoringPieces.add(new scorerPiece(row,col,boardData,true,"left",turn));
                        players.get(turn).addPiece(basicScoringPieces.get(basicScoringPieces.size()-1));
                    }else if (col==0){
                        basicScoringPieces.add(new scorerPiece(row,col,boardData,true,"right",turn));
                        players.get(turn).addPiece(basicScoringPieces.get(basicScoringPieces.size()-1));
                    }else if (row==boardData[0].length-1){
                        basicScoringPieces.add(new scorerPiece(row,col,boardData,true,"up",turn));
                        players.get(turn).addPiece(basicScoringPieces.get(basicScoringPieces.size()-1));
                    }
                    turn++;
                    if (turn>players.size()-1){
                        turn=0;
                    }
                    timeLeft=11;
                }else if (selectedBug.equals("Exploding Orb ($7)") && players.get(turn).getMoney()>=7 && (row==0||col==boardData.length-1||col==0||row==boardData[0].length-1)){
                    players.get(turn).decreaseMoney(7);
                    if (row==0){
                        exBugs.add(new EXBug(row,col,boardData,true,"right",true,players.get(turn).getBombLevel()));
                    }else if(row== boardData.length-1){
                        exBugs.add(new EXBug(row,col,boardData,true,"left",true,players.get(turn).getBombLevel()));
                    }else if (col==0){
                        exBugs.add(new EXBug(row,col,boardData,true,"down",true,players.get(turn).getBombLevel()));
                    }else if (col==boardData[0].length-1){
                        exBugs.add(new EXBug(row,col,boardData,true,"up",true,players.get(turn).getBombLevel()));
                    }
                }else if (selectedBug.equals("Walls Horizontal ($5)")&& players.get(turn).getMoney()>=5&&row>0 && row< board.length-1 && col>0 && col< boardData[0].length-1 && boardData[row][col]==0){
                    players.get(turn).decreaseMoney(5);
                    for (int i=col-players.get(turn).getWallLevel();i<=col+players.get(turn).getWallLevel();i++){
                        if (i>0&&i<boardData[0].length-1&& boardData[row][i]==0){
                            boardData[row][i]=6;
                        }
                    }
                }else if(selectedBug.equals("Walls Vertical ($5)")&& players.get(turn).getMoney()>=5&&row>0 && row< board.length-1 && col>0 && col< boardData[0].length-1&& boardData[row][col]==0){
                    players.get(turn).decreaseMoney(5);
                    for (int i=row-players.get(turn).getWallLevel();i<=row+players.get(turn).getWallLevel();i++){
                        if (i>0&&i<boardData[0].length-1&& boardData[i][row]==0){
                            boardData[i][col]=6;
                        }
                    }
                }else if (selectedBug.equals("Space Station ($50)") && players.get(turn).getMoney()>=50&& (row==0 && boardData[1][col]==0||col==boardData.length-1&&boardData[row][boardData.length-2]==0||col==0 && boardData[row][1]==0||row==boardData[0].length-1&&boardData[boardData[0].length-2][col]==0)){
                    players.get(turn).decreaseMoney(50);
                    if (row==0){
                        spaceStations.add(new spStation(row,col,boardData,true,"down",turn,players.get(turn).getSpaceStationLevel()));
                    }else if(col== boardData.length-1){
                        spaceStations.add(new spStation(row,col,boardData,true,"left",turn,players.get(turn).getSpaceStationLevel()));
                    }else if (col==0){
                        spaceStations.add(new spStation(row,col,boardData,true,"right",turn,players.get(turn).getSpaceStationLevel()));
                    }else if (row==boardData[0].length-1){
                        spaceStations.add(new spStation(row,col,boardData,true,"up",turn,players.get(turn).getSpaceStationLevel()));
                    }
                }else if (selectedBug.equals("Wall Fortress ($40)") && players.get(turn).getMoney()>=40&& (row==0 && boardData[1][col]==0||col==boardData.length-1&&boardData[row][boardData.length-2]==0||col==0 && boardData[row][1]==0||row==boardData[0].length-1&&boardData[boardData[0].length-2][col]==0)){
                    players.get(turn).decreaseMoney(40);
                    if (row==0){
                        wallFortresses.add(new wallFortress(row,col,boardData,true,"down",turn));
                    }else if(col== boardData.length-1){
                        wallFortresses.add(new wallFortress(row,col,boardData,true,"left",turn));
                    }else if (col==0){
                        wallFortresses.add(new wallFortress(row,col,boardData,true,"right",turn));
                    }else if (row==boardData[0].length-1){
                        wallFortresses.add(new wallFortress(row,col,boardData,true,"up",turn));
                    }
                }else if (selectedBug.equals("Plasma Beam ($15)") && players.get(turn).getMoney()>=15 && (row==0 ||col==boardData.length-1||col==0 ||row==boardData[0].length-1)){
                    players.get(turn).decreaseMoney(15);
                    if (row==0){
                        plasmaBeams.add(new plasmaBeam(row,col,boardData,true,"right",players.get(turn).getPlasmaBeamLevel()));
                    }else if(row== boardData.length-1){
                        plasmaBeams.add(new plasmaBeam(row,col,boardData,true,"left",players.get(turn).getPlasmaBeamLevel()));
                    }else if (col==0){
                        plasmaBeams.add(new plasmaBeam(row,col,boardData,true,"down",players.get(turn).getPlasmaBeamLevel()));
                    }else if (col==boardData[0].length-1){
                        plasmaBeams.add(new plasmaBeam(row,col,boardData,true,"up",players.get(turn).getPlasmaBeamLevel()));
                    }
                }else if (selectedBug.equals("Nuke ($70)") && players.get(turn).getMoney()>=70 && (row==0||col==boardData.length-1||col==0||row==boardData[0].length-1)){
                    players.get(turn).decreaseMoney(70);
                    if (row==0){
                        exBugs.add(new EXBug(row,col,boardData,true,"right",false,4));
                    }else if(row== boardData.length-1){
                        exBugs.add(new EXBug(row,col,boardData,true,"left",false,4));
                    }else if (col==0){
                        exBugs.add(new EXBug(row,col,boardData,true,"down",false,4));
                    }else if (col==boardData[0].length-1){
                        exBugs.add(new EXBug(row,col,boardData,true,"up",false,4));
                    }
                }else if (selectedBug.equals("Scorer Trail ($15)") && players.get(turn).getMoney()>=15 && (row==0 && boardData[1][col]==0||col==boardData.length-1&&boardData[row][boardData.length-2]==0||col==0 && boardData[row][1]==0||row==boardData[0].length-1&&boardData[boardData[0].length-2][col]==0)){
                    players.get(turn).decreaseMoney(15);
                    if (row==0){
                        scorerTrails.add(new scorerTrail(row,col,boardData,true,"down",turn));
                    }else if(col== boardData.length-1){
                        scorerTrails.add(new scorerTrail(row,col,boardData,true,"left",turn));
                    }else if (col==0){
                        scorerTrails.add(new scorerTrail(row,col,boardData,true,"right",turn));
                    }else if (row==boardData[0].length-1){
                        scorerTrails.add(new scorerTrail(row,col,boardData,true,"up",turn));
                    }
                    turn++;
                    if (turn>players.size()-1){
                        turn=0;
                    }
                    timeLeft=11;
                }else if (selectedBug.equals("Color Swapper ($10)") && players.get(turn).getMoney()>=10 && (row==0 && boardData[1][col]==0||col==boardData.length-1&&boardData[row][boardData.length-2]==0||col==0 && boardData[row][1]==0||row==boardData[0].length-1&&boardData[boardData[0].length-2][col]==0)){
                    players.get(turn).decreaseMoney(10);
                    if (row==0){
                        colorSwappers.add(new colorSwapper(row,col,boardData,true,"down",turn));
                    }else if(col== boardData.length-1){
                        colorSwappers.add(new colorSwapper(row,col,boardData,true,"left",turn));
                    }else if (col==0){
                        colorSwappers.add(new colorSwapper(row,col,boardData,true,"right",turn));
                    }else if (row==boardData[0].length-1){
                        colorSwappers.add(new colorSwapper(row,col,boardData,true,"up",turn));
                    }
                    turn++;
                    if (turn>players.size()-1){
                        turn=0;
                    }
                    timeLeft=11;
                }
                updateGame();
            }
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setOnAction(z);
            }
        }
        start();
    }
    public void start(){
        new AnimationTimer(){
            @Override
            public void handle(long now){
                if (now-time>1000000000){
                    timeLeft=timeLeft-1;
                    lblTimeLeft.setText("Time Left: " + Math.round(timeLeft));
                    time=System.nanoTime();
                }
                if (timeLeft<1){
                    turn++;
                    if (turn>players.size()-1){
                        turn=0;
                    }
                    updateGame();
                    timeLeft=11;
                }
                for (int i=0;i<boardData.length;i++){
                    for (int j=0;j<boardData[i].length;j++){
                        if (boardData[i][j]==0 && (i==0 || j==0 || i==boardData.length-1 || j==boardData[i].length-1) ){
                            boardData[i][j]=4;
                        }
                    }
                }
                for (int i=0;i<plasmaBeams.size();i++){
                    if (now - plasmaBeams.get(i).getStartTime() > 100000000) {
                        plasmaBeams.get(i).changeLocation(boardData,players,basicScoringPieces);
                        plasmaBeams.get(i).ResetStartTime();
                        if (!plasmaBeams.get(i).isBorderBug()){
                            boardData[plasmaBeams.get(i).getX()][plasmaBeams.get(i).getY()]=0;
                        }
                    }
                }
                plasmaBeams.removeIf(x -> !x.isBorderBug());
                for (int i = 0; i < basicScoringPieces.size(); i++) {
                    if (now - basicScoringPieces.get(i).getStartTime() > 200000000) {
                        basicScoringPieces.get(i).changeLocation(boardData);
                        basicScoringPieces.get(i).ResetStartTime();
                    }
                }
                for (int i = 0; i < exBugs.size(); i++) {
                    if(now - exBugs.get(i).getStartTime()>200000000){
                        if (exBugs.get(i).getExplosionTimer()>0&& !exBugs.get(i).isBorderBug()){
                            exBugs.get(i).decreaseExplosionTimer();
                        }
                        if (!exBugs.isEmpty()) {
                            if (!exBugs.get(i).isBorderBug()) {
                                if (exBugs.get(i).explode(basicScoringPieces, spaceStations,boardData, players.get(0).getOwnedBasic(), players.get(1).getOwnedBasic())) {
                                    updateGame();
                                    for (int n = exBugs.get(i).getX() - exBugs.get(i).getViewDistance(); n <= exBugs.get(i).getX() + exBugs.get(i).getViewDistance(); n++) {
                                        for (int m = exBugs.get(i).getY() - exBugs.get(i).getViewDistance(); m <= exBugs.get(i).getY() + exBugs.get(i).getViewDistance(); m++) {
                                            if (m > -1 && n > -1 && n < boardData.length && m < boardData[n].length && boardData[n][m]!=6) {
                                                boardData[n][m] = 5;
                                                exBugs.get(i).setExplosionTimer(5);
                                            }
                                        }
                                    }
                                }
                            }
                            exBugs.get(i).changeLocation(boardData);
                            exBugs.get(i).ResetStartTime();
                        }
                        if (exBugs.get(i).getExplosionTimer()==0 && !exBugs.get(i).isBorderBug()){
                            for (int n = exBugs.get(i).getX()-exBugs.get(i).getViewDistance();n<=exBugs.get(i).getX()+exBugs.get(i).getViewDistance();n++) {
                                for (int m = exBugs.get(i).getY() - exBugs.get(i).getViewDistance(); m <= exBugs.get(i).getY() + exBugs.get(i).getViewDistance(); m++) {
                                    if (m > -1 && n > -1 && n < boardData.length && m < boardData[n].length && boardData[n][m]!=6) {
                                        boardData[n][m]=0;
                                    }
                                }
                            }
                            boardData[exBugs.get(i).getX()][exBugs.get(i).getY()] = 0;
                            exBugs.remove(exBugs.get(i));
                        }
                    }

                }
                for (int i = 0; i < spaceStations.size(); i++) {
                    if (now - spaceStations.get(i).getStartTime() > 500000000) {
                        if (!spaceStations.get(i).isBorderBug()) {
                            if (spaceStations.get(i).getSpawnTimer() > 0) {
                                spaceStations.get(i).decreaseSpawnTime();
                            }
                            if (spaceStations.get(i).getSpawnTimer() == 0) {
                                spaceStations.get(i).spawnPieces(boardData, basicScoringPieces, players.get(spaceStations.get(i).getPlayerOwned()).getOwnedBasic());
                                if (players.get(turn).getSpaceStationLevel()==1){
                                    spaceStations.get(i).setSpawnTimer(300);
                                }else if (players.get(turn).getSpaceStationLevel()==2){
                                    spaceStations.get(i).setSpawnTimer(200);
                                } else if (players.get(turn).getSpaceStationLevel()==3){
                                    spaceStations.get(i).setSpawnTimer(100);
                                }

                            }
                        }else {
                            spaceStations.get(i).changeLocation(boardData);
                            spaceStations.get(i).ResetStartTime();
                        }
                    }

                }
                for (int i = 0; i < wallFortresses.size(); i++) {
                    if (now - wallFortresses.get(i).getStartTime() > 200000000) {
                        if (!wallFortresses.get(i).isBorderBug()) {
                            wallFortresses.get(i).spawnWalls(boardData, players.get(wallFortresses.get(i).getPlayerOwned()).getOwnedBasic());
                            wallFortresses.remove(wallFortresses.get(i));
                        }else {
                            wallFortresses.get(i).changeLocation(boardData);
                            wallFortresses.get(i).ResetStartTime();
                        }
                    }
                }
                for (int i = 0; i < colorSwappers.size(); i++) {
                    if (now - colorSwappers.get(i).getStartTime() > 200000000) {
                        if (!colorSwappers.get(i).isBorderBug()) {
                            colorSwappers.get(i).changePieces(boardData, basicScoringPieces,players.get((colorSwappers.get(i).getPlayerOwned()+1)%2).getOwnedBasic(),players);
                            colorSwappers.remove(colorSwappers.get(i));
                        }else {
                            colorSwappers.get(i).changeLocation(boardData);
                            colorSwappers.get(i).ResetStartTime();
                        }
                    }
                }
                for (int i = 0; i < scorerTrails.size(); i++) {
                    if (now - scorerTrails.get(i).getStartTime() > 200000000) {
                        if (!scorerTrails.get(i).isBorderBug()) {
                            basicScoringPieces.add(new scorerPiece(scorerTrails.get(i).getX(),scorerTrails.get(i).getY(),boardData,false,"",scorerTrails.get(i).getPlayerTurn()));
                            players.get(scorerTrails.get(i).getPlayerTurn()).addPiece(basicScoringPieces.get(basicScoringPieces.size()-1));
                            scorerTrails.remove(scorerTrails.get(i));
                        }else {
                            scorerTrails.get(i).changeLocation(boardData,players,basicScoringPieces);
                            scorerTrails.get(i).ResetStartTime();
                        }
                    }

                }
                updateGame();
            }
        }.start();
    }
    public void updateGame(){
        lblTurn.setText("Turn: " + players.get(turn).getPlayerName());
        lblPlayer1.setText(players.get(0).getPlayerName());
        lblMoney1.setText("Money: " + players.get(0).getMoney());
        lblScore1.setText("Score: " + players.get(0).currentScore());
        lblPlayer2.setText(players.get(1).getPlayerName());
        lblMoney2.setText("Money: " + players.get(1).getMoney());
        lblScore2.setText("Score: " + players.get(1).currentScore());

        if (players.get(turn).getWallLevel()+1==3){
            lstUpgrades.getItems().set(0,"Walls Level 3 (Max)");
        }else{
            lstUpgrades.getItems().set(0,"Wall: Level "+(players.get(turn).getWallLevel()+1) + " ($"+(5*(players.get(turn).getWallLevel()+1))+")");
        }

        if (players.get(turn).getBombLevel()==3){
            lstUpgrades.getItems().set(1,"Exploding Orb Level 3 (Max)");
        }else{
            lstUpgrades.getItems().set(1,"Exploding Orb: Level "+players.get(turn).getBombLevel() + " ($"+(10*players.get(turn).getBombLevel())+")");
        }

        if (players.get(turn).getColorSwapperLevel()+1==3){
            lstUpgrades.getItems().set(2,"Color Swapper Level 3 (Max)");
        }else{
            lstUpgrades.getItems().set(2,"Color Swapper: Level "+(players.get(turn).getColorSwapperLevel()+1) + " ($"+(15*(players.get(turn).getColorSwapperLevel()+1))+")");
        }

        if (players.get(turn).getPlasmaBeamLevel()==3){
            lstUpgrades.getItems().set(3,"Plasma Beam Level 3 (Max)");
        }else{
            lstUpgrades.getItems().set(3,"Plasma Beam: Level "+players.get(turn).getPlasmaBeamLevel() + " ($"+(20*players.get(turn).getPlasmaBeamLevel())+")");
        }

        if (players.get(turn).getSpaceStationLevel()==3){
            lstUpgrades.getItems().set(4,"Space Station Level 3 (Max)");
        }else{
            lstUpgrades.getItems().set(4,"Space Station: Level "+players.get(turn).getSpaceStationLevel() + " ($"+(30*players.get(turn).getSpaceStationLevel())+")");
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(boardData[i][j]==0){
                    ImageView dummy = new ImageView(back);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                } else if (boardData[i][j]==1) {
                    ImageView dummy = new ImageView(r);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                } else if (boardData[i][j]==2) {
                    ImageView dummy = new ImageView(b);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }
                else if (boardData[i][j]==3) {
                    ImageView dummy = new ImageView(ex);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }
                else if (boardData[i][j]==4) {
                    ImageView dummy = new ImageView(gr);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==5) {
                    ImageView dummy = new ImageView(exp);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==6){
                    ImageView dummy = new ImageView(wall);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==7){
                    ImageView dummy = new ImageView(stat);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==8){
                    ImageView dummy = new ImageView(frcBall);
                    dummy.setFitHeight(20);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }
            }
        }
    }
    private int row,col;
    public HelloController(){
        int i=25;
        int j=25;
        board = new Button[i][j];
        boardData = new int[i][j];
        try{
            rr = new FileInputStream("src/main/resources/Pictures/redCircle.jpg");
            r = new Image(rr);
            exx = new FileInputStream("src/main/resources/Pictures/whiteBall.png");
            ex=new Image(exx);
            bb = new FileInputStream("src/main/resources/Pictures/blueCircle.jpg");
            b = new Image(bb);
            backk = new FileInputStream("src/main/resources/Pictures/black.jpg");
            back = new Image(backk);
            grr=new FileInputStream("src/main/resources/Pictures/green.jpg");
            gr = new Image(grr);
            expp=new FileInputStream("src/main/resources/Pictures/explosion.png");
            exp=new Image(expp);
            walll = new FileInputStream("src/main/resources/Pictures/wall.png");
            wall=new Image(walll);
            station = new FileInputStream("src/main/resources/Pictures/station.jpg");
            stat=new Image(station);
            forceBall= new FileInputStream("src/main/resources/Pictures/forceFieldBall.jpg");
            frcBall=new Image(forceBall);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void handleUpgrade(ActionEvent actionEvent) {
        if (lstUpgrades.getSelectionModel().getSelectedIndex()==0&& players.get(turn).getMoney()>=5*(players.get(turn).getWallLevel()+1)&&(players.get(turn).getWallLevel()+1)<3){
            players.get(turn).decreaseMoney(5*(players.get(turn).getWallLevel()+1));
            players.get(turn).incWallLevel();
            updateGame();
        }
        if (lstUpgrades.getSelectionModel().getSelectedIndex()==1&& players.get(turn).getMoney()>=10*players.get(turn).getBombLevel()&&players.get(turn).getWallLevel()<3){
            players.get(turn).decreaseMoney(10*players.get(turn).getBombLevel());
            players.get(turn).incBombLevel();
            updateGame();
        }
        if (lstUpgrades.getSelectionModel().getSelectedIndex()==2&& players.get(turn).getMoney()>=15*(players.get(turn).getColorSwapperLevel()+1)&&(players.get(turn).getColorSwapperLevel()+1)<3){
            players.get(turn).decreaseMoney(15*(players.get(turn).getColorSwapperLevel()+1));
            players.get(turn).incColorSwapperLevel();
            updateGame();
        }
        if (lstUpgrades.getSelectionModel().getSelectedIndex()==3&& players.get(turn).getMoney()>=20*players.get(turn).getPlasmaBeamLevel()&&players.get(turn).getPlasmaBeamLevel()<3){
            players.get(turn).decreaseMoney(20*players.get(turn).getPlasmaBeamLevel());
            players.get(turn).incPlasmaBeamLevel();
            updateGame();
        }
        if (lstUpgrades.getSelectionModel().getSelectedIndex()==4&& players.get(turn).getMoney()>=30*players.get(turn).getSpaceStationLevel()&&players.get(turn).getSpaceStationLevel()<3){
            players.get(turn).decreaseMoney(30*players.get(turn).getSpaceStationLevel());
            players.get(turn).incSpaceStationLevel();
            updateGame();
        }
    }

}