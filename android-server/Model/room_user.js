var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var roomUserSchema = new Schema({
    roomName:String,
    accountid:String,
});

var RoomUser = mongoose.model('RoomUser',roomUserSchema);

module.exports = RoomUser;