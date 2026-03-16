import java.util.*;
// I am use Trie data structure because it is used in Fast searching
class TrieNode {

    TrieNode[] children;
    boolean isEnd;
    int frequency;

    TrieNode() {
        children = new TrieNode[26];
        isEnd = false;
        frequency = 0;}
}

class Dictionary {
    TrieNode root;
    Dictionary() {
        root = new TrieNode();
    }
    // this part of code used to add word .
    public void addWord(String word) {
            if(searchWord(word, false)) {
            System.out.println("already exists");
            return;}
            TrieNode curr = root;

        for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';

            if(curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
        }
        curr.isEnd = true;
        curr.frequency = 1;
        System.out.println("word is added");
    }

    public boolean searchWord(String word, boolean increaseFreq) {
        TrieNode curr = root;
          for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            if(curr.children[index] == null) {
                return false;}
            curr = curr.children[index];
        }
        if(curr.isEnd) {
            if(increaseFreq) {
                curr.frequency++;}
            return true;
        }
        return false;
    }


    // this part of code is for search the words
    public void search(String word) {
        boolean found = searchWord(word, true);
        if(found)
            System.out.println("Word is Found");
        else
            System.out.println("Word not found");
    }


    // this part of code is used to suggest the word
    public void suggest(String prefix, int k) {
        TrieNode curr = root;
        for(int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if(curr.children[index] == null) {
                System.out.println("No suggestions");
                return;
            }
            curr = curr.children[index];
        }
        List<WordFreq> list = new ArrayList<>();
        dfs(curr, prefix, list);

        Collections.sort(list, new Comparator<WordFreq>() {
            public int compare(WordFreq a, WordFreq b) {

                if(b.freq != a.freq)
                    return b.freq - a.freq;

                return a.word.compareTo(b.word);
            }
        });

        int limit = Math.min(k, list.size());

        for(int i = 0; i < limit; i++) {
            System.out.println(list.get(i).word);
        }
    }
    // here i am used dfs
    void dfs(TrieNode node, String word, List<WordFreq> list) {
        if(node.isEnd) {
            list.add(new WordFreq(word, node.frequency));
        }
        for(int i = 0; i < 26; i++) {
            if(node.children[i] != null) {
                char ch = (char)('a' + i);
                dfs(node.children[i], word + ch, list);
            }
        }
    }
}

class WordFreq {
    String word;
    int freq;
    WordFreq(String w, int f) {
        word = w;
        freq = f;}
}




public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dictionary dict = new Dictionary();
        while(true) {
            System.out.println("To Add Word press 1");
            System.out.println("For Search Word press 2");
            System.out.println("For Suggest Words press 3");
            System.out.println("For Exit press 4");

            int choice = sc.nextInt();
            if(choice == 1) {
                System.out.print("Enter word: ");
                String word = sc.next();
                dict.addWord(word);
            } else if(choice == 2) {
                System.out.print("Enter word to search: ");
                String word = sc.next();
                dict.search(word);
            } else if(choice == 3) {
                System.out.print("Enter prefix: ");
                String prefix = sc.next();
                System.out.print("Enter k: ");
                int k = sc.nextInt();
                dict.suggest(prefix, k);
            } else if(choice == 4) {
                System.out.println("Program Ended");
                break;
            }

            else {
                System.out.println("Invalid choice");
            }
        }

    }
}
