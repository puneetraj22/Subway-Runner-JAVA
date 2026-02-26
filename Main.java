import java.util.Scanner;
import java.util.Random;

/**
 * Project: Subway Runner Console Game
 * Purpose: Demonstrating OOP concepts (Interfaces, Abstraction, Polymorphism)
 */

// 1. THE CONTRACT
interface Buttant {
    void start();
    String getName();
    int getHealth();
    void setHealth(int health);
}

// 2. THE FOUNDATION (Abstraction)
abstract class Character implements Buttant {
    protected String name;
    protected int health;
    
    public Character(String name, int health) {
        this.name = name;
        this.health = health;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
}

// 3. THE PLAYER (Inheritance)
class Human extends Character {
    public Human(String name) {
        super(name, 100);
    }
    
    @Override
    public void start() {
        System.out.println(name + " is sprinting down the tracks!");
    }
}

// 4. THE ENEMY (Inheritance)
class Alien extends Character {
    public Alien(String name) {
        super(name, 100);
    }
    
    @Override
    public void start() {
        System.out.println(name + " is closing in!");
    }
}

// 5. THE GAME ENGINE
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        Buttant player = new Human("Jack");
        Buttant enemy = new Alien("Cosmo");
        
        int distanceToCover = 100;
        int distanceTraveled = 0;

        System.out.println("--- SUBWAY CHASE START ---");
        player.start();
        
        // THE GAME LOOP
        while (player.getHealth() > 0) {
            System.out.println("\n[HP: " + player.getHealth() + "] [Distance: " + distanceTraveled + "m/100m]");
            System.out.print("Action: (D)odge or (S)print? ");
            String choice = scanner.nextLine();
            
            if (choice.equalsIgnoreCase("D") || choice.equalsIgnoreCase("S")) {
                
                if (choice.equalsIgnoreCase("D")) {
                    if (random.nextBoolean()) {
                        System.out.println(">>> Clean Dodge! You took 0 damage.");
                    } else {
                        int dmg = random.nextInt(15) + 5;
                        player.setHealth(player.getHealth() - dmg);
                        System.out.println(">>> Trip! Cosmo swiped you for " + dmg + " HP.");
                    }
                } else { // Sprint Logic
                    int dmg = random.nextInt(10) + 1;
                    player.setHealth(player.getHealth() - dmg);
                    System.out.println(">>> Sprinted ahead! Exhaustion cost you " + dmg + " HP.");
                    enemy.start();
                }

                // Progress update
                distanceTraveled += 20;

                // Win Condition Check
                if (distanceTraveled >= distanceToCover) {
                    System.out.println("\n🏆 VICTORY! You reached the safe zone!");
                    scanner.close();
                    return; // Exit the game early
                }

            } else {
                System.out.println(">>> Invalid input! You hesitated and Cosmo attacked!");
                player.setHealth(player.getHealth() - 20);
            }
        }
        
        // Loss Condition
        System.out.println("\n--- GAME OVER ---");
        System.out.println(player.getName() + " was caught by " + enemy.getName() + ".");
        scanner.close();
    }
}
