var Connection = require('../MongoDBConnection');
var Friends = require('../Model/friends');
var FriendsDTO = require('../DTO/FriendsDTO');
var User = require('../Model/users');
function TryAdd(friends,callback)
{
    _friends = FriendsDTO.FriendsDTO(friends);
    _friends.save((error)=>{
        if(!error)
            return callback(null,_friends);
        else
            return callback(error,null);
    });
}

function TryDelete(friends,callback)
{
    Friends.findOneAndDelete({user_account_id : friends.user_account_id,
                            friends_account_id : friends.friends_account_id},
    (error,data)=>{
        if(!error)
            return callback(null,data);
        else
            return callback(error,null);
    });
}

function TryGetAllFriendsById(user_account_id,callback)
{
    Friends.find({user_account_id:user_account_id},(error,data)=>{
        if(!error)
            return callback(null,data);
        else
            return callback(error,null);
    });
}

function IsTheyFriends(accountid,friendsid,callback)
{
    Friends.find({user_account_id:accountid,friends_account_id:friendsid},(error,data)=>{
        if(data[0] == null)
            return callback(false);
        return callback(true);
    });
}

function SearchFriendsByName(accountid,callback)
{
	var _accountid = new RegExp(accountid);
    account_id = null;
    User.find({accountid:_accountid},(error,data)=>{
	for(var x=0;x<data.length;x++)
        {
            IsTheyFriends("hasangzm",data[x].accountid,(_data)=>{
				
			});
        }
		if(!error)
			return callback(null,data);
		else
			return callback(error,null);
	});
}

module.exports.TryAdd = TryAdd;
module.exports.TryDelete = TryDelete;
module.exports.TryGetAllFriendsById = TryGetAllFriendsById;
module.exports.SearchFriendsByName = SearchFriendsByName;