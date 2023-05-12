package Products;
import Database.Control;
import java.util.List;

public class Catalog {
    private static List<Product> products;

    public static void LoadProducts(){
        products = Control.LoadProducts() ;
    }

    public void print() {
        for (Product p : products) {
            p.getInfo().print() ;
        }
    }
    public Product getProduct(String pName, String pCategory, String pBrand) {
        for (Product product : products) {
            if (pBrand.equals(product.getInfo().brand) &&
                    pCategory.equals(product.getInfo().category) &&
                        pName.equals(product.getInfo().name)){
                return product ;
            }
        }
        return null ;
    }
}
