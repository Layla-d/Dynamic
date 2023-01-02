package sample;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.util.*;


public class Controller {

    @FXML
    private TextField dictionaryTF;

    @FXML
    private TextField sentenceTF;

    @FXML
    private Button check;

    @FXML
    private TextArea output;

    ArrayList<String> dictionary = new ArrayList<>();

    public void dictAction() {    //parsing the dictionary into the arraylist
        String word = dictionaryTF.getText();
        dictionary.add(word);
        dictionaryTF.clear();
        System.out.println(dictionary.toString());
    }

//    public void checkClicked(ActionEvent actionEvent) {
//        String sentence = sentenceTF.getText();        //getting user input(sentence)
//        int sentenceSize = sentence.length();            //size of the sentence
//        boolean[][] arr = new boolean[sentenceSize][sentenceSize];         //creating the table for true and false
//
//        //If sentence is "hello", this will gro through letters 'h' to 'o' with indices 0 to 4
//
//        for (int i = 0; i < sentenceSize; i++) {
//            char letter = sentence.charAt(i);          //checking one character at a time to see if its in the dictionary
//            boolean existsInDict = dictionary.contains(letter + "");
//            arr[i][i] = existsInDict;
//        }
//        for (int l = 2; l <= sentenceSize; l++) {               //increasing the length (number of characters at a time) of which we go through the sent.
//            System.out.println("length is " + l);
//            for (int i = 0; i <= sentenceSize - l; i++) {      // the number of subsrings we will have depending on the length of the substring each time
//                String subSentence = sentence.substring(i, i + l);     // getting the substring
//                System.out.println(subSentence + "-> " + i + "," + (i + l));
//                boolean currentSubsentenceExistsInDict = dictionary.contains(subSentence);      //checking if the substring exists in the dictionary
////                arr[i][i + l - 1] = existsInDict;      // if it does, add it to the indecies specified by the start and end of that substring
//                if (!currentSubsentenceExistsInDict) {
//                    for (int split = i + 1; split < i + l; split++) {     // checking if the sub-substring exists in the dictionary
//                        String firstString = sentence.substring(i, split);       //first part of substring
//                        String secondString = sentence.substring(split, i + l);    //second part of substring
//                        System.out.println(firstString + " , " + secondString);
//                        boolean firstExists = arr[i][split - 1];
//                        boolean secondExists = arr[split][i + l - 1];
//                        if (firstExists && secondExists) {
//                            currentSubsentenceExistsInDict = true;
//                            break;
//                        }
//                    }
//                }
//                arr[i][i + l - 1] = currentSubsentenceExistsInDict;
//
//            }
//        }
//        printTable(arr);
//        if (arr[0][sentenceSize-1])
//            output.setText("Breakable");
//        else
//            output.setText("Not Breakable");
////        String res = "";
//        for (int i = 0; i < dictionary.size(); i++) {
//            String word = dictionary.get(i);
//            String star = "";
//            for (int j = 0; j < word.length(); j++) {
//                star = star + "*";
//            }
//            String newSent = sentence.replace(word, star);
//            res += newSent + "\n";
//        }
//        output.setText(res);


