var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var friendRequestSchema = new Schema({
    sended_request_accountid:String,
	to_request_accountid:String,
	created_time:Date
});

var FriendRequest = mongoose.model('FriendRequest',friendRequestSchema);

module.exports = FriendRequest;