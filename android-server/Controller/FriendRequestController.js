var Connection = require('../MongoDBConnection');
var FriendRequest = require('../Model/friend_request');
var FriendRequestDTO = require('../DTO/FriendRequestDTO');
function TrySendRequest(request,callback){
	friend_request = FriendRequestDTO.FriendRequestDTO(request);
	friend_request.save((error)=>{
		if(!error)
		{
			return callback(null,friend_request);
		}
		else
			return callback(error,null);
	});
}

function TryGetAllRequestsByAccountId(accountid,callback)
{
	FriendRequest.find({to_request_accountid : accountid},(error,data)=>{
		if(error)
			throw error;
		return callback(null,data);
	});
}

function TryDeleteFriendRequest(sended_request_accountid,to_request_accountid,callback)
{
	FriendRequest.findOneAndDelete(
		{
			sended_request_accountid:sended_request_accountid,
			to_request_accountid:to_request_accountid
		},
		(error,data)=>{
				if(error)
					throw error;
				return callback(null,data);
		});
}



module.exports.TrySendRequest = TrySendRequest;
module.exports.TryGetAllRequestsByAccountId = TryGetAllRequestsByAccountId;
module.exports.TryDeleteFriendRequest = TryDeleteFriendRequest;