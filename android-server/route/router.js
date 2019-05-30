var express = require('express');
const mongodb = require('../Socket/mongodbconnection');
var bodyParser = require('body-parser');
var userController = require('../Controller/UserController');
var messageController = require('../Socket/Controller/MessageController');
var roomController = require('../Socket/Controller/RoomController');
var friendsController = require('../Controller/FriendsController');
var friendRequestController = require('../Controller/FriendRequestController');
const listenIPAdress = 8000;
const urlEncoded = bodyParser.urlencoded({limit:'50mb', extended: true });



const app = express();

app.use(bodyParser.json({limit: '50mb'}));
app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));

/*app.use(express.json({
    inflate: true,
    limit: '100kb',
    reviver: null,
    strict: true,
    type: 'application/json',
    verify: undefined
  }))
*/
app.post('/login', urlEncoded, function (req, res) {
    userController.TryLogin(req.body, (error, data) => {
        if (!error) {
            res.contentType('application/json');
            res.json({
                'email': data.email,
                'accountid': data.accountid,
                'name': data.name,
                'imgUrl': data.imgUrl,
                'created_time': data.created_time,
                'status': 'OK'
            });
            console.log("girdin kardeş");
            console.log(data);
        }
        else {
            res.contentType('application/json');
            res.json({
                'status': 'NO'
            });
        }
    });
});

app.post('/register', urlEncoded, function (req, res) {

    userController.TryRegister(req.body, (error, data) => {
        if (!error && data.email !=null) {
            res.contentType('application/json');
            res.json({
                'email': data.email,
                'accountid': data.accountid,
                'name': data.name,
                'imgUrl': data.imgUrl,
                'created_time': data.created_time,
                'status': 'OK'
            });
        }
        else {
            res.contentType('application/json');
            res.json({
                'status': 'NO'
            });
        }
    });

});

app.get('/getall', urlEncoded, function (req, res) {
    userController.GetAll((error,data)=>{
        res.contentType('application/json');
        if(error || data[0] ==null) 
            res.json({
            'data': null,
            'status': 'NO'});
        else{
            res.json({
            'data' : data,
            'status': 'OK'});
        }
    })
});

app.get('/getallrooms',urlEncoded,function(req,res){
    roomController.TryGetAllRoom((error,data)=>{
        if(!error){
            res.contentType('application/json');
            res.json({
                'data' : data,
                'status' : 'OK'
            });
        }
    });
})


app.delete('/delete/user/:id', function (req, res) {
    userController.TryDelete(req.params.id);
    res.end();
});

app.get('/test',function(req,res){
    console.log("deneme");
    res.end();
})

app.get('/getallmessages',urlEncoded, function(req,res)
{
    messageController.TryGetAllMessagesByRoomId(req.body.roomid,(error,data)=>{
        res.contentType("application/json");
        res.json({
            'data' : data,
            'status' : 'OK'
        });
    });
});

app.get('/getallfriends/:accountid',urlEncoded,function(req,res){
	friendsController.TryGetAllFriendsById(req.params.accountid,(error,data)=>{
		res.contentType("application/json");
		res.json({
			'data' : data,
			'status':'OK'
		});
	});
});

app.post('/addfriend',urlEncoded,function(req,res){
    friendsController.TryAdd(req.body,(error,data)=>{
        if(error)
            throw error;
        res.contentType("application/json");
        res.json({
            'data' : data,
            'status' :'OK'
        });
    });
});

app.post('/searchfriends',urlEncoded, function(req,res){
	friendsController.SearchFriendsByName(req.body.accountid,(error,data)=>{
		res.contentType("application/json");
		res.json({
			'data' : data,
			'status' : 'OK',
		});
	});
});

app.post('/sendfriendrequest',urlEncoded,function(req,res){
	friendRequestController.TrySendRequest(req.body,(error,data)=>{
		if(error)
			throw error;
		res.contentType("application/json");
		res.json({
			'data' : data,
			'status' : 'OK',
		});
	});
});

app.get('/getfriendrequestbyid/:accountid',urlEncoded,function(req,res){
	friendRequestController.TryGetAllRequestsByAccountId(req.params.accountid,(error,data)=>{
		if(error)
			throw error;
		res.contentType("application/json");
		res.json({
			'data' : data,
			'status' : 'OK',
		});
	});
});

app.post('/deletefriendrequest',urlEncoded,function(req,res){
	friendRequestController.TryDeleteFriendRequest(
		req.body.sended_request_accountid,
		req.body.to_request_accountid,(error,data)=>{
		if(error)
			throw error;
		res.contentType("application/json");
		res.json({
			'data' : data,
			'status' : 'OK'
		});
	 });
});

app.post('/adduserstoroom',function(req,res){
    roomController.TryAddUsersRoom(req.body.users);
        res.contentType("application/json");
        res.json({
            'status' : 'OK',
        });
});

app.get('/checkuserroom',function(req,res){
    roomController.IsUserOnRoom(req.body,(error,data)=>{
        res.contentType("application/json");
        res.json({
            'data' : data,  
        });
    });
});

app.get('/getallpublicrooms',function(req,res){
    roomController.TryGetAllPublicRoom((error,data)=>{
        res.contentType("application/json");
        res.json({
            'data' : data,
        });
    });
});

app.post('/resimekle',urlEncoded,function(req,res){
	userController.AddImage(req.body.accountid,req.body.image,(error,data)=>{
		res.contentType("application/json");
		res.json({
			'status':'OK',
			'data' : data,
		});
	});
});

app.get('/resimgetir/:accountid',urlEncoded,function(req,res){
	userController.GetImage(req.params.accountid,(error,data)=>{
		res.contentType("application/json");
		console.log(req.params.accountid);
		res.json({
			'data' : data[0].imgUrl
		});
	});
});

app.get('/getroomusers/:roomName',function(req,res){
    
        
 

    mongodb.TryGetRoomUsers(req.params.roomName,(error,data)=>{
        res.contentType("application/json");
        res.json({
            'data' : data,
        });
    });
});


app.listen(listenIPAdress, function () {
    console.log("Server is started...");
})