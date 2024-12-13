public class Human {
    protected String fullName;
    protected String phoneNumber;
    protected String age;


    public void setFullName() {
        System.out.println("enter your new name");
        this.fullName = Main.reader.next();
    }

    public void setPhoneNumber() {
        System.out.println("enter your new name");
        this.phoneNumber = Main.reader.next();
    }
}
