var Friends = require('../Model/friends');

function FriendsDTO(friends)
{
    _friends = new Friends();
    _friends.user_account_id = friends.user_account_id;
    _friends.friends_account_id = friends.friends_account_id;
    _friends.created_time= friends.created_time;
    return _friends;
}

module.exports.FriendsDTO = FriendsDTO;