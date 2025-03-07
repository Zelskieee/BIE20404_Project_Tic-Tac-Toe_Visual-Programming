/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package firstproject;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//connection
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Ahmad Syukri
 */
public class Homepage extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    /**
     * Creates new form Homepage
     */
    public Homepage() {
        setUndecorated(true);
        initComponents();
        setBackground(new Color(0,0,0,0));
        mainPanel.setBackground(new Color(0,0,0,0));
        
        bttPlay.setBackground(new Color(0,0,0));
        bttSetting.setBackground(new Color(0,0,0));
        
        // App icon
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
    
    private void fitIconToLabel(JLabel bg, ImageIcon icon) {
        // Get the size of the label
        int labelWidth = bg.getWidth();
        int labelHeight = bg.getHeight();

        // Resize the icon to fit the label size
        Image image = icon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        icon.setImage(image);
        bg.setIcon(icon);
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
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        bttPlay = new javax.swing.JButton();
        bttSetting = new javax.swing.JButton();
        creditLabel = new javax.swing.JLabel();
        tictactoeIcon = new javax.swing.JLabel();
        playPanel = new javax.swing.JPanel();
        settingPanel = new javax.swing.JPanel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iPhoneTemplate.setBackground(new java.awt.Color(255, 255, 255));
        iPhoneTemplate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iphone.png"))); // NOI18N
        mainPanel.add(iPhoneTemplate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        phonePanel.setBackground(new java.awt.Color(102, 102, 102));
        phonePanel.setMaximumSize(new java.awt.Dimension(260, 550));
        setResizable(false);
        phonePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        topPanel.setBackground(new java.awt.Color(51, 51, 51));

        closeButton.setBackground(new java.awt.Color(0, 0, 0));
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

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addGap(17, 17, 17))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(closeButton)
                .addContainerGap())
        );

        phonePanel.add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, -1));

        titlePanel.setBackground(new java.awt.Color(0, 0, 0));

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
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        phonePanel.add(titlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 176, 150, 50));

        bttPlay.setBackground(new java.awt.Color(0, 0, 0));
        bttPlay.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        bttPlay.setForeground(new java.awt.Color(255, 255, 255));
        bttPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-play-16.png"))); // NOI18N
        bttPlay.setText("Play");
        bttPlay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        bttPlay.setContentAreaFilled(false);
        bttPlay.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bttPlay.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-battle-16.png"))); // NOI18N
        bttPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttPlayActionPerformed(evt);
            }
        });
        phonePanel.add(bttPlay, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 252, 150, 30));

        bttSetting.setBackground(new java.awt.Color(0, 0, 0));
        bttSetting.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        bttSetting.setForeground(new java.awt.Color(255, 255, 255));
        bttSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-setting-16.png"))); // NOI18N
        bttSetting.setText("Settings");
        bttSetting.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        bttSetting.setContentAreaFilled(false);
        bttSetting.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        bttSetting.setPreferredSize(new java.awt.Dimension(53, 22));
        bttSetting.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/img/settingbtt.gif"))); // NOI18N
        bttSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttSettingActionPerformed(evt);
            }
        });
        phonePanel.add(bttSetting, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 304, 150, 30));

        creditLabel.setFont(new java.awt.Font("Book Antiqua", 1, 10)); // NOI18N
        creditLabel.setForeground(new java.awt.Color(255, 255, 255));
        creditLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        creditLabel.setText("© 2023 ADROIT | All Rights Reserved");
        phonePanel.add(creditLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 240, 60));

        tictactoeIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tictactoeIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tictactoeIcon.gif"))); // NOI18N
        phonePanel.add(tictactoeIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 76, 140, 90));

        playPanel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout playPanelLayout = new javax.swing.GroupLayout(playPanel);
        playPanel.setLayout(playPanelLayout);
        playPanelLayout.setHorizontalGroup(
            playPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );
        playPanelLayout.setVerticalGroup(
            playPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        phonePanel.add(playPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 253, 148, 29));

        settingPanel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout settingPanelLayout = new javax.swing.GroupLayout(settingPanel);
        settingPanel.setLayout(settingPanelLayout);
        settingPanelLayout.setHorizontalGroup(
            settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );
        settingPanelLayout.setVerticalGroup(
            settingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        phonePanel.add(settingPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 303, 148, 30));
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

    private void bttPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttPlayActionPerformed
        // TODO add your handling code here:
        try {
            
            // Define the SQL query to retrieve data from the table
            String sqlQuery = "SELECT Gamemode, Board FROM setting";

            // Prepare the SQL statement
            pst = con.prepareStatement(sqlQuery);

            // Execute the query
            rs = pst.executeQuery();

            // Retrieve the first value from the result set
            if (rs.next()) {
                String gamemode = rs.getString("Gamemode");
                String board = rs.getString("Board");
                // Set the selected item of the combo box to match the retrieved value
                if ("Singleplayer".equals(gamemode)){
                    if (null != board)switch (board) {
                        case "3x3" -> {
                            Board33_vsBot mp33 = new Board33_vsBot();
                            this.hide();
                            mp33.setVisible(true);
                        }
                        case "5x5" -> {
                            Board55_vsBot mp55 = new Board55_vsBot();
                            this.hide();
                            mp55.setVisible(true);
                        }
                        case "7x7" -> {
                            Board77_vsBot mp77 = new Board77_vsBot();
                            this.hide();
                            mp77.setVisible(true);
                        }
                        default -> {
                        }
                    }
                }
                else if ("Multiplayer".equals(gamemode)){
                    if (null != board)switch (board) {
                        case "3x3" -> {
                            Board33 sp33 = new Board33();
                            this.hide();
                            sp33.setVisible(true);
                        }
                        case "5x5" -> {
                            Board55 sp55 = new Board55();
                            this.hide();
                            sp55.setVisible(true);
                        }
                        case "7x7" -> {
                            Board77 sp77 = new Board77();
                            this.hide();
                            sp77.setVisible(true);
                        }
                        default -> {
                        }
                    } 
                }
            }

            // Close the result set, statement, and connection
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential SQL errors here
        }
    }//GEN-LAST:event_bttPlayActionPerformed

    private void bttSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttSettingActionPerformed
        // TODO add your handling code here:
        Setting set = new Setting();
        this.hide();
        set.setVisible(true);
    }//GEN-LAST:event_bttSettingActionPerformed

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
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Homepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Homepage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton bttPlay;
    private javax.swing.JButton bttSetting;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel creditLabel;
    private javax.swing.JLabel iPhoneTemplate;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel phonePanel;
    private javax.swing.JPanel playPanel;
    private javax.swing.JPanel settingPanel;
    private javax.swing.JLabel tictactoeIcon;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
