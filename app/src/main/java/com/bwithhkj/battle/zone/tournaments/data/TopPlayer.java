package com.bwithhkj.battle.zone.tournaments.data;


public class TopPlayer {

    private String playerName;
    private String playerWinning;
    private String position;

    public TopPlayer() {
    }

    public TopPlayer(String playerName, String playerWinning, String position) {
        this.playerName = playerName;
        this.playerWinning = playerWinning+"0";
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerWinning() {
        return playerWinning;
    }

    public void setPlayerWinning(String playerWinning) {
        this.playerWinning = playerWinning;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
