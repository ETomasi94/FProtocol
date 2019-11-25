
import java.io.Serializable;

/*
    @CLASS Key
    @OVERVIEW Class implementing a generic message key
*/
public class Key implements Instrument, Serializable
{
   String keyname;
   String key;
   
   /*
     @METHOD Key
     @OVERVIEW Constructor for initialize a generic message key
     @PAR Kn String representing the keyname (Password) of the message
     @PAR Ky String representing the encryption key of the message
   */
   public Key(String kn,String ky)
   {
       this.keyname = kn;
       this.key = ky;
   }

   /*
     @METHOD name
     @OVERVIEW Method that prints a message's password (DEBUG ONLY)
   */
    @Override
    public void name() 
    {
        System.out.println("NOME CHIAVE : "+this.keyname+"\n");
    }

    /*
     @METHOD name
     @OVERVIEW Method that prints a message's encryption key (DEBUG ONLY)
    */
    @Override
    public void code()
    {
       System.out.println("ID CHIAVE : "+this.key+"\n");
    }
    
}
