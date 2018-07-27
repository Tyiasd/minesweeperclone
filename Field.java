/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assweeper;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 *
 * @author komp
 */
public class Field extends Button{
    public boolean mine;
    public int num;
    public int idi;
    public int idj;

    public Field(boolean mine, int num, int idi, int idj) {
        this.mine = mine;
        this.num = num;
        this.idi = idi;
        this.idj = idj;
    }

    public boolean isMine() {
        return mine;
    }

    public int getNum() {
        return num;
    }

    public int getIdi() {
        return idi;
    }

    public int getIdj() {
        return idj;
    }
    
    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setIdi(int idi) {
        this.idi = idi;
    }

    public void setIdj(int idj) {
        this.idj = idj;
    }
   
}
