package user;

public class User
{
    private String _login;
    private String _passwd;
    private String _type;
    
///////////////////////////////////////////
//   Setters
///////////////////////////////////////////
    public void setLogin(String login)
    {
       this._login = login.trim(); 
    }
    public void setPassword(String passwd)
    {
       this._passwd = passwd.trim(); 
    }
    public void setType(String type)
    {
        this._type = type.trim();
    }

///////////////////////////////////////////
//   Getters
///////////////////////////////////////////
    
    public String getLogin()
    {
        return this._login;
    }
    public String getPassword()
    {
        return this._passwd;
    }
    public String getType()
    {
        return this._type;
    }
    
}//end User
