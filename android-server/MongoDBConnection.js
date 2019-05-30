
// var mongoose = require('mongoose');
// mongoose.connect('mongodb+srv://hasangzm:Hasan3869.@chatm8-hx9uf.mongodb.net/test?retryWrites=true',(error)=>{
//     if(!error)
//         console.log("Bağlandın !");
//     else
//         console.log(error.message);
// });

var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/socketdb',{ useNewUrlParser: true },(error)=>{
    if(!error)
        console.log("MongoDB'ye Bağlandın !");
    else
        console.log(error.message);
});

// User.findById('5c966658c7175e1420f021c9',(error,data)=>{
//     if(error)
//         throw error;

    
//     data.first_name = 'Tuba';
//     data.save((errors)=>{ //remove de var
//         if(errors)
//         {
//             throw errors;
//         }
//             console.log("updated");
//             console.log(data);
//     });
// });



// User.findById('5c966658c7175e1420f021c9',(error,data) => {
//     if(error)
//     throw error;
//     console.log(data);
// });
