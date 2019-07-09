/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tx.utilitarios.TXProperties;

/**
 *
 * @author Taffrel Xavier <taffarel_deus@hotmail.com>
 */
public class Projeto {

    /**
     *
     */
    public static String raizDoProjetoLocal = "";

    /**
     * <p style="font-weight:900;">Cria a pasta do projeto dentro da pasta Local</p>
     * <p style="font-weight:900;"><b>Exemplo:C:\Users\Taffarel\AppData\Local\CRUD_TX</b></p>
     */
    public static void criarPastaDoProjeto() {
        try {
            File file = new File(System.getProperty("java.io.tmpdir"));

            File localDir = new File(file.getParent());

            if (localDir.isDirectory()) {

                if (localDir.getName().equals("local") || localDir.getName().equals("Local")) {
                    File fileCRUD_TX = new File(localDir + "\\CRUD_TX");
                    if (!fileCRUD_TX.exists()) {
                        fileCRUD_TX.mkdir();
                    }
                    raizDoProjetoLocal = fileCRUD_TX.getAbsolutePath();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

   /**
    * <p style="font-weight:900;">Para chamar este método, primeiramente, 
    * devemos criar o projeto pelo método criarPastaDoProjeto() (Uma única vez).</p>
    * @param keyName
    * @param value
    * @return 
    */
    public static boolean criarPropriedade(String keyName, String value) {
        try {
            TXProperties.setFilePath(Projeto.raizDoProjetoLocal+ "\\setting.properties");
            TXProperties.addItem(keyName, value);
        } catch (IOException ex) {
            Logger.getLogger(Projeto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
