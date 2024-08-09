# REST API Testing Guide

This guide provides instructions on how to test the REST API for managing notes. The API supports the following operations: creating, retrieving, updating, and deleting notes.

## Prerequisites

- Java installed
- Gradle installed
- A REST client tool like [Postman](https://www.postman.com/downloads/) or [curl](https://curl.se/download.html)
- If using curl on Windows, PowerShell is recommended

## Testing the API Using Postman

### 1. Get All Notes

- **Method**: GET
- **URL**: `http://localhost:8080/api/notes`
- **Description**: Retrieves a list of all notes.

### 2. Get Note by ID

- **Method**: GET
- **URL**: `http://localhost:8080/api/notes/{id}`
- **Description**: Retrieves a specific note by its ID.
- **Example**: `http://localhost:8080/api/notes/1`

### 3. Create a New Note

- **Method**: POST
- **URL**: `http://localhost:8080/api/notes`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body** (raw JSON):
    ```json
    {
        "title": "New Note",
        "content": "This is the content of the new note."
    }
    ```
- **Description**: Creates a new note with the provided title and content.

### 4. Update an Existing Note

- **Method**: PUT
- **URL**: `http://localhost:8080/api/notes/{id}`
- **Headers**: 
  - `Content-Type`: `application/json`
- **Body** (raw JSON):
    ```json
    {
        "title": "Updated Note",
        "content": "This is the updated content."
    }
    ```
- **Description**: Updates the title and content of an existing note identified by its ID.

### 5. Delete a Note

- **Method**: DELETE
- **URL**: `http://localhost:8080/api/notes/{id}`
- **Description**: Deletes the note identified by its ID.
