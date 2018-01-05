/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectTemplates;

/**
 *
 * @author Jason
 */
public class OuterWrapper {
    public Node[] menus;
    public Pagination pagination;
    
    @Override
    public String toString(){
        String returnValue = "";
        for(Node n : menus)
        {
            returnValue += "ID: " + n.id + " Data: " + n.data + "\n";
        }

//        returnValue += "Total nodes: " + pagination.curren +  "\n";
        returnValue += "Total nodes: " + pagination.total +  "\n";
        
        return returnValue;
    }
}
