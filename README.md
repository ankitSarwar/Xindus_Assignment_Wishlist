<h1 align = "center"> Xindus Assessment of wishlists API </h1>

<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.0.5-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.0.6-brightgreen.svg" />
</a>
  </p>

  ## Technologies Used
* Java
* Spring Boot
* Mysq
* Hibernate

## Dependencies
The following dependencies are required to run the project:

* Spring Boot Dev Tools
* Spring Web
* Spring Data JPA
* MySQL Driver
* Lombok
* Validation
* spring security


## Description:

The WishlistController class is a Spring MVC controller responsible for handling HTTP requests related to user wishlists in a web application. 
The controller is mapped to the "/api" endpoint, and it collaborates with various services and repositories to perform CRUD (Create, Read, Update, Delete) 
operations on wishlist items.

>## Key Features:

### User Registration (/api/signUp):
Endpoint: /api/signUp
> http://localhost:8080/api/signUp
```JSON
{
  "username": "ankit",
  "password": "123",
  "email": "ankit@gmail.com",
  "phone_no": "9370215465"
}
```
Method: POST
Description: Registers a new user by accepting a User object in the request body. The UserService is responsible for user registration, and the response indicates the success or failure of the registration process.

### User Login (/api/logIn):
Endpoint: /api/logIn
Method: POST
Description: Handles user login by authenticating user credentials. If the authentication is successful, a JWT (JSON Web Token) is generated using JwtService. If unsuccessful, appropriate error messages are returned.
> http://localhost:8080/api/logIn
```JSON
{
  "username": "ankit",
  "password": "123"
}
```

### Get User Wishlist (/api/wishlists/{username}):
Endpoint: /api/wishlists/{username}
Method: GET
Description: Retrieves the wishlist items for a specific user. The UserService is used to validate the user's access by checking the token. The response contains a list of WishlistItemResponseDTO objects with essential details.
> http://localhost:8080/api/wishlists/abhi?token={token}

### Create Wishlist Item (/api/wishlists):
Endpoint: /api/wishlists
Method: POST
Description: Creates a new wishlist item by accepting a WishlistItem object in the request body. The item is associated with the authenticated user, and the response includes a WishlistItemResponseDTO with the saved item details.
> http://localhost:8080/api/wishlists?token={token}
```JSON
{
  "itemName": "nokia 15",
  "price": 20
}
```

### Delete Wishlist Item (/api/wishlists/{id}):
Endpoint: /api/wishlists/{id}
Method: DELETE
Description: Deletes a wishlist item based on the provided item ID. The user's access is verified using the JWT token. If the user has the correct permissions, 
the item is deleted, and a success message is returned.
> http://localhost:8080/api/wishlists/3?token={token}



## how to run project
> click to download zip file after extract this downloaded file on your system
![image](https://github.com/ankitSarwar/Xindus_Assignment_Wishlist/assets/111841677/f7a43560-a702-4268-abfc-c44dc998c14a)

> open this file on your computer then open Aplication.properties folder and change username and password and add your mysql workbench username and password.
> Run the project to click on this

![Screenshot (1803)](https://github.com/ankitSarwar/Xindus_Assignment_Wishlist/assets/111841677/f9db66a3-cd33-4de1-8e7c-c39add02b442)

> after this go to postman and call All of endpoints given previously.

## how to test project
> click on run WishlistControllerTest class
 ![Screenshot (1809)](https://github.com/ankitSarwar/Xindus_Assignment_Wishlist/assets/111841677/eaf19724-4018-4953-84b4-0610bb083ce7)


![Screenshot (1813)](https://github.com/ankitSarwar/Xindus_Assignment_Wishlist/assets/111841677/17d116e5-b488-4481-9397-25b8dc27628b)

  
