var mongoose = require('mongoose');
let _db;
mongoose.connect('mongodb://localhost/socketdb',{ useNewUrlParser: true },(error,db)=>{
    if(!error){
        console.log("MongoDB'ye Bağlandın !");  
        
        _db = db;
        
    }
        
    else
        console.log(error.message);
});

function TryGetRoomUsers(_accountid,callback)
{
    _db.collection('rooms').aggregate([
        {
         $lookup:
                {
                    from: 'roomusers',
                    localField : 'roomName',
                    foreignField : 'roomName',
                       as : 'roomwithuser',
                }
        },
        {
            $match:
            {
                "roomwithuser.accountid": _accountid
            }
        }
    ]).toArray(function(err,res){
        if(err) throw err;
        callback(null,res);
    });
}

module.exports.TryGetRoomUsers = TryGetRoomUsers;



