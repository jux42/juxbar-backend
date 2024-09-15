
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

---

### FavouritesController

#### `GET /user/favouritecocktails`
- **Description**: Retrieves the authenticated user's favorite cocktails.
- **Returns**: A list of favorite cocktails.

#### `PUT /user/favouritecocktail/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the cocktail.
- **Description**: Adds a cocktail to the authenticated user's favorite list.
- **Returns**: A success message.

#### `PUT /user/rmfavouritecocktail/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the cocktail.
- **Description**: Removes a cocktail from the user's favorite list.
- **Returns**: A success message.

#### `GET /user/favouritesoftdrinks`
- **Description**: Retrieves the authenticated user's favorite soft drinks.
- **Returns**: A list of favorite soft drinks.

#### `PUT /user/favouritesoftdrink/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the soft drink.
- **Description**: Adds a soft drink to the user's favorite list.
- **Returns**: A success message.

#### `PUT /user/rmfavouritesoftdrink/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the soft drink.
- **Description**: Removes a soft drink from the user's favorite list.
- **Returns**: A success message.

---

### IngredientController

#### `GET /ingredients/download`
- **Description**: Downloads and updates the list of ingredients from an external API.
- **Returns**: A success message once the download is complete.

#### `GET /ingredient/name/{strIngredient}`
- **Parameters**: 
  - `strIngredient` (String, required) - name of the ingredient.
- **Description**: Retrieves an ingredient by its name.
- **Returns**: The ingredient object.

#### `GET /ingredient/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the ingredient.
- **Description**: Retrieves an ingredient by its ID.
- **Returns**: The ingredient object.

#### `GET /ingredients`
- **Description**: Retrieves a list of all ingredients asynchronously.
- **Returns**: A list of ingredients.

#### `GET /ingredient/{strIngredient}/image`
- **Parameters**: 
  - `strIngredient` (String, required) - Description of the ingredient.
- **Description**: Retrieves the image for a specific ingredient.
- **Returns**: Image data in byte array.

#### `GET /ingredient/{strIngredient}/smallimage`
- **Parameters**: 
  - `strIngredient` (String, required) - Description of the ingredient.
- **Description**: Retrieves a smaller version of the image for a specific ingredient.
- **Returns**: Preview image in byte array.

#### `GET /ingredients/downloadimages`
- **Description**: Downloads images for all ingredients from the external API.
- **Returns**: A success message.

#### `GET /ingredients/downloadpreviews`
- **Description**: Downloads preview images for all ingredients.
- **Returns**: A success message.

#### `GET /ingredients/strings`
- **Description**: Retrieves a list of ingredient descriptions as strings.
- **Returns**: A list of ingredient strings.

---

### LoginController

#### `POST /login`
- **Parameters**: 
  - `username` (String, required)
  - `password` (String, required)
- **Description**: Authenticates the user and returns a JWT token if successful.
- **Returns**: A JWT token or an error message (401 or 403).

#### `GET /user`
- **Description**: Retrieves the username of the authenticated user.
- **Returns**: The username as a plain string.

#### `GET /admin`
- **Description**: Checks if the admin is authenticated.
- **Returns**: A success message for authenticated admins.

---

### PersonalCocktailController

#### `GET /user/personalcocktails`
- **Description**: Retrieves the list of personal cocktails for the authenticated user.
- **Returns**: A list of personal cocktails.

#### `POST /user/personalcocktail`
- **Description**: Saves a new personal cocktail for the authenticated user.
- **Returns**: A success message.

#### `GET /user/personalcocktail/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the personal cocktail.
- **Description**: Retrieves a personal cocktail by its ID for the authenticated user.
- **Returns**: The personal cocktail object.

#### `DELETE /user/personalcocktail/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the personal cocktail.
- **Description**: Deletes a personal cocktail by its ID.
- **Returns**: A success message.

#### `PUT /user/personalcocktail/trash/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the personal cocktail.
- **Description**: Moves a personal cocktail to the trash instead of deleting it permanently.
- **Returns**: A success message.

---

### SoftDrinkController

#### `GET /softdrinks`
- **Description**: Retrieves a list of all soft drinks.
- **Returns**: A list of soft drinks.

#### `GET /softdrink/{id}`
- **Parameters**: 
  - `id` (Integer, required) - ID of the soft drink.
- **Description**: Retrieves details for a specific soft drink by its ID.
- **Returns**: The soft drink object.

#### `GET /softdrink/{id}/image`
- **Parameters**: 
  - `id` (Integer, required) - ID of the soft drink.
- **Description**: Retrieves the image for a specific soft drink.
- **Returns**: Image data in byte array.

#### `GET /softdrink/{id}/preview`
- **Parameters**: 
  - `id` (Integer, required) - ID of the soft drink.
- **Description**: Retrieves a preview image for a specific soft drink.
- **Returns**: Preview image in byte array.

#### `GET /softdrinks/download`
- **Description**: Triggers the download and update of soft drink data from an external API.
- **Returns**: A success message once the download is complete.

#### `GET /softDrinks/downloadimages`
- **Description**: Downloads images for all soft drinks.
- **Returns**: A success message once the images are downloaded.

#### `GET /softdrinks/downloadpreviews`
- **Description**: Downloads preview images for all soft drinks.
- **Returns**: A success message once the previews are downloaded.

---

### UserManagementController

#### `GET /admin/users`
- **Description**: Retrieves a list of all users, with passwords hidden.
- **Returns**: A list of users.

#### `POST /admin/user`
- **Parameters**: 
  - `username` (String, required)
  - `password` (String, required)
- **Description**: Creates a new user with the given username and password.
- **Returns**: A success message or an error if the user already exists.

#### `POST /admin/userpassword/{username}`
- **Parameters**:
  - `username` (String, required) - The username of the user.
  - `newPassword` (String, required) - The new password for the user.
- **Description**: Changes the password for the specified user.
- **Returns**: A success message upon successful password change.

#### `GET /admin/reactivate/{username}`
- **Parameters**:
  - `username` (String, required) - The username of the user.
- **Description**: Reactivates a deactivated user.
- **Returns**: A success message upon successful reactivation.

---

### AdminOperationsController

#### `GET /admin/trashlist/{username}`
- **Parameters**:
  - `username` (String, required) - The username of the user.
- **Description**: Retrieves a list of trashed cocktails' IDs for the specified user.
- **Returns**: A list of cocktail IDs that have been moved to the trash for the given user.

#### `POST /admin/untrash/{username}/{id}`
- **Parameters**:
  - `username` (String, required) - The username of the user.
  - `id` (Integer, required) - The ID of the trashed cocktail to restore.
- **Description**: Restores a specific trashed cocktail for the given user by ID.
- **Returns**: A success message upon restoration.

#### `POST /admin/untrashall/{username}`
- **Parameters**:
  - `username` (String, required) - The username of the user.
- **Description**: Restores all trashed cocktails for the specified user.
- **Returns**: A success message upon restoring all cocktails.

---