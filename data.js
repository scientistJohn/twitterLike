use post
db.createUser(
{
  user: "postService",
  pwd: "123456",
  roles: [
    {role: "readWrite", db: "post"}
  ]
}
)
db.createCollection("posts")
db.posts.createIndex({"userId": 1})

use comment
db.createUser(
{
  user: "commentService",
  pwd: "123456",
  roles: [
    {role: "readWrite", db: "comment"}
  ]
}
)
db.createCollection("comments")
db.comments.createIndex({"postId": 1})

use user
db.createUser(
{
  user: "userService",
  pwd: "123456",
  roles: [
    {role: "readWrite", db: "user"}
  ]
}
)
db.createCollection("users")

use auth
db.createUser(
{
  user: "authService",
  pwd: "123456",
  roles: [
    {role: "readWrite", db: "auth"}
  ]
}
)
db.createCollection("credentials")
db.credentials.createIndex({"login": 1}, {unique: true})
db.credentials.createIndex({"userId": 1}, {unique: true})


use like
db.createUser(
{
user: "likeService",
pwd: "123456",
roles: [
{role: "readWrite", db: "like"}
]
}
)
db.createCollection("likes")
db.likes.createIndex({"objectId": 1, "objectType": 1, "userId": 1})

use feed
db.createUser(
{
user: "feedService",
pwd: "123456",
roles: [
{role: "readWrite", db: "feed"}
]
}
)
db.createCollection("feedRecords")
db.feedRecords.createIndex({"userId": 1})
db.createCollection("subscriptions")
db.subscriptions.createIndex({"id.subscriberId": 1})
