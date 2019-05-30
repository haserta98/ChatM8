var socket = io.connect('http://localhost:3000'); //3000 portuna bağlandık
        if(socket != undefined)
            console.log("bağlandın")
        var element = function(id){
            return document.getElementById(id);
        }
        
        //inputlarımızı aldık
        var _name = element('name');
        var message = element('message_text');
        var _room = element('room');
        var sendButton = element('send');
        var joinButton = element('join');
        var deleteAllMessages = element('deleteAllMessages');
        var messages = element('messages');
        var _rooms = element('rooms');
        
        //enter tuşuna basılınca ve shift e basılmadığı takdirde
        //new message eventini tetikliyoruz
        //gönderilmiş mesajı manuel olarak kendi sayfamıza addSendedMessage fonksiyonu ile yazıyoruz
        //message inputumuzu temizliyoruz
        message.addEventListener('keydown',function(event){
            if(event.which === 13 && event.shiftKey==false){
                socket.emit('new message',{ 
                    message : message.value, 
                    name : _name.value
                });
                addSendedMessage({message:message.value,name:_name.value}); 
                message.value = '';
                event.preventDefault();                
            }
        });

        
        
        //girilen oda inputunu alarak join eventini tetikliyoruz
        joinButton.addEventListener('click',function(event){ //butona click eventi tanımladık
            joinRoom(_room.value);
        });

        function joinRoom(roomName){
            messages.textContent='';
            socket.emit('join',{
                room : roomName,
                name: _name.value
            });
        }

        //odadaki bütün mesajları silme eventini tetikliyoruz
        deleteAllMessages.addEventListener('click',function(event){
            socket.emit('delete messages by room id');
            messages.textContent = '';
        });

        //biz hariç diğer kullanıcıların yolladığı mesajların event tanımı
        //tetiklendiği zaman addComingMessage ile mesajları ekrana yazdırıyoruz
        socket.on('coming message',function(message){
            addComingMessage(message);
        });

        socket.on('on message',function(message)
        {
            addComingMessage(message);
        })
        //yeni oda eklenince odayı ekrana yaz
        socket.on('new room',function(data){
            addRoom(data.roomName);
        });

        //önceden oluşturulmuş bütün odaları al
        socket.on('get all room',function(data){
            for(var x=0;x<data.length;x++)
            {
                addRoom(data[x].roomName);
            }
        });

        //mesaj silinme eventi tetiklenince ekrandaki bütün mesajları temizliyoruz
        socket.on('clear message',function(){
            messages.textContent = '';
        });

        //gelen verileri div elementi oluşturarak ekrana yazdırıyoruz 
        function addComingMessage(data){
            if(data.length){
                for(var x=0;x<data.length;x++){
                    var message = document.createElement('div'); 
                    message.setAttribute('id','chat-coming-message'); 
                    message.textContent = data[x].name+": "+data[x].message; 
                    messages.appendChild(message);          
                }
            }
        }
            //gönderdiğimiz mesajı ekrana yazdırıyoruz
        function addSendedMessage(data){
            var message = document.createElement('div');
            message.setAttribute('id','chat-sended-message');
            message.textContent = data.name+": "+data.message;
            messages.appendChild(message);
        }
        
        //ekrana oda ekle
        function addRoom(_room)
        {
            var link = document.createElement('a');
            link.setAttribute('href','#');
            var room = document.createElement('div');
            room.setAttribute('id','created-room');
            room.setAttribute('class','form-control');
            room.setAttribute('onClick',`joinRoom('${_room}')`);
            room.textContent = _room;
            
            link.appendChild(room);
            rooms.appendChild(link);
        }

    