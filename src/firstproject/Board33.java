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
public class Board33 extends javax.swing.JFrame {
    
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
    public Board33() {
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
            int choice = JOptionPane.showConfirmDialog(null, "Last scores is saved\nPlay again?", "TIMED OUT", 
                    JOptionPane.YES_NO_OPTION);
        
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
       
        if(!"".equals(one) && !"".equals(two) && !"".equals(three) && !"".equals(four) && !"".equals(five)
                && !"".equals(six) && !"".equals(seven) && !"".equals(eight) && !"".equals(nine) ){
            
            drawSound();
            
            Status.setText("Draw");
            if(timer_info){
            timer.stop();
            }
            
            int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Draw", 
                    JOptionPane.YES_NO_OPTION);
        
            switch (choice) {
                case JOptionPane.YES_OPTION -> {
                resetGame();
                Status.setText("Who will win?");
                }
                // Perform action if user clicked Yes
                case JOptionPane.NO_OPTION -> {
                resetGame();
                isExit = true;
                generateSong();
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
        if (timer_info) {
        timer.stop();
        }
        
        
        int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Player 1 win", 
                JOptionPane.YES_NO_OPTION);
        
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
    private void oWins(){
        winSound();
        Status.setText("Player 2 win!");
        if (score_info){
        player2score.setText(""+playerTwoScore+++"");
        }
        if (timer_info){
        timer.stop();
        }
        
        int choice = JOptionPane.showConfirmDialog(null, "Continue to play?", "Player 2 win",
                JOptionPane.YES_NO_OPTION);
        
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
        
        
        if("X".equals(one) && "X".equals(two) && "X".equals(three)  ){
            winEffect(board1,board2,board3);
            xWins();
        }
        else if("X".equals(four) && "X".equals(five) && "X".equals(six)  ){
            winEffect(board4,board5,board6);
            xWins();
        }
        else if("X".equals(seven) && "X".equals(eight) && "X".equals(nine)  ){
            winEffect(board7,board8,board9);
            xWins();
        }
        else if("X".equals(one) && "X".equals(four) && "X".equals(seven)  ){
            winEffect(board1,board4,board7);
            xWins();
        }
        else if("X".equals(two) && "X".equals(five) && "X".equals(eight)  ){
            winEffect(board2,board5,board8);
            xWins();
        }
        else if("X".equals(three) && "X".equals(six) && "X".equals(nine)  ){
            winEffect(board3,board6,board9);
            xWins();
        }
        else if("X".equals(one) && "X".equals(five) && "X".equals(nine)  ){
            winEffect(board1,board5,board9);
            xWins();
        }
        else if("X".equals(seven) && "X".equals(five) && "X".equals(three)  ){
            winEffect(board7,board5,board3);
            xWins();
        }
        
        // if o wins 
        else if("O".equals(one) && "O".equals(two) && "O".equals(three)  ){
            winEffect(board1,board2,board3);
            oWins();
        }
        else if("O".equals(four) && "O".equals(five) && "O".equals(six)  ){
            winEffect(board4,board5,board6);
             oWins();
        }
        else if("O".equals(seven) && "O".equals(eight) && "O".equals(nine)  ){
            winEffect(board7,board8,board9);
             oWins();
        }
        else if("O".equals(one) && "O".equals(four) && "O".equals(seven)  ){
            winEffect(board1,board4,board7);
            oWins();
        }
        else if("O".equals(two) && "O".equals(five) && "O".equals(eight)  ){
            winEffect(board2,board5,board8);
             oWins();
        }
        else if("O".equals(three) && "O".equals(six) && "O".equals(nine)  ){
            winEffect(board3,board6,board9);
             oWins();
        }
        else if("O".equals(one) && "O".equals(five) && "O".equals(nine)  ){
            winEffect(board1,board5,board9);
             oWins();
        }
        else if("O".equals(seven) && "O".equals(five) && "O".equals(three)  ){
            winEffect(board7,board5,board3);
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
        
        if(board_counter){
        clicktime=1;
        txtClick.setText("0/9");
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
        
        board1.setBackground(Color.LIGHT_GRAY);
        board2.setBackground(Color.LIGHT_GRAY);
        board3.setBackground(Color.LIGHT_GRAY);
        board4.setBackground(Color.LIGHT_GRAY);
        board5.setBackground(Color.LIGHT_GRAY);
        board6.setBackground(Color.LIGHT_GRAY);
        board7.setBackground(Color.LIGHT_GRAY);
        board8.setBackground(Color.LIGHT_GRAY);
        board9.setBackground(Color.LIGHT_GRAY);
        
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
        String click = String.format("%d/9", clicktime++);
        txtClick.setText(click);
        }
    }
    
    private void winEffect(JButton b1,JButton b2,JButton b3){
        b1.setBackground(Color.green);
        b2.setBackground(Color.green);
        b3.setBackground(Color.green);
        
        b1.setEnabled(true);
        b2.setEnabled(true);
        b3.setEnabled(true);
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
        bottomPanel = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        reset = new javax.swing.JButton();
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
        txtClick.setText("0/9");

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
                            .addComponent(clickIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(8, 8, 8))
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

        board1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
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

        board2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board2.setFocusPainted(false);
        board2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board2.setMaximumSize(new java.awt.Dimension(67, 67));
        board2.setMinimumSize(new java.awt.Dimension(67, 67));
        board2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board2ActionPerformed(evt);
            }
        });

        board3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board3.setFocusPainted(false);
        board3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board3.setMaximumSize(new java.awt.Dimension(67, 67));
        board3.setMinimumSize(new java.awt.Dimension(67, 67));
        board3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board3ActionPerformed(evt);
            }
        });

        board4.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board4.setFocusPainted(false);
        board4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board4.setMaximumSize(new java.awt.Dimension(67, 67));
        board4.setMinimumSize(new java.awt.Dimension(67, 67));
        board4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board4ActionPerformed(evt);
            }
        });

        board5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board5.setFocusPainted(false);
        board5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board5.setMaximumSize(new java.awt.Dimension(67, 67));
        board5.setMinimumSize(new java.awt.Dimension(67, 67));
        board5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board5ActionPerformed(evt);
            }
        });

        board6.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board6.setFocusPainted(false);
        board6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board6.setMaximumSize(new java.awt.Dimension(67, 67));
        board6.setMinimumSize(new java.awt.Dimension(67, 67));
        board6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board6ActionPerformed(evt);
            }
        });

        board7.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board7.setFocusPainted(false);
        board7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board7.setMaximumSize(new java.awt.Dimension(67, 67));
        board7.setMinimumSize(new java.awt.Dimension(67, 67));
        board7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board7ActionPerformed(evt);
            }
        });

        board8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board8.setFocusPainted(false);
        board8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board8.setMaximumSize(new java.awt.Dimension(67, 67));
        board8.setMinimumSize(new java.awt.Dimension(67, 67));
        board8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board8ActionPerformed(evt);
            }
        });

        board9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        board9.setFocusPainted(false);
        board9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        board9.setMaximumSize(new java.awt.Dimension(67, 67));
        board9.setMinimumSize(new java.awt.Dimension(67, 67));
        board9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                board9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(boardPanelLayout.createSequentialGroup()
                        .addComponent(board7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(board1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(board4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(board7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(board9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
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

        reset.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-bin-16.png"))); // NOI18N
        reset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        phonePanel.add(bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 507, 248, -1));
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

    private void audioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioButtonActionPerformed
        // TODO add your handling code here:
        
        generateSong();
        toggleMute();
    }//GEN-LAST:event_audioButtonActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
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
    }//GEN-LAST:event_resetActionPerformed

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
            java.util.logging.Logger.getLogger(Board33.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board33.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board33.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board33.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board33().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.JLabel VS;
    private javax.swing.JButton audioButton;
    private javax.swing.JLabel bg;
    private javax.swing.JButton board1;
    private javax.swing.JButton board2;
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
    private javax.swing.JButton reset;
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
