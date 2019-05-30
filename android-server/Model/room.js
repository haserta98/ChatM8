var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var roomSchema = new Schema({
    roomName:String,
    roomCreaterUserName:String,
	roomImage:Buffer,
    created_time:Date,
	roomType: String,
});

var Room = mongoose.model('Room',roomSchema);

module.exports = Room;