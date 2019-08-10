package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.ResponseCode;
import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.dao.ProductMapper;
import YingzheGroup.yingzhemall.pojo.Product;
import YingzheGroup.yingzhemall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("Updated product successfully.");
                }
                return ServerResponse.createByErrorMessage("Updated product failed.");
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("Added product successfully.");
                }
                return ServerResponse.createByErrorMessage("Added product failed.");
            }
        }
        return ServerResponse.createByErrorMessage("Add or update product failed because wrong parameters.");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("Updated product status successfully.");
        }
        return ServerResponse.createByErrorMessage("Updated product status failed.");
    }
}
