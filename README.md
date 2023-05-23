# API Filmorate

## Description

Filmorate is a social network that helps users choose movies based on what their friends watch and the ratings they give.

## Features

### Films

- Create a film
- Update a film
- Get a film by ID
- Like a film
- Unlike a film
- Get popular films
- Delete a film

### Users

- Add a user
- Update a user
- Get all users
- Get a user by ID
- Add a user as a friend
- Remove a user from friends
- Get user's friends
- Get common friends with a user
- Delete a user

### Genres

- Get all genres
- Get a genre by ID

### Rating

- Get an age rating by ID
- Get all age ratings

## Installation

To use this API, follow these steps:

1. Clone the repository: `git clone https://github.com/DevSMike/java-filmorate.git`
2. Navigate to the project directory: `cd filmorate-api`
3. Install any required dependencies: `npm install`

## ER - diagram of the Filmorate project database:
![This is an image](https://i.ibb.co/mDNFBN2/image.png)

*An example of solving the problem of finding the most highly rated movie by compiling an SQL query*
```sql
SELECT name
FROM film
WHERE film_id = (  SELECT film_id 
                   FROM film_likes
                   GROUP BY film_id
                   ORDER BY COUNT(user_id) desc
                   LIMIT 1);
```
## Development Stack

The project is developed using the following technologies:

- Java 11
- Spring Boot
- JDBC
- Maven
- Lambok
- JUnit

## Contributing

Contributions to this project are welcome. To contribute, follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b my-branch`
3. Make your changes and commit them: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin my-branch`
5. Submit a pull request.
