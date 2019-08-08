package YingzheGroup.yingzhemall.service;

import YingzheGroup.yingzhemall.common.ServerResponse;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);
}
