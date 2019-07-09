/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

/**
 * This class reads a resource file from classpath.
 *
 * @author Hussein
 *
 */
public class ClasspathFileReader {

    private static final String CONFIG_FILE = "//src//context-menu//jquery.contextMenu.js";

    public void readFileFromClasspath() {

        String rrr = getClass().getResource("resources\\filling.png").getFile();
        
        System.out.println(rrr);

    }
    /*
    public static void main(String[] args) {
        ClasspathFileReader c = new ClasspathFileReader();
        c.readFileFromClasspath();
    }
    */
}
