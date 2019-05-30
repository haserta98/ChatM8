const mongodb = require('../mongodbconnection');
const Room = require('../../Model/room');
const RoomUser = require('../../Model/room_user');
const User = require('../../Model/users');
function TryCreateNewRoom(data,callback){
    var _room = new Room();
    _room.roomName = data.room;
    _room.roomCreaterUserName = data.username;
	_room.roomType = data.roomType;
    _room.created_time = Date.now();
    _room.save((error)=>{
        if(!error)
        console.log("başarılı");
        return callback(error,_room);
    });
}

function TryGetAllRoom(callback){
    Room.find({},(error,data)=>{
        return callback(error,data);
    });
}

function TryGetRoom(roomName,callback)
{
    Room.findOne({roomName:roomName},(error,data)=>{
        return callback(error,data);
    });
}

function TryGetAllPublicRoom(callback)
{
    Room.find({roomType:"public"},(error,data)=>{
		if(error) throw error;
		return callback(null,data);
	});
}



function TryCheckRoom(_roomname,callback){
    Room.find({roomName:_roomname},(error,data)=>{
        return callback(error,data);
    });
}

function TryAddUsersRoom(data)
{
    for(_data of data)
    {
        var _roomUser = new RoomUser();
        _roomUser.roomName = _data.roomName;
        _roomUser.accountid = _data.accountid;

        _roomUser.save((error)=>{
            if(error)
             throw error;
        });
    }	
}

function IsUserOnRoom(data,callback)
{
    RoomUser.find({roomName : data.roomName, accountid : data.accountid},(error,data)=>{
        if(error)
            throw error;
        if(Object.keys(data).length === 0)
            return callback(null,false);
        return callback(null,true);
    });
}







module.exports.TryCreateNewRoom = TryCreateNewRoom;
module.exports.TryGetAllRoom = TryGetAllRoom;
module.exports.TryCheckRoom = TryCheckRoom;
module.exports.TryAddUsersRoom = TryAddUsersRoom;
module.exports.IsUserOnRoom = IsUserOnRoom;
module.exports.TryGetRoom = TryGetRoom;
module.exports.TryGetAllPublicRoom = TryGetAllPublicRoom;