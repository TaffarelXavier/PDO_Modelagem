/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

public class Funcoes {

    /**
     *
     * @param caminhoRaiz <p style="font-weight:900;">O caminho root</p>
     * @param nomeDoArquivo <p style="font-weight:900;">Nome do arquivo</p>
     * @param conteudo  <p style="font-weight:900;">Conteúdo</p>
     * @return
     */
    public static boolean salvar(String caminhoRaiz, String nomeDoArquivo, String conteudo) {
        try {

            FileWriter arq;

            File diretorio = new File(caminhoRaiz); // ajfilho é uma pasta!

            if (!diretorio.exists()) {

                diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.

            }

            arq = new FileWriter(caminhoRaiz + "\\" + nomeDoArquivo);

            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf(conteudo);

            arq.close();
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção", 2);
        }
        return false;
    }

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    /**
     *
     * @param caminhoRaiz
     * @param nomeDaPasta
     * @return
     */
    public static int criarPasta(String caminhoRaiz, String nomeDaPasta) {

        File diretorio = new File(caminhoRaiz + "\\" + nomeDaPasta); //

        int resultado = 0;

        if (caminhoRaiz.equals("")) {
            JOptionPane.showMessageDialog(null, "Escolha o nome do diretório princial corretamente.", "Atenção!", JOptionPane.INFORMATION_MESSAGE);
        } else if (nomeDaPasta.equals("")) {
            JOptionPane.showMessageDialog(null, "Escolha o nome da pasta corretamente.", "Atenção!", JOptionPane.INFORMATION_MESSAGE);

        } else {
            if (!diretorio.exists()) {

                resultado = diretorio.mkdirs() == true ? 1 : 0; //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.

            } else {
                JOptionPane.showMessageDialog(null, "O diretório '" + nomeDaPasta.toUpperCase() + "' já existe dentro da pasta:" + caminhoRaiz + ".",
                        "Atenção!", JOptionPane.INFORMATION_MESSAGE);
                resultado = -1;
            }
        }

        return resultado;
    }

  /**
   * 
   * @param source
   * @param dest
   * @throws IOException 
   */
    public static void copyFileUsingFileChannels(File source, File dest)throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }
}
