import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import rita.*;


public class Poem {
    public static int NUM_POEMS = 5;
    public static int MAX_CHAR_SENTENCE_LENGTH = 80;
    public static String NIETZSCHE_TEXT = "nietzsche1.txt";
    private static String output = "";

    private String readBirthOfTragedy() {
        String s = "";
        try {
            File f = new File(NIETZSCHE_TEXT);
            Scanner scn = new Scanner(f);
            while (scn.hasNext()) {
                s += scn.next();
                s += " ";
            }
            scn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    public String getNewPoem() {
        String tmp = readBirthOfTragedy();
        RiString nietzsche = new RiString(tmp);
        RiMarkov rm = new RiMarkov(3);
        rm.loadText(nietzsche.text());

        for (int i = 0; i < NUM_POEMS; i++) {
            String[] sentences = rm.generateSentences(20);
            String s = getSentence(sentences);

            String[] words = s.split(" ");
            output += s + "\n";

            while (words.length > 1) {
                words = removeAnItem(words);
                words = changeRandomWordToRhyme(words);
                words = changeNounOrVerb(words, "noun");
                words = changeNounOrVerb(words, "verb");
                printArrayAsStringSentence(words);
            }
            //output += "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n";
            //System.out.println(output);
            //printOutput();
        }
        return output;
    }

    private int getFirstIndexOfNoun(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (RiTa.isNoun(words[i])) {
                return i;
            }
        }
        return 0;
    }

    private int getLastIndexOfNoun(String[] words) {
        for (int i = words.length - 1; i >= 0; i--) {
            if (RiTa.isNoun(words[i])) {
                return i;
            }
        }
        return 0;
    }

    private int getFirstIndexOfVerb(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (RiTa.isVerb(words[i])) {
                return i;
            }
        }
        return 0;
    }

    private int getLastIndexOfVerb(String[] words) {
        for (int i = words.length - 1; i >= 0; i--) {
            if (RiTa.isVerb(words[i])) {
                return i;
            }
        }
        return 0;
    }

    private static void printOutput() {
        try {
            PrintWriter out = new PrintWriter("nietzsche_generative_poems.txt");
            out.println(output);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String[] removeAnItem(String[] arr) {
        List<String> tmp = new ArrayList<>(Arrays.asList(arr));
        for (int i = 0; i < tmp.size(); i++) {
            for (int j = i + 1; j < tmp.size(); j++) {
                if (tmp.get(i).equals(tmp.get(j))) {
                    tmp.remove(tmp.get(j));
                    return tmp.toArray(new String[0]);
                }
            }
        }

        tmp.remove(tmp.size() / 2);
        return tmp.toArray(new String[0]);
    }

    private String getSentence(String[] sentences) {
        for (int i = 0; i < sentences.length; i++) {
            if (sentences[i].length() <= MAX_CHAR_SENTENCE_LENGTH) {
                return sentences[i];
            }
        }
        return sentences[0];
    }

    private void printArrayAsStringSentence(String[] words) {
        String newSentence = "";
        for (String t : words) {
            newSentence += t;
            newSentence += " ";
        }
        //System.out.println(newSentence.toLowerCase());

        output += newSentence.toLowerCase() + "\n";
    }

    private String[] changeNounOrVerb(String[] words, String typeOfChange) {
        int index = 0;
        if (typeOfChange.equals("noun")) {
            if (words.length % 2 == 0) {
                index = getFirstIndexOfNoun(words);
            } else {
                index = getLastIndexOfNoun(words);
            }

        } else if (typeOfChange.equals("verb")) {
            if (words.length % 2 == 0) {
                index = getFirstIndexOfVerb(words);
            } else {
                index = getLastIndexOfVerb(words);
            }

        }
        if (words.length == 0) return words;
        String newWord = RiTa.similarBySoundAndLetter(words[index])[0];
        words[index] = newWord;
        return words;
    }

    private String[] changeRandomWordToRhyme(String[] words) {
        int indexOfChange = new Random().nextInt(words.length);
        words[indexOfChange] = getNewRhymeWord(words[indexOfChange]);
        return words;
    }

    private String getNewRhymeWord(String w) {
        String[] wordsThatRhyme = RiTa.rhymes(w);
        if (wordsThatRhyme.length <= 1) return w;
        return wordsThatRhyme[new Random().nextInt(wordsThatRhyme.length)];
    }

}
