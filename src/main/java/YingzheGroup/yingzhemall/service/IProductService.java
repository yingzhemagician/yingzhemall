package YingzheGroup.yingzhemall.service;

import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.pojo.Product;
import YingzheGroup.yingzhemall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);
}
