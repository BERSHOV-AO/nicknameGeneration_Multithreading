import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger lengthThree = new AtomicInteger(0);
    public static AtomicInteger lengthFour = new AtomicInteger(0);
    public static AtomicInteger lengthFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (isWordPalindrome(str)) {
                    beautifulWordsCount(str);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (isSameWorld(str)) {
                    beautifulWordsCount(str);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (isAlphabeticalLetters(str)) {
                    beautifulWordsCount(str);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread3.join();
        thread2.join();
        thread1.join();

        System.out.printf("Красивых слов с длиной 3: %d шт", lengthThree.get());
        System.out.printf("\nКрасивых слов с длиной 4: %d шт", lengthFour.get());
        System.out.printf("\nКрасивых слов с длиной 5: %d шт", lengthFive.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isWordPalindrome(String word) {
        String lowerCaseWorld = word.toLowerCase();
        int left = 0;
        int right = lowerCaseWorld.length() - 1;
        while (left < right) {
            if (lowerCaseWorld.charAt(left) != lowerCaseWorld.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameWorld(String word) {
        String lowerCaseWorld = word.toLowerCase();
        char firstChar = lowerCaseWorld.charAt(0);
        for (int i = 0; i < lowerCaseWorld.length(); i++) {
            if (lowerCaseWorld.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphabeticalLetters(String world) {
        String lowerCaseWorld = world.toLowerCase();
        for (int i = 1; i < lowerCaseWorld.length(); i++) {
            char previous = lowerCaseWorld.charAt(i - 1);
            char current = lowerCaseWorld.charAt(i);
            if (current < previous) {
                return false;
            }
        }
        return true;
    }

    public static void beautifulWordsCount(String str) {
        if (str.length() == 3) {
            lengthThree.getAndAdd(1);
        }
        if (str.length() == 4) {
            lengthFour.getAndAdd(1);
        }
        if (str.length() == 5) {
            lengthFive.getAndAdd(1);
        }
    }
}
