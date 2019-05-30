var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var messageSchema = new Schema({
    userid:String,
    name:String,
    message:String,
    roomid:String,
    created_time:Date
});

var Message = mongoose.model('Message',messageSchema);

module.exports = Message;