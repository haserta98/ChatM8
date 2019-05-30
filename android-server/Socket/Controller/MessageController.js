const Message = require('../../Model/message');
const mongodb = require('../mongodbconnection');

function TryInsertMessage(data,callback){
    var _message = new Message();
    _message.userid = data.userid;
    _message.name = data.name;
    _message.message = data.message;
    _message.roomid = data.room;
    _message.created_time = Date.now();
    _message.save((error) =>{
        return callback(error,1);
    });
}

function TryGetAllMessagesByRoomId(_roomid,callback){
    Message.find({roomid:_roomid},function(error,data){
        console.log(_roomid);
        return callback(error,data);
    })
}

function TryDeleteMessagesById(_roomid,callback){
    Message.deleteMany({roomid:_roomid},function(error,data){
        return callback(error,data);
    });
}

module.exports.TryInsertMessage = TryInsertMessage;
module.exports.TryGetAllMessagesByRoomId = TryGetAllMessagesByRoomId;
module.exports.TryDeleteMessagesById = TryDeleteMessagesById;