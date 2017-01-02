import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private List<Product> products;

    public Order() {
        products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}