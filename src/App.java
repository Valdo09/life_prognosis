import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to life prognosis\n 1)Admin Login \n 2)Patient login \n 3)Complete your registration (Patient)");
        Scanner scanner=new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());

        switch(choice){
            case 1 :
                adminLogin();
            case 2 :
                // initiatePatientRegistration();
        }

    }
    public static void adminLogin(){
        System.out.println("Enter your email");
        Scanner scanner=new Scanner(System.in);
        String email;
        email=scanner.nextLine();
        System.out.println("Enter your password");
        String password;
        password=scanner.nextLine();

        Admin admin = new Admin("","",email,password);
        admin.login(email, password);
    }
}
