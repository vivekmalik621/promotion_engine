# promotion_engine
Promotion Engine is an application for checkoutprocess. Cart contains a list of single character SKU ids (A,B,C....) 
over which the promotion engine needs to run.

## Requirements:

The promotion engine will need to calculate the total order value after applying the 2 promotion types

* buy 'n' items of a SKU for a fixed price (3 A's for 130)

* buy SKU1 & SKU2 for a fixed price (C+D =30)

The promotion engine should be modular to allow for more promotion types to be added at a later date (eg.future promotion 
could be x% of a SKU unit price.

## Assumptions:
Promotions are mutually exclusive , only 1 promotion on a SKU will be applicable at a given point in time.

## Technologies used
Spring boot (Java 8), h2 (in memory) -Database

## H2 database : 
http://localhost:8080/h2-console

## DB Schema :

![E_R](https://user-images.githubusercontent.com/74326762/99197348-3f8f5a80-2792-11eb-8c89-e8e6a5f1e7fb.jpg)


## API

1) Order Processing : 

POST Method , http://localhost:8080/order/process , Input/Output format: JSON

Request:

{
   "items":[
      {
         "sku":"A",
         "quantity":3
      },
      {
         "sku":"B",
         "quantity":2
      },
      {
         "sku":"C",
         "quantity":1
      },
      {
         "sku":"D",
         "quantity":1
      },
      {
         "sku":"E",
         "quantity":1
      }
   ]
}

Response:

{
    "orderValue": 305.00
}

