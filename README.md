### Shopping Cart Application 

# Description
The app allows a user to have multiple carts and reuse them once the cart order has been processed and purchased.

Since each purchase generates a register, it also allows to keep track of them, making easy to keep track of remaining stock of products after each purchase and elaborate informs such as regular purchases in general or for particular users, etc. 


# ERD


![ERD_shoppingcart_app.svg](ERD_shoppingcart_app.svg)


# ENDPOINTS

## POST User Cart

Creates a new cart for an existing user, with at least 1 existing product order. Minimun quantity of product order is 1.

POST [/api/cart/{userId}](http://localhost:8080/api/cart)


### Body JSON 
#### Example

    {
	    "productId": 10,
	    "quantity": 2
    }


#### Requirements

- Path variable *{userId}*: 
    - Existing and active user 
    - String, not null, not blank, not empty
- *productId*: Integer, not null
- *quantity*: Integer, not null, greater than 0

### Returns

**200 OK**

Indicates that a new cart had been created, related to the specified user, and that the specified product order has been added to the new cart. Returns the new cart id.

    {
	    "newCartId": 18
    }


**400 BAD REQUEST**

- **UserRequiredException**:

  Caused by: *userId* is null, empty or blank

      {
          "type": "user_id_required",   
          "message": "User id is required in order to create a new cart or product order"
        }


- **ProductRequiredException**: ﻿

  Caused by: *productId* is null

  	{
  		"type": "product_id_required_error",
  		"message": "Product specification is required in order to create a new cart or product order"
  	}


- **MinimunQuantityProductOrderException**:

  Caused by: quantity is null or less than 1

        {
           "type": "min_quantity_order_error",
            "message": "Minimun quantity of 1 product is required in order to create a new cart"
        }


**409 CONFLICT**

- **StockAvailabilityException**

  Caused by: *quantity* is greater than available product stock

       {
           "type": "product_stock_not_available",
    			"message": "The required quantity: 2 for product id: 1 is over the available stock: 0"
        }


**404 NOT FOUND**

- **UserNotFoundException**:

  Caused by: *userId* does not exist or is inactive

       {
            "type": "user_not_found",
            "message": "There's no user with username: string"
        }


- **ProductNotFoundException**:

  Caused by: *productId* does not exist

       {
           "type": "product_not_found",
           "message": "There´s no product id: 0"
        }

## POST Cart Product orders 

Allows to modify the product´s list contained in an existing and not processed user´s cart:
- Adds the product to the cart, with the specified quantity, if the product is not contained in the cart´s list
  - Minimun quantity of product order is 1
- Updates the quantity of an existing product order to the specified quantity, if the product already exists in the cart´s list
  - If the specified quantity equals to 0, the product is removed from the cart´s list


POST [/api/cart/{userId}/{cartId}](http://localhost:8080/api/cart)


### Body JSON
#### Example

    {
	    "productId": 10,
	    "quantity": 2
    }


#### Requirements

- Path variable *{userId}*:
  - Existing and active user´s id
  - String, not null, not blank, not empty
- Path variable *{cartId}*:
  - Existing and not purchased cart´s id
  - Integer, not null, not blank, not empty
- *productId*: Integer, not null
- *quantity*: Integer, not null, 
  - greater than 0 to add a product or update an existing one´s quantity
  - equal to 0 will remove a product from cart

### Returns

**200 OK**

Indicates that a product order has been successfully added to the product´s cart list

    {
	    The new product order product id: 10, quantity: 1 has been added!
    }

Indicates that an existing product order has been successfully updated

    {
	    The quantity of product id: 10 has been updated to 3!
    }

Indicates that an existing product order has been successfully updated to 0, hence removed from the cart list

    {
	    The product id: 10 order has been deleted
    }


**400 BAD REQUEST**

- **UserRequiredException**:

  Caused by: *userId* is null, empty or blank

      {
          "type": "user_id_required",   
          "message": "User id is required in order to create a new cart or product order"
        }


- **ProductRequiredException**: ﻿

  Caused by: *productId* is null

  	{
  		"type": "product_id_required_error",
  		"message": "Product specification is required in order to create a new cart or product order"
  	}


- **MinimunQuantityProductOrderException**:

  Caused by: quantity is null or less than 1

        {
           "type": "min_quantity_order_error",
            "message": "Minimun quantity of 1 product is required in order to create a new cart"
        }

**404 NOT FOUND**

- **UnavailableCartException**:

  Caused by: *cartId* exists but has already been purchased

       {
            "type": "unavailable_cart",
            "message": "Can not access cart id: 1, user id: userid since it state is purchased"
        }


**409 CONFLICT**

- **StockAvailabilityException**

  Caused by: *quantity* is greater than available product stock

       {
           "type": "product_stock_not_available",
            "message": "The required quantity: 2 for product id: 1 is over the available stock: 0"
       }


**404 NOT FOUND**

- **UserNotFoundException**:

  Caused by: *userId* does not exist or is inactive

       {
            "type": "user_not_found",
            "message": "There's no user with username: string"
        }

- **CartNotFoundException**:

  Caused by: *cartId* of *userId* does not exist

       {
           "type": "cart_not_found",
           "message": "There´s no cart with cart id: 20 and user id: userid"
        }


- **ProductNotFoundException**:

  Caused by: *productId* does not exist

       {
           "type": "product_not_found",
           "message": "There´s no product id: 0"
        }

## GET User Cart

Returns the detail of an existing cart identified by its id and the user id,
including the products and quantity of orders contained in it.

GET [/api/cart/{userId}/{cartId}](http://localhost:8080/api/cart)

#### Requirements

- Path variable *{userId}*:
  - String, not null, not blank, not empty 
  - Active/inactive user
- Path variable *{cartId}*:
  - Integer, not null
  - Existing processed/unprocessed

### Returns

**200 OK**

Indicates that the cart has been successfully found. 
May contain an empty list if it has no products orders

    {
        "id": 2,
        "userId": "userid",
        "processed": false,
        "products": [
            {
                "productId": 4,
                "quantity": 1
            },
            {
                "productId": 10,
                "quantity": 2
            }
        ]
    }

**404 NOT FOUND**

- **CartNotFoundException**:

  Caused by: *cartId* of *userId* does not exist

       {
           "type": "cart_not_found",
           "message": "There´s no cart with cart id: 20 and user id: userid"
        }

## GET Cart Amount

Returns the total amount of an existing cart identified by its id and the user id, meaning by *total amount* the addition of each product order contained in it

GET [/api/cart/{userId}/{cartId}/amount](http://localhost:8080/api/cart)

#### Requirements

- Path variable *{userId}*:
  - String, not null, not blank, not empty
  - Existing active user id
- Path variable *{cartId}*:
  - Integer, not null
  - Existing unprocessed cart id

### Returns

**200 OK**

Indicates that the total amount for the cart has been successfully obtained.
Detail shows "0.00" when the cart has no products orders

    {
        "cartId": 2,
        "userId": "userid",
        "totalAmount": 319.97
    }

**404 NOT FOUND**

- **UserNotFoundException**:

  Caused by: *userId* does not exist or is inactive

       {
            "type": "user_not_found",
            "message": "There's no user with username: string"
        }

- **CartNotFoundException**:

  Caused by: *cartId* of *userId* does not exist

       {
           "type": "cart_not_found",
           "message": "There´s no cart with cart id: 20 and user id: userid"
        }

- **UnavailableCartException**:

  Caused by: *cartId* exists but has already been purchased

       {
            "type": "unavailable_cart",
            "message": "Can not access cart id: 1, user id: userid since it state is purchased"
        }

## POST Purchase

Allows the finalize the purchase of an existing and available cart, from an exisiting and active user, 
setting the cart state as processed, updating the products stock and 
creating a new register of the purchase and each of its product orders. 

POST [/api/purchase/confirm/cart/{userId}/{cartId}](http://localhost:8080/api/purchase)

#### Requirements

- Path variable *{userId}*:
  - Existing and active user
  - String, not null, not blank, not empty
- Path variable *{cartId}*:
  - Integer, not null
  - Existing unprocessed cart id

### Returns

**200 OK**

Indicates that a cart has been processed and a new purchase 
has been created along with each one of its product orders.

    {
	    "purchaseId": 16,
        "cartId": 7,
        "userId": "userid",
        "purchaseDate": "2023-10-16T17:25:32.351Z",
        "purchaseItems": [
            {
                "id": 15,
                "prodId": 11,
                "prodDescription": "The Cheesecake Factory White Chocolate Raspberry Truffle Cheesecake - 10",
                "prodPrice": 99.99,
                "quantity": 3,
                "amount": 299.96
            }
        ],
        "totalAmount": 299.96
    }

**404 NOT FOUND**

- **UserNotFoundException**:

  Caused by: *userId* does not exist or is inactive

       {
            "type": "user_not_found",
            "message": "There's no user with username: string"
        }

- **CartNotFoundException**:

  Caused by: *cartId* of *userId* does not exist

       {
           "type": "cart_not_found",
           "message": "There´s no cart with cart id: 20 and user id: userid"
        }

**401 UNAUTHORIZED**

- **UnavailableCartException**:

  Caused by: *cartId* exists but has already been purchased

       {
            "type": "unavailable_cart",
            "message": "Can not access cart id: 1, user id: userid since it state is purchased"
        }

**400 BAD REQUEST**

- **EmptyCartException**:

  Caused by: the specified cart has no product orders

      {
          "type": "empty_cart",   
          "message": "Cart id: 1, user id: userid is empty""
        }

**409 CONFLICT**

- **UnavailableProductsException**

  Caused by: *quantity* is greater than available product stock or products are not available to purchase anymore

       {
           "type": "available_products",
            "message": "The following product ids are no longer available: [8,2]"
       }

## Postman collection

[Find it here](shoppingcart_api.postman_collection.json)


## Demo

![shoppingcart_api_demo.gif](shoppingcart_api_demo.gif)
