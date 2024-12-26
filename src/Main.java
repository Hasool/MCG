import java.util.Scanner;

public class Main {

    static Scanner reader = new Scanner(System.in);
    static Owner owner = new Owner();
    static Frame frame = new Frame();


    static void signOut() {
        App app = new App();
        app.Choose(owner);
    }


    public static void main(String[] args) {
        signOut(); 
    }



}
