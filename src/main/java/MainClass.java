
import java.io.IOException;
import com.google.gson.Gson;
import ObjectTemplates.*;

import java.util.*;

public class MainClass {

    /**
     * Constructs an adjacency list to represent the menus
     * @param totalNodes total number of nodes in the graph
     * @param nodes list of nodes parsed from JSON data
     * @return adjacency list
     */
    private static List<List<Integer>> constructGraph(int totalNodes, List<Node> nodes){
        List<List<Integer>> adj = new ArrayList<>();
        
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
     * Determines whether the graph connected to the starting node, current, contains a circular reference
     * @param adj adjacency list for the graph
     * @param current node to start traversal from
     * @param isInitSearch used for recursive condition, always set to be true when calling this function
     * @param target used for recursive condition, always set to be the same as node when calling this function
     * @return true if the graph has a circular reference, false otherwise
     */
    private static boolean isCircularGraph(List<List<Integer>> adj, int current, boolean isInitSearch, int target){
        if(!isInitSearch && current == target){
            return true;
        }
        Stack<Integer> unvisited = new Stack<>();
        for (int adjNode : adj.get(current)){
            unvisited.push(adjNode);


        }

        while(!unvisited.empty()){
            if (isCircularGraph(adj, unvisited.pop(), false, target)){
                return true;
            }
        }

        return false;
    }

    /**
     * Traverses graph and mutates visited with all reachable nodes using DFS
     * @param adj adjacency list for the graph
     * @param visited list to be mutated with all reachable nodes
     * @param current ID of the node to start the traversal from
     */
    private static void getAllChildNodes(List<List<Integer>> adj, List<Integer> visited, int current){
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
     * Main logic for the challenge
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        HTTPHandler httpHandler = new HTTPHandler();
        int page = 0;
        int numNodes = 0;
        int totalNodes;
        ArrayList<Node> nodes = new ArrayList<>();
        Gson parser = new Gson();
        //Adjust for naming convention of nodes (they start at 1)
        nodes.add(null);
        //Retrieve and parse JSON
        do{
            page++;
            String currentPageURL = "https://backend-challenge-summer-2018.herokuapp.com/challenges.json?id=2&page=" + page;
            String unparsedResponse = httpHandler.run(currentPageURL);
            System.out.println(unparsedResponse);

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


        List<List<Integer>> adj = constructGraph(totalNodes, nodes);

        //Console logging
        for(int i = 1; i < totalNodes + 1; i++){
            List<Integer> k = adj.get(i);
            System.out.print(i+ "'s children: ");
            for(int j : k){
                System.out.print(j+" ");
            }
            System.out.println();
        }

        //Array to avoid searching nodes connected to graphs that have already been checked
        List<Boolean> alreadyChecked = new ArrayList<>(Collections.nCopies(totalNodes+1, false));
        //Check to see if each menu is valid


        List<ShopifyMenu> invalid_menus = new ArrayList<>();
        List<ShopifyMenu> valid_menus = new ArrayList<>();

        for(int i = 1; i < adj.size(); i++)
        {
            if(alreadyChecked.get(i)){
                continue;
            }

            ArrayList<Integer> allChildren = new ArrayList<>();
            getAllChildNodes(adj, allChildren, i);

            for(int child : allChildren){
                alreadyChecked.set(child, true);
            }

            Collections.sort(allChildren);
            if(isCircularGraph(adj,i,true,i)) {
                System.out.print(i+" Is Circular With Children: ");
                Integer[] arrAllChildren = allChildren.toArray(new Integer[allChildren.size()]);
                invalid_menus.add(new ShopifyMenu(i, convertToIntArray(allChildren)));
            } else{
                System.out.print(i+" Is Not Circular With Children: ");
                allChildren.remove(allChildren.indexOf(i));
                valid_menus.add(new ShopifyMenu(i, convertToIntArray(allChildren)));
            }

            //Output children
            for(int child : allChildren){
                System.out.print(child +" ");
            }
            System.out.println();

        }

        OutputWrapper finalAnswer = new OutputWrapper(convertToShopifyMenuArray(valid_menus), convertToShopifyMenuArray(invalid_menus));

        String finalAnswerJSON = parser.toJson(finalAnswer);
        System.out.println(finalAnswerJSON);

    }

    private static int[] convertToIntArray(List<Integer> a){
        int[] returnVal = new int[a.size()];
        for(int i = 0; i < a.size(); i++){
            returnVal[i] = a.get(i);
        }
        return returnVal;
    }

    private static ShopifyMenu[] convertToShopifyMenuArray(List<ShopifyMenu> a){
        ShopifyMenu[] returnVal = new ShopifyMenu[a.size()];
        for(int i = 0; i < a.size(); i++){
            returnVal[i] = a.get(i);
        }
        return returnVal;
    }
}
