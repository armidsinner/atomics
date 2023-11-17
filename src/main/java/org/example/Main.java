package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger count3 = new AtomicInteger(0);
    private static AtomicInteger count4 = new AtomicInteger(0);
    private static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new ArrayList<>();

        for (String text : texts) {
            Thread palindromeThread = new Thread(() -> {
                if (isPalindrome(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            });

            Thread sameLetterThread = new Thread(() -> {
                if (isSameLetter(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            });

            Thread increasingThread = new Thread(() -> {
                if (isIncreasing(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            });

            palindromeThread.start();
            threads.add(palindromeThread);

            sameLetterThread.start();
            threads.add(sameLetterThread);

            increasingThread.start();
            threads.add(increasingThread);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean isSameLetter(String text) {
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreasing(String text) {
        int prevIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            int currentIndex = text.charAt(i) - 'a';
            if (currentIndex < prevIndex) {
                return false;
            }
            prevIndex = currentIndex;
        }
        return true;
    }
}