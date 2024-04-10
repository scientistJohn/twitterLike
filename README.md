# TwitterLike Application

Here is completely implemented business logic for user, post, comment, likes and feed operations.

Task described inconsistent API so I've adjusted it.

You can use compose-environment.yml to run kafka and mongo.

Please run data.js in mongo for applying required db updates.

You can find exported collection of http requests for postman in file twitterLike.postman_collection.json

I found out again that spring security doesn't provide authentication service out of box. And I haven't time to play with keycloack. For the same reason I didn't add tests.