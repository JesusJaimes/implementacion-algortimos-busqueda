import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Hashtable;
import java.util.Scanner;

public class textSearchAlgorithms {

    //Implementacion de Fuerza Bruta
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String BRF(String pattern, String text){
        int patternLength = pattern.length();
        int textLength = text.length();
        char[] patternArray = pattern.toCharArray();
        char[] textArray = text.toCharArray();
        int range = textLength-patternLength;
        int matches = 0;
        int i = 0;
        long start = Instant.now().toEpochMilli();
        while(i <= range){
            int patternIndex = 0;
            while (patternIndex < patternLength && textArray[i + patternIndex] == patternArray[patternIndex]){
                patternIndex++;
            }
            if(patternIndex == patternLength){
                matches++;
            }
            i++;
        }
        long finish = Instant.now().toEpochMilli();
        long time = finish - start;
        return "BÚSQUEDA CON ALGORITMO FUERZA BRUTA"+"\n"+"COINCIDENCIAS: "+matches+"\n"+"TIEMPO DE BÚSQUEDA: "+time;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Implementacion de Knuth Morris Pratt
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int[] getLPS(char[] patternArray, int patternLength) {
        int[] lps = new int[patternLength];
        int i = 1;
        int j = 0;
        lps[0] = 0;
        while (i < patternLength) {
            if (patternArray[i] == patternArray[j]) {
                j++;
                lps[i] = j;
                i++;
            }else{
                if (j != 0) {
                    j = lps[j - 1];
                }else{
                    lps[i] = j;
                    i++;
                }
            }
        }
        return lps;
    }

    public String KMP(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();
        char[] patternArray = pattern.toCharArray();
        char[] textArray = text.toCharArray();
        int[] lps = this.getLPS(patternArray, patternLength);
        int matches = 0;
        int patternIndex = 0;
        int textIndex = 0;
        long start = Instant.now().toEpochMilli();
        while (textIndex < textLength) {
            if (patternArray[patternIndex] == textArray[textIndex]) {
                patternIndex++;
                textIndex++;
            }
            if (patternIndex == patternLength) {
                matches++;
                patternIndex = lps[patternIndex - 1];
            }else if (textIndex < textLength && patternArray[patternIndex] != textArray[textIndex]) {
                if (patternIndex != 0){
                    patternIndex = lps[patternIndex - 1];
                }else{
                    textIndex = textIndex + 1;
                }
            }
        }
        long finish = Instant.now().toEpochMilli();
        long time = finish - start;
        return "BÚSQUEDA CON ALGORITMO KNUTH MORRIS PRATT"+"\n"+"COINCIDENCIAS: "+matches+"\n"+"TIEMPO DE BÚSQUEDA: "+time;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Implementacion de Boyer Moore Horspool
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Hashtable<Character, Integer> getTableBMH(char[] patternArray, int patternLength) {
        Hashtable<Character, Integer> table = new Hashtable<>();
        int patternIndex = 0;
        while (patternIndex < (patternLength - 1)) {
            char character = patternArray[patternIndex];
            int value = patternLength - patternIndex - 1;
            table.put(character, value);
            patternIndex++;
        }
        return table;
    }

    public String BMH(String pattern, String text){
        int patternLength = pattern.length();
        int textLength = text.length();
        char[] patternArray = pattern.toCharArray();
        char[] textArray = text.toCharArray();
        Hashtable<Character, Integer> table = this.getTableBMH(patternArray, patternLength);
        int matches = 0;
        int i = 0;
        int j = patternLength - 1;
        int range = textLength - patternLength;
        long start = Instant.now().toEpochMilli();
        while(i <= range){
            int patternIndex = j;
            int textIndex = j + i;
            char character = textArray[textIndex];
            while (patternIndex >= 0 && patternArray[patternIndex] == textArray[textIndex]) {
                patternIndex--;
                textIndex--;
            }
            if (patternIndex < 0){
                matches++;
                i++;
            }else{
                i = i + table.getOrDefault(character, patternLength);
            }
        }
        long finish = Instant.now().toEpochMilli();
        long time = finish - start;
        return "BÚSQUEDA CON ALGORITMO BOYER MOORE HORSPOOL"+"\n"+"COINCIDENCIAS: "+matches+"\n"+"TIEMPO DE BÚSQUEDA: "+time;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Implementacion de Boyer Moore Sunday
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Hashtable<Character, Integer> getTableBMS(char[] patternArray, int patternLength) {
        Hashtable<Character, Integer> table = new Hashtable<>();
        int i = 0;
        while (i < patternLength) {
            char character= patternArray[i];
            int value = patternLength - i;
            table.put(character, value);
            i++;
        }
        return table;
    }

    public String BMS(String pattern, String text){
        int patternLength = pattern.length();
        int textLength = text.length();
        char[] patternArray = pattern.toCharArray();
        char[] textArray = text.toCharArray();
        Hashtable<Character, Integer> table = this.getTableBMS(patternArray, patternLength);
        int matches = 0;
        int i = 0;
        int j = patternLength - 1;
        int range = textLength - patternLength;
        long start = Instant.now().toEpochMilli();
        while(i <= range){
            int patternIndex = j;
            int textIndex = j + i;
            char character = textArray[textIndex + 1];
            while (patternIndex >= 0 && patternArray[patternIndex] == textArray[textIndex]) {
                patternIndex--;
                textIndex--;
            }
            if (patternIndex < 0){
                matches++;
                i++;
            }else{
                i = i + table.getOrDefault(character, patternLength+1);
            }
        }
        long finish = Instant.now().toEpochMilli();
        long time = finish - start;
        return "BÚSQUEDA CON ALGORITMO BOYER MOORE SUNDAY"+"\n"+"COINCIDENCIAS: "+matches+"\n"+"TIEMPO DE BÚSQUEDA: "+time;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        StringBuilder data = new StringBuilder();
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            try {
                File file = fileChooser.getSelectedFile();
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    data.append(reader.nextLine());
                    data.append("\n");
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "NO SE SELCCIONO NINGUN ARCHIVO");
        }
        textSearchAlgorithms search = new textSearchAlgorithms();
        String text = data.toString();
        String pattern = "And Zacchaeus stood, and said unto the Lord: Behold, Lord, the" +
                "\n"+ "half of my goods I give to the poor";
        String result1 = search.BRF(pattern, text);
        String result2 = search.KMP(pattern, text);
        String result3 = search.BMH(pattern, text);
        String result4 = search.BMS(pattern, text);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
    }

}
