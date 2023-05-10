package Products;

public class Product {
    ProductInformation productInfo;

    public Product(ProductInformation productInfo) {
        this.productInfo = productInfo;
    }

    public ProductInformation getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInformation productInfo) {
        this.productInfo = productInfo;
    }
}
