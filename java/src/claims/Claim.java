package claims;

public class Claim
{
    public void failed(String in)
    {
        String o = String.format("[-] %s\n",in);
        System.out.println(o);
    }


    public void passed(String in)
    {
        String o = String.format("[+] %s\n",in);
        System.out.println(o);
    }


    public void message(String in)
    {
        String o = String.format("[!] %s\n",in);
        System.out.println(o);

    }

}
