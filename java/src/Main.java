import dbinterface.*;
import querycmd.*;
import menu.*;
import user.*;


public class Main
{
    public static void main(String[] argc)
    {
        try
        {
        
        String dbname = "biomarketdb";
        String port = "5432";
        String user = "postgres";
        String password = "postgres";


        System.out.println("[!] Setting up Menu and Database...");
        CommandLineMenu menu = new CommandLineMenu();
        BioMeDB db = new BioMeDB(dbname, port, user, password);

        menu.run(db);
        }
        catch(Exception e)
        {
            System.out.println("[-] Failed to run..");
        }
    }

}
