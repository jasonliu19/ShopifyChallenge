package ObjectTemplates;

public class OutputWrapper {
    public ShopifyMenu[] valid_menus;
    public ShopifyMenu[] invalid_menus;

    public OutputWrapper(ShopifyMenu[] valid_menus, ShopifyMenu[] invalid_menus){
        this.invalid_menus = invalid_menus;
        this.valid_menus = valid_menus;
    }

}
