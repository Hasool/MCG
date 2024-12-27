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
        Doctor defaultDoctor = new Doctor();
        defaultDoctor.fullName = "yacin";
        defaultDoctor.Id = "GM2005268";
        defaultDoctor.code = "GM2005268";
        owner.doctors.add(defaultDoctor);
        signOut(); 
    }



}
