/*
    @INTERFACE Sendable
    @OVERVIEW Interface describing the methods of a sendable object (like a Message)
*/
public interface Sendable {
    
    public String read();
    
    public Message write(String text);
    
    public Message encode(String keynm);
    
    public void Save();
       
    public int decode(String KeyAttempt);
}
