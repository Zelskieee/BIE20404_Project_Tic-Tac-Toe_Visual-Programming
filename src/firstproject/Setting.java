/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package firstproject;


import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import swing.EventSwitchSelected;
/**
 *
 * @author Ahmad Syukri
 */
public class Setting extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    boolean switchtimer;
    boolean switchboard;
    boolean switchscore;
    /**
     * Creates new form Homepage
     */
    public Setting(){
        setUndecorated(true);
        initComponents();
        setBackground(new Color(0,0,0,0));
        mainPanel.setBackground(new Color(0,0,0,0));
        
        //appIcon
        setIcon();
        
        // Load the image icon
        ImageIcon icon = new ImageIcon("src/img/neonlight.jpg");

        // Set the icon to the label
        bg.setIcon(icon);
        
        // Fit the icon to the size of the label
        fitIconToLabel(bg, icon);
        
        try {
            Connect();
        } catch (SQLException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        currentSetting();
        
        switchTimer.addEventSelected(new EventSwitchSelected() {
            @Override
            
            public void onSelected(boolean selected) {
                if (selected) {
                    switchtimer = true;
                    setSwitchTimer();
                } else {
                    switchtimer = false;
                    setSwitchTimer();
                }
            }
            
        });
        
        
        switchBoardInfo.addEventSelected(new EventSwitchSelected() {
            @Override
            
            public void onSelected(boolean selected) {
                if (selected) {
                    switchboard = true;
                    setSwitchBoardInfo();
                } else {
                    switchboard = false;
                    setSwitchBoardInfo();
                }
            }
            
        });
        
        switchScoreInfo.addEventSelected(new EventSwitchSelected() {
            @Override
            
            public void onSelected(boolean selected) {
                if (selected) {
                    switchscore = true;
                    setSwitchScoreInfo();
                } else {
                    switchscore = false;
                    setSwitchScoreInfo();
                }
            }
            
        });
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
            String sqlQuery = "SELECT Gamemode, Board, Match_timer, Board_info, Score FROM setting";

            // Prepare the SQL statement
            pst = con.prepareStatement(sqlQuery);

            // Execute the query
            rs = pst.executeQuery();

            // Retrieve the first value from the result set
            if (rs.next()) {
                String gamemode = rs.getString("Gamemode");
                String board = rs.getString("Board");
                boolean match_timer = rs.getBoolean("Match_timer");
                boolean board_info = rs.getBoolean("Board_info");
                boolean score = rs.getBoolean("Score");
                // Set the selected item of the combo box to match the retrieved value
                cbGamemode.setSelectedItem(gamemode);
                cbBoardtype.setSelectedItem(board);
                switchTimer.setSelected(match_timer);
                switchBoardInfo.setSelected(board_info);
                switchScoreInfo.setSelected(score);
            }

            // Close the result set, statement, and connection
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential SQL errors here
        }
    }

    private void setSwitchTimer(){
        try {
            // TODO add your handling code here:
            
            
            String query = "UPDATE setting SET Match_timer=? where 1";
            pst = con.prepareStatement(query);
            pst.setBoolean(1, switchtimer);
            pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void setSwitchBoardInfo(){
        try {
            // TODO add your handling code here:
            
            
            String query = "UPDATE setting SET Board_info=? where 1";
            pst = con.prepareStatement(query);
            pst.setBoolean(1, switchboard);
            pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void setSwitchScoreInfo(){
        try {
            // TODO add your handling code here:
            
            
            String query = "UPDATE setting SET Score=? where 1";
            pst = con.prepareStatement(query);
            pst.setBoolean(1, switchscore);
            pst.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        btnClose = new javax.swing.JButton();
        btnHome = new javax.swing.JButton();
        settingLabel = new javax.swing.JLabel();
        generalLabel = new javax.swing.JLabel();
        gamemodePanel = new javax.swing.JPanel();
        gamemodeLabel = new javax.swing.JLabel();
        gamemodeIcon = new javax.swing.JLabel();
        cbGamemode = new javax.swing.JComboBox<>();
        boardPanel = new javax.swing.JPanel();
        boardLabel = new javax.swing.JLabel();
        boardIcon = new javax.swing.JLabel();
        cbBoardtype = new javax.swing.JComboBox<>();
        matchinfoLabel = new javax.swing.JLabel();
        matchtimerPanel = new javax.swing.JPanel();
        matchtimerLabel = new javax.swing.JLabel();
        matchtimerIcon = new javax.swing.JLabel();
        switchTimer = new swing.SwitchButton();
        boardinfoPanel = new javax.swing.JPanel();
        boardinfoLabel = new javax.swing.JLabel();
        boardinfoIcon = new javax.swing.JLabel();
        switchBoardInfo = new swing.SwitchButton();
        scoreinfoPanel = new javax.swing.JPanel();
        scoreinfoLabel = new javax.swing.JLabel();
        scoreinfoIcon = new javax.swing.JLabel();
        switchScoreInfo = new swing.SwitchButton();
        resetdefaultButton = new javax.swing.JButton();
        resetdefaultPanel = new javax.swing.JPanel();
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
        phonePanel.setPreferredSize(new java.awt.Dimension(260, 550));
        setResizable(false);
        phonePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel.setBackground(new java.awt.Color(51, 51, 51));
        topPanel.setMaximumSize(new java.awt.Dimension(260, 35));
        topPanel.setMinimumSize(new java.awt.Dimension(260, 35));

        btnClose.setBackground(new java.awt.Color(0, 0, 0));
        btnClose.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 0, 0));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-x-16.png"))); // NOI18N
        btnClose.setText("X");
        btnClose.setAlignmentY(0.0F);
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClose.setFocusPainted(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setMaximumSize(new java.awt.Dimension(30, 23));
        btnClose.setMinimumSize(new java.awt.Dimension(30, 23));
        btnClose.setPreferredSize(new java.awt.Dimension(30, 23));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnHome.setBackground(new java.awt.Color(0, 0, 0));
        btnHome.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-home-16.png"))); // NOI18N
        btnHome.setFocusPainted(false);
        btnHome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHome.setMaximumSize(new java.awt.Dimension(30, 23));
        btnHome.setMinimumSize(new java.awt.Dimension(30, 23));
        btnHome.setPreferredSize(new java.awt.Dimension(30, 23));
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );

        phonePanel.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        settingLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        settingLabel.setForeground(new java.awt.Color(255, 255, 255));
        settingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        settingLabel.setText("Settings");
        settingLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        phonePanel.add(settingLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 45, 226, 30));

        generalLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        generalLabel.setForeground(new java.awt.Color(255, 255, 255));
        generalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        generalLabel.setText("General");
        generalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        phonePanel.add(generalLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 91, 226, -1));

        gamemodePanel.setBackground(new Color(0,0,0,150));
        gamemodePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), new java.awt.Color(0, 0, 0)));
        gamemodePanel.setMaximumSize(new java.awt.Dimension(226, 40));
        gamemodePanel.setMinimumSize(new java.awt.Dimension(226, 40));
        gamemodePanel.setPreferredSize(new java.awt.Dimension(112, 58));

        gamemodeLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        gamemodeLabel.setForeground(new java.awt.Color(255, 255, 255));
        gamemodeLabel.setText("Gamemode");

        gamemodeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gamemodeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-game-controller-30.png"))); // NOI18N
        gamemodeIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gamemodeIcon.setMaximumSize(new java.awt.Dimension(45, 45));
        gamemodeIcon.setMinimumSize(new java.awt.Dimension(45, 45));

        cbGamemode.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        cbGamemode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Singleplayer", "Multiplayer" }));
        cbGamemode.setFocusable(false);
        cbGamemode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbGamemodeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout gamemodePanelLayout = new javax.swing.GroupLayout(gamemodePanel);
        gamemodePanel.setLayout(gamemodePanelLayout);
        gamemodePanelLayout.setHorizontalGroup(
            gamemodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamemodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamemodeIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamemodeLabel)
                .addGap(16, 16, 16)
                .addComponent(cbGamemode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        gamemodePanelLayout.setVerticalGroup(
            gamemodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamemodePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamemodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, gamemodePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gamemodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbGamemode, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(gamemodeIcon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(3, Short.MAX_VALUE))
        );

        phonePanel.add(gamemodePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 113, 226, -1));

        boardPanel.setBackground(new Color(0,0,0,150));
        boardPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), new java.awt.Color(0, 0, 0)));
        boardPanel.setPreferredSize(new java.awt.Dimension(112, 58));

        boardLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        boardLabel.setForeground(new java.awt.Color(255, 255, 255));
        boardLabel.setText("Board");

        boardIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boardIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-tic-tac-toe-30.png"))); // NOI18N
        boardIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boardIcon.setMaximumSize(new java.awt.Dimension(45, 45));
        boardIcon.setMinimumSize(new java.awt.Dimension(45, 45));

        cbBoardtype.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        cbBoardtype.setMaximumRowCount(3);
        cbBoardtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3x3", "5x5", "7x7" }));
        cbBoardtype.setFocusable(false);
        cbBoardtype.setMaximumSize(new java.awt.Dimension(89, 20));
        cbBoardtype.setMinimumSize(new java.awt.Dimension(89, 20));
        cbBoardtype.setPreferredSize(new java.awt.Dimension(89, 20));
        cbBoardtype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbBoardtypeItemStateChanged(evt);
            }
        });
        cbBoardtype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBoardtypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boardLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbBoardtype, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boardLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbBoardtype, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        phonePanel.add(boardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 183, 226, -1));

        matchinfoLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        matchinfoLabel.setForeground(new java.awt.Color(255, 255, 255));
        matchinfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchinfoLabel.setText("Match info");
        phonePanel.add(matchinfoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 259, 226, -1));

        matchtimerPanel.setBackground(new Color(0,0,0,150));
        matchtimerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), new java.awt.Color(0, 0, 0)));
        matchtimerPanel.setMaximumSize(new java.awt.Dimension(226, 58));
        matchtimerPanel.setMinimumSize(new java.awt.Dimension(226, 58));
        matchtimerPanel.setPreferredSize(new java.awt.Dimension(226, 58));

        matchtimerLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        matchtimerLabel.setForeground(new java.awt.Color(255, 255, 255));
        matchtimerLabel.setText("Match Timer");
        matchtimerLabel.setToolTipText("Keep track of how long the match takes");

        matchtimerIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchtimerIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-historical-30.png"))); // NOI18N
        matchtimerIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        matchtimerIcon.setMaximumSize(new java.awt.Dimension(45, 45));
        matchtimerIcon.setMinimumSize(new java.awt.Dimension(45, 45));
        matchtimerIcon.setPreferredSize(new java.awt.Dimension(45, 45));

        switchTimer.setBackground(new java.awt.Color(255, 0, 0));
        switchTimer.setName(""); // NOI18N
        switchTimer.setSelected(true);

        javax.swing.GroupLayout matchtimerPanelLayout = new javax.swing.GroupLayout(matchtimerPanel);
        matchtimerPanel.setLayout(matchtimerPanelLayout);
        matchtimerPanelLayout.setHorizontalGroup(
            matchtimerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(matchtimerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(matchtimerIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchtimerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(switchTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        matchtimerPanelLayout.setVerticalGroup(
            matchtimerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(matchtimerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(matchtimerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(matchtimerIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matchtimerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, matchtimerPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(switchTimer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        phonePanel.add(matchtimerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 281, -1, -1));

        boardinfoPanel.setBackground(new Color(0,0,0,150));
        boardinfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), new java.awt.Color(0, 0, 0)));
        boardinfoPanel.setPreferredSize(new java.awt.Dimension(226, 58));

        boardinfoLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        boardinfoLabel.setForeground(new java.awt.Color(255, 255, 255));
        boardinfoLabel.setText("Board Info");
        boardinfoLabel.setToolTipText("Display number of spots taken");

        boardinfoIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boardinfoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-dialing-number-30.png"))); // NOI18N
        boardinfoIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boardinfoIcon.setMaximumSize(new java.awt.Dimension(45, 45));
        boardinfoIcon.setMinimumSize(new java.awt.Dimension(45, 45));

        switchBoardInfo.setBackground(new java.awt.Color(255, 0, 0));
        switchBoardInfo.setSelected(true);

        javax.swing.GroupLayout boardinfoPanelLayout = new javax.swing.GroupLayout(boardinfoPanel);
        boardinfoPanel.setLayout(boardinfoPanelLayout);
        boardinfoPanelLayout.setHorizontalGroup(
            boardinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardinfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardinfoIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boardinfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(switchBoardInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        boardinfoPanelLayout.setVerticalGroup(
            boardinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardinfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boardinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(boardinfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardinfoIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardinfoPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(switchBoardInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        phonePanel.add(boardinfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 351, -1, -1));

        scoreinfoPanel.setBackground(new Color(0,0,0,150));
        scoreinfoPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 0, 0), new java.awt.Color(0, 0, 0)));
        scoreinfoPanel.setPreferredSize(new java.awt.Dimension(226, 58));

        scoreinfoLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        scoreinfoLabel.setForeground(new java.awt.Color(255, 255, 255));
        scoreinfoLabel.setText("Score Info");
        scoreinfoLabel.setToolTipText("Display score ");

        scoreinfoIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreinfoIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-score-30 (1).png"))); // NOI18N
        scoreinfoIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        scoreinfoIcon.setMaximumSize(new java.awt.Dimension(45, 45));
        scoreinfoIcon.setMinimumSize(new java.awt.Dimension(45, 45));

        switchScoreInfo.setBackground(new java.awt.Color(255, 0, 0));
        switchScoreInfo.setSelected(true);

        javax.swing.GroupLayout scoreinfoPanelLayout = new javax.swing.GroupLayout(scoreinfoPanel);
        scoreinfoPanel.setLayout(scoreinfoPanelLayout);
        scoreinfoPanelLayout.setHorizontalGroup(
            scoreinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreinfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreinfoIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scoreinfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(switchScoreInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        scoreinfoPanelLayout.setVerticalGroup(
            scoreinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreinfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scoreinfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scoreinfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scoreinfoIcon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreinfoPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(switchScoreInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        phonePanel.add(scoreinfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 421, -1, -1));

        resetdefaultButton.setBackground(new java.awt.Color(0, 0, 0));
        resetdefaultButton.setForeground(new java.awt.Color(255, 255, 255));
        resetdefaultButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset-30.png"))); // NOI18N
        resetdefaultButton.setText("Reset to default");
        resetdefaultButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        resetdefaultButton.setContentAreaFilled(false);
        resetdefaultButton.setFocusable(false);
        resetdefaultButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        resetdefaultButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-reset-30 - rotatright.png"))); // NOI18N
        resetdefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetdefaultButtonActionPerformed(evt);
            }
        });
        phonePanel.add(resetdefaultButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 491, 150, 40));

        resetdefaultPanel.setBackground(new Color(0,0,0,200));

        javax.swing.GroupLayout resetdefaultPanelLayout = new javax.swing.GroupLayout(resetdefaultPanel);
        resetdefaultPanel.setLayout(resetdefaultPanelLayout);
        resetdefaultPanelLayout.setHorizontalGroup(
            resetdefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );
        resetdefaultPanelLayout.setVerticalGroup(
            resetdefaultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        phonePanel.add(resetdefaultPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 491, 149, 40));
        phonePanel.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 30, 300, 520));

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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        // TODO add your handling code here:
        Homepage m = new Homepage();
        this.hide();
        m.setVisible(true);
    }//GEN-LAST:event_btnHomeActionPerformed

    private void resetdefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetdefaultButtonActionPerformed
        // TODO add your handling code here:
        cbGamemode.setSelectedItem("Singleplayer");
        cbBoardtype.setSelectedItem("3x3");
        switchTimer.setSelected(true);
        switchBoardInfo.setSelected(true);
        switchScoreInfo.setSelected(true);
    }//GEN-LAST:event_resetdefaultButtonActionPerformed

    private void cbGamemodeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbGamemodeItemStateChanged
        try {
            // TODO add your handling code here:
            String Gamemode = (String) cbGamemode.getSelectedItem();
            
            String query = "UPDATE setting SET Gamemode=? where 1";
            pst = con.prepareStatement(query);
            pst.setString(1, Gamemode);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbGamemodeItemStateChanged

    private void cbBoardtypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbBoardtypeItemStateChanged
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            String Boardtype = (String) cbBoardtype.getSelectedItem();
            
            String query = "UPDATE setting SET Board=? where 1";
            pst = con.prepareStatement(query);
            pst.setString(1, Boardtype);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbBoardtypeItemStateChanged

    private void cbBoardtypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBoardtypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBoardtypeActionPerformed

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
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Setting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JLabel boardIcon;
    private javax.swing.JLabel boardLabel;
    private javax.swing.JPanel boardPanel;
    private javax.swing.JLabel boardinfoIcon;
    private javax.swing.JLabel boardinfoLabel;
    private javax.swing.JPanel boardinfoPanel;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnHome;
    private javax.swing.JComboBox<String> cbBoardtype;
    private javax.swing.JComboBox<String> cbGamemode;
    private javax.swing.JLabel gamemodeIcon;
    private javax.swing.JLabel gamemodeLabel;
    private javax.swing.JPanel gamemodePanel;
    private javax.swing.JLabel generalLabel;
    private javax.swing.JLabel iPhoneTemplate;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel matchinfoLabel;
    private javax.swing.JLabel matchtimerIcon;
    private javax.swing.JLabel matchtimerLabel;
    private javax.swing.JPanel matchtimerPanel;
    private javax.swing.JPanel phonePanel;
    private javax.swing.JButton resetdefaultButton;
    private javax.swing.JPanel resetdefaultPanel;
    private javax.swing.JLabel scoreinfoIcon;
    private javax.swing.JLabel scoreinfoLabel;
    private javax.swing.JPanel scoreinfoPanel;
    private javax.swing.JLabel settingLabel;
    private swing.SwitchButton switchBoardInfo;
    private swing.SwitchButton switchScoreInfo;
    private swing.SwitchButton switchTimer;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
