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
public class Node {
    public int id;
    public String data;
    public int[] child_ids;
    
    @Override
    public String toString(){
        return "ID: " + id + " Data: " + data;
    }
}
