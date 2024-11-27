import java.util.Scanner;

public class Main {
    public static Scanner reader = new Scanner(System.in);
    public static Owner owner = new Owner();
    public static void signOut(){
        App app = new App();
        app.Choose(owner);
    }

    public static void main(String[] args) {
        signOut();
    }

}