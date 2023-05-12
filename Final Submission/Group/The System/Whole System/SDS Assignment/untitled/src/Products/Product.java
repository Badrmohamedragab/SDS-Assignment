package Products;

/**
 * This class represents a Product with a ProductInformation object that contains
 * information such as the product's name, description, price, and discount amount.
 * This class allows access to the ProductInformation object through the getInfo()
 * method and allows for the setting of the ProductInformation object through the
 * setInfo() method.
 */
public class Product {
    ProductInformation info;

    /**
     * Returns the ProductInformation object of this Product.
     * @return ProductInformation object of this Product.
     */
    public ProductInformation getInfo() {
        return info;
    }

    /**
     * Sets the ProductInformation object of this Product.
     * @param productInfo the ProductInformation object to be set for this Product.
     */
    public void setInfo(ProductInformation productInfo) {
        this.info = productInfo;
    }
}
