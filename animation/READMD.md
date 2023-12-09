# Animation API

The Animation API provides endpoints for retrieving information about quarters and top animations. It exposes functionality to get all quarters, filter quarters by name, get the top 20 animations, and filter top animations by quarter and rank.

## Overview

This API, implemented with the Spring framework, facilitates the retrieval of animation-related data. The existing endpoints offer various ways to query and filter animation information.

## Endpoints

### 1. Get All Quarters

- **Endpoint:** `/api/all`
- **Method:** `GET`
- **Description:** Retrieve a list of quarters excluding those with an empty title.

### 2. Get Quarters by Name

- **Endpoint:** `/api/quart`
- **Method:** `GET`
- **Query Parameter:** `quart` (String)
- **Description:** Retrieve quarters based on the provided name.

### 3. Get Top 20 Animations

- **Endpoint:** `/api/top20`
- **Method:** `GET`
- **Description:** Retrieve a list of the top 20 animations with corresponding details.

### 4. Get Top Animations by ID Range

- **Endpoint:** `/api/top20/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (int)
- **Description:** Retrieve a list of top animations within a specified ID range.

### 5. Get Top Animation by ID

- **Endpoint:** `/api/rank/{id}`
- **Method:** `GET`
- **Path Variable:** `id` (int)
- **Description:** Retrieve a top animation by its ID.

### 6. Get Top Animations by Quarter

- **Endpoint:** `/api/top20/quart`
- **Method:** `GET`
- **Query Parameter:** `quart` (String)
- **Description:** Retrieve top animations filtered by the specified quarter.

## Contributing

If you want to contribute to the development of this API, please follow the standard GitHub flow:

1. Fork the repository.
2. Create a branch for your feature or bug fix.
3. Make your changes.
4. Submit a pull request.

Feel free to contribute and help improve the Animation API!
