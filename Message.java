import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import javax.swing.JFileChooser;

/*
    @CLASS Message
    @OVERVIEW Class implementing a generic message
*/
public class Message implements Sendable, Serializable
{
    String message;
    Key key;
    
    /*
        @METHOD Message
        @OVERVIEW Initializes a message by declaring his key fields to null
        (Key fields will be assigned during the encryption phase).
    */
    public Message()
    {
        key = new Key(null,null);
    }

    /*
        @METHOD read
        @OVERVIEW Method that reads a message's text.
    */
    public String read() 
    {
        return this.message;
    }

    /*
        @METHOD write
        @OVERVIEW Method that writes a string into message's text.
        @PAR text String to write inside the message.
    
        @RETURNS The message modified
    */
    public Message write(String text) 
    {
        this.message = text;
        
        return this;
    }

    /*
        @METHOD encode
        @OVERVIEW Method that encodes a message generating a key by substituting
        each vocal with a specific character inside the key and with the letter
        'f' inside message's text.
    */
    public Message encode(String keynm) 
    {   
        int i = 0;
        String privatekey;   
        
        privatekey = this.message;
        privatekey = privatekey.replaceAll("[^aàèìeioufAEIOUF]","0");
        privatekey = privatekey.replace('a', '1');
        privatekey = privatekey.replace('e', '2');
        privatekey = privatekey.replace('i', '3');
        privatekey = privatekey.replace('o','4');
        privatekey = privatekey.replace('u','5');
        privatekey = privatekey.replace('f','8');
        privatekey = privatekey.replace('à','^');
        privatekey = privatekey.replace('è','!');
        privatekey = privatekey.replace('ì','|');
        privatekey = privatekey.replace('ò','=');
        privatekey = privatekey.replace('A','6');
        privatekey = privatekey.replace('E','7');
        privatekey = privatekey.replace('I','?');
        privatekey = privatekey.replace('O','9');
        privatekey = privatekey.replace('U','-');
        privatekey = privatekey.replace('F','.');
     
        this.key.key = privatekey;
        this.key.keyname = keynm;

        message = message.replace('a', 'f');
        message = message.replace('e', 'f');
        message = message.replace('i', 'f');
        message = message.replace('o', 'f'); 
        message = message.replace('u', 'f');
        message = message.replace('à','f');
        message = message.replace('è','f');
        message = message.replace('ì','f');
        message = message.replace('ò','f');
        message = message.replace('A','F');
        message = message.replace('E','F');
        message = message.replace('I','F');
        message = message.replace('O','F');
        message = message.replace('U','F');
        
        return this;       
    }
    
    /*
        @METHOD GetKeyName
        @OVERVIEW Method that returns the mesagge's password (DEBUG ONLY)
        
        @RETURNS Current message's password
    */
    public String GetKeyName()
    {
        return this.key.keyname;
    }
    
    /*
        @METHOD decode
        @OVERVIEW Method that takes a Key in input and decodes a message 
        substituting each 'f' occurence in the message with a character 
        after comparing message's text and encryption key if the Key
        equals message's key or returns an error otherwise.
        @PAR KeyAttempt String representing a Key to try to gain access to
        message's text.
    
        @RETURNS 1 if the message is decrypted successfully, -1 otherwise.
    */
    @Override
    public int decode(String KeyAttempt) 
    {         
       boolean access = (KeyAttempt.equals(this.key.keyname.trim()));
       
       if(access)
       {
        int i = 0;

        char[] m = this.message.toCharArray();
        char[] k = this.key.key.toCharArray();
        
        for(i=0; i<m.length; i++)
        {
                switch (k[i]) 
                {
                    case '1':
                        m[i] = 'a';
                        break;
                    case '2':
                        m[i] = 'e';
                        break;
                    case '3':
                        m[i] = 'i';
                        break;
                    case '4':
                        m[i] = 'o';
                        break;
                    case '5':
                        m[i] = 'u';
                        break;
                    case '6':
                        m[i] = 'A';
                        break;
                    case '7':
                        m[i] = 'E';
                        break;    
                    case '8':
                        m[i] = 'f';
                        break;
                    case '9':
                        m[i] = 'O';
                        break;
                    case '?':
                        m[i] = 'I';
                        break;
                    case '-':
                        m[i] = 'U';
                        break;
                    case '.':
                        m[i] = 'F';
                        break;
                    case '^':
                        m[i] = 'à';
                        break;
                    case '!':
                        m[i] = 'è';
                        break;
                    case '|':
                        m[i] = 'ì';
                        break;
                    case '=':
                        m[i] = 'ò';
                        break;
                    default:
                        break;
                }
            
            }          
        this.message = new String(m);
        
        return 1;
       }
       else
       {

           return -1;
       }     
    }

    /*
        @METHOD Save
        @OVERVIEW Method that saves the current messages in the current directory
    */
    public void Save() 
    {
        String time = LocalTime.now().toString().trim().replace(":","_").replace(".","__");
        File mt = new File("MESSAGE_WRITTEN_AT_"+time+".FProt");
        String s = mt.getAbsolutePath();
        
        try 
        {
          FileOutputStream F = new FileOutputStream(s);
   
          String content = (""+this.message+"\n"+this.key.key+"\n "+this.key.keyname+"\n");
          byte[] contentbytes = content.getBytes();

          F.write(contentbytes);
          
          F.close();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    
    /*
        @METHOD Load
        @OVERVIEW Method that opens a JFileChooser in order for us to choose
        a message (defined by ":FProt" format) to open and decrypt.
    */
    public void Load() throws IOException
    {     
        String currentdir = System.getProperty("user.dir");
        
        JFileChooser Chooser = new JFileChooser(currentdir);

        int retval = Chooser.showOpenDialog(null);
        
        if(retval == JFileChooser.APPROVE_OPTION)
        {
            File LoadedFile = Chooser.getSelectedFile();
            
            String Type = Chooser.getTypeDescription(LoadedFile);

            try
            {
                FileInputStream In = new FileInputStream(LoadedFile.getAbsolutePath());

                byte[] input = new byte[1024];
                
                In.read(input);
                
                String fprotinput = new String(input);
                
                In.close();
                
                String[] result = fprotinput.split("\n",3);
                
                int i = 0;

                for(String s : result)
                {
                   s = s.trim();
                }
                
                this.write(result[0]);
                this.key.key = (result[1]);
                this.key.keyname = (result[2]);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }   
        }
    }
}
