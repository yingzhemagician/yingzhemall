package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.ResponseCode;
import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.dao.CategoryMapper;
import YingzheGroup.yingzhemall.dao.ProductMapper;
import YingzheGroup.yingzhemall.pojo.Category;
import YingzheGroup.yingzhemall.pojo.Product;
import YingzheGroup.yingzhemall.service.IProductService;
import YingzheGroup.yingzhemall.util.DateTimeUtil;
import YingzheGroup.yingzhemall.util.PropertiesUtil;
import YingzheGroup.yingzhemall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("Product has been deleted.");
        }
        // VO value object for now
        // In the future pojo->bo(business object)->vo(view object)

        //为什么不直接返回product呢
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);

    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.yingzhemall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());

        if(category == null){
            productDetailVo.setParentCategoryId(0);//The default node is root.
        }
        else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;


    }
}









