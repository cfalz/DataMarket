package menu;
import dbinterface.BaseDatabase;
import java.util.Scanner;
import java.io.Console;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public interface Menu
{
	//returns username login
    public void run(BaseDatabase bdb);
}
