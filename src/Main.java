import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner reader = new Scanner(System.in);
    public static Owner owner = new Owner();

    public static void main(String[] args) {

        if (owner.doctors.isEmpty() && owner.patients.isEmpty()){
            owner.Owners("admin","admin",false);
        }else {
            System.out.println("1: owner \n2: doctor\n3: Patient");
            byte c;
            do{
                c = reader.nextByte();
            }while (c<1 || c>3);

            if(c == 1){
                owner.Owners("admin","admin",false);
            } else if (c == 2) {

            }
        }
    }

}