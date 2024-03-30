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
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Ahmad Syukri
 */
public class Board77 extends javax.swing.JFrame {
    
    //board
    private String whoseTurn="X";
    private int playerOneScore=1;
    private int playerTwoScore=1;
    private int clicktime=1;
    
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
    public Board77() {
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
        
        //set button Sound
        setButtonSound();
        
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
        String twentySix=board26.getText();
        String twentySeven=board27.getText();
        String twentyEight=board28.getText();
        String twentyNine=board29.getText();
        String thirty=board30.getText();
        String thirtyOne=board31.getText();
        String thirtyTwo=board32.getText();
        String thirtyThree=board33.getText();
        String thirtyFour=board34.getText();
        String thirtyFive=board35.getText();
        String thirtySix=board36.getText();
        String thirtySeven=board37.getText();
        String thirtyEight=board38.getText();
        String thirtyNine=board39.getText();
        String fourty=board40.getText();
        String fourtyOne=board41.getText();
        String fourtyTwo=board42.getText();
        String fourtyThree=board43.getText();
        String fourtyFour=board44.getText();
        String fourtyFive=board45.getText();
        String fourtySix=board46.getText();
        String fourtySeven=board47.getText();
        String fourtyEight=board48.getText();
        String fourtyNine=board49.getText();
        
       
        if(!"".equals(one) && !"".equals(two) && !"".equals(three) && !"".equals(four) && !"".equals(five) 
                && !"".equals(six) && !"".equals(seven) && !"".equals(eight) && !"".equals(nine)
                && !"".equals(ten) && !"".equals(eleven) && !"".equals(twelve) && !"".equals(thirteen) 
                && !"".equals(fourteen) && !"".equals(fifteen) && !"".equals(sixteen) && !"".equals(seventeen)
                && !"".equals(eighteen) && !"".equals(nineteen) && !"".equals(twenty) && !"".equals(twentyOne) 
                && !"".equals(twentyTwo) && !"".equals(twentyThree) && !"".equals(twentyFour) && !"".equals(twentyFive) 
                && !"".equals(twentySix) && !"".equals(twentySeven) && !"".equals(twentyEight) && !"".equals(twentyNine) 
                && !"".equals(thirty) && !"".equals(thirtyOne) && !"".equals(thirtyTwo) && !"".equals(thirtyThree) 
                && !"".equals(thirtyFour) && !"".equals(thirtyFive) && !"".equals(thirtySix) && !"".equals(thirtySeven) 
                && !"".equals(thirtyEight) && !"".equals(thirtyNine) && !"".equals(fourty) && !"".equals(fourtyOne) 
                && !"".equals(fourtyTwo) && !"".equals(fourtyThree) && !"".equals(fourtyFour) && !"".equals(fourtyFive) 
                && !"".equals(fourtySix) && !"".equals(fourtySeven) && !"".equals(fourtyEight) && !"".equals(fourtyNine)){
            
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
    
    private void xWins(){
        winSound();
        Status.setText("Player 1 win!");
        if (score_info){
        player1score.setText(""+playerOneScore+++"");
        }
        if (timer_info){
        timer.stop();
        }
        
        
        int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Player 1 win", JOptionPane.YES_NO_OPTION);
        
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
        winSound();
        Status.setText("Player 2 win!");
        if (score_info){
        player2score.setText(""+playerTwoScore+++"");
        }
        if (timer_info){
        timer.stop();
        }
        
        
        int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Player 2 win", JOptionPane.YES_NO_OPTION);
        
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
        String twentySix=board26.getText();
        String twentySeven=board27.getText();
        String twentyEight=board28.getText();
        String twentyNine=board29.getText();
        String thirty=board30.getText();
        String thirtyOne=board31.getText();
        String thirtyTwo=board32.getText();
        String thirtyThree=board33.getText();
        String thirtyFour=board34.getText();
        String thirtyFive=board35.getText();
        String thirtySix=board36.getText();
        String thirtySeven=board37.getText();
        String thirtyEight=board38.getText();
        String thirtyNine=board39.getText();
        String fourty=board40.getText();
        String fourtyOne=board41.getText();
        String fourtyTwo=board42.getText();
        String fourtyThree=board43.getText();
        String fourtyFour=board44.getText();
        String fourtyFive=board45.getText();
        String fourtySix=board46.getText();
        String fourtySeven=board47.getText();
        String fourtyEight=board48.getText();
        String fourtyNine=board49.getText();
        
        //if x win
        if("X".equals(one) && "X".equals(two) && "X".equals(three) && "X".equals(four)
                && "X".equals(five) && "X".equals(six) && "X".equals(seven)){
            winEffect(board1,board2,board3,board4,board5,board6,board7);
            xWins();
        }
        else if("X".equals(eight) && "X".equals(nine) && "X".equals(ten) && "X".equals(eleven) 
                && "X".equals(twelve) && "X".equals(thirteen) && "X".equals(fourteen)){
            winEffect(board8,board9,board10,board11,board12,board13,board14);
            xWins();
        }
        else if("X".equals(fifteen) && "X".equals(sixteen) && "X".equals(seventeen) && "X".equals(eighteen) 
                && "X".equals(nineteen) && "X".equals(twenty) && "X".equals(twentyOne)){
            winEffect(board15,board16,board17,board18,board19,board20,board21);
            xWins();
        }
        else if("X".equals(twentyTwo) && "X".equals(twentyThree) && "X".equals(twentyFour) && "X".equals(twentyFive) 
                && "X".equals(twentySix) && "X".equals(twentySeven) && "X".equals(twentyEight)){
            winEffect(board22,board23,board24,board25,board26,board27,board28);
            xWins();
        }
        else if("X".equals(twentyNine) && "X".equals(thirty) && "X".equals(thirtyOne) && "X".equals(thirtyTwo) 
                && "X".equals(thirtyThree) && "X".equals(thirtyFour) && "X".equals(thirtyFive)){
            winEffect(board29,board30,board31,board32,board33,board34,board35);
            xWins();
        }
        else if("X".equals(thirtySix) && "X".equals(thirtySeven) && "X".equals(thirtyEight) && "X".equals(thirtyNine) 
                && "X".equals(fourty) && "X".equals(fourtyOne) && "X".equals(fourtyTwo)){
            winEffect(board36,board37,board38,board39,board40,board41,board42);
            xWins();
        }
        else if("X".equals(fourtyThree) && "X".equals(fourtyFour) && "X".equals(fourtyFive) && "X".equals(fourtySix) 
                && "X".equals(fourtySeven) && "X".equals(fourtyEight) && "X".equals(fourtyNine)){
            winEffect(board43,board44,board45,board46,board47,board48,board49);
            xWins();
        }
        else if("X".equals(one) && "X".equals(eight) && "X".equals(fifteen) && "X".equals(twentyTwo) 
                && "X".equals(twentyNine) && "X".equals(thirtySix) && "X".equals(fourtyThree)){
            winEffect(board1,board8,board15,board22,board29,board36,board43);
            xWins();
        }
        else if("X".equals(two) && "X".equals(nine) && "X".equals(sixteen) && "X".equals(twentyThree) 
                && "X".equals(thirty) && "X".equals(thirtySeven) && "X".equals(fourtyFour)){
            winEffect(board2,board9,board16,board23,board30,board37,board44);
            xWins();
        }
        else if("X".equals(three) && "X".equals(ten) && "X".equals(seventeen) && "X".equals(twentyFour) 
                && "X".equals(thirtyOne) && "X".equals(thirtyEight) && "X".equals(fourtyFive)){
            winEffect(board3,board10,board17,board24,board31,board38,board45);
            xWins();
        }
        else if("X".equals(four) && "X".equals(eleven) && "X".equals(eighteen) && "X".equals(twentyFive) 
                && "X".equals(thirtyTwo) && "X".equals(thirtyNine) && "X".equals(fourtySix)){
            winEffect(board4,board11,board18,board25,board32,board39,board46);
            xWins();
        }
        else if("X".equals(five) && "X".equals(twelve) && "X".equals(nineteen) && "X".equals(twentySix) 
                && "X".equals(thirtyThree) && "X".equals(fourty) && "X".equals(fourtySeven)){
            winEffect(board5,board12,board19,board26,board33,board40,board47);
            xWins();
        }
        else if("X".equals(six) && "X".equals(thirteen) && "X".equals(twenty) && "X".equals(twentySeven) 
                && "X".equals(thirtyFour) && "X".equals(fourtyOne) && "X".equals(fourtyEight)){
            winEffect(board6,board13,board20,board27,board34,board41,board48);
            xWins();
        }
        else if("X".equals(seven) && "X".equals(fourteen) && "X".equals(twentyOne) && "X".equals(twentyEight) 
                && "X".equals(thirtyFive) && "X".equals(fourtyTwo) && "X".equals(fourtyNine)){
            winEffect(board7,board14,board21,board28,board35,board42,board49);
            xWins();
        }
        else if("X".equals(one) && "X".equals(nine) && "X".equals(seventeen) && "X".equals(twentyFive) 
                && "X".equals(thirtyThree) && "X".equals(fourtyOne) && "X".equals(fourtyNine)){
            winEffect(board1,board9,board17,board25,board33,board41,board49);
            xWins();
        }
        else if("X".equals(seven) && "X".equals(thirteen) && "X".equals(nineteen) && "X".equals(twentyFive) 
                && "X".equals(thirtyOne) && "X".equals(thirtySeven) && "X".equals(fourtyThree)){
            winEffect(board7,board13,board19,board25,board31,board37,board43);
            xWins();
        }
        
        // if o wins 
        else if("O".equals(one) && "O".equals(two) && "O".equals(three) && "O".equals(four) && "O".equals(five) 
                && "O".equals(six) && "O".equals(seven)){
            winEffect(board1,board2,board3,board4,board5,board6,board7);
            oWins();
        }
        else if("O".equals(eight) && "O".equals(nine) && "O".equals(ten) && "O".equals(eleven) && "O".equals(twelve) 
                && "O".equals(thirteen) && "O".equals(fourteen)){
            winEffect(board8,board9,board10,board11,board12,board13,board14);
            oWins();
        }
        else if("O".equals(fifteen) && "O".equals(sixteen) && "O".equals(seventeen) && "O".equals(eighteen) 
                && "O".equals(nineteen) && "O".equals(twenty) && "O".equals(twentyOne)){
            winEffect(board15,board16,board17,board18,board19,board20,board21);
            oWins();
        }
        else if("O".equals(twentyTwo) && "O".equals(twentyThree) && "O".equals(twentyFour) && "O".equals(twentyFive) 
                && "O".equals(twentySix) && "O".equals(twentySeven) && "O".equals(twentyEight)){
            winEffect(board22,board23,board24,board25,board26,board27,board28);
            oWins();
        }
        else if("O".equals(twentyNine) && "O".equals(thirty) && "O".equals(thirtyOne) && "O".equals(thirtyTwo) 
                && "O".equals(thirtyThree) && "O".equals(thirtyFour) && "O".equals(thirtyFive)){
            winEffect(board29,board30,board31,board32,board33,board34,board35);
            oWins();
        }
        else if("O".equals(thirtySix) && "O".equals(thirtySeven) && "O".equals(thirtyEight) && "O".equals(thirtyNine) 
                && "O".equals(fourty) && "O".equals(fourtyOne) && "O".equals(fourtyTwo)){
            winEffect(board36,board37,board38,board39,board40,board41,board42);
            oWins();
        }
        else if("O".equals(fourtyThree) && "O".equals(fourtyFour) && "O".equals(fourtyFive) && "O".equals(fourtySix) 
                && "O".equals(fourtySeven) && "O".equals(fourtyEight) && "O".equals(fourtyNine)){
            winEffect(board43,board44,board45,board46,board47,board48,board49);
            oWins();
        }
        else if("O".equals(one) && "O".equals(eight) && "O".equals(fifteen) && "O".equals(twentyTwo) 
                && "O".equals(twentyNine) && "O".equals(thirtySix) && "O".equals(fourtyThree)){
            winEffect(board1,board8,board15,board22,board29,board36,board43);
            oWins();
        }
        else if("O".equals(two) && "O".equals(nine) && "O".equals(sixteen) && "O".equals(twentyThree) 
                && "O".equals(thirty) && "O".equals(thirtySeven) && "O".equals(fourtyFour)){
            winEffect(board2,board9,board16,board23,board30,board37,board44);
            oWins();
        }
        else if("O".equals(three) && "O".equals(ten) && "O".equals(seventeen) && "O".equals(twentyFour) 
                && "O".equals(thirtyOne) && "O".equals(thirtyEight) && "O".equals(fourtyFive)){
            winEffect(board3,board10,board17,board24,board31,board38,board45);
            oWins();
        }
        else if("O".equals(four) && "O".equals(eleven) && "O".equals(eighteen) && "O".equals(twentyFive) 
                && "O".equals(thirtyTwo) && "O".equals(thirtyNine) && "O".equals(fourtySix)){
            winEffect(board4,board11,board18,board25,board32,board39,board46);
            oWins();
        }
        else if("O".equals(five) && "O".equals(twelve) && "O".equals(nineteen) && "O".equals(twentySix) 
                && "O".equals(thirtyThree) && "O".equals(fourty) && "O".equals(fourtySeven)){
            winEffect(board5,board12,board19,board26,board33,board40,board47);
            oWins();
        }
        else if("O".equals(six) && "O".equals(thirteen) && "O".equals(twenty) && "O".equals(twentySeven) 
                && "O".equals(thirtyFour) && "O".equals(fourtyOne) && "O".equals(fourtyEight)){
            winEffect(board6,board13,board20,board27,board34,board41,board48);
            oWins();
        }
        else if("O".equals(seven) && "O".equals(fourteen) && "O".equals(twentyOne) && "O".equals(twentyEight) 
                && "O".equals(thirtyFive) && "O".equals(fourtyTwo) && "O".equals(fourtyNine)){
            winEffect(board7,board14,board21,board28,board35,board42,board49);
            oWins();
        }
        else if("O".equals(one) && "O".equals(nine) && "O".equals(seventeen) && "O".equals(twentyFive) 
                && "O".equals(thirtyThree) && "O".equals(fourtyOne) && "O".equals(fourtyNine)){
            winEffect(board1,board9,board17,board25,board33,board41,board49);
            oWins();
        }
        else if("O".equals(seven) && "O".equals(thirteen) && "O".equals(nineteen) && "O".equals(twentyFive) 
                && "O".equals(thirtyOne) && "O".equals(thirtySeven) && "O".equals(fourtyThree)){
            winEffect(board7,board13,board19,board25,board31,board37,board43);
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
        board26.setText("");
        board27.setText("");
        board28.setText("");
        board29.setText("");
        board30.setText("");
        board31.setText("");
        board32.setText("");
        board33.setText("");
        board34.setText("");
        board35.setText("");
        board36.setText("");
        board37.setText("");
        board38.setText("");
        board39.setText("");
        board40.setText("");
        board41.setText("");
        board42.setText("");
        board43.setText("");
        board44.setText("");
        board45.setText("");
        board46.setText("");
        board47.setText("");
        board48.setText("");
        board49.setText("");
        
        if(board_counter){
        clicktime=1;
        txtClick.setText("0/49");
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
        board26.setEnabled(true);
        board27.setEnabled(true);
        board28.setEnabled(true);
        board29.setEnabled(true);
        board30.setEnabled(true);
        board31.setEnabled(true);
        board32.setEnabled(true);
        board33.setEnabled(true);
        board34.setEnabled(true);
        board35.setEnabled(true);
        board36.setEnabled(true);
        board37.setEnabled(true);
        board38.setEnabled(true);
        board39.setEnabled(true);
        board40.setEnabled(true);
        board41.setEnabled(true);
        board42.setEnabled(true);
        board43.setEnabled(true);
        board44.setEnabled(true);
        board45.setEnabled(true);
        board46.setEnabled(true);
        board47.setEnabled(true);
        board48.setEnabled(true);
        board49.setEnabled(true);
        
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
        board26.setBackground(Color.LIGHT_GRAY);
        board27.setBackground(Color.LIGHT_GRAY);
        board28.setBackground(Color.LIGHT_GRAY);
        board29.setBackground(Color.LIGHT_GRAY);
        board30.setBackground(Color.LIGHT_GRAY);
        board31.setBackground(Color.LIGHT_GRAY);
        board32.setBackground(Color.LIGHT_GRAY);
        board33.setBackground(Color.LIGHT_GRAY);
        board34.setBackground(Color.LIGHT_GRAY);
        board35.setBackground(Color.LIGHT_GRAY);
        board36.setBackground(Color.LIGHT_GRAY);
        board37.setBackground(Color.LIGHT_GRAY);
        board38.setBackground(Color.LIGHT_GRAY);
        board39.setBackground(Color.LIGHT_GRAY);
        board40.setBackground(Color.LIGHT_GRAY);
        board41.setBackground(Color.LIGHT_GRAY);
        board42.setBackground(Color.LIGHT_GRAY);
        board43.setBackground(Color.LIGHT_GRAY);
        board44.setBackground(Color.LIGHT_GRAY);
        board45.setBackground(Color.LIGHT_GRAY);
        board46.setBackground(Color.LIGHT_GRAY);
        board47.setBackground(Color.LIGHT_GRAY);
        board48.setBackground(Color.LIGHT_GRAY);
        board49.setBackground(Color.LIGHT_GRAY);
        
        
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
        try {
            File audioFile = new File("src/audio/buttonclicksound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            // Get the audio clip and open it
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Get the volume control
            gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            originalVolume = gainControl.getValue();

            // Start playing the audio clip
            audioClip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        // Handle exceptions
    }
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
    
    private void toggleMute() {
        // Toggle mute and adjust volume accordingly
        if (gainControl != null && gainControl2 != null && gainControl3 != null && gainControl4 != null) {
        if (!isMuted && !isBGsoundMuted && !isWinSoundMuted && !isDrawSoundMuted) {
            gainControl.setValue(gainControl.getMinimum());
            isMuted = true;
            gainControl2.setValue(gainControl2.getMinimum());
            isBGsoundMuted = true;
            gainControl3.setValue(gainControl3.getMinimum());
            isWinSoundMuted = true;
            gainControl4.setValue(gainControl4.getMinimum());
            isDrawSoundMuted = true;
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
            audioButton.setIcon(new ImageIcon("src/img/icons8-voice-16.png")); // Set mute icon
        }
        
        }
    }
    
    private void checkClick(){
        if(board_counter){
        String click = String.format("%d/49", clicktime++);
        txtClick.setText(click);
        }
    }
    
    private void winEffect(JButton b1,JButton b2,JButton b3,JButton b4,JButton b5,JButton b6,JButton b7){
        b1.setBackground(Color.green);
        b2.setBackground(Color.green);
        b3.setBackground(Color.green);
        b4.setBackground(Color.green);
        b5.setBackground(Color.green);
        b6.setBackground(Color.green);
        b7.setBackground(Color.green);
        
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
        b4.setEnabled(true);
        b5.setEnabled(true);
        b6.setEnabled(true);
        b7.setEnabled(true);
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
        board26 = new javax.swing.JButton();
        board27 = new javax.swing.JButton();
        board28 = new javax.swing.JButton();
        board29 = new javax.swing.JButton();
        board30 = new javax.swing.JButton();
        board31 = new javax.swing.JButton();
        board32 = new javax.swing.JButton();
        board33 = new javax.swing.JButton();
        board34 = new javax.swing.JButton();
        board35 = new javax.swing.JButton();
        board36 = new javax.swing.JButton();
        board37 = new javax.swing.JButton();
        board38 = new javax.swing.JButton();
        board39 = new javax.swing.JButton();
        board40 = new javax.swing.JButton();
        board41 = new javax.swing.JButton();
        board42 = new javax.swing.JButton();
        board43 = new javax.swing.JButton();
        board44 = new javax.swing.JButton();
        board45 = new javax.swing.JButton();
        board46 = new javax.swing.JButton();
        board47 = new javax.swing.JButton();
        board48 = new javax.swing.JButton();
        board49 = new javax.swing.JButton();
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

        phonePanel.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, -1));

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

        phonePanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 39, 248, -1));

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
        txtClick.setText("0/49");

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

        phonePanel.add(statusPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 101, 260, -1));

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
        player1icon.setText("Player 1");
        player1icon.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        player2icon.setForeground(new java.awt.Color(255, 255, 255));
        player2icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        player2icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-user-30.png"))); // NOI18N
        player2icon.setText("Player 2");
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
                .addComponent(player2icon, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        turn.setText("Player 1 turn");

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

        phonePanel.add(turnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 213, -1, -1));

        boardPanel.setBackground(new Color(0,0,0,200));
        boardPanel.setMaximumSize(new java.awt.Dimension(246, 246));
        boardPanel.setMinimumSize(new java.awt.Dimension(246, 246));
        boardPanel.setPreferredSize(new java.awt.Dimension(246, 246));

        board1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board1.setBorder(null);
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

        board2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board2.setBorder(null);
        board2.setFocusPainted(false);
        board2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board2.setMaximumSize(new java.awt.Dimension(67, 67));
        board2.setMinimumSize(new java.awt.Dimension(67, 67));
        board2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board2ActionPerformed(evt);
            }
        });

        board3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board3.setBorder(null);
        board3.setFocusPainted(false);
        board3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board3.setMaximumSize(new java.awt.Dimension(67, 67));
        board3.setMinimumSize(new java.awt.Dimension(67, 67));
        board3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board3ActionPerformed(evt);
            }
        });

        board4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board4.setBorder(null);
        board4.setFocusPainted(false);
        board4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board4.setMaximumSize(new java.awt.Dimension(67, 67));
        board4.setMinimumSize(new java.awt.Dimension(67, 67));
        board4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board4ActionPerformed(evt);
            }
        });

        board5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board5.setBorder(null);
        board5.setFocusPainted(false);
        board5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board5.setMaximumSize(new java.awt.Dimension(67, 67));
        board5.setMinimumSize(new java.awt.Dimension(67, 67));
        board5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board5ActionPerformed(evt);
            }
        });

        board6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board6.setBorder(null);
        board6.setFocusPainted(false);
        board6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board6.setMaximumSize(new java.awt.Dimension(67, 67));
        board6.setMinimumSize(new java.awt.Dimension(67, 67));
        board6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board6ActionPerformed(evt);
            }
        });

        board7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board7.setBorder(null);
        board7.setFocusPainted(false);
        board7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board7.setMaximumSize(new java.awt.Dimension(67, 67));
        board7.setMinimumSize(new java.awt.Dimension(67, 67));
        board7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board7ActionPerformed(evt);
            }
        });

        board8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board8.setBorder(null);
        board8.setFocusPainted(false);
        board8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board8.setMaximumSize(new java.awt.Dimension(67, 67));
        board8.setMinimumSize(new java.awt.Dimension(67, 67));
        board8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board8ActionPerformed(evt);
            }
        });

        board9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board9.setBorder(null);
        board9.setFocusPainted(false);
        board9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board9.setMaximumSize(new java.awt.Dimension(67, 67));
        board9.setMinimumSize(new java.awt.Dimension(67, 67));
        board9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board9ActionPerformed(evt);
            }
        });

        board10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board10.setBorder(null);
        board10.setFocusPainted(false);
        board10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board10.setMaximumSize(new java.awt.Dimension(67, 67));
        board10.setMinimumSize(new java.awt.Dimension(67, 67));
        board10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board10ActionPerformed(evt);
            }
        });

        board11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board11.setBorder(null);
        board11.setFocusPainted(false);
        board11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board11.setMaximumSize(new java.awt.Dimension(67, 67));
        board11.setMinimumSize(new java.awt.Dimension(67, 67));
        board11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board11ActionPerformed(evt);
            }
        });

        board12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board12.setBorder(null);
        board12.setFocusPainted(false);
        board12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board12.setMaximumSize(new java.awt.Dimension(67, 67));
        board12.setMinimumSize(new java.awt.Dimension(67, 67));
        board12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board12ActionPerformed(evt);
            }
        });

        board13.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board13.setBorder(null);
        board13.setFocusPainted(false);
        board13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board13.setMaximumSize(new java.awt.Dimension(67, 67));
        board13.setMinimumSize(new java.awt.Dimension(67, 67));
        board13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board13ActionPerformed(evt);
            }
        });

        board14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board14.setBorder(null);
        board14.setFocusPainted(false);
        board14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board14.setMaximumSize(new java.awt.Dimension(67, 67));
        board14.setMinimumSize(new java.awt.Dimension(67, 67));
        board14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board14ActionPerformed(evt);
            }
        });

        board15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board15.setBorder(null);
        board15.setFocusPainted(false);
        board15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board15.setMaximumSize(new java.awt.Dimension(67, 67));
        board15.setMinimumSize(new java.awt.Dimension(67, 67));
        board15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board15ActionPerformed(evt);
            }
        });

        board16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board16.setBorder(null);
        board16.setFocusPainted(false);
        board16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board16.setMaximumSize(new java.awt.Dimension(67, 67));
        board16.setMinimumSize(new java.awt.Dimension(67, 67));
        board16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board16ActionPerformed(evt);
            }
        });

        board17.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board17.setBorder(null);
        board17.setFocusPainted(false);
        board17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board17.setMaximumSize(new java.awt.Dimension(67, 67));
        board17.setMinimumSize(new java.awt.Dimension(67, 67));
        board17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board17ActionPerformed(evt);
            }
        });

        board18.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board18.setBorder(null);
        board18.setFocusPainted(false);
        board18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board18.setMaximumSize(new java.awt.Dimension(67, 67));
        board18.setMinimumSize(new java.awt.Dimension(67, 67));
        board18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board18ActionPerformed(evt);
            }
        });

        board19.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board19.setBorder(null);
        board19.setFocusPainted(false);
        board19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board19.setMaximumSize(new java.awt.Dimension(67, 67));
        board19.setMinimumSize(new java.awt.Dimension(67, 67));
        board19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board19ActionPerformed(evt);
            }
        });

        board20.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board20.setBorder(null);
        board20.setFocusPainted(false);
        board20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board20.setMaximumSize(new java.awt.Dimension(67, 67));
        board20.setMinimumSize(new java.awt.Dimension(67, 67));
        board20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board20ActionPerformed(evt);
            }
        });

        board21.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board21.setBorder(null);
        board21.setFocusPainted(false);
        board21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board21.setMaximumSize(new java.awt.Dimension(67, 67));
        board21.setMinimumSize(new java.awt.Dimension(67, 67));
        board21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board21ActionPerformed(evt);
            }
        });

        board22.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board22.setBorder(null);
        board22.setFocusPainted(false);
        board22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board22.setMaximumSize(new java.awt.Dimension(67, 67));
        board22.setMinimumSize(new java.awt.Dimension(67, 67));
        board22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board22ActionPerformed(evt);
            }
        });

        board23.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board23.setBorder(null);
        board23.setFocusPainted(false);
        board23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board23.setMaximumSize(new java.awt.Dimension(67, 67));
        board23.setMinimumSize(new java.awt.Dimension(67, 67));
        board23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board23ActionPerformed(evt);
            }
        });

        board24.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board24.setBorder(null);
        board24.setFocusPainted(false);
        board24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board24.setMaximumSize(new java.awt.Dimension(67, 67));
        board24.setMinimumSize(new java.awt.Dimension(67, 67));
        board24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board24ActionPerformed(evt);
            }
        });

        board25.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board25.setBorder(null);
        board25.setFocusPainted(false);
        board25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board25.setMaximumSize(new java.awt.Dimension(67, 67));
        board25.setMinimumSize(new java.awt.Dimension(67, 67));
        board25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board25ActionPerformed(evt);
            }
        });

        board26.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board26.setBorder(null);
        board26.setFocusPainted(false);
        board26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board26.setMaximumSize(new java.awt.Dimension(67, 67));
        board26.setMinimumSize(new java.awt.Dimension(67, 67));
        board26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board26ActionPerformed(evt);
            }
        });

        board27.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board27.setBorder(null);
        board27.setFocusPainted(false);
        board27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board27.setMaximumSize(new java.awt.Dimension(67, 67));
        board27.setMinimumSize(new java.awt.Dimension(67, 67));
        board27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board27ActionPerformed(evt);
            }
        });

        board28.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board28.setBorder(null);
        board28.setFocusPainted(false);
        board28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board28.setMaximumSize(new java.awt.Dimension(67, 67));
        board28.setMinimumSize(new java.awt.Dimension(67, 67));
        board28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board28ActionPerformed(evt);
            }
        });

        board29.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board29.setBorder(null);
        board29.setFocusPainted(false);
        board29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board29.setMaximumSize(new java.awt.Dimension(67, 67));
        board29.setMinimumSize(new java.awt.Dimension(67, 67));
        board29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board29ActionPerformed(evt);
            }
        });

        board30.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board30.setBorder(null);
        board30.setFocusPainted(false);
        board30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board30.setMaximumSize(new java.awt.Dimension(67, 67));
        board30.setMinimumSize(new java.awt.Dimension(67, 67));
        board30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board30ActionPerformed(evt);
            }
        });

        board31.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board31.setBorder(null);
        board31.setFocusPainted(false);
        board31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board31.setMaximumSize(new java.awt.Dimension(67, 67));
        board31.setMinimumSize(new java.awt.Dimension(67, 67));
        board31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board31ActionPerformed(evt);
            }
        });

        board32.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board32.setBorder(null);
        board32.setFocusPainted(false);
        board32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board32.setMaximumSize(new java.awt.Dimension(67, 67));
        board32.setMinimumSize(new java.awt.Dimension(67, 67));
        board32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board32ActionPerformed(evt);
            }
        });

        board33.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board33.setBorder(null);
        board33.setFocusPainted(false);
        board33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board33.setMaximumSize(new java.awt.Dimension(67, 67));
        board33.setMinimumSize(new java.awt.Dimension(67, 67));
        board33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board33ActionPerformed(evt);
            }
        });

        board34.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board34.setBorder(null);
        board34.setFocusPainted(false);
        board34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board34.setMaximumSize(new java.awt.Dimension(67, 67));
        board34.setMinimumSize(new java.awt.Dimension(67, 67));
        board34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board34ActionPerformed(evt);
            }
        });

        board35.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board35.setBorder(null);
        board35.setFocusPainted(false);
        board35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board35.setMaximumSize(new java.awt.Dimension(67, 67));
        board35.setMinimumSize(new java.awt.Dimension(67, 67));
        board35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board35ActionPerformed(evt);
            }
        });

        board36.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board36.setBorder(null);
        board36.setFocusPainted(false);
        board36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board36.setMaximumSize(new java.awt.Dimension(67, 67));
        board36.setMinimumSize(new java.awt.Dimension(67, 67));
        board36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board36ActionPerformed(evt);
            }
        });

        board37.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board37.setBorder(null);
        board37.setFocusPainted(false);
        board37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board37.setMaximumSize(new java.awt.Dimension(67, 67));
        board37.setMinimumSize(new java.awt.Dimension(67, 67));
        board37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board37ActionPerformed(evt);
            }
        });

        board38.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board38.setBorder(null);
        board38.setFocusPainted(false);
        board38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board38.setMaximumSize(new java.awt.Dimension(67, 67));
        board38.setMinimumSize(new java.awt.Dimension(67, 67));
        board38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board38ActionPerformed(evt);
            }
        });

        board39.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board39.setBorder(null);
        board39.setFocusPainted(false);
        board39.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board39.setMaximumSize(new java.awt.Dimension(67, 67));
        board39.setMinimumSize(new java.awt.Dimension(67, 67));
        board39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board39ActionPerformed(evt);
            }
        });

        board40.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board40.setBorder(null);
        board40.setFocusPainted(false);
        board40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board40.setMaximumSize(new java.awt.Dimension(67, 67));
        board40.setMinimumSize(new java.awt.Dimension(67, 67));
        board40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board40ActionPerformed(evt);
            }
        });

        board41.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board41.setBorder(null);
        board41.setFocusPainted(false);
        board41.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board41.setMaximumSize(new java.awt.Dimension(67, 67));
        board41.setMinimumSize(new java.awt.Dimension(67, 67));
        board41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board41ActionPerformed(evt);
            }
        });

        board42.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board42.setBorder(null);
        board42.setFocusPainted(false);
        board42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board42.setMaximumSize(new java.awt.Dimension(67, 67));
        board42.setMinimumSize(new java.awt.Dimension(67, 67));
        board42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board42ActionPerformed(evt);
            }
        });

        board43.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board43.setBorder(null);
        board43.setFocusPainted(false);
        board43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board43.setMaximumSize(new java.awt.Dimension(67, 67));
        board43.setMinimumSize(new java.awt.Dimension(67, 67));
        board43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board43ActionPerformed(evt);
            }
        });

        board44.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board44.setBorder(null);
        board44.setFocusPainted(false);
        board44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board44.setMaximumSize(new java.awt.Dimension(67, 67));
        board44.setMinimumSize(new java.awt.Dimension(67, 67));
        board44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board44ActionPerformed(evt);
            }
        });

        board45.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board45.setBorder(null);
        board45.setFocusPainted(false);
        board45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board45.setMaximumSize(new java.awt.Dimension(67, 67));
        board45.setMinimumSize(new java.awt.Dimension(67, 67));
        board45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board45ActionPerformed(evt);
            }
        });

        board46.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board46.setBorder(null);
        board46.setFocusPainted(false);
        board46.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board46.setMaximumSize(new java.awt.Dimension(67, 67));
        board46.setMinimumSize(new java.awt.Dimension(67, 67));
        board46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board46ActionPerformed(evt);
            }
        });

        board47.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board47.setBorder(null);
        board47.setFocusPainted(false);
        board47.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board47.setMaximumSize(new java.awt.Dimension(67, 67));
        board47.setMinimumSize(new java.awt.Dimension(67, 67));
        board47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board47ActionPerformed(evt);
            }
        });

        board48.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board48.setBorder(null);
        board48.setFocusPainted(false);
        board48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board48.setMaximumSize(new java.awt.Dimension(67, 67));
        board48.setMinimumSize(new java.awt.Dimension(67, 67));
        board48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board48ActionPerformed(evt);
            }
        });

        board49.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        board49.setBorder(null);
        board49.setFocusPainted(false);
        board49.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board49.setMaximumSize(new java.awt.Dimension(67, 67));
        board49.setMinimumSize(new java.awt.Dimension(67, 67));
        board49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board49ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(boardPanelLayout.createSequentialGroup()
                                    .addComponent(board43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(board44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(board45, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(boardPanelLayout.createSequentialGroup()
                                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(board15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(board16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(board17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(board3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(boardPanelLayout.createSequentialGroup()
                                    .addComponent(board46, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(board47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                    .addComponent(board18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(board19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                                    .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addComponent(board36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addComponent(board29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addComponent(board22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(boardPanelLayout.createSequentialGroup()
                            .addComponent(board34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                            .addComponent(board20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardPanelLayout.createSequentialGroup()
                            .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(board7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(board42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(boardPanelLayout.createSequentialGroup()
                                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(board7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(board13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(board14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(board21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(board20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board36, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board37, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board39, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board47, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board43, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board45, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board44, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board46, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(board41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board42, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(board49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(board48, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        phonePanel.add(boardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 250, 248, -1));

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

        phonePanel.add(bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 502, 248, -1));
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
        
        if (board1.getText().isEmpty()) {
            board1.setEnabled(false);
            board1.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board1.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board1.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board1ActionPerformed

    private void board3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board3ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board3.getText().isEmpty()) {
            board3.setEnabled(false);
            board3.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board3.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board3.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board3ActionPerformed

    private void board5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board5ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board5.getText().isEmpty()) {
            board5.setEnabled(false);
            board5.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board5.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board5.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board5ActionPerformed

    private void board15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board15ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board15.getText().isEmpty()) {
            board15.setEnabled(false);
            board15.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board15.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board15.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board15ActionPerformed

    private void board17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board17ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board17.getText().isEmpty()) {
            board17.setEnabled(false);
            board17.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board17.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board17.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board17ActionPerformed

    private void board19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board19ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board19.getText().isEmpty()) {
            board19.setEnabled(false);
            board19.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board19.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board19.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board19ActionPerformed

    private void board43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board43ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board43.getText().isEmpty()) {
            board43.setEnabled(false);
            board43.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board43.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board43.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board43ActionPerformed

    private void board45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board45ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board45.getText().isEmpty()) {
            board45.setEnabled(false);
            board45.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board45.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board45.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board45ActionPerformed

    private void board47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board47ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board47.getText().isEmpty()) {
            board47.setEnabled(false);
            board47.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board47.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board47.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board47ActionPerformed

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
        if (board2.getText().isEmpty()) {
            board2.setEnabled(false);
            board2.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board2.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board2.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board2ActionPerformed

    private void board4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board4ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board4.getText().isEmpty()) {
            board4.setEnabled(false);
            board4.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board4.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board4.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board4ActionPerformed

    private void board8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board8ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board8.getText().isEmpty()) {
            board8.setEnabled(false);
            board8.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board8.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board8.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board8ActionPerformed

    private void board10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board10ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board10.getText().isEmpty()) {
            board10.setEnabled(false);
            board10.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board10.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board10.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board10ActionPerformed

    private void board9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board9ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board9.getText().isEmpty()) {
            board9.setEnabled(false);
            board9.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board9.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board9.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board9ActionPerformed

    private void board11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board11ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board11.getText().isEmpty()) {
            board11.setEnabled(false);
            board11.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board11.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board11.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board11ActionPerformed

    private void board18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board18ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board18.getText().isEmpty()) {
            board18.setEnabled(false);
            board18.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board18.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board18.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board18ActionPerformed

    private void board12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board12ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board12.getText().isEmpty()) {
            board12.setEnabled(false);
            board12.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board12.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board12.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board12ActionPerformed

    private void board16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board16ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board16.getText().isEmpty()) {
            board16.setEnabled(false);
            board16.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board16.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board16.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board16ActionPerformed

    private void board36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board36ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board36.getText().isEmpty()) {
            board36.setEnabled(false);
            board36.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board36.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board36.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board36ActionPerformed

    private void board37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board37ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board37.getText().isEmpty()) {
            board37.setEnabled(false);
            board37.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board37.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board37.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board37ActionPerformed

    private void board38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board38ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board38.getText().isEmpty()) {
            board38.setEnabled(false);
            board38.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board38.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board38.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board38ActionPerformed

    private void board39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board39ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board39.getText().isEmpty()) {
            board39.setEnabled(false);
            board39.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board39.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board39.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board39ActionPerformed

    private void board40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board40ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board40.getText().isEmpty()) {
            board40.setEnabled(false);
            board40.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board40.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board40.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board40ActionPerformed

    private void board44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board44ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board44.getText().isEmpty()) {
            board44.setEnabled(false);
            board44.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board44.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board44.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board44ActionPerformed

    private void board46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board46ActionPerformed
        // TODO add your handling code here:
        generateSong();
        if (board46.getText().isEmpty()) {
            board46.setEnabled(false);
            board46.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board46.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board46.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board46ActionPerformed

    private void board6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board6ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board6.getText().isEmpty()) {
            board6.setEnabled(false);
            board6.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board6.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board6.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board6ActionPerformed

    private void board7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board7ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board7.getText().isEmpty()) {
            board7.setEnabled(false);
            board7.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board7.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board7.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board7ActionPerformed

    private void board27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board27ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board27.getText().isEmpty()) {
            board27.setEnabled(false);
            board27.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board27.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board27.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board27ActionPerformed

    private void board28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board28ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board28.getText().isEmpty()) {
            board28.setEnabled(false);
            board28.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board28.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board28.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board28ActionPerformed

    private void board34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board34ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board34.getText().isEmpty()) {
            board34.setEnabled(false);
            board34.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board34.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board34.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board34ActionPerformed

    private void board35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board35ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board35.getText().isEmpty()) {
            board35.setEnabled(false);
            board35.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board35.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board35.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board35ActionPerformed

    private void board13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board13ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board13.getText().isEmpty()) {
            board13.setEnabled(false);
            board13.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board13.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board13.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board13ActionPerformed

    private void board14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board14ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board14.getText().isEmpty()) {
            board14.setEnabled(false);
            board14.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board14.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board14.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board14ActionPerformed

    private void board20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board20ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board20.getText().isEmpty()) {
            board20.setEnabled(false);
            board20.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board20.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board20.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board20ActionPerformed

    private void board21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board21ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board21.getText().isEmpty()) {
            board21.setEnabled(false);
            board21.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board21.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board21.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board21ActionPerformed

    private void board41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board41ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board41.getText().isEmpty()) {
            board41.setEnabled(false);
            board41.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board41.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board41.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board41ActionPerformed

    private void board42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board42ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board42.getText().isEmpty()) {
            board42.setEnabled(false);
            board42.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board42.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board42.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board42ActionPerformed

    private void board48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board48ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board48.getText().isEmpty()) {
            board48.setEnabled(false);
            board48.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board48.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board48.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board48ActionPerformed

    private void board49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board49ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board49.getText().isEmpty()) {
            board49.setEnabled(false);
            board49.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board49.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board49.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board49ActionPerformed

    private void board22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board22ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board22.getText().isEmpty()) {
            board22.setEnabled(false);
            board22.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board22.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board22.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board22ActionPerformed

    private void board23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board23ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board23.getText().isEmpty()) {
            board23.setEnabled(false);
            board23.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board23.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board23.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board23ActionPerformed

    private void board24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board24ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board24.getText().isEmpty()) {
            board24.setEnabled(false);
            board24.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board24.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board24.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board24ActionPerformed

    private void board25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board25ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board25.getText().isEmpty()) {
            board25.setEnabled(false);
            board25.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board25.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board25.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board25ActionPerformed

    private void board26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board26ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board26.getText().isEmpty()) {
            board26.setEnabled(false);
            board26.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board26.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board26.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board26ActionPerformed

    private void board29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board29ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board29.getText().isEmpty()) {
            board29.setEnabled(false);
            board29.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board29.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board29.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board29ActionPerformed

    private void board30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board30ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board30.getText().isEmpty()) {
            board30.setEnabled(false);
            board30.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board30.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board30.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board30ActionPerformed

    private void board31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board31ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board31.getText().isEmpty()) {
            board31.setEnabled(false);
            board31.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board31.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board31.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board31ActionPerformed

    private void board32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board32ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board32.getText().isEmpty()) {
            board32.setEnabled(false);
            board32.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board32.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board32.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board32ActionPerformed

    private void board33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_board33ActionPerformed
        // TODO add your handling code here:
        generateSong();
        
        if (board33.getText().isEmpty()) {
            board33.setEnabled(false);
            board33.setText(whoseTurn);

            if(whoseTurn.equalsIgnoreCase("X")){
                board33.setForeground(Color.blue);
                turn.setText("Player 2 turn");
            }
            else{
                board33.setForeground(Color.magenta);
                turn.setText("Player 1 turn");
            }
            checkClick();
            determineWhoseTurn();
            determineIfWin();
            detDraw();
        }
    }//GEN-LAST:event_board33ActionPerformed

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
            java.util.logging.Logger.getLogger(Board77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board77.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Board77().setVisible(true);
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
    private javax.swing.JButton board26;
    private javax.swing.JButton board27;
    private javax.swing.JButton board28;
    private javax.swing.JButton board29;
    private javax.swing.JButton board3;
    private javax.swing.JButton board30;
    private javax.swing.JButton board31;
    private javax.swing.JButton board32;
    private javax.swing.JButton board33;
    private javax.swing.JButton board34;
    private javax.swing.JButton board35;
    private javax.swing.JButton board36;
    private javax.swing.JButton board37;
    private javax.swing.JButton board38;
    private javax.swing.JButton board39;
    private javax.swing.JButton board4;
    private javax.swing.JButton board40;
    private javax.swing.JButton board41;
    private javax.swing.JButton board42;
    private javax.swing.JButton board43;
    private javax.swing.JButton board44;
    private javax.swing.JButton board45;
    private javax.swing.JButton board46;
    private javax.swing.JButton board47;
    private javax.swing.JButton board48;
    private javax.swing.JButton board49;
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
