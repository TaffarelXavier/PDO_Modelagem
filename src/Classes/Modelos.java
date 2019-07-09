/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Modelos {

    public static String salvar(JTable jTable1, String prefixo) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        String codigo = "<?php\n"
                + "if ($_SERVER['REQUEST_METHOD'] == 'POST') {\n"
                + "\n"
                + "    include '../autoload.php';\n"
                + "    \n"
                + "    \n";

        /*----------------CABEÇALHO DA FUNÇÃO-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();
            
            String tipo = model.getValueAt(count, 2).toString();

            codigo += "$" + prefixo + col1 + "=val_input::sani_string(\"" + prefixo + col1 + "\");\n";

        }

        codigo += "print_r($_POST);\n";
        
        codigo += "}";

        return codigo;

    }
}
