import menu.Menu;
//import biomeDB.BioMeDB;

import java.util.Scanner;
import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


public class CommandLineMenu implements Menu
{
    //User Login
    private static String userLogin = null;
    private static String userType = null;
    //private Database = new BioMeDB(
    //keyboard input
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


    public List<String> getCreds()
    {
        System.out.println("getCreds called");
    }

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






    public static void main(String[] argc)
    {
      
       String dbname = "biomarketdb";
       String dbport = "5432";
       String uname = "postgres";
       String paswd = "postgres";

      try
      {
       Database db = new BioMeDB(dbname, dbport, uname, paswd); 
    
        boolean menu = true;
        greeting();
       
        while(menu)
        {
            System.out.println("*** MAIN MENU ***");
            System.out.println("------------------");
            System.out.println("1. Create User ");
            System.out.println("2. Log In ");
            System.out.println("3. EXIT ");

            //Reset User Login 
            userLogin = null;

            switch (readChoice())
            {
                case 1: db.createUser(db); break;
                case 2: db.login(db); break;
                case 3: menu = false; System.out.println("[!] GoodBye! "); break;
                default: System.out.println("[-] Invalid Choice. "); break;
            }//End Switch

        
        if(userLogin != null)
        {
            boolean userMenu = true;
            userType = userType.trim();
            switch(userType)
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
                        case 4: userMenu = false; System.out.println("[!] GoodBye! "); break;
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
                        case 6: userMenu = false; System.out.println("[!] GoodBye! "); break;
                        default: System.out.println("[-] Invalid Choice. "); break;
                        
                    }//End readChoice Switch
                    
                }break;//End While

            }//End userType Switch
            
        }//End userLogin if

        }//End While
        
            db.cleanup();
        }
        catch(Exception e)
        {
            String error = String.format("Main Error: %s", e.getMessage());
            System.out.println(error);
        }

    }//End Main


////////////////////////////////////////////////////////////////////
//       login
////////////////////////////////////////////////////////////////////
public void login(Database db)
{
	try
	{
		System.out.println("[!] Enter username: ");
		String username = in.readLine();

		System.out.println("[!] Enter password: ");
		String pass = in.readLine();


		if(isValidLogin(username, pass))
		{
		    System.out.println("[+] Authentication Successful.");
		    db.userLogin = username;
		    db.userType = findType();
		}
		else
		{
		    System.out.println("[-] Invalid user name or password");
		}
	}
	catch(Exception e)
	{
		System.out.println("[-] User Login Failed.");
	}
}

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
//      findType 
////////////////////////////////////////////////////////////////////
public String findType()
{
	try
	{
		String query = String.format("select Users.type from Users where Users.login = '%s'", userLogin);
		
		List<List<String> > typeList = executeQueryAndReturnResult(query);
		return typeList.get(0).get(0);
	}
	catch(Exception e)
	{
		System.out.println("[-] Finding User Type Failed.");
		return null;
	}
}//end findType









}//End MenuClass
