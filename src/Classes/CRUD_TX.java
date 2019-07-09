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
public class CRUD_TX {

    /**
     *
     * @param classeName
     * @param tableName
     * @param nomeDaClassse
     * @param jTable1
     * @param prefixo
     * @return
     */
    public static String conteudo(String classeName, String tableName, String nomeDaClassse, JTable jTable1, String prefixo) {

        return "<?php \n"
                + "\n"
                + "class " + classeName + " {\n"
                + "\n"
                + "    private $conexao = null;\n"
                + "    //Nome da tabela:" + tableName + "\n"
                + "    private $tableName = '" + tableName + "';\n"
                + "\n"
                + "    public function __construct($connection) {\n"
                + "        $this->conexao = $connection;\n"
                + "    }\n"
                + "\n"
                + PDOselect()
                + "\n"
                + PDOUpdate(tableName, nomeDaClassse, jTable1, prefixo)
                + "\n"
                + PDOInsert(tableName, nomeDaClassse, jTable1, prefixo)
                + "\n"
                + getLastId(jTable1, prefixo)
                + "\n"
                + PDO_delete(jTable1, prefixo)
                + "\n"
                + PDOTeste(tableName, nomeDaClassse, jTable1, prefixo)
                + "}\n\n"
                + PDO_ExecutarTeste(nomeDaClassse);
    }

