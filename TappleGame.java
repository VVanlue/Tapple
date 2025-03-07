import java.util.*;
import java.util.concurrent.*;

public class TappleGame {
    private static final List<String> CATEGORIES = Arrays.asList(
        "Fruits", "Animals", "Countries", "Movies", "Sports", "Colors", "Cities"
    );
    private static Set<Character> availableLetters;
    private static Scanner scanner = new Scanner(System.in);
    private static ScheduledExecutorService timer;
    private static boolean timeUp;

    public static void main(String[] args) {
        Random random = new Random();
        String category = CATEGORIES.get(random.nextInt(CATEGORIES.size()));
        availableLetters = new LinkedHashSet<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            availableLetters.add(c);
        }
        
        System.out.println("Welcome to Tapple!");
        System.out.println("Category: " + category);
        playGame();
    }

    private static void playGame() {
        while (!availableLetters.isEmpty()) {
            System.out.println("Available letters: " + availableLetters);
            timeUp = false;
            startTimer();
            
            System.out.print("Enter a letter: ");
            String input = scanner.nextLine().toUpperCase();
            stopTimer();
            
            if (timeUp) {
                System.out.println("Time's up! Game over.");
                break;
            }
            
            if (input.length() == 1 && availableLetters.contains(input.charAt(0))) {
                availableLetters.remove(input.charAt(0));
            } else {
                System.out.println("Invalid letter or already used! Try again.");
            }
        }
        System.out.println("Game Over! All letters used.");
    }

    private static void startTimer() {
        timer = Executors.newSingleThreadScheduledExecutor();
        Runnable countdown = new Runnable() {
            int timeLeft = 10;
            public void run() {
                if (timeLeft > 0) {
                    System.out.print("\rTime left: " + timeLeft + " seconds   ");
                    timeLeft--;
                } else {
                    timeUp = true;
                    timer.shutdown();
                }
            }
        };
        timer.scheduleAtFixedRate(countdown, 0, 1, TimeUnit.SECONDS);
    }

    private static void stopTimer() {
        if (timer != null && !timer.isShutdown()) {
            timer.shutdown();
        }
    }
}
