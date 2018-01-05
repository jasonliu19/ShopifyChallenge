
import java.io.IOException;
import com.google.gson.Gson;
import ObjectTemplates.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainClass {
    
    private static ArrayList<ArrayList<Integer>> constructGraph(int totalNodes, ArrayList<Node> nodes){
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();
        
        for(int i = 0; i < totalNodes + 1; i++){
            adj.add(new ArrayList<Integer>());
        }
        
        for(int i = 1; i <= totalNodes; i++){
            Node n = nodes.get(i);
            for(int child : n.child_ids){
                if (!adj.get(i).contains(child)){
                    adj.get(i).add(child);
                }
            }
            
            if(n.parent_id != -500 && !adj.get(n.parent_id).contains(i)){
                adj.get(n.parent_id).add(i);
            }
        }
        
        return adj;
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        HTTPHandler httpHandler = new HTTPHandler();
        int page = 0;
        int numNodes = 0;
        int totalNodes;
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(null);
        do{
            page++;
            String currentPageURL = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=1&page=" + page;
            String unparsedResponse = httpHandler.run(currentPageURL);
//            System.out.println(unparsedResponse);

            Gson parser = new Gson();
            OuterWrapper parsedResponse = parser.fromJson(unparsedResponse, OuterWrapper.class);
            totalNodes = parsedResponse.pagination.total;
            
            nodes.addAll(Arrays.asList(parsedResponse.menus));
            numNodes += parsedResponse.menus.length;
            
        } while(numNodes < totalNodes);
        
        for(Node n : nodes){
            if(n != null)
                System.out.println(n.toString());
        }
        ArrayList<Integer> seen = new ArrayList<>(totalNodes);
        ArrayList<ArrayList<Integer>> adj = constructGraph(totalNodes, nodes);

        
        for(int i = 1; i < totalNodes + 1; i++){
            ArrayList<Integer> k = adj.get(i);
            System.out.print(i+ "'s children: ");
            for(int j : k){
                System.out.print(j+" ");
            }
            System.out.println();
        }
        
       
    }
    
}
