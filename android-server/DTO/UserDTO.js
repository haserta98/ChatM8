var User = require('../Model/users');
var md5 = require('md5');
function UserDTO(user)
{
    _user = new User();
    _user.name = user.name;
    _user.email = user.email;
    _user.accountid = user.accountid;

    _user.password = md5(user.password);
    _user.imgUrl = user.imgUrl;
    _user.isActive = user.isActive;
    _user.created_time = user.created_time;
    return _user;   
}

module.exports.UserDTO = UserDTO;