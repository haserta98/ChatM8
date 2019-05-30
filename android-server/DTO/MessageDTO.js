var Message = require('../Model/message');
function MessageDTO(message)
{
    _message = new Message();
    _message.userId = message.userId;
    _message.message = message.message;
    _message.created_time = message.created_time;
    return _message;   
}

module.exports.MessageDTO = MessageDTO;