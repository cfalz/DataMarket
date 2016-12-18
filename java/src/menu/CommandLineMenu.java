package menu;
import user.User;
import dbinterface.*;

import java.util.Scanner;
import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class CommandLineMenu implements Menu
{
    //keyboard input
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private User _user = null;

    public static int readChoice()
    {
        int choice;
        do
        {
            System.out.print("[!] Enter Your Choice: "); 
            try
            {
                choice = Integer.parseInt(in.readLine());
                break;
            }
            catch(Exception e)
            {
                System.out.println("[-] -- Invalid Input. ");
                continue;
            }
        }while(true);//End do
        return choice;
    }//End readChoice()


    public void run(BaseDatabase db)
    {
      
      try
      {
    
        boolean m = true;
        greeting();
       
        while(m)
        {
            System.out.println("*** MAIN MENU ***");
            System.out.println("------------------");
            System.out.println("1. Create User ");
            System.out.println("2. Log In ");
            System.out.println("3. EXIT ");

            switch (readChoice())
            {
                case 1: db.createUser(makeUser()); _user = null; break;
                case 2: db.login(userInfoMenu()); break;
                case 3: m = false; System.out.println("[!] GoodBye! ");_user=null; break;
                default: System.out.println("[-] Invalid Choice. "); break;
            }//End Switch
        
        if(_user != null)
        {
            boolean userMenu = true;
            switch(_user.getType())
            {
                /////////////// BASIC USER MENU //////////////////////
                case "basic":
                while(userMenu)
                {
                    System.out.println("*** MENU ***");
                    System.out.println("------------------");
                    System.out.println("1. Search Users ");
                    System.out.println("2. Search Data ");
                    System.out.println("3. My Profile ");
                    System.out.println("4. Exit ");

                    switch(readChoice())
                    {
                        case 1: System.out.println("[!] Search Users Called, But Currently Not Implemented. "); break; 
                        case 2: System.out.println("[!] Search Data Called, But Currently Not Implemented. "); break; 
                        case 3: System.out.println("[!] My Profile Called, But Currently Not Implemented. "); break; 
                        case 4: userMenu = false; System.out.println("[!] GoodBye! "); _user = null; break;
                        default: System.out.println("[-] Invalid Choice. "); break;
                        
                    }//End readChoice Switch
                    
                }break;//End While


                /////////////// ADMIN USER MENU //////////////////////
                case "admin":
                while(userMenu)
                {
                    System.out.println("*** ADMIN MENU ***");
                    System.out.println("------------------");
                    System.out.println("1. Search Users ");
                    System.out.println("2. Search Data ");
                    System.out.println("3. My Profile ");
                    System.out.println("4. Update User ");
                    System.out.println("5. View Statistics ");
                    System.out.println("6. Exit ");

                    switch(readChoice())
                    {
                        case 1: System.out.println("[!] Search Users Called, But Currently Not Implemented. "); break; 
                        case 2: System.out.println("[!] Search Data Called, But Currently Not Implemented. "); break; 
                        case 3: System.out.println("[!] My Profile Called, But Currently Not Implemented. "); break; 
                        case 4: System.out.println("[!] Update User Called, But Currently Not Implemented. "); break; 
                        case 5: System.out.println("[!] View Statistics Called, But Currently Not Implemented. "); break; 
                        case 6: userMenu = false; System.out.println("[!] GoodBye! "); _user = null;break;
                        default: System.out.println("[-] Invalid Choice. "); break;
                        
                    }//End readChoice Switch

                }break;//End While
                

                ///////////////////// Default ///////////////////////
                default:
                    System.out.println("[-] No user type specified");     

            }//End userType Switch
            
        }//End userLogin if

        }//End While
        
           db.logout();
        }
        catch(Exception e)
        {
            String error = String.format("Main Error: %s", e.getMessage());
            System.out.println(error);
        }

    }//End Main



////////////////////////////////////////////////////////////////////
//      greeting 
////////////////////////////////////////////////////////////////////
public static void greeting()
{
	System.out.println(
	"\n\n" +
       "//////////////////////////////////////////////////\n" +
       "//						//\n" +
       "//	  WELCOME TO THE BioMe DATAMARKET       //\n" +
       "//						//\n" +
       "//      Contributing To The Health Of Others,   //\n" +
       "//	      One Dataset At A Time.		//\n" + 
       "//						//\n" +
       "//////////////////////////////////////////////////\n" +
	"\n\n");
	
}//end greeting

////////////////////////////////////////////////////////////////////
//      Login (usrInfoMenu) 
////////////////////////////////////////////////////////////////////
private User userInfoMenu()
{
        try
        {
        System.out.println("Enter Username: ");
        String uname = in.readLine();

        System.out.println("Enter Password: ");
        String pass = in.readLine();
        _user = new User();
        _user.setLogin(uname);
        _user.setPassword(pass);

        return _user;
        }
        catch(Exception e)
        {
            System.out.println("[-] Userinfo failed.");
            return null;
        }
}


////////////////////////////////////////////////////////////////////
//      makeUser 
////////////////////////////////////////////////////////////////////
private User makeUser()
{
    try
    {
        _user = userInfoMenu();
    
    // TODO: add phonenum etc
    return _user;
    }
    catch(Exception e)
    {
        System.out.println("[-] Userinfo failed.");
        return null;

    }
}










}//End MenuClass
