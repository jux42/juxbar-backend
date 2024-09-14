
# Juxbar API

The **Juxbar API** is a backend service built with Java and Spring, designed to manage cocktails, ingredients, and user-created personal cocktails. The API provides functionality for both authenticated users and administrators to manage their favorites, download cocktail and ingredient data, and interact with various drink resources.

## API Routes Overview

Below is a detailed list of all the available API routes, including the HTTP verb, route, expected parameters, and behavior for each.

---

### AppNameController

#### `GET /appname`
- **Description**: Returns the application name from the configuration.
- **Returns**: The application name or `"???"` if the name is not set.

---

### CocktailController

#### `GET /cocktails`
- **Parameters**: 
  - `page` (Integer, optional)
  - `limit` (Integer, optional)
- **Description**: Retrieves a list of all cocktails, with optional pagination support.
- **Returns**: A list of cocktails. If pagination is provided, the result is paginated.

#### `GET /cocktail/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the cocktail.
- **Description**: Retrieves details for a specific cocktail by its ID.
- **Returns**: A cocktail object or `null` if not found.

#### `GET /cocktail/{id}/image`
- **Parameters**: 
  - `id` (Integer, required) - ID of the cocktail.
- **Description**: Retrieves the image associated with a specific cocktail.
- **Returns**: Image data in byte array.

#### `GET /cocktail/{id}/preview`
- **Parameters**: 
  - `id` (Integer, required) - ID of the cocktail.
- **Description**: Retrieves a preview image for a specific cocktail.
- **Returns**: Preview image in byte array.

#### `GET /cocktails/download`
- **Description**: Triggers the download and update of cocktail data from an external API.
- **Returns**: A success message once the download is complete.

#### `GET /cocktails/downloadimages`
- **Description**: Downloads images for all cocktails from an external API.
- **Returns**: A success message once the images are downloaded.

#### `GET /cocktails/downloadpreviews`
- **Description**: Downloads preview images for all cocktails.
- **Returns**: A success message once the previews are downloaded.
