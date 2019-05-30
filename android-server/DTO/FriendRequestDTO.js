var FriendRequest = require('../Model/friend_request');

function FriendRequestDTO(friend_request)
{
    _request = new FriendRequest();
    _request.sended_request_accountid = friend_request.sended_request_accountid;
	_request.to_request_accountid = friend_request.to_request_accountid;
	_request.created_time = friend_request.created_time;
    return _request;   
}

module.exports.FriendRequestDTO = FriendRequestDTO;