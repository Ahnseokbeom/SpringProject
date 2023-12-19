

# Song API

The Song API provides endpoints for retrieving information about songs, music types, genres, and real-time music rankings.

## Overview

This API, built with the Spring framework, offers functionality to access various aspects of music data. While it's still under development, the existing endpoints provide valuable insights into different music-related categories.

## ERD(Entity Relationshop Diagram)
<br>
<p algin="center">
  <img src="https://github.com/Ahnseokbeom/SpringProject/blob/main/melonAPI/musicERD.png"
  width="600" height="500" alt="MusicERD">
</p>

## Endpoints

### 1. Get Song Information

- **Endpoint:** `/api/chart`
- **Method:** `GET`
- **Description:** Retrieve a list of song information.

### 2. Get Top Music

- **Endpoint:** `/api/music/top`
- **Method:** `GET`
- **Description:** Retrieve a list of top music.

### 3. Get Music by Genre

- **Endpoint:** `/api/music/genre/{genre_id}`
- **Method:** `GET`
- **Description:** Retrieve music based on a specific genre.
- **Exception Handling:** Throws `InvalidRequestException` for invalid genre IDs.

### 4. Get Music by Type

- **Endpoint:** `/api/music/type/{type_id}`
- **Method:** `GET`
- **Description:** Retrieve music based on a specific music type.
- **Exception Handling:** Throws `InvalidRequestException` for invalid type IDs.

### 5. Get Music by Type and Genre

- **Endpoint:** `/api/music/{type_id}/{genre_id}`
- **Method:** `GET`
- **Description:** Retrieve music based on a specific music type and genre.
- **Exception Handling:** Throws `InvalidRequestException` for invalid type or genre IDs.

### 6. Get Music Types

- **Endpoint:** `/api/type`
- **Method:** `GET`
- **Description:** Retrieve a list of music types.

### 7. Get Genres

- **Endpoint:** `/api/genre`
- **Method:** `GET`
- **Description:** Retrieve a list of genres.

## Utility Functions

The controller includes utility functions for validating genre and music type IDs. These functions ensure that the provided IDs are within valid ranges.

- `genreCheck(int genre_id)`: Validates the genre ID.
- `typeCheck(int type_id)`: Validates the music type ID.
- `topCheck(int id)`: Validates the real-time ranking ID.

## Contributing

If you want to contribute to the development of this API, please follow the standard GitHub flow:

1. Fork the repository.
2. Create a branch for your feature or bug fix.
3. Make your changes.
4. Submit a pull request.

Feel free to contribute and help improve the Song API!


