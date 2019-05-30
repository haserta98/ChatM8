const bcrypt = require('bcrypt');

function cryptPassword(password,callback)
{
    bcrypt.genSalt(10, function (error, salt) {
        if (error)
            return callback(error);

        bcrypt.hash(password, salt, function (err, hash) {
            return callback(err, hash);
        });
    });
}

function comparePassword(plainPass,hashPass,callback)
{
    bcrypt.compare(plainPass, hashPass, function (error, result) {
        return err == null ? callback(null, result) : callback(error);
    });
}

// let pass = "Hasan3869.";
// let cryptd;
// cryptPassword(pass,function(error,hash){
//     cryptd = hash;
//     console.log(cryptd);
// });
// cryptPassword(pass,(err,hash)=>{
//     cryptd = hash;
//     console.log(cryptd);
// });
module.exports.comparePassword = comparePassword;
module.exports.cryptPassword = cryptPassword;