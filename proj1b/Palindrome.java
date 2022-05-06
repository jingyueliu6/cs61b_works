public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> dequeChar = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char w = word.charAt(i);
            dequeChar.addLast(w);
        }
        return dequeChar;
    }

    private boolean isPalindromeHelper(Deque<Character> wordDeque) {
        if (wordDeque.isEmpty() || wordDeque.size() == 1) {
            return true;
        }
        char first = wordDeque.removeFirst();
        char last = wordDeque.removeLast();
        if (first == last) {
            return isPalindromeHelper(wordDeque);
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeHelper(wordDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length();
        if (len == 0 || len == 1) {
            return true;
        }
        for (int i = 0; i < len/2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(len - i - 1))) {
                return false;
            }
        }
        return true;
    }
}
