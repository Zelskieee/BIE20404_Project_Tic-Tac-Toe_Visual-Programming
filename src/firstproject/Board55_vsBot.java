/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package firstproject;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

//connection
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;

/**
 *
 * @author Ahmad Syukri
 */
public class Board55_vsBot extends javax.swing.JFrame {
    
    //board
    private String whoseTurn="X";
    private int playerOneScore=1;
    private int playerTwoScore=1;
    private int clicktime=1;
    
    //player turn
    private boolean playerTurn;
    
    //audio
    private Clip audioClip;
    private FloatControl gainControl;
    private float originalVolume;
    private boolean isMuted;
    
    
    //bgaudio
    private Clip audioClip2;
    private FloatControl gainControl2;
    private float originalVolume2;
    private boolean isBGsoundMuted;
    private boolean isSongGenerated;
    
    //winSound
    private Clip audioClip3;
    private FloatControl gainControl3;
    private float originalVolume3;
    private boolean isWinSoundMuted;
    
    //drawSound
    private Clip audioClip4;
    private FloatControl gainControl4;
    private float originalVolume4;
    private boolean isDrawSoundMuted;
    
    //loseSound
    private Clip audioClip5;
    private FloatControl gainControl5;
    private float originalVolume5;
    private boolean isLoseSoundMuted;
    
    //timer
    private Timer timer;
    private int totalSecondsRemaining = 0;
    //exit game
    private boolean isExit;
    
    //switchScore
    private boolean score_info;
    
    //switchTimer
    private boolean timer_info;
    
    //switchBoard
    private boolean board_counter;
    
