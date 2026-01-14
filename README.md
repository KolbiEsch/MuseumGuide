# API Documentation & Usage

### Visits
1. Log a New Visit
   - URL: /api/visits
   - Method: POST
   - Request Body (CreateVisitRequest):

| Field           | Type          | Required | Description                     |
|-----------------|---------------|----------|---------------------------------|
| userId          | Long          | Yes      | The ID of the visiting user.    |
| exhibitID       | Long          | Yes      | The ID of the exhibit visited.  |
| visitDate       | LocalDateTime | No       | The date and time of the visit. |
| durationMinutes | Integer       | No       | Rating from 1 to 5.             |
| rating          | Integer       | No       | Rating from 1 to 5.             |
| notes           | String        | No       | Personal notes about the visit  |

**cURL Example**
```
curl -X POST http://localhost:8080/api/visits \
     -H "Content-Type: application/json" \
     -d '{
       "userId": 1,
       "exhibitId": 8,
       "visitDate": "2026-01-13T10:00:00",
       "durationMinutes": 15,
       "rating": 5,
       "notes": "Absolutely stunning display!"
     }'
```

### Exhibits
1. Get All Exhibits

    Retrieves a list of all exhibits with optional pagination
   - URL: /api/exhibits
   - Method: GET
   - Query Params: page (default 0), size (default 20)
2. Get Exhibit by ID
   -  URL: /api/exhibits/{id}
   -  Method: GET

### Comments
1. Post a Comment

     Submit a new comment for an exhibit
   - URL: /api/comments
   - Method: POST
   - Body (JSON):
```
{
    "userId": 1,
    "exhibitId": 10,
    "content": "A must-see for history buffs!",
    "isPublic": true
}
```
2. Get Comments by Exhibit

    Retrieve all comments associated with a specific exhibit.
    - URL: /api/comments/exhibit/{id}
    - Method: GET
