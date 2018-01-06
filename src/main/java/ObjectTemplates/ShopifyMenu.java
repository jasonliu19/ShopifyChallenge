package ObjectTemplates;

public class ShopifyMenu {
    int root_id;
    int[] children;

    public ShopifyMenu(int root_id, int[] children){
        this.root_id = root_id;
        this.children = children;
    }
}
