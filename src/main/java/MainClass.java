
import java.io.IOException;
import com.google.gson.Gson;
import ObjectTemplates.*;
import java.util.ArrayList;
import java.util.Locale;

public class MainClass {
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        HTTPHandler httpHandler = new HTTPHandler();
        int page = 0;
        int totalPages = 1;
        ArrayList<Node> nodes = new ArrayList<>();
        do{
            page++;
            String currentPageURL = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=" + page;
            String unparsedResponse = httpHandler.run(currentPageURL);
//            System.out.println(unparsedResponse);

            Gson parser = new Gson();
            OuterWrapper parsedResponse = parser.fromJson(unparsedResponse, OuterWrapper.class);
            totalPages = parsedResponse.pagination.total / parsedResponse.pagination.per_page;
            
            for(Node n : parsedResponse.menus){
                nodes.add(n);
            }
            
        } while(page < totalPages);
        
        for(Node n : nodes){
            System.out.println(n.toString());
        }
        
    }
    
}
