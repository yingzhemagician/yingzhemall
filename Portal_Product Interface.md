# Portal Product Interface
* [1. Product Search and Dynamic List Sorting](#1-product-search-and-dynamic-list-sorting)
* [2. Product Detail](#2-product-detail)
* [Template](#template)

#### 1. Product Search and Dynamic List Sorting 

**/product/list.do**

http://localhost:8080/product/list.do?keyword=&categoryId=1&orderBy=price_desc

> request

```
categoryId
keyword
pageNum(default=1)
pageSize(default=10)
orderBy(default=""): eg: price_desc，price_asc
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
    "status": 1,
    "msg": "Wrong Parameters."
}
```

------

#### 2. Product Detail

**/product/detail.do**

http://localhost:8080/product/detail.do?productId=2

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
    "name": "oppo R8",
    "subtitle": "oppo is being promoted",
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
    "msg": "This product has been removed or deleted."
}
```

------

#### Template

**/product/.do**

> request

```
k
```

> response

success

```
k
```

fail
```
k
```

------

