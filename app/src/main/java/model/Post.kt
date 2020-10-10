package model

class Post{
    var userId: Int ?= null
    var id: Int ?= null
    var title :String ?= null
    var body: String ?= null

    constructor(userId: Int, id: Int, title: String, body: String){
        this.userId = userId
        this.id = id
        this.title = title
        this.body = body
    }
}