    public void checkClickedV2(ActionEvent actionEvent) {
        String sentence = sentenceTF.getText();        //getting user input(sentence)
        int sentenceSize = sentence.length();            //size of the sentence
        HashSet<String>[][] table = new HashSet[sentenceSize][sentenceSize];         //creating the table for true and false
        //If sentence is "hello", this will gro through letters 'h' to 'o' with indices 0 to 4

        for (int i = 0; i < sentenceSize; i++) {
            char letter = sentence.charAt(i);          //checking one character at a time to see if its in the dictionary
            if (dictionary.contains(letter + "")) { //if the letter we're holding exists in the dictionary
                table[i][i] = new HashSet<String>();
                table[i][i].add(letter + "");
            }
        }

        for (int len = 2; len <= sentenceSize; len++) {               //increasing the length (number of characters at a time) of which we go through the sent.
            System.out.println("length is " + len);
            for (int i = 0; i <= sentenceSize - len; i++) {      // the number of subsrings we will have depending on the length of the substring each time
                String subSentence = sentence.substring(i, i + len);     // getting the substring
                System.out.println(subSentence + "-> " + i + "," + (i + len));
//                boolean currentSubsentenceExistsInDict = ;
                if (dictionary.contains(subSentence)) {
                    table[i][i + len - 1] = new HashSet<String>();
                    table[i][i + len - 1].add(subSentence);
                }                                                                //checking if the substring exists in the dictionary
//                arr[i][i + l - 1] = existsInDict;      // if it does, add it to the indecies specified by the start and end of that substring

                for (int split = i + 1; split < i + len; split++) {     // checking if the sub-substring exists in the dictionary
                    String firstString = sentence.substring(i, split);       //first part of substring
                    String secondString = sentence.substring(split, i + len);    //second part of substring
                    System.out.println(firstString + " , " + secondString);
                    boolean firstExists = table[i][split - 1] != null;
                    boolean secondExists = table[split][i + len - 1] != null;
                    if (firstExists && secondExists) {
                        if (table[i][i + len - 1] == null)
                            table[i][i + len - 1] = new HashSet<String>();
                        crossProduct(table[i][split - 1], table[split][i + len - 1], table[i][i + len - 1]);
                    }
                }
//                table[i][i + l - 1] = currentSubsentenceExistsInDict;
            }
        }
        printTable(table);

        if (table[0][sentenceSize - 1] != null) {
            String str = "Breakable\n\n";
            int count = 1;
            for (String sol : table[0][sentenceSize - 1]) {
                str += "Sol." + count + ": " + sol + "\n";
                count++;
            }
            output.setText(str);

        } else
            output.setText("Not Breakable");
//        String res = "";
//        for (int i = 0; i < dictionary.size(); i++) {
//            String word = dictionary.get(i);
//            String star = "";
//            for (int j = 0; j < word.length(); j++) {
//                star = star + "*";
//            }
//            String newSent = sentence.replace(word, star);
//            res += newSent + "\n";
//        }
//        output.setText(res);
    }

    public static void crossProduct(HashSet<String> set1, HashSet<String> set2, HashSet<String> target) {
        for (String str1 : set1)
            for (String str2 : set2)
                target.add(str1 + "," + str2);
    }

    public static void addUtilWrong(ArrayList<Integer> list, int num) {
        if (list == null)
            list = new ArrayList<>();
        list.add(num);
    }

    public static void addUtil(ArrayList<Integer>[][] table, int i, int j, int num) {
        if (table[i][j] == null)
            table[i][j] = new ArrayList<Integer>();
        table[i][j].add(num);
    }
    public void printTable(HashSet<String>[][] arr){

        for(int k =0; k<arr.length; k++){
            System.out.print(" " + k);
        }

        for(int i =0; i<arr.length; i++){
            System.out.print(i + " ");

            for(int j =0; j<arr[0].length; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }




}


/*
 * 0-5
 * 0) 0-0, 1-5
 *         2) 0-0, 1-2, 3-5
 *                      ~) 0-0, 1-2, 3-5     => "i,am,ace"
 *                      3) 0-0, 1-2, 3-3, 4-5    => "i,am,a,ce"
 *         3) 0-0, 1-3, 4-5
 *                  2) 0-0, 1-2, 3-3, 4-5     => "i,am,a,ce"
 * 2) 0-2, 3-5
 *     0) 0-0, 1-2, 3-5
 *                   ~) 0-0, 1-2, 3-5     => "i,am,ace"
 *                   3) 0-0, 1-2, 3-3, 4-5    => "i,am,a,ce"
 * 3) 0-3, 4-5
 *    0) 0-0, 1-3, 4-5
 *             2) 0-0, 1-2, 3-3, 4-5     => "i,am,a,ce"

 *
 * */