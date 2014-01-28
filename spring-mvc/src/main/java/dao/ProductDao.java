package dao;

import entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
@Repository
public class ProductDao extends CrudDao<Product> {
}
