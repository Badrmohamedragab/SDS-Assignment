package Products;

public class Catalog {
    //catalog of products
    private Product[] ProductsList;


    public Catalog(Product[] productsList) {
        ProductsList = productsList;
    }

    public Product[] getProductsList() {
        return ProductsList;
    }

    public void setProductsList(Product[] productsList) {
        ProductsList = productsList;
    }


    Product[] viewCatalog() {
        return ProductsList;
    }


    Product[] getProductByName(String Name) {

        return ProductsList;


    }

    Product[] getProductByBrand(String Brand) {

        return ProductsList;
    }


    Product[] filterByCategory(String Category) {

            return ProductsList;

    }

}