    /**
     *
     * @param tableName
     * @param nomeDaClassse
     * @param jTable1
     * @param prefixoDaColuna
     * @return
     */
    public static String PDOInsert(String tableName, String nomeDaClassse, JTable jTable1, String prefixoDaColuna) {

        String textoCodigo = "";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        textoCodigo += "public function insert(";

        /*----------------CABEÇALHO DA FUNÇÃO-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            if (count > 0 && count < (total - 1)) {
                textoCodigo += "$" + prefixoDaColuna + col1 + ",";
            }

        }
        String ultimaLinha = model.getValueAt(total - 1, 1).toString();

        textoCodigo += "$" + prefixoDaColuna + ultimaLinha;

        textoCodigo += "){\n";

        textoCodigo += "try {\n\n$this->conexao->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n"
                + "\n"
                + "$stmt = $this->conexao->prepare(\"";

        textoCodigo += "INSERT INTO `\". $this->tableName .\"` (";

        /*----------------COLUNAS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();
            if (count == 0) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "`,";
            } else if (count < (total - 1)) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "`,";
            } else if (count == (total - 1)) {

                textoCodigo += "`" + prefixoDaColuna + col1 + "`";

                textoCodigo += ") VALUES (NULL";

                for (int x = 0; x < total; x++) {

                    if (x == 0) {
                        textoCodigo += ",?";
                    } else if (x < (total) - 1) {
                        textoCodigo += ",?";
                    } else if (x == (total) - 1) {
                        textoCodigo += ")\"";
                    }
                }
                textoCodigo += ");";

            }
        }
        textoCodigo += "\n";

        /*----------------TIPOS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            String col2 = model.getValueAt(count, 2).toString();

            if (count > 0) {
                textoCodigo += "$stmt->bindParam(" + count + ", $" + prefixoDaColuna + col1 + ", PDO::" + col2 + ");\n";
            }
        }

        textoCodigo += "\n$stmt->execute();\n"
                + "return (int) $stmt->rowCount();";

        textoCodigo += "} catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n}";

        return textoCodigo;
    }

    public static String PDO_ExecutarTeste(String nomeDaClassse) {

        return "include '../autoload.php';\n"
                + "\n"
                + "$" + nomeDaClassse + " = new " + nomeDaClassse + "($connection);\n"
                + "\n"
                + "var_dump($" + nomeDaClassse + "->PDOTeste());";

    }

    public static String PDOTeste(String tableName, String nomeDaClassse, JTable jTable1, String prefixoDaColuna) {

        String textoCodigo = "";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        textoCodigo += "public function PDOTeste()";

        textoCodigo += "{\n";

        textoCodigo += "try {\n\n$this->conexao->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n"
                + "\n"
                + "$stmt = $this->conexao->prepare(\"";

        textoCodigo += "INSERT INTO `\". $this->tableName .\"` (";

        /*----------------COLUNAS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();
            if (count == 0) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "`,";
            } else if (count < (total - 1)) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "`,";
            } else if (count == (total - 1)) {

                textoCodigo += "`" + prefixoDaColuna + col1 + "`";

                textoCodigo += ") VALUES (NULL";

                for (int x = 0; x < total; x++) {

                    if (x == 0) {
                        textoCodigo += ",?";
                    } else if (x < (total) - 1) {
                        textoCodigo += ",?";
                    } else if (x == (total) - 1) {
                        textoCodigo += ")\"";
                    }
                }
                textoCodigo += ");";

            }
        }
        textoCodigo += "\n";

        /*----------------TIPOS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            String col2 = model.getValueAt(count, 2).toString();

            textoCodigo += "$" + prefixoDaColuna + col1 + " = time();\n";

            if (count > 0) {
                textoCodigo += "$stmt->bindParam(" + count + ", $" + prefixoDaColuna + col1 + ", PDO::" + col2 + ");\n";
            }
        }

        textoCodigo += "\n$stmt->execute();\n"
                + "return (int) $stmt->rowCount();";

        textoCodigo += "} catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n}";

        return textoCodigo;
    }

    /**
     *
     * @param tableName
     * @param nomeDaClassse
     * @param jTable1
     * @return
     */
    public static String PDOUpdate(String tableName, String nomeDaClassse, JTable jTable1, String prefixoDaColuna) {

        String textoCodigo = "";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        textoCodigo += "public function update(";

        String id = model.getValueAt(0, 1).toString();

        /*----------------CABEÇALHO DA FUNÇÃO-------------------*/
        for (int count = 1; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            if (count > 0 && count < total) {
                textoCodigo += "$" + prefixoDaColuna + col1 + ",";
            }
        }

        textoCodigo += "$" + prefixoDaColuna + id + "";

        textoCodigo += "){\n";

        textoCodigo += "try {\n\n$this->conexao->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);\n"
                + "\n"
                + "$stmt = $this->conexao->prepare(\"";

        textoCodigo += "UPDATE `\". $this->tableName .\"` SET ";

        /*----------------COLUNAS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            if (count > 0 && count < total - 1) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "` = ?,";
            } else if (count > 0 && count < total) {
                textoCodigo += "`" + prefixoDaColuna + col1 + "` = ?";
            }
        }

        textoCodigo += " WHERE " + prefixoDaColuna + id + " = ? \");\n";

        /*----------------TIPOS-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();

            String col2 = model.getValueAt(count, 2).toString();

            if (count > 0 && count < total) {
                textoCodigo += "$stmt->bindParam(" + (count) + ", $" + prefixoDaColuna + col1 + ", PDO::" + col2 + ");\n";
            }

        }

        textoCodigo += "$stmt->bindParam(" + total + ", $" + prefixoDaColuna + id + ", PDO::" + model.getValueAt(0, 2).toString() + ");\n";

        textoCodigo += "\n$stmt->execute();\n"
                + "return (int) $stmt->rowCount();";

        textoCodigo += "} catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n}";

        return textoCodigo;
    }

    public static String PDOselect() {
        return "    public function select() {\n"
                + "        try {\n"
                + "\n"
                + "            $sth = $this->conexao->prepare('SELECT * FROM ' . $this->tableName);\n"
                + "\n"
                + "            //$sth->bindParam(1, $param, PDO::PARAM_STR);\n"
                + "\n"
                + "            $sth->execute();\n"
                + "\n"
                + "            return $sth;\n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n"
                + "    }";
    }

    /**
     *
     * @param jTable1
     * @param prefixoDaColuna
     * @return
     */
    private static String PDO_delete(JTable jTable1, String prefixoDaColuna) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        Object id = model.getValueAt(0, 1);
        Object tipo = model.getValueAt(0, 2);

        return "public function delete($" + prefixoDaColuna + id + ") {\n"
                + "        try {\n"
                + "\n"
                + "            $sth = $this->conexao->prepare('DELETE FROM ' . $this->tableName.' WHERE " + prefixoDaColuna + id + " = ?; ');\n"
                + "\n"
                + "            $sth->bindParam(1, $" + prefixoDaColuna + id + ", PDO::" + tipo + ");\n"
                + "\n"
                + "            $sth->execute();\n"
                + "\n"
                + "            return (int) $sth->rowCount();\n"
                + "        } catch (Exception $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n"
                + "    }";

    }

    /**
     *
     * @param jTable1
     * @return
     */
    public static String getLastId(JTable jTable1, String prefixoDaColuna) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Object id = model.getValueAt(0, 1);
        return " public function getLastId() {\n"
                + "        try {\n"
                + "            $sth = $this->conexao->prepare('SELECT " + prefixoDaColuna + id + " FROM `' . $this->tableName . '` ORDER BY " + prefixoDaColuna + id + " DESC LIMIT 1');\n"
                + "\n"
                + "            $sth->execute();\n"
                + "\n"
                + "            $lastId = $sth->fetch();\n"
                + "\n"
                + "            return (int) $lastId[0];\n"
                + "            \n"
                + "        } catch (PDOException $exc) {\n"
                + "            echo $exc->getMessage();\n"
                + "        }\n"
                + "    }";

    }

}
