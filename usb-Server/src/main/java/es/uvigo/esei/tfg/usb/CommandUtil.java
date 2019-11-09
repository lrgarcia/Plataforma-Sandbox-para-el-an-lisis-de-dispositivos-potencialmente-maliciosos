/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.tfg.usb;

/**
 *
 * @author Luis
 */
import java.util.ArrayList;
 
import java.util.Arrays;
 
import java.util.List;
 

/*la cual la utilizamos únicamente para procesar los datos tecleados por el usuario y dividir las instrucciones en tokens, los tokes pueden ser el nombre del comando y los parámetros. La regla que se sigue para dividir los comandos es la siguiente.

Cada palabra separada por un espacio es considera como un token a excepción que se encuentre en medio de comillas dobles, ejemplo “Hola Mundo”, en este caso toda la oración es considera un único token.*/
/*Clase que se utiliza para el procesado de datos dividiendo las intrucciones en tokens que puede pueden 
ser el nombre del comando y los parámetros. Cada palabra esta separada por un espacio en caso de estar entre comillas dobles
cuyo caso sera interpretado toda la oracion entre comillas dobles como un token*/

public class CommandUtil {
 
    public static String[] extractTokens(String args) {
        List<String> tokens = new ArrayList<String>();
        char[] charArray = args.toCharArray();
        String sentence = "";
        boolean inQuotes = false;
        for (char c : charArray) {
            //En caso de que encuentre un espacio y no esté entre comillas dobles
            if (c == ' ' && !inQuotes) {
                if (sentence.length() != 0) {
                    /*Añade la palabra al encontra un espacio a la lista de tokens e inicializa*/
                    tokens.add(sentence);
                    sentence = "";
                }
             //Si encuentra comillas dobles   
            } else if (c == '"') {
                //En caso de encontrar otra vez comillas dobles acaba la oración entre comillas y se guarda en la lista de tokens e inicializa
                if (inQuotes) {
                    tokens.add(sentence);
                    sentence = "";
                    inQuotes = false;
                } else {
                    /*Primeras comillas dobles encontradas*/
                    inQuotes = true;
                }
            } else {
                /*Se añade el caracter a la palabra*/
                sentence += c;
            }
        }
        if (sentence.trim().length() != 0) {
            tokens.add(sentence.trim());
        }
        String[] argsArray = new String[tokens.size()];
        argsArray = tokens.toArray(argsArray);
        return argsArray;
 
    }
}
