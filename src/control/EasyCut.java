/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.Database;
import javax.swing.JOptionPane;
import view.Principal;

/**
 *
 * @author Luiz Alberto Teodoro de Arruda
 */
public class EasyCut {
    public static void main(String[] args) {
        Database db = new Database();
        
        if (db.create()) {
            Principal principal = new Principal();
            principal.show();
        } else {
            JOptionPane.showMessageDialog(
                    null, "Impossível criar o banco de dados!");
        }
    }
}