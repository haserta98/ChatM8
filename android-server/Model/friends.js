var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var friendsSchema = new Schema({
    user_account_id:String,
    friends_account_id:String,
    created_time:Date
});

var Friends = mongoose.model('Friends',friendsSchema);

module.exports = Friends;