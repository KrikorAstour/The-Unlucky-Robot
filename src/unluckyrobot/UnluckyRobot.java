/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 * This project is a game about a robot that can move in 5*5 grid cells and
 * lose or gain points based on rewards he finds in every cell
 * try to keep the score above -1000 and do not exceed the 5th grid to not lose
 * points, gain 2000 points and win!
 * when asked to enter direction, please enter (r, l, u or d)
 * @author Krikor Astour
 */
public class UnluckyRobot {

    /**
     * unlucky robot is a robot in 5*5 grid who travels in 4 directions and moves
     * cell by cell trying to collect points
     * all you have to do is to enter the direction you want the robot to go.
     * rules: you can move in one direction at a time, if your score is less than
     * -1000 you lose, but if its more than 2000 you win!
     * if you try to exit the 5*5 grid, you will lose 2000 points
     * good luck!
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int totalScore = 300;
        int reward = 0;
        int itrCount = 0;
        int x = 0;
        int y = 0;
        char direction;
        
        do {
            displayInfo(x, y, itrCount, totalScore);
            direction = inputDirection();
            if (doesExceed(x, y, direction)) {
                System.out.println("Boundry exceed: damage -2000 applied");
                totalScore -= 2000;
            } 
            else {
                if (direction == 'r') {
                    x++;
                    totalScore -= 50;
                } else if (direction == 'l') {
                    x--;
                    totalScore -= 50;
                } else if (direction == 'u') {
                    y++;
                    totalScore -= 10;
                } else if (direction == 'd') {
                    y--;
                    totalScore -= 50;
                }
            }
            reward = reward();
            if(direction == 'u' && reward < 0)
                reward = punishOrmercey(direction, reward);
            System.out.println("");
            totalScore += reward;
            itrCount++;
        } while (!isGameOver(x, y, totalScore, itrCount));
        System.out.println("Please Enter Your Name: ");
        String name = console.nextLine();
        evaluation(totalScore, name);
        
    }

    /**
     * to display the location of the robot
     * @param x is the x coordinate
     * @param y the y coordinate
     * @param itrCount the number of iterations
     * @param totalScore the overall score
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.println("for point(X = " + x + ", Y = " + y + ") at iteration: "
                + itrCount + " The total score is: " + totalScore);
    }

    /**
     * to check if the robot will exceed the grid limit after taking the step
     * @param x the x coordinates of the robot
     * @param y the y coordinates of the robot
     * @param direction the direction the robot will go(u, d, r, l)
     * @return true or false
     */
    public static boolean doesExceed(int x, int y, char direction) {
        switch (direction) {
            case 'r':
                x++;
                break;
            case 'l':
                x--;
                break;
            case 'u':
                y++;
                break;
            case 'd':
                y--;
                break;
        }
        return x > 4 || x < 0 || y > 4 || y < 0;
    }

    /**
     * to roll a dice and find a reward and print message based on the rolled
     * dice
     * @return the reward amount
     */
    public static int reward() {
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1; // +1 to generate between (1-6)
        switch (dice) {
            case 1:
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case 3:
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case 4:
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case 5:
                System.out.println("Dice: 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice: 6, reward: 600");
                return 600;
        }
    }

    /**
     * flips a coin to check if the robot will face punish or mercy
     * @param direction the direction
     * @param reward the reward value
     * @return the reward with mercy or punishment
     */
    public static int punishOrmercey(char direction, int reward) {
        Random rand = new Random();
            int coin = rand.nextInt(1);
            if (coin == 0) {
                System.out.println("Coin: tail | Mercy, the negative reward is "
                        + "removed");
                return 0;
            } else {
                System.out.println("Coin: head | No mercy, the negative rewarde "
                        + "is applied.");
                return reward;
            }            
    }

    /**
     * to convert a string to title case
     * @param str the name of the player
     * @return the name of the player in title case
     */
    public static String toTitleCase(String str) {
        String firstName = str.substring(0, 1).toUpperCase()
                + str.substring(1, str.indexOf(' ')).toLowerCase() + " ";
        String lastName = str.substring(str.indexOf(' ') + 1, str.indexOf(' ')
                + 2).toUpperCase() + str.substring(str.indexOf(' ') + 2).toLowerCase();
        return firstName + lastName;
    }

    /**
     * prints the final message to tell the player if he won or lost
     * @param totalScore the total scored
     * @param name name of the user
     */
    public static void evaluation(int totalScore, String name) {
        if (totalScore >= 2000) {
            System.out.println("Victory, " + toTitleCase(name) + ", your score "
                    + "is: " + totalScore);
        } else {
            System.out.println("Mission failed! " + toTitleCase(name) + ", your"
                    + " score is: " + totalScore);
        }
    }

    /**
     * to ask the user to input direction and keep asking if the user inputs
     * something else than (u, d, l, r)
     * @return the direction
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter a direction: ");
        char direction = console.next().charAt(0);
        while (direction != 'd' && direction != 'l' && direction != 'r' && direction != 'u') {
            System.out.print("Please enter a direction: ");
            direction = console.next().charAt(0);
        }
        return direction;
    }

    /**
     * to check if the game is over
     * @param x x location
     * @param y y location
     * @param totalScore the score
     * @param itrCount the number of iterations
     * @return true or false is the game finished or not
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        return itrCount > 20 || totalScore < -1000 || totalScore > 2000;
    }
}
