
import java.io.IOException;
import com.google.gson.Gson;
import ObjectTemplates.*;

import java.util.*;

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

    private static boolean isCircularGraph(ArrayList<ArrayList<Integer>> adj, int current, boolean isInitSearch, int target,
                                           ArrayList<Boolean> seen){
        if(!isInitSearch && current == target){
            return true;
        }
        Stack<Integer> unvisited = new Stack<>();
        for (int adjNode : adj.get(current)){
            unvisited.push(adjNode);
//
//            //Add to seen nodes
//            seen.set(adjNode, true);
//            //Add to root node
//            if(!adj.get(target).contains(adjNode)){
//                adj.get(target).add(adjNode);
//            }

        }

        while(!unvisited.empty()){
            if (isCircularGraph(adj, unvisited.pop(), false, target, seen)){
                return true;
            }
        }

        return false;
    }

    private static void getAllChildNodes(ArrayList<ArrayList<Integer>> adj, ArrayList<Integer> visited, int current){
        visited.add(current);
        Stack<Integer> unvisited = new Stack<>();
        for (int adjNode : adj.get(current)) {
            unvisited.push(adjNode);
        }

        while(!unvisited.empty()){
            int nextNode = unvisited.pop();
            if(!visited.contains(nextNode)){
                getAllChildNodes(adj, visited, nextNode);
            }
        }

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
        //Adjust for naming convention of nodes (they start at 1)
        nodes.add(null);
        //Retrieve and parse JSON
        do{
            page++;
            String currentPageURL = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=2&page=" + page;
            String unparsedResponse = httpHandler.run(currentPageURL);
            System.out.println(unparsedResponse);

            Gson parser = new Gson();
            OuterWrapper parsedResponse = parser.fromJson(unparsedResponse, OuterWrapper.class);
            totalNodes = parsedResponse.pagination.total;
            
            nodes.addAll(Arrays.asList(parsedResponse.menus));
            numNodes += parsedResponse.menus.length;
            
        } while(numNodes < totalNodes);

        //Console logging
        for(Node n : nodes){
            if(n != null)
                System.out.println(n.toString());
        }


        ArrayList<ArrayList<Integer>> adj = constructGraph(totalNodes, nodes);

        //Console logging
        for(int i = 1; i < totalNodes + 1; i++){
            ArrayList<Integer> k = adj.get(i);
            System.out.print(i+ "'s children: ");
            for(int j : k){
                System.out.print(j+" ");
            }
            System.out.println();
        }

        //Check if menu is valid
        ArrayList<Boolean> seen = new ArrayList<>(Collections.nCopies(totalNodes+1, false));
        for(int i = 1; i < adj.size(); i++)
        {
            if(seen.get(i)){
                continue;
            }

            ArrayList<Integer> allChildren = new ArrayList<>();
            getAllChildNodes(adj, allChildren, i);

            for(int child : allChildren){
                seen.set(child, true);
            }

            if(isCircularGraph(adj,i,true,i, seen)) {
                System.out.print(i+" Is Circular With Children: ");
            } else{
                System.out.print(i+" Is Not Circular With Children: ");
                allChildren.remove(allChildren.indexOf(i));
            }

            //Output children
            Collections.sort(allChildren);
            for(int child : allChildren){
                System.out.print(child +" ");
            }
            System.out.println();

        }

       
    }
    
}
