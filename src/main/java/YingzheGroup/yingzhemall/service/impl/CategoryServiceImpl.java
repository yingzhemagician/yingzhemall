package YingzheGroup.yingzhemall.service.impl;

import YingzheGroup.yingzhemall.common.ServerResponse;
import YingzheGroup.yingzhemall.dao.CategoryMapper;
import YingzheGroup.yingzhemall.pojo.Category;
import YingzheGroup.yingzhemall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.tools.corba.se.idl.InterfaceGen;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service("CategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Wrong parameters for add category.");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true); // This category is available

        int rowCount = categoryMapper.insert(category);

        if(rowCount > 0){
            return ServerResponse.createBySuccess("Added category successfully.");
        }
        return ServerResponse.createByErrorMessage("Added category failed");
    }

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName){
        if(categoryId == null|| StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("Wrong parameters for update category.");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("Updated category name successfully.");
        }
        return ServerResponse.createByErrorMessage("Updated category name failed");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("Cannot find the sub categories.");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * recursively retrieve the id and sub id
     * @param categoryId
     * @return YingzheGroup.yingzhemall.common.ServerResponse
     */
    public ServerResponse selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }
}
