/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Funcoes.ConexaoMysql;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class CriadorDeSQL {

    /**
     *
     * @param tipo
     * @return
     */
    private static String tipoSQL(String tipo) {

        String tipoSql = "";

        switch (tipo) {
            case "PARAM_INT":
                tipoSql = "int";
                break;
            case "PARAM_BOOL":
                tipoSql = "bool";
                break;
            case "PARAM_STR":
                tipoSql = "varchar";
                break;
            default:
                break;
        }
        return tipoSql.toUpperCase();
    }

    /**
     *
     * @param nomeDaTabela
     * @param jTable1
     * @param prefixoDaColuna
     * @return
     */
    private static String criarTabela(String nomeDaTabela, JTable jTable1, String prefixoDaColuna) {

        String textoCodigo = "";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        textoCodigo += "CREATE TABLE `" + nomeDaTabela + "`(";

        /*----------------CABEÇALHO DA FUNÇÃO-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            String col2 = model.getValueAt(count, 2).toString();

            String tamanho = model.getValueAt(count, 3).toString();

            if (count == 0 || count < (total - 1)) {
                if (count == 0) {
                    textoCodigo += "`" + prefixoDaColuna + col1 + "` INT NOT NULL AUTO_INCREMENT,";
                } else {
                    textoCodigo += "`" + prefixoDaColuna + col1 + "` " + tipoSQL(col2) + "(" + tamanho + ")" + ",";
                }

            } else {
                textoCodigo += "`" + prefixoDaColuna + col1 + "` " + tipoSQL(col2) + "(" + tamanho + ")" + "";
            }
        }
        String primaryKey = model.getValueAt(0, 1).toString();

        textoCodigo += ", PRIMARY KEY (`" + prefixoDaColuna + primaryKey + "`)) ENGINE = innoDB;";

        return textoCodigo;
    }

    /**
     * <p>
     * Este método executa o SQL criado.</p>
     *
     * @param nomeDaTabela
     * @param jTable1
     * @param prefixoDaColuna
     * @return
     */
    public static boolean executarSQL(String nomeDaTabela, JTable jTable1, String prefixoDaColuna) {
        try {
            PreparedStatement dropTable = ConexaoMysql.abrir().prepareStatement(
                    String.format("DROP TABLE IF EXISTS %s", nomeDaTabela));
            boolean resultado1 = false;
            if (dropTable.execute() != false) {
                resultado1 = true;
            }

            PreparedStatement stmt2 = ConexaoMysql.abrir().prepareStatement(CriadorDeSQL.criarTabela(nomeDaTabela, jTable1, prefixoDaColuna));
            
            int resultado2 = stmt2.executeUpdate();

            if (resultado2 >= 0 && resultado1 == false) {
                JOptionPane.showMessageDialog(null, "SQL Criado e executado com sucesso!", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }
}
