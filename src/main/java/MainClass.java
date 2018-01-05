
import java.io.IOException;
import com.google.gson.Gson;
import ObjectTemplates.*;

public class MainClass {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        HTTPHandler httpHandler = new HTTPHandler();
        String unparsedResponse = httpHandler.run("https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=1");
        System.out.println(unparsedResponse);
        Gson parser = new Gson();
        OuterWrapper parsedResponse = parser.fromJson(unparsedResponse, OuterWrapper.class);
        
        
        
        System.out.println(parsedResponse.toString());
                

    }
}
