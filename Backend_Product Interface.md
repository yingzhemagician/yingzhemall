# Backend Product Interface
* [1. Product List](#1-product-list)
* [2. Search Product](#2-search-product)
* [3. Upload Images](#3-upload-images)
* [4. Product Details](#4-product-details)
* [5. Product Sale Status Setting](#5-product-sale-status-setting)
* [6. Add Or Update Products](#6-add-or-update-products)
* [7. Richtext Images Upload](#7-richtext-images-upload)

#### 1. Product List

http://localhost:8080/manage/product/list.do

**/manage/product/list.do**

> request

```
pageNum(default=1)
pageSize(default=10)
```

> response

success

```json
{
    "status": 0,
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "size": 2,
        "orderBy": null,
        "startRow": 1,
        "endRow": 2,
        "total": 2,
        "pages": 1,
        "list": [
            {
                "id": 1,
                "categoryId": 3,
                "name": "iphone7",
                "subtitle": "YZ Festival Promotion",
                "mainImage": "mainimage.jpg",
                "status":1,
                "price": 7199.22
            },
            {
                "id": 2,
                "categoryId": 2,
                "name": "oppo R8",
                "subtitle": "oppo is being promoted",
                "mainImage": "mainimage.jpg",
                "status":1,
                "price": 2999.11
            }
        ],
        "firstPage": 1,
        "prePage": 0,
        "nextPage": 0,
        "lastPage": 1,
        "isFirstPage": true,
        "isLastPage": true,
        "hasPreviousPage": false,
        "hasNextPage": false,
        "navigatePages": 8,
        "navigatepageNums": [
            1
        ]
    }
}
```

fail
```json
{
    "status": 10,
    "msg": "Need login."
}

```

------

#### 2. Search Product

http://localhost:8080/manage/product/search.do?productName=p

http://localhost:8080/manage/product/search.do?productId=1

**/manage/product/search.do**

> request

```
productName
productId
pageNum(default=1)
pageSize(default=10)
```

> response

success

```json
{
    "status": 0,
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "size": 1,
        "orderBy": null,
        "startRow": 1,
        "endRow": 1,
        "total": 1,
        "pages": 1,
        "list": [
            {
                "id": 1,
                "categoryId": 3,
                "name": "iphone7",
                "subtitle": "YZ Festival Promotion",
                "mainImage": "mainimage.jpg",
                "price": 7199.22
            }
        ],
        "firstPage": 1,
        "prePage": 0,
        "nextPage": 0,
        "lastPage": 1,
        "isFirstPage": true,
        "isLastPage": true,
        "hasPreviousPage": false,
        "hasNextPage": false,
        "navigatePages": 8,
        "navigatepageNums": [
            1
        ]
    }
}
```

fail
```json
{
    "status": 10,
    "msg": "Need login."
}
```

------

#### 3. Upload Images

**/manage/product/upload.do**

> request

```html
<form name="form2" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="upload"/>
</form>
```

> response

success

```json
{
    "status": 0,
    "data": {
        "uri": "e6604558-c0ff-41b9-b6e1-30787a1e3412.jpg",
        "url": "http://img.yingzhemall.com/e6604558-c0ff-41b9-b6e1-30787a1e3412.jpg"
    }
}
```

fail
```
when status!=0
```

------

#### 4. Product Details

http://localhost:8080/manage/product/detail.do?productId=2

**/manage/product/detail.do**

> request

```
productId
```

> response

success

```json
{
    "status": 0,
    "data": {
        "id": 2,
        "categoryId": 2,
        "parentCategoryId":1,
        "name": "oppo R8",
        "subtitle": "oppo is being promoted",
        "imageHost": "http://img.yingzhemall.com/",
        "mainImage": "mainimage.jpg",
        "subImages": "[\"mmall/aa.jpg\",\"mmall/bb.jpg\",\"mmall/cc.jpg\",\"mmall/dd.jpg\",\"mmall/ee.jpg\"]",
        "detail": "richtext",
        "price": 2999.11,
        "stock": 71,
        "status": 1,
        "createTime": "2016-11-20 14:21:53",
        "updateTime": "2016-11-20 14:21:53"
    }
}
```

fail
```json
{
    "status": 1,
    "msg": "No Permission"
}
```

------

#### 5. Product Sale Status Setting

http://localhost:8080/manage/product/set_sale_status.do?productId=1&status=1

**/manage/product/set_sale_status.do**

> request

```
productId
status
```

> response

success

```json
{
    "status": 0,
    "data": "Set product status successfully."
}
```

fail
```json
{
    "status": 1,
    "data": "Set product status failed."
}
```

------

#### 6. Add Or Update Products

Add
http://localhost:8080/manage/product/save.do?categoryId=1&name=Samsungwashingmachine&subtitle=Samsungpromotion&subImages=test.jpg,11.jpg,2.jpg,3.jpg&detail=detailtext&price=1000&stock=100&status=1

更新
http://localhost:8080/manage/product/save.do?categoryId=1&name=Samsungwashingmachine&subtitle=Samsungpromotion&subImages=test.jpg&detail=detailtext&price=1000&stock=100&status=1&id=3


**/manage/product/save.do**

> request

```
categoryId=1&name=Samsungwashingmachine&subtitle=Samsungpromotion&mainImage=sss.jpg&subImages=test.jpg&detail=detailtext&price=1000&stock=100&status=1&id=3
```

> response

success

```json
{
    "status": 0,
    "data": "Updated product successfully."
}
or
{
    "status": 0,
    "data": "Added product successfully."
}

```

fail
```json
{
    "status": 1,
    "data": "Updated product failed."
}
```

------



#### 7. Richtext Images Upload


**/manage/product/richtext_img_upload.do**

> request

```html
<form name="form2" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" value="upload"/>
</form>
```

> response

success

```json
{
    "file_path": "http://img.yingzhemall.com/5fb239f2-0007-40c1-b8e6-0dc11b22779c.jpg",
    "msg": "Upload successfully",
    "success": true
}
```

fail
```
{
    "success": false,
    "msg": "error message",
    "file_path": "[real file path]"
}
```

------

