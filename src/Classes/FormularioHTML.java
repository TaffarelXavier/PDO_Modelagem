/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import tx.utilitarios.TXDatas;

/**
 *
 * @author Taffarel Xavier <taffarel_deus@hotmail.com>
 */
public class FormularioHTML {

    public static String salvar(JTable jTable1, String prefixo) {

        String formularioId = "formulario_" + TXDatas.getTimeStampAtual();

        String codigo = " <form action=\"\" id='" + formularioId + "' method='POST'>\n"
                + "            \n";

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        int total = model.getRowCount();

        /*----------------CABEÇALHO DA FUNÇÃO-------------------*/
        for (int count = 0; count < total; count++) {

            String col1 = model.getValueAt(count, 1).toString();
            if (count == 0) {
                codigo += "<label>" + col1.toUpperCase() + "</label><br/>\n";
                codigo += "<input name=\"" + prefixo + col1 + "\" autofocus=\"\" /><br/>\n";
            } else {
                codigo += "<label>" + col1.toUpperCase() + "</label><br/>\n";
                codigo += "<input name=\"" + prefixo + col1 + "\" /><br/>\n";
            }

        }
        codigo += "<button>Salvar</button>\n";
        codigo += "</form>\n\n";
        codigo += "      <script>\n"
                + "            $(document).ready(function () {\n"
                + "                $(\"#" + formularioId + "\").ajaxForm({\n"
                + "                    beforeSend: function () {\n"
                + "\n"
                + "                    },\n"
                + "                    uploadProgress: function (event, position, total, percentComplete) {\n"
                + "                        var percentVal = percentComplete + '%';\n"
                + "                        /*bar.width(percentVal)\n"
                + "                         percent.html(percentVal);*/\n"
                + "                    },\n"
                + "                    success: function (data) {\n"
                + "\n"
                + "                        if (data == '1') {\n"
                + "\n"
                + "                            alert('Sucesso!');\n"
                + "                        } else if (data == '0') {\n"
                + "                            alert('Não ouve alteração!');\n"
                + "                        } else {\n"
                + "                            alert('Ops! Algum erro inesperado aconteceu.\\n' + data);\n"
                + "\n"
                + "                        }\n"
                + "                    },\n"
                + "                    complete: function (xhr) {\n"
                + "                        /*status.html(xhr.responseText);*/\n"
                + "                    }\n"
                + "                });\n"
                + "            });\n"
                + "        </script>";
        return codigo;

    }
}
