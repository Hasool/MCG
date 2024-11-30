import java.util.Scanner;

public class Main {

    public static Scanner reader = new Scanner(System.in);
    public static Owner owner = new Owner();


    public static void signOut() {
        App app = new App();
        app.Choose(owner);
    }



    public static void main(String[] args) {



        System.out.println("First-time setup: You are the owner ." +
                "\n  just enter admin as name and password you can change them in the future");
        signOut(); 
    }
}
