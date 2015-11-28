/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecaro;

import javax.swing.JButton;

/**
 *
 * @author anhnghiammk
 */
public class MyButton extends JButton {

    private int row, col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public MyButton(String text) {
       super(text);
    }

}
