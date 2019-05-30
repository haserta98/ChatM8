var Connection = require('../MongoDBConnection');
var UserDTO = require('../DTO/UserDTO');
var User = require('../Model/users');
var md5 = require('md5');

function TryLogin(user, callback) {
    User.findOne({ email: user.email, password: md5(user.password) }, (error, data) => {
        if (error)
            throw error;
        else if (data == null) {
            console.log("bilgiler yanlış ! " + error);
            return callback("Hata var kardeşim", null);
        }
        else if (data.length != 0) {
            return callback(null, data);
        }
    });
}

function TryRegister(user, callback) {
    _user = UserDTO.UserDTO(user);
    _user.save((error) => {
        if (!error)
            return callback(null, _user);
        else
            return callback(error, null);
    });
}

function TryDelete(id) {
    User.findByIdAndDelete(id, (error, data) => {
        if (error)
            throw error;
    });
}

function GetAll(callback)
{
    User.find({},function(error,data){
        if(error)
            throw error;
        return callback(error,data); 
    });
}

function GetUserByAcccountId(accountid,callback)
{
    User.find({accountid:accountid},(error,data)=>{
        if(error)
            throw error;
        return callback(error,data);
    });
}

function AddImage(_accountid,imageString,callback)
{
	var imageBuffer = new Buffer.from(imageString)
	User.updateOne({accountid:_accountid},{imgUrl:imageBuffer},(error,data)=>{
		if(error)
			throw error;
		return callback(null,data);
	});
}

function GetImage(accountid,callback){
	User.find({accountid:accountid},(error,data)=>{
		if(error)
			throw error;
		return callback(null,data);
	});
}
module.exports.TryRegister = TryRegister;
module.exports.TryLogin = TryLogin;
module.exports.TryDelete = TryDelete;
module.exports.GetAll = GetAll;
module.exports.GetUserByAcccountId = GetUserByAcccountId;
module.exports.AddImage = AddImage;
module.exports.GetImage = GetImage;