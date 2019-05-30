const app = require('express')()
const server = require('http').createServer(app)
const io = require('socket.io')(server)


const MessageController = require('./Controller/MessageController');
const RoomController = require('./Controller/RoomController');

var connectedClients = {};
io.on('connection', client => { //bağlantı olduğunda

        

        let room;
        let username;
        console.log("bağlandın");

        RoomController.TryGetAllRoom((error,data)=>{
            if(error)
                throw error;
                client.emit('get all room',data);
        });

        //odaya katılma fonksiyonu
        
        //verilen odaya join ile katılıyoruz
        //o odada mesaj varsa mesajı çekiyoruz ve triggerlıyoruz
        client.on('join',function(_data){


            

            /**
              eğer kullanıcı oda değiştirmek istiyorsa önceden dolu olup olmadığını kontrol ediyoruz eğer doluysa leave ile ayrılıyoruz odadan
            */
            if(room !=null && _data.room != room)
            {
                client.leave(room); 
            }

            if(room == _data.room){
                MessageController.TryGetAllMessagesByRoomId(room,(error,data)=>{
                    if(!error){
                        client.emit('coming message',data);
                        client.broadcast.emit('new join',{username:username});
                    }
                    else{
                        console.log(error);
                    }
                });
            }
            else{
                client.join(_data.room,()=>{ 

                    /**
                     * Böyle bir oda oluşturul oluşturulmadığı kontrol ediliyor
                     * Eğer yok ise yeni oda oluşturuluyor veritabanında
                     */
                    RoomController.TryCheckRoom(_data.room,(error,data)=>{
                        if(data.length ==0 && !error){
                            RoomController.TryCreateNewRoom({room:_data.room,username:_data.name,roomType:_data.roomType},(error,data)=>{
								console.log(_data.roomType);
                                if(!error){
                                console.log("yeni oda oluştu " + data + " adı da " + _data.room);
                                client.broadcast.emit('new room',data);
                                client.emit('new room',data);
                            }
                            });
                        }
                    });
    
                    username = _data.name;
                    connectedClients[username] = client;
                    room = Object.keys(client.rooms)[1];
                    console.log("Başarılı bir şekilde " + _data.room  + " = " + room + " odasına katıldınız")
                    console.log(Object.keys(connectedClients));
                    MessageController.TryGetAllMessagesByRoomId(room,(error,data)=>{
                        if(!error){
                            client.emit('coming message',data);
                            client.broadcast.emit('new join',{username:username});
                        }
                        else{
                            console.log(error);
                        }
                    });
                });
            }
            
        });

        client.on('private message',function(data){
            let messageTo = data.to;
            let message = data.message;

            connectedClients[messageTo].emit('private message',message);
        });
        
        //mesaj attığımızda burası çalışıyor
        //alınan mesaj,isim ve odalar veritabanına kaydoluyor
        //kaydolduktan sonra attığımız mesaj odadaki diğer kullanıcılara triggerlanıyor
        //kendi mesajımızı görmek için kendimiz manuel ekliyoruz
        client.on('new message',function(data){
            let _name = data.name;
            let _message = data.message;
            let _id = data.userid
            if(_name != null && _message != null && _id != null){
                MessageController.TryInsertMessage({
                    name:_name,
                    message:_message,
                    room:room,
                    userid:_id
                },(error,_data)=>{
                    if(!error){
                        client.to(room).emit('on message',[data]);
                    }
                    else{
                        console.log(error);
                    }
                });     
        }
        });

        
        //verilen oda numarasına göre o odadaki bütün mesajları sil
        //silme metodu MessageController'i içerisinde mevcut
        //event geldiğinde o odadaki bütün kullanıcılar clear message eventi tetikleniyor
        //kendi sayfamız içinde bu eventi triggerladığımız yerde kendimiz siliyoruz
        client.on('delete messages by room id',function(){
            MessageController.TryDeleteMessagesById(room,(error,data)=>{
                if(!error){
                    client.to(room).emit('clear message');
                }
                else{
                    console.log("Hata !");
                }
            });
        });

        

        client.on('disconnect',function(){
            delete connectedClients[username];
            client.broadcast.emit("on disconnect",{username:username});
            console.log(username + "çıkış yaptı kardeşşş");
            client.leaveAll();
        });

      });
  
  server.listen(3000); 