var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
    name:String,
    email:String,
    accountid:String,
    password:String,
    imgUrl:Buffer,
    isActive:Boolean,
    created_time:Date,
})

var User = mongoose.model('User',userSchema);

module.exports = User;