package a2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class PizzaParlour {

    public static void main(String[] args) {

        System.out.println("Welcome to 301 Pizza!: ");
        System.out.println("Would you like to order in, or make a delivery order?: ");


        

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("hi"));
            String line = reader.readLine();
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        // Input
        String genInput;
        Scanner in = new Scanner(System.in);

        System.out.println("Enter ");
        genInput = in.nextLine();

        while(!genInput.equals("exit")){

            System.out.println("Enter a genre");
            genInput = in.nextLine();

        }
        in.close();

    }

}