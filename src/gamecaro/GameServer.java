/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecaro;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anhnghiammk
 */
public class GameServer extends javax.swing.JFrame implements ActionListener {

    private MyButton matrix[][];
    private int row, col;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream objOutputStream;
    private ObjectInputStream objInputStream;
    private boolean isTick = false;

    private void drawBoard(int row, int col) {
        matrix = new MyButton[row][col];
        jPanelBoard.setLayout(new GridLayout(row, col)); // set Layout
        jTextAreaTextChat.setEnabled(false); // disable jTextArea
        this.row = row;
        this.col = col;

        for (int i = 0; i < row; i++) {

            for (int j = 0; j < col; j++) {
                matrix[i][j] = new MyButton(" ");
                matrix[i][j].setFont(new Font("Time New Roman", Font.PLAIN, 20));
                matrix[i][j].setFocusable(false);
                matrix[i][j].setRow(i);
                matrix[i][j].setCol(j);
                matrix[i][j].addActionListener(this);
                matrix[i][j].setBackground(Color.WHITE);
                jPanelBoard.add(matrix[i][j]);
            }
        } // draw matrix

    }

    private void socketServer() {

        try {
            serverSocket = new ServerSocket(2222); // open port

            socket = serverSocket.accept(); // accept client connect

            objOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        receiveData();
    }
    
    // receive data
    private void receiveData() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        String message = objInputStream.readObject().toString();
                        String[] data = message.split("#");
                        
                         // read Object
                        if (data[0].equals("chat")) {
                            jTextAreaTextChat.append("Client: " + data[1] + '\n');
                            System.out.println(data[1]);
                        }
                        if (data[0].equals("tick")) {
                            int x = Integer.parseInt(data[1]);
                            int y = Integer.parseInt(data[2]);

                            matrix[x][y].setText("O");

                            setEnableButton(true);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        });
        thread.start();
    }

    private void sendData(String text) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    objOutputStream.writeObject("chat#" + text); // send message
                } catch (IOException ex) {
                    Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.start();
    }
    
    // check win
    private boolean checkWinnerHorizontal(int currentRow, String value) {
        int count = 0;
        for (int j = 0; j < 10; ++j) {

            if (this.matrix[currentRow][j].getText().equals(value)) {
                ++count;
            } else {
                count = 0;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean checkWinVertical(int currentCol, String value) {
        int count = 0;
        for (int i = 0; i < 10; i++) {

            if (this.matrix[i][currentCol].getText().equals(value)) {
                count++;
            } else {
                count = 0;
            }
            if (count == 5) {
                return true;
            }

        }

        return false;
    }

    private boolean checkWinnerCrossLeft(int currentRow, int currentCol, String value) {
        int count = 0;
        while (currentRow != 0 && currentCol != 0) {
            --currentRow;
            --currentCol;

        }

        while (currentRow < 10 && currentCol < 10) {
            if (this.matrix[currentRow++][currentCol++].getText().equals(value)) {
                ++count;
            } else {
                count = 0;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean chechWinnerCrossRight(int currentRow, int currentCol, String value) {
        int count = 0;
        while (currentCol < 10 - 1 && currentRow != 0) {
            --currentRow;
            ++currentCol;
        }

        while (currentRow < 10 && currentCol != -1) {
            if (this.matrix[currentRow++][currentCol--].getText().equals(value)) {
                ++count;
            } else {
                count = 0;
            }
            if (count == 5) {
                return true;
            }
        }

        return false;
    }
    // end check win
    
    // disable - enable Button
    private void setEnableButton(boolean b) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                matrix[i][j].setEnabled(b);

            }
        }
    }

    /**
     * Creates new form GameServer
     */
    public GameServer() throws IOException {
        initComponents();
        drawBoard(10, 10);
        socketServer();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBoard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaTextChat = new javax.swing.JTextArea();
        jTextFieldTextChat = new javax.swing.JTextField();
        jButtonSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setResizable(false);
        setSize(new java.awt.Dimension(581, 524));

        jPanelBoard.setPreferredSize(new java.awt.Dimension(516, 454));

        javax.swing.GroupLayout jPanelBoardLayout = new javax.swing.GroupLayout(jPanelBoard);
        jPanelBoard.setLayout(jPanelBoardLayout);
        jPanelBoardLayout.setHorizontalGroup(
            jPanelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        jPanelBoardLayout.setVerticalGroup(
            jPanelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        jTextAreaTextChat.setColumns(20);
        jTextAreaTextChat.setRows(5);
        jScrollPane1.setViewportView(jTextAreaTextChat);

        jTextFieldTextChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldTextChatKeyPressed(evt);
            }
        });

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanelBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldTextChat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSend)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTextChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        // TODO add your handling code here:
        String text = jTextFieldTextChat.getText().trim();
        sendData(text);
        jTextFieldTextChat.setText("");
        jTextAreaTextChat.append(text + '\n');
    }//GEN-LAST:event_jButtonSendActionPerformed

    private void jTextFieldTextChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTextChatKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            jButtonSend.doClick();
        }
    }//GEN-LAST:event_jTextFieldTextChatKeyPressed

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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GameServer().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSend;
    private javax.swing.JPanel jPanelBoard;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaTextChat;
    private javax.swing.JTextField jTextFieldTextChat;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {

        MyButton btn = (MyButton) e.getSource();

        int i = btn.getRow();
        int j = btn.getCol();

        if (!btn.getText().trim().equals("")) {
            return;
        }

        btn.setText("X");
        setEnableButton(isTick);
        try {
            objOutputStream.writeObject("tick#" + i + "#" + j); // send Object
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       if (checkWinnerHorizontal(i, matrix[i][j].getText()) || checkWinVertical(j, matrix[i][j].getText()) || chechWinnerCrossRight(i, j, matrix[i][j].getText()) || checkWinnerCrossLeft(i, j, matrix[i][j].getText())) {
            JOptionPane.showMessageDialog(null, "Bên " + this.matrix[i][j].getText() + " thắng!!!", "Chung cuộc", JOptionPane.PLAIN_MESSAGE);
        }
        System.out.println(i + "," + j);
    }

}