    //connection
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    /**
     * Creates new form Homepage
     */
    public Board55_vsBot() {
        this.isExit = false;
        setUndecorated(true);
        initComponents();
        setBackground(new Color(0,0,0,0));
        mainPanel.setBackground(new Color(0,0,0,0));
        
        //setIcon
        setIcon();
        
        // bg Sound
        generateBGsound();
        
        //set win sound
        setWinSound();
        
        //set draw sound
        setDrawSound();
        
        //set lose sound
        setLoseSound();
        
        //set button Sound
        setButtonSound();
        
        // Set player's turn to true to start the game
        playerTurn = true;
        
        //connection
        try {
            Connect();
        } catch (SQLException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //settingInfo
        currentSetting();
        
        //timer
        if(timer_info){
        Timer();
        }
        
        // Load the image icon
        ImageIcon icon = new ImageIcon("src/img/redglitter.jpg");

        // Set the icon to the label
        bg.setIcon(icon);
        
        // Fit the icon to the size of the label
        fitIconToLabel(bg, icon);
    }
    
    private void fitIconToLabel(JLabel bg, ImageIcon icon) {
        // Get the size of the label
        int labelWidth = bg.getWidth();
        int labelHeight = bg.getHeight();

        // Resize the icon to fit the label size
        Image image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        icon.setImage(image);
        bg.setIcon(icon);
    }
    
    private void setIcon(){
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Icon/TicTacToeIcon.png"));
    }
    
    public void Connect() throws SQLException
    {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe","root","");
            System.out.println("Connect!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void currentSetting(){
        
        try {
            
            // Define the SQL query to retrieve data from the table
            String sqlQuery = "SELECT Match_timer, Board_info, Score FROM setting";

            // Prepare the SQL statement
            pst = con.prepareStatement(sqlQuery);

            // Execute the query
            rs = pst.executeQuery();

            // Retrieve the first value from the result set
            if (rs.next()) {
                boolean match_timer = rs.getBoolean("Match_timer");
                boolean board_info = rs.getBoolean("Board_info");
                boolean score = rs.getBoolean("Score");
                // Set the selected item of the combo box to match the retrieved value
                
                //matchtimer
                if (match_timer){
                    timer_info=true;
                    txtTimer.setEnabled(true);
                    timerIcon.setEnabled(true);
                }
                else {
                    timer_info=false;
                    txtTimer.setText("--:--");
                    txtTimer.setEnabled(false);
                    timerIcon.setEnabled(false);
                    
                }
                
                //boardinfo
                if (board_info){
                    board_counter=true;
                    txtClick.setEnabled(true);
                    clickIcon.setEnabled(true);
                }
                else {
                    board_counter=false;
                    txtClick.setText("-/-");
                    txtClick.setEnabled(false);
                    clickIcon.setEnabled(false);
                    
                }
                
                //scoreinfo
                if (score){
                    score_info = true;
                    player1score.setEnabled(true);
                    player1score.setEnabled(true);
                }
                else {
                    score_info = false;
                    player1score.setText("-");
                    player2score.setText("-");
                }
            }

            // Close the result set, statement, and connection
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential SQL errors here
        }
    }
    
    private void generateBGsound(){
        // Code to generate the song goes here
        
        // Load and play the audio file
        if(isBGsoundMuted==true && isSongGenerated==true){
            
        }
        else if(isBGsoundMuted==false && isSongGenerated==false)
        {
        try {
            File audioFile2 = new File("src/audio/bgsound.wav");
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
            
            // Get the audio clip and open it
            audioClip2 = AudioSystem.getClip();
            audioClip2.open(audioStream2);

            // Get the volume control
            gainControl2 = (FloatControl) audioClip2.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume2 = gainControl2.getValue();
            isSongGenerated = true;
            
            // Add listener to restart the clip when it stops
            audioClip2.addLineListener((LineEvent event) -> {
                if (event.getType() == LineEvent.Type.STOP && isExit == false) {
                    audioClip2.setFramePosition(0); // Reset frame position
                    audioClip2.start(); // Start playing again
                }
            });
            
            // Start playing the audio clip
            audioClip2.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            // Handle exceptions
        }
        }
    }
    
    private void resetScore(){
        if (score_info){
        player1score.setText("0");
        player2score.setText("0");
        }
    }
    
    
    private void Timer(){
        
         
    
         // Set initial display to 0 minutes and 00 seconds
        timer = new Timer(1000, (ActionEvent e) -> {
        totalSecondsRemaining++;
        
        int minutes = totalSecondsRemaining / 60; // Calculate the minutes
        int seconds = totalSecondsRemaining % 60; // Calculate the remaining seconds
        
        // Format the minutes and seconds with leading zeros if needed
        String timeText = String.format("%02d:%02d", minutes, seconds);
        
        txtTimer.setText(timeText);
        
        if (minutes == 5) { // Change the condition as needed
            Status.setText("Timed out!");
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(null, "Last scores is saved\nPlay again?", "TIMED OUT", JOptionPane.YES_NO_OPTION);
        
            switch (choice) {
            case JOptionPane.YES_OPTION -> {
                txtTimer.setText("00:00");
                resetGame();
                resetScore();
                Status.setText("Who will win?");
                
            }
            // Perform action if user clicked Yes
            case JOptionPane.NO_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);
            }
            
            case JOptionPane.CLOSED_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);   
            }
            
            default -> {
            }
        }
            // Timer has finished, do something here
        }
    });
    
    timer.start();
    }
    
    private void detDraw(){
        String one=board1.getText();
        String two=board2.getText();
        String three=board3.getText();
        String four=board4.getText();
        String five=board5.getText();
        String six=board6.getText();
        String seven=board7.getText();
        String eight=board8.getText();
        String nine=board9.getText();
        String ten=board10.getText();
        String eleven=board11.getText();
        String twelve=board12.getText();
        String thirteen=board13.getText();
        String fourteen=board14.getText();
        String fifteen=board15.getText();
        String sixteen=board16.getText();
        String seventeen=board17.getText();
        String eighteen=board18.getText();
        String nineteen=board19.getText();
        String twenty=board20.getText();
        String twentyOne=board21.getText();
        String twentyTwo=board22.getText();
        String twentyThree=board23.getText();
        String twentyFour=board24.getText();
        String twentyFive=board25.getText();
        
       
        if(!"".equals(one) && !"".equals(two) && !"".equals(three) && !"".equals(four) && !"".equals(five) && !"".equals(six) && !"".equals(seven) && !"".equals(eight) && !"".equals(nine) && !"".equals(ten) && !"".equals(eleven) && !"".equals(twelve) && !"".equals(thirteen) && !"".equals(fourteen) && !"".equals(fifteen) && !"".equals(sixteen) && !"".equals(seventeen) && !"".equals(eighteen) && !"".equals(nineteen) && !"".equals(twenty) && !"".equals(twentyOne) && !"".equals(twentyTwo) && !"".equals(twentyThree) && !"".equals(twentyFour) && !"".equals(twentyFive)){
            
            drawSound();
            
            Status.setText("Draw");
            if (timer_info){
            timer.stop();
            }
        
        
            int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Draw", JOptionPane.YES_NO_OPTION);
        
            switch (choice) {
                case JOptionPane.YES_OPTION -> {
                resetGame();
                Status.setText("Who will win?");
                }
                // Perform action if user clicked Yes
                case JOptionPane.NO_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);
                }
                
                case JOptionPane.CLOSED_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);   
                }
                
                default -> {
                }
            }
            
        }
        
    }
    
    private void determineWhoseTurn(){
        if(whoseTurn.equalsIgnoreCase("X")){
            whoseTurn="O";
        }else{
            whoseTurn="X";
        }
        
    }
    
    private void botMove(){
        // Generate a random move for the bot
        JButton botButton;
        int buttonNumber;
        do {
            buttonNumber = (int) (Math.random() * 25) + 1;
            botButton = getButtonByNumber(buttonNumber);
        } while (!botButton.getText().isEmpty());

        // Set the button text to O for the bot's move
        botButton.setEnabled(false);
        botButton.setText(whoseTurn);
        botButton.setForeground(Color.magenta);
        turn.setText("Human turn");
        
        checkClick();
        
        // Check for a win or a draw
        determineIfWin();
        detDraw();
        // Switch to player's turn
        determineWhoseTurn();
        playerTurn = true;
    }
    
    private JButton getButtonByNumber(int number) {
        JButton button = null;
        switch (number) {
            case 1 -> button = board1;
            case 2 -> button = board2;
            case 3 -> button = board3;
            case 4 -> button = board4;
            case 5 -> button = board5;
            case 6 -> button = board6;
            case 7 -> button = board7;
            case 8 -> button = board8;
            case 9 -> button = board9;
            case 10 -> button = board10;
            case 11 -> button = board11;
            case 12 -> button = board12;
            case 13 -> button = board13;
            case 14 -> button = board14;
            case 15 -> button = board15;
            case 16 -> button = board16;
            case 17 -> button = board17;
            case 18 -> button = board18;
            case 19 -> button = board19;
            case 20 -> button = board20;
            case 21 -> button = board21;
            case 22 -> button = board22;
            case 23 -> button = board23;
            case 24 -> button = board24;
            case 25 -> button = board25;
        }
        return button;
    }
    
     private void handleButtonClicked(JButton button) {
        if (playerTurn && button.getText().isEmpty()) {
            // Set the button text to X or O based on the player's turn
            button.setEnabled(false);
            button.setText(whoseTurn);
            button.setForeground(Color.blue);
            turn.setText("Robot turn");
            
            checkClick();
            
            // Check for a win or a draw
            determineIfWin();
            detDraw();
            // Switch turns between players
            determineWhoseTurn();
            // Call the bot's move if it's the bot's turn
            playerTurn = false;
            if (!playerTurn) {
                botMove();
            }
        }
    }
    
    private void xWins(){
        winSound();
        
        Status.setText("Human win!");
        if (score_info){
        player1score.setText(""+playerOneScore+++"");
        }
        if (timer_info){
        timer.stop();
        }
        
        
        int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Human win", JOptionPane.YES_NO_OPTION);
        
        switch (choice) {
            case JOptionPane.YES_OPTION -> {
                resetGame();
                Status.setText("Who will win?");
            }
            // Perform action if user clicked Yes
            case JOptionPane.NO_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);
            }
            
            case JOptionPane.CLOSED_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);   
            }
            
            default -> {
            }
        }
        // Perform action if user closed the dialog
                
    }
    private void oWins(){
        loseSound();
        
        Status.setText("Robot win!");
        if (score_info){
        player2score.setText(""+playerTwoScore+++"");
        }
        if (timer_info){
        timer.stop();
        }
        
        
        int choice = JOptionPane.showConfirmDialog(null, "Try again?", "Robot win", JOptionPane.YES_NO_OPTION);
        
        switch (choice) {
            case JOptionPane.YES_OPTION -> {
                resetGame();
                Status.setText("Who will win?");
            }
            // Perform action if user clicked Yes
            case JOptionPane.NO_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);
            }
            
            case JOptionPane.CLOSED_OPTION -> {
                resetGame();
                isExit = true;
                audioClip.stop();
                audioClip2.stop();
                Homepage m = new Homepage();
                this.hide();
                m.setVisible(true);   
            }
            
            default -> {
            }
        }
    }
    
    private void determineIfWin(){
        
        String one=board1.getText();
        String two=board2.getText();
        String three=board3.getText();
        String four=board4.getText();
        String five=board5.getText();
        String six=board6.getText();
        String seven=board7.getText();
        String eight=board8.getText();
        String nine=board9.getText();
        String ten=board10.getText();
        String eleven=board11.getText();
        String twelve=board12.getText();
        String thirteen=board13.getText();
        String fourteen=board14.getText();
        String fifteen=board15.getText();
        String sixteen=board16.getText();
        String seventeen=board17.getText();
        String eighteen=board18.getText();
        String nineteen=board19.getText();
        String twenty=board20.getText();
        String twentyOne=board21.getText();
        String twentyTwo=board22.getText();
        String twentyThree=board23.getText();
        String twentyFour=board24.getText();
        String twentyFive=board25.getText();
        
        //if x win
        if("X".equals(one) && "X".equals(two) && "X".equals(three) && "X".equals(four) && "X".equals(five)){
            winEffect(board1,board2,board3,board4,board5);
            xWins();
        }
        else if("X".equals(six) && "X".equals(seven) && "X".equals(eight) && "X".equals(nine) && "X".equals(ten)){
            winEffect(board6,board7,board8,board9,board10);
            xWins();
        }
        else if("X".equals(eleven) && "X".equals(twelve) && "X".equals(thirteen) && "X".equals(fourteen) && "X".equals(fifteen)){
            winEffect(board11,board12,board13,board14,board15);
            xWins();
        }
        else if("X".equals(sixteen) && "X".equals(seventeen) && "X".equals(eighteen) && "X".equals(nineteen) && "X".equals(twenty)){
            winEffect(board16,board17,board18,board19,board20);
            xWins();
        }
        else if("X".equals(twentyOne) && "X".equals(twentyTwo) && "X".equals(twentyThree) && "X".equals(twentyFour) && "X".equals(twentyFive)){
            winEffect(board21,board22,board23,board24,board25);
            xWins();
        }
        else if("X".equals(one) && "X".equals(six) && "X".equals(eleven) && "X".equals(sixteen) && "X".equals(twentyOne)){
            winEffect(board1,board6,board11,board16,board21);
            xWins();
        }
        else if("X".equals(two) && "X".equals(seven) && "X".equals(twelve) && "X".equals(seventeen) && "X".equals(twentyTwo)){
            winEffect(board2,board7,board12,board17,board22);
            xWins();
        }
        else if("X".equals(three) && "X".equals(eight) && "X".equals(thirteen) && "X".equals(eighteen) && "X".equals(twentyThree)){
            winEffect(board3,board8,board13,board18,board23);
            xWins();
        }
        else if("X".equals(four) && "X".equals(nine) && "X".equals(fourteen) && "X".equals(nineteen) && "X".equals(twentyFour)){
            winEffect(board4,board9,board14,board19,board24);
            xWins();
        }
        else if("X".equals(five) && "X".equals(ten) && "X".equals(fifteen) && "X".equals(twenty) && "X".equals(twentyFive)){
            winEffect(board5,board10,board15,board20,board25);
            xWins();
        }
        else if("X".equals(one) && "X".equals(seven) && "X".equals(thirteen) && "X".equals(nineteen) && "X".equals(twentyFive)){
            winEffect(board1,board7,board13,board19,board25);
            xWins();
        }
        else if("X".equals(five) && "X".equals(nine) && "X".equals(thirteen) && "X".equals(seventeen) && "X".equals(twentyOne)){
            winEffect(board5,board9,board13,board17,board21);
            xWins();
        }
        
        // if o wins 
        else if("O".equals(one) && "O".equals(two) && "O".equals(three) && "O".equals(four) && "O".equals(five)){
            winEffect(board1,board2,board3,board4,board5);
            oWins();
        }
        else if("O".equals(six) && "O".equals(seven) && "O".equals(eight) && "O".equals(nine) && "O".equals(ten)){
            winEffect(board6,board7,board8,board9,board10);
            oWins();
        }
        else if("O".equals(eleven) && "O".equals(twelve) && "O".equals(thirteen) && "O".equals(fourteen) && "O".equals(fifteen)){
            winEffect(board11,board12,board13,board14,board15);
            oWins();
        }
        else if("O".equals(sixteen) && "O".equals(seventeen) && "O".equals(eighteen) && "O".equals(nineteen) && "O".equals(twenty)){
            winEffect(board16,board17,board18,board19,board20);
            oWins();
        }
        else if("O".equals(twentyOne) && "O".equals(twentyTwo) && "O".equals(twentyThree) && "O".equals(twentyFour) && "O".equals(twentyFive)){
            winEffect(board21,board22,board23,board24,board25);
            oWins();
        }
        else if("O".equals(one) && "O".equals(six) && "O".equals(eleven) && "O".equals(sixteen) && "O".equals(twentyOne)){
            winEffect(board1,board6,board11,board16,board21);
            oWins();
        }
        else if("O".equals(two) && "O".equals(seven) && "O".equals(twelve) && "O".equals(seventeen) && "O".equals(twentyTwo)){
            winEffect(board2,board7,board12,board17,board22);
            oWins();
        }
        else if("O".equals(three) && "O".equals(eight) && "O".equals(thirteen) && "O".equals(eighteen) && "O".equals(twentyThree)){
            winEffect(board3,board8,board13,board18,board23);
            oWins();
        }
        else if("O".equals(four) && "O".equals(nine) && "O".equals(fourteen) && "O".equals(nineteen) && "O".equals(twentyFour)){
            winEffect(board4,board9,board14,board19,board24);
            oWins();
        }
        else if("O".equals(five) && "O".equals(ten) && "O".equals(fifteen) && "O".equals(twenty) && "O".equals(twentyFive)){
            winEffect(board5,board10,board15,board20,board25);
            oWins();
        }
        else if("O".equals(one) && "O".equals(seven) && "O".equals(thirteen) && "O".equals(nineteen) && "O".equals(twentyFive)){
            winEffect(board1,board7,board13,board19,board25);
            oWins();
        }
        else if("O".equals(five) && "O".equals(nine) && "O".equals(thirteen) && "O".equals(seventeen) && "O".equals(twentyOne)){
            winEffect(board5,board9,board13,board17,board21);
            oWins();
        }

        
        
    }
    
    private void resetGame(){
        board1.setText("");
        board2.setText("");
        board3.setText("");
        board4.setText("");
        board5.setText("");
        board6.setText("");
        board7.setText("");
        board8.setText("");
        board9.setText("");
        board10.setText("");
        board11.setText("");
        board12.setText("");
        board13.setText("");
        board14.setText("");
        board15.setText("");
        board16.setText("");
        board17.setText("");
        board18.setText("");
        board19.setText("");
        board20.setText("");
        board21.setText("");
        board22.setText("");
        board23.setText("");
        board24.setText("");
        board25.setText("");
        
        if(board_counter){
        clicktime=1;
        txtClick.setText("0/25");
        }
        
        board1.setEnabled(true);
        board2.setEnabled(true);
        board3.setEnabled(true);
        board4.setEnabled(true);
        board5.setEnabled(true);
        board6.setEnabled(true);
        board7.setEnabled(true);
        board8.setEnabled(true);
        board9.setEnabled(true);
        board10.setEnabled(true);
        board11.setEnabled(true);
        board12.setEnabled(true);
        board13.setEnabled(true);
        board14.setEnabled(true);
        board15.setEnabled(true);
        board16.setEnabled(true);
        board17.setEnabled(true);
        board18.setEnabled(true);
        board19.setEnabled(true);
        board20.setEnabled(true);
        board21.setEnabled(true);
        board22.setEnabled(true);
        board23.setEnabled(true);
        board24.setEnabled(true);
        board25.setEnabled(true);
        
        board1.setBackground(Color.LIGHT_GRAY);
        board2.setBackground(Color.LIGHT_GRAY);
        board3.setBackground(Color.LIGHT_GRAY);
        board4.setBackground(Color.LIGHT_GRAY);
        board5.setBackground(Color.LIGHT_GRAY);
        board6.setBackground(Color.LIGHT_GRAY);
        board7.setBackground(Color.LIGHT_GRAY);
        board8.setBackground(Color.LIGHT_GRAY);
        board9.setBackground(Color.LIGHT_GRAY);
        board10.setBackground(Color.LIGHT_GRAY);
        board11.setBackground(Color.LIGHT_GRAY);
        board12.setBackground(Color.LIGHT_GRAY);
        board13.setBackground(Color.LIGHT_GRAY);
        board14.setBackground(Color.LIGHT_GRAY);
        board15.setBackground(Color.LIGHT_GRAY);
        board16.setBackground(Color.LIGHT_GRAY);
        board17.setBackground(Color.LIGHT_GRAY);
        board18.setBackground(Color.LIGHT_GRAY);
        board19.setBackground(Color.LIGHT_GRAY);
        board20.setBackground(Color.LIGHT_GRAY);
        board21.setBackground(Color.LIGHT_GRAY);
        board22.setBackground(Color.LIGHT_GRAY);
        board23.setBackground(Color.LIGHT_GRAY);
        board24.setBackground(Color.LIGHT_GRAY);
        board25.setBackground(Color.LIGHT_GRAY);
        
        if(timer_info){
        txtTimer.setText("00:00");
        totalSecondsRemaining = 0;
        Timer();
        }
    }
    
    private void setButtonSound(){
        try {
            File audioFile = new File("src/audio/buttonclicksound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            // Get the audio clip and open it
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Get the volume control
            gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume = gainControl.getValue();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            // Handle exceptions
        }
    }
    
    private void generateSong() {
        // Code to generate the song goes here
        
        // Load and play the audio file
        if(isMuted==true){
            
        }
        else if(isMuted==false)
        {
            //set button Sound
            setButtonSound();
            // Start playing the audio clip
            audioClip.start();
        
    }
    }
    
    private void setDrawSound(){
        try {
                File audioFile4 = new File("src/audio/drawsound.wav");
                AudioInputStream audioStream4 = AudioSystem.getAudioInputStream(audioFile4);

                // Get the audio clip and open it
                audioClip4 = AudioSystem.getClip();
                audioClip4.open(audioStream4);

                // Get the volume control
                gainControl4 = (FloatControl) audioClip4.getControl(FloatControl.Type.MASTER_GAIN);
                originalVolume4 = gainControl4.getValue();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                // Handle exceptions
        }
    }
    
    private void drawSound(){
        // Load and play the audio file
        if(isDrawSoundMuted==true){
              
        }
        else if(isDrawSoundMuted==false)
        {
                //set win sound
                setDrawSound();
            
                // Start playing the audio clip
                audioClip4.start();
            
        }
    }
    
    private void setWinSound(){
        try {
        File audioFile3 = new File("src/audio/winsound.wav");
                AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(audioFile3);

                // Get the audio clip and open it
                audioClip3 = AudioSystem.getClip();
                audioClip3.open(audioStream3);

                // Get the volume control
                gainControl3 = (FloatControl) audioClip3.getControl(FloatControl.Type.MASTER_GAIN);
                originalVolume3 = gainControl3.getValue();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                // Handle exceptions
        }
    }
    
    private void winSound() {
        // Code to generate the song goes here
        
        // Load and play the audio file
        if(isWinSoundMuted==true){
              
        }
        else if(isWinSoundMuted==false)
        {
                //set win sound
                setWinSound();
            
                // Start playing the audio clip
                audioClip3.start();
            
        }
    }
    
    private void setLoseSound(){
        try {
                File audioFile5 = new File("src/audio/losesound.wav");
                AudioInputStream audioStream5 = AudioSystem.getAudioInputStream(audioFile5);

                // Get the audio clip and open it
                audioClip5 = AudioSystem.getClip();
                audioClip5.open(audioStream5);

                // Get the volume control
                gainControl5 = (FloatControl) audioClip5.getControl(FloatControl.Type.MASTER_GAIN);
                originalVolume5 = gainControl5.getValue();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                // Handle exceptions
        }
    }
    
    private void loseSound() {
        // Code to generate the song goes here
        
        // Load and play the audio file
        if(isLoseSoundMuted==true){
              
        }
        else if(isLoseSoundMuted==false)
        {
                //set win sound
                setLoseSound();
            
                // Start playing the audio clip
                audioClip5.start();
            
        }
    }
    
    private void toggleMute() {
        // Toggle mute and adjust volume accordingly
        if (gainControl != null && gainControl2 != null && gainControl3 != null && gainControl4 != null && gainControl5 != null) {
        if (!isMuted && !isBGsoundMuted && !isWinSoundMuted && !isDrawSoundMuted && !isLoseSoundMuted) {
            gainControl.setValue(gainControl.getMinimum());
            isMuted = true;
            gainControl2.setValue(gainControl2.getMinimum());
            isBGsoundMuted = true;
            gainControl3.setValue(gainControl3.getMinimum());
            isWinSoundMuted = true;
            gainControl4.setValue(gainControl4.getMinimum());
            isDrawSoundMuted = true;
            gainControl5.setValue(gainControl5.getMinimum());
            isLoseSoundMuted = true;
            audioButton.setIcon(new ImageIcon("src/img/icons8-mute-16.png")); // Set unmute icon
        } else{
            gainControl.setValue(originalVolume);
            isMuted = false;
            gainControl2.setValue(originalVolume2);
            isBGsoundMuted = false;
            gainControl3.setValue(originalVolume3);
            isWinSoundMuted = false;
            gainControl4.setValue(originalVolume4);
            isDrawSoundMuted = false;
            gainControl5.setValue(originalVolume5);
            isLoseSoundMuted = false;
            audioButton.setIcon(new ImageIcon("src/img/icons8-voice-16.png")); // Set mute icon
        }
        
        }
    }
    
    private void checkClick(){
        if(board_counter){
        String click = String.format("%d/25", clicktime++);
        txtClick.setText(click);
        }
    }
    
    private void winEffect(JButton b1,JButton b2,JButton b3,JButton b4,JButton b5){
        b1.setBackground(Color.green);
        b2.setBackground(Color.green);
        b3.setBackground(Color.green);
        b4.setBackground(Color.green);
        b5.setBackground(Color.green);
        
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        iPhoneTemplate = new javax.swing.JLabel();
        phonePanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        audioButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        timerIcon = new javax.swing.JLabel();
        clickIcon = new javax.swing.JLabel();
        txtClick = new javax.swing.JLabel();
        txtTimer = new javax.swing.JLabel();
        Status = new javax.swing.JLabel();
        scorePanel = new javax.swing.JPanel();
        player1score = new javax.swing.JLabel();
        player2score = new javax.swing.JLabel();
        VS = new javax.swing.JLabel();
        player1icon = new javax.swing.JLabel();
        player2icon = new javax.swing.JLabel();
        turnPanel = new javax.swing.JPanel();
        xLabel = new javax.swing.JLabel();
        oLabel = new javax.swing.JLabel();
        turn = new javax.swing.JLabel();
        boardPanel = new javax.swing.JPanel();
        board1 = new javax.swing.JButton();
        board2 = new javax.swing.JButton();
        board3 = new javax.swing.JButton();
        board4 = new javax.swing.JButton();
        board5 = new javax.swing.JButton();
        board6 = new javax.swing.JButton();
        board7 = new javax.swing.JButton();
        board8 = new javax.swing.JButton();
        board9 = new javax.swing.JButton();
        board10 = new javax.swing.JButton();
        board11 = new javax.swing.JButton();
        board12 = new javax.swing.JButton();
        board13 = new javax.swing.JButton();
        board14 = new javax.swing.JButton();
        board15 = new javax.swing.JButton();
        board16 = new javax.swing.JButton();
        board17 = new javax.swing.JButton();
        board18 = new javax.swing.JButton();
        board19 = new javax.swing.JButton();
        board20 = new javax.swing.JButton();
        board21 = new javax.swing.JButton();
        board22 = new javax.swing.JButton();
        board23 = new javax.swing.JButton();
        board24 = new javax.swing.JButton();
        board25 = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iPhoneTemplate.setBackground(new java.awt.Color(255, 255, 255));
        iPhoneTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iphone.png"))); // NOI18N
        mainPanel.add(iPhoneTemplate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        phonePanel.setBackground(new java.awt.Color(102, 102, 102));
        phonePanel.setMaximumSize(new java.awt.Dimension(260, 550));
        phonePanel.setMinimumSize(new java.awt.Dimension(260, 550));
        setResizable(false);
        phonePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel.setBackground(new java.awt.Color(0, 0, 0));

        closeButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        closeButton.setForeground(new java.awt.Color(255, 0, 0));
        closeButton.setText("X");
        closeButton.setAlignmentY(0.0F);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        audioButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-voice-16.png"))); // NOI18N
        audioButton.setFocusPainted(false);
        audioButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        audioButton.setMaximumSize(new java.awt.Dimension(21, 21));
        audioButton.setMinimumSize(new java.awt.Dimension(21, 21));
        audioButton.setPreferredSize(new java.awt.Dimension(21, 21));
        audioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(audioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addGap(17, 17, 17))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(audioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        phonePanel.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 261, -1));

        titlePanel.setBackground(new java.awt.Color(0, 0, 0));
        titlePanel.setMaximumSize(new java.awt.Dimension(248, 56));

        titleLabel.setBackground(new java.awt.Color(102, 0, 102));
        titleLabel.setFont(new java.awt.Font("Showcard Gothic", 1, 20)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 0, 0));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setLabelFor(titlePanel);
        titleLabel.setText("Tic Tac Toe");
        titleLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        phonePanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 39, 249, -1));

        statusPanel.setBackground(new Color(0,0,0,200));
        statusPanel.setMaximumSize(new java.awt.Dimension(62, 44));
        statusPanel.setMinimumSize(new java.awt.Dimension(62, 44));

        timerIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-sand-watch-16.png"))); // NOI18N
        timerIcon.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        timerIcon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        clickIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clickIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dialing-number-16.png"))); // NOI18N
        clickIcon.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        clickIcon.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        clickIcon.setIconTextGap(0);

        txtClick.setForeground(new java.awt.Color(255, 255, 255));
        txtClick.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtClick.setText("0/25");

        txtTimer.setForeground(new java.awt.Color(255, 255, 255));
        txtTimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTimer.setText("00:00");

        Status.setForeground(new java.awt.Color(255, 102, 0));
        Status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Status.setText("Who will win?");
        Status.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(timerIcon)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtClick, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(clickIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(statusPanelLayout.createSequentialGroup()
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(timerIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(clickIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtClick, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        phonePanel.add(statusPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 101, 261, -1));

        scorePanel.setBackground(new Color(0,0,0,200));
        scorePanel.setPreferredSize(new java.awt.Dimension(0, 44));

        player1score.setForeground(new java.awt.Color(255, 255, 255));
        player1score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player1score.setText("0");
        player1score.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        player1score.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        player1score.setMaximumSize(new java.awt.Dimension(24, 30));
        player1score.setMinimumSize(new java.awt.Dimension(24, 30));
        player1score.setPreferredSize(new java.awt.Dimension(24, 30));

        player2score.setForeground(new java.awt.Color(255, 255, 255));
        player2score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2score.setText("0");
        player2score.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        player2score.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        player2score.setMaximumSize(new java.awt.Dimension(24, 30));
        player2score.setMinimumSize(new java.awt.Dimension(24, 30));
        player2score.setPreferredSize(new java.awt.Dimension(24, 30));

        VS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        VS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-battle-16.png"))); // NOI18N

        player1icon.setForeground(new java.awt.Color(255, 255, 255));
        player1icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player1icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-user-30.png"))); // NOI18N
        player1icon.setText("Human");
        player1icon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        player2icon.setForeground(new java.awt.Color(255, 255, 255));
        player2icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bot-32.png"))); // NOI18N
        player2icon.setText("Robot");
        player2icon.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout scorePanelLayout = new javax.swing.GroupLayout(scorePanel);
        scorePanel.setLayout(scorePanelLayout);
        scorePanelLayout.setHorizontalGroup(
            scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scorePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(player1icon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player1score, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VS, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player2score, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(player2icon, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );
        scorePanelLayout.setVerticalGroup(
            scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scorePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(VS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(player2score, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(player2icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, scorePanelLayout.createSequentialGroup()
                        .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(player1score, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(player1icon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(59, 59, 59))
        );

        phonePanel.add(scorePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 163, 260, -1));

        turnPanel.setBackground(new Color(0,0,0,200));

        xLabel.setFont(new java.awt.Font("Maiandra GD", 1, 12)); // NOI18N
        xLabel.setForeground(new java.awt.Color(0, 153, 255));
        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel.setText("X");

        oLabel.setFont(new java.awt.Font("Maiandra GD", 1, 12)); // NOI18N
        oLabel.setForeground(java.awt.Color.magenta);
        oLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        oLabel.setText("O");

        turn.setForeground(new java.awt.Color(255, 255, 255));
        turn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turn.setText("Human turn");

        javax.swing.GroupLayout turnPanelLayout = new javax.swing.GroupLayout(turnPanel);
        turnPanel.setLayout(turnPanelLayout);
        turnPanelLayout.setHorizontalGroup(
            turnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(turnPanelLayout.createSequentialGroup()
                .addComponent(xLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(turn, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(oLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        turnPanelLayout.setVerticalGroup(
            turnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(turnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(turnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(oLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(turnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(turn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(xLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        phonePanel.add(turnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 213, 261, -1));

        boardPanel.setBackground(new Color(0,0,0,200));
        boardPanel.setMaximumSize(new java.awt.Dimension(249, 249));
        boardPanel.setMinimumSize(new java.awt.Dimension(249, 249));

        board1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        board1.setFocusPainted(false);
        board1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board1.setMaximumSize(new java.awt.Dimension(67, 67));
        board1.setMinimumSize(new java.awt.Dimension(67, 67));
        board1.setPreferredSize(new java.awt.Dimension(67, 67));
        board1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board1ActionPerformed(evt);
            }
        });

        board2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board2.setFocusPainted(false);
        board2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board2.setMaximumSize(new java.awt.Dimension(67, 67));
        board2.setMinimumSize(new java.awt.Dimension(67, 67));
        board2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board2ActionPerformed(evt);
            }
        });

        board3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board3.setFocusPainted(false);
        board3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board3.setMaximumSize(new java.awt.Dimension(67, 67));
        board3.setMinimumSize(new java.awt.Dimension(67, 67));
        board3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board3ActionPerformed(evt);
            }
        });

        board4.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board4.setFocusPainted(false);
        board4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board4.setMaximumSize(new java.awt.Dimension(67, 67));
        board4.setMinimumSize(new java.awt.Dimension(67, 67));
        board4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board4ActionPerformed(evt);
            }
        });

        board5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board5.setFocusPainted(false);
        board5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board5.setMaximumSize(new java.awt.Dimension(67, 67));
        board5.setMinimumSize(new java.awt.Dimension(67, 67));
        board5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board5ActionPerformed(evt);
            }
        });

        board6.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board6.setFocusPainted(false);
        board6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board6.setMaximumSize(new java.awt.Dimension(67, 67));
        board6.setMinimumSize(new java.awt.Dimension(67, 67));
        board6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board6ActionPerformed(evt);
            }
        });

        board7.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board7.setFocusPainted(false);
        board7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board7.setMaximumSize(new java.awt.Dimension(67, 67));
        board7.setMinimumSize(new java.awt.Dimension(67, 67));
        board7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board7ActionPerformed(evt);
            }
        });

        board8.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board8.setFocusPainted(false);
        board8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board8.setMaximumSize(new java.awt.Dimension(67, 67));
        board8.setMinimumSize(new java.awt.Dimension(67, 67));
        board8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board8ActionPerformed(evt);
            }
        });

        board9.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board9.setFocusPainted(false);
        board9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board9.setMaximumSize(new java.awt.Dimension(67, 67));
        board9.setMinimumSize(new java.awt.Dimension(67, 67));
        board9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board9ActionPerformed(evt);
            }
        });

        board10.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board10.setFocusPainted(false);
        board10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board10.setMaximumSize(new java.awt.Dimension(67, 67));
        board10.setMinimumSize(new java.awt.Dimension(67, 67));
        board10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board10ActionPerformed(evt);
            }
        });

        board11.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board11.setFocusPainted(false);
        board11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board11.setMaximumSize(new java.awt.Dimension(67, 67));
        board11.setMinimumSize(new java.awt.Dimension(67, 67));
        board11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board11ActionPerformed(evt);
            }
        });

        board12.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board12.setFocusPainted(false);
        board12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board12.setMaximumSize(new java.awt.Dimension(67, 67));
        board12.setMinimumSize(new java.awt.Dimension(67, 67));
        board12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board12ActionPerformed(evt);
            }
        });

        board13.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board13.setFocusPainted(false);
        board13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board13.setMaximumSize(new java.awt.Dimension(67, 67));
        board13.setMinimumSize(new java.awt.Dimension(67, 67));
        board13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board13ActionPerformed(evt);
            }
        });

        board14.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board14.setFocusPainted(false);
        board14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board14.setMaximumSize(new java.awt.Dimension(67, 67));
        board14.setMinimumSize(new java.awt.Dimension(67, 67));
        board14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board14ActionPerformed(evt);
            }
        });

        board15.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board15.setFocusPainted(false);
        board15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board15.setMaximumSize(new java.awt.Dimension(67, 67));
        board15.setMinimumSize(new java.awt.Dimension(67, 67));
        board15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board15ActionPerformed(evt);
            }
        });

        board16.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board16.setFocusPainted(false);
        board16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board16.setMaximumSize(new java.awt.Dimension(67, 67));
        board16.setMinimumSize(new java.awt.Dimension(67, 67));
        board16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board16ActionPerformed(evt);
            }
        });

        board17.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board17.setFocusPainted(false);
        board17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board17.setMaximumSize(new java.awt.Dimension(67, 67));
        board17.setMinimumSize(new java.awt.Dimension(67, 67));
        board17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board17ActionPerformed(evt);
            }
        });

        board18.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board18.setFocusPainted(false);
        board18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board18.setMaximumSize(new java.awt.Dimension(67, 67));
        board18.setMinimumSize(new java.awt.Dimension(67, 67));
        board18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board18ActionPerformed(evt);
            }
        });

        board19.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board19.setFocusPainted(false);
        board19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board19.setMaximumSize(new java.awt.Dimension(67, 67));
        board19.setMinimumSize(new java.awt.Dimension(67, 67));
        board19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board19ActionPerformed(evt);
            }
        });

        board20.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board20.setFocusPainted(false);
        board20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board20.setMaximumSize(new java.awt.Dimension(67, 67));
        board20.setMinimumSize(new java.awt.Dimension(67, 67));
        board20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board20ActionPerformed(evt);
            }
        });

        board21.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board21.setFocusPainted(false);
        board21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board21.setMaximumSize(new java.awt.Dimension(67, 67));
        board21.setMinimumSize(new java.awt.Dimension(67, 67));
        board21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board21ActionPerformed(evt);
            }
        });

        board22.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board22.setFocusPainted(false);
        board22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board22.setMaximumSize(new java.awt.Dimension(67, 67));
        board22.setMinimumSize(new java.awt.Dimension(67, 67));
        board22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board22ActionPerformed(evt);
            }
        });

        board23.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board23.setFocusPainted(false);
        board23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board23.setMaximumSize(new java.awt.Dimension(67, 67));
        board23.setMinimumSize(new java.awt.Dimension(67, 67));
        board23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board23ActionPerformed(evt);
            }
        });

        board24.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board24.setFocusPainted(false);
        board24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board24.setMaximumSize(new java.awt.Dimension(67, 67));
        board24.setMinimumSize(new java.awt.Dimension(67, 67));
        board24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board24ActionPerformed(evt);
            }
        });

        board25.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        board25.setFocusPainted(false);
        board25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board25.setMaximumSize(new java.awt.Dimension(67, 67));
        board25.setMinimumSize(new java.awt.Dimension(67, 67));
        board25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(boardPanelLayout.createSequentialGroup()
                                .addComponent(board21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardPanelLayout.createSequentialGroup()
                                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(board11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(board12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(board13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(board3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardPanelLayout.createSequentialGroup()
                                .addComponent(board24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                .addComponent(board14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(board20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board11, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board13, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board12, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(board14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(board16, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board17, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board18, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(board25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board21, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board23, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board22, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        phonePanel.add(boardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 250, -1, -1));

        bottomPanel.setBackground(new Color(0,0,0,200));

        btnHome.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-home-16.png"))); // NOI18N
        btnHome.setFocusPainted(false);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bin-16.png"))); // NOI18N
        btnReset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        phonePanel.add(bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 505, 249, -1));
        phonePanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 36, 300, 510));

        mainPanel.add(phonePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 260, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_closeButtonActionPerformed

    private void board1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board1ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board1);
    }//GEN-LAST:event_board1ActionPerformed

    private void board3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board3ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board3);
    }//GEN-LAST:event_board3ActionPerformed

    private void board5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board5ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board5);
    }//GEN-LAST:event_board5ActionPerformed

    private void board11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board11ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board11);
    }//GEN-LAST:event_board11ActionPerformed

    private void board13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board13ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board13);
    }//GEN-LAST:event_board13ActionPerformed

    private void board15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board15ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board15);
    }//GEN-LAST:event_board15ActionPerformed

    private void board21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board21ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board21);
    }//GEN-LAST:event_board21ActionPerformed

    private void board23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board23ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board23);
    }//GEN-LAST:event_board23ActionPerformed

    private void board25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board25ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board25);
    }//GEN-LAST:event_board25ActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        if(timer_info){
        timer.stop();
        }
        isExit = true;
        generateSong();
        audioClip.stop();
        audioClip2.stop();
        Homepage m = new Homepage();
        this.hide();
        m.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        if(timer_info){
        timer.stop();
        }
        
        
        
        int choice = JOptionPane.showInternalConfirmDialog(null, "Restart game?\nAll scores and timer will be reset and save", "Restart game?", JOptionPane.YES_NO_OPTION);
        
        switch (choice) {
            case JOptionPane.YES_OPTION -> {
                playerOneScore=1;
                playerTwoScore=1;
                resetGame();
                resetScore();
                Status.setText("Who will win?");
            }
            // Perform action if user clicked Yes
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void audioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioButtonActionPerformed
        // TODO add your handling code here:
        
        generateSong();
        toggleMute();
    }//GEN-LAST:event_audioButtonActionPerformed

    private void board2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board2ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board2);
    }//GEN-LAST:event_board2ActionPerformed

    private void board4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board4ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board4);
    }//GEN-LAST:event_board4ActionPerformed

    private void board6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board6ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board6);
    }//GEN-LAST:event_board6ActionPerformed

    private void board8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board8ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board8);
    }//GEN-LAST:event_board8ActionPerformed

    private void board7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board7ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board7);
    }//GEN-LAST:event_board7ActionPerformed

    private void board9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board9ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board9);
    }//GEN-LAST:event_board9ActionPerformed

    private void board14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board14ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board14);
    }//GEN-LAST:event_board14ActionPerformed

    private void board10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board10ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board10);
    }//GEN-LAST:event_board10ActionPerformed

    private void board12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board12ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board12);
    }//GEN-LAST:event_board12ActionPerformed

    private void board16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board16ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board16);
    }//GEN-LAST:event_board16ActionPerformed

    private void board17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board17ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board17);
    }//GEN-LAST:event_board17ActionPerformed

    private void board18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board18ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board18);
    }//GEN-LAST:event_board18ActionPerformed

    private void board19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board19ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board19);
    }//GEN-LAST:event_board19ActionPerformed

    private void board20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board20ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board20);
    }//GEN-LAST:event_board20ActionPerformed

    private void board22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board22ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board22);
    }//GEN-LAST:event_board22ActionPerformed

    private void board24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board24ActionPerformed
        // TODO add your handling code here:
        generateSong();
        handleButtonClicked(board24);
    }//GEN-LAST:event_board24ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Board55_vsBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board55_vsBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board55_vsBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board55_vsBot.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board55_vsBot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.JLabel VS;
    private javax.swing.JButton audioButton;
    private javax.swing.JLabel bg;
    private javax.swing.JButton board1;
    private javax.swing.JButton board10;
    private javax.swing.JButton board11;
    private javax.swing.JButton board12;
    private javax.swing.JButton board13;
    private javax.swing.JButton board14;
    private javax.swing.JButton board15;
    private javax.swing.JButton board16;
    private javax.swing.JButton board17;
    private javax.swing.JButton board18;
    private javax.swing.JButton board19;
    private javax.swing.JButton board2;
    private javax.swing.JButton board20;
    private javax.swing.JButton board21;
    private javax.swing.JButton board22;
    private javax.swing.JButton board23;
    private javax.swing.JButton board24;
    private javax.swing.JButton board25;
    private javax.swing.JButton board3;
    private javax.swing.JButton board4;
    private javax.swing.JButton board5;
    private javax.swing.JButton board6;
    private javax.swing.JButton board7;
    private javax.swing.JButton board8;
    private javax.swing.JButton board9;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel clickIcon;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel iPhoneTemplate;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel oLabel;
    private javax.swing.JPanel phonePanel;
    private javax.swing.JLabel player1icon;
    private javax.swing.JLabel player1score;
    private javax.swing.JLabel player2icon;
    private javax.swing.JLabel player2score;
    private javax.swing.JPanel scorePanel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JLabel timerIcon;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel turn;
    private javax.swing.JPanel turnPanel;
    private javax.swing.JLabel txtClick;
    private javax.swing.JLabel txtTimer;
    private javax.swing.JLabel xLabel;
    // End of variables declaration//GEN-END:variables
}
