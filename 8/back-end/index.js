const express = require('express')
const axios = require('axios') 
var cors = require('cors'); 

const app = express()
const port = 8080 

app.use(express.static('dist/angular-app'))
app.use("/images",  express.static(__dirname + '/public/images'));  

app.use(cors({
  'allowedHeaders': ['Content-Type'],
  'origin': '*',
  'preflightContinue': true
})); 
 
//Get Auth
const getAuth = async () => {
  var token
  
  await axios({
      url: 'https://api.artsy.net/api/tokens/xapp_token', 
      method: 'post',
      params: {
        'client_id': '120872502231defb0204',
        'client_secret': '45b6764ebca39b6f9613372a3eb45577'
      }
  }).then((resp)=>{
    token=resp.data.token
    console.log(token)
  })
  .catch((error) => {
    console.log(error)
  })
  return token 
}

const getArtistData =async(url,token)=>{  
  var info

  await axios({
    url: url, 
    method: 'get', 
    headers: {
      'X-XAPP-Token': token
  }
})
  .then(function (response) { 
    info=response.data
    console.log(info);       
  })
  .catch(function (error) { 
    console.log(error);   
  }) 
  return info
}

async function getData(url){
  var token = await getAuth(); 
  var artworks = await getArtistData(url,token); 
  return artworks
}

app.get('/', (req, res) => {

  getAuth().then((response)=>{ 

  //   fs.readFile(__dirname + 'dist/angular-app/index.html', 'utf8', function(err, text){
  //     res.send(text);  
  // });

  res.sendFile('index.html',{root:__dirname})
    //res.sendFile('public/index.html', {root: __dirname })  
  }).catch((error)=>{
    console.log(error)
  })
   
})

app.get('/search/artists/:name/', (req, res) => {   
  var name=req.params.name; 
  var url = 'https://api.artsy.net/api/search?q='+name+'&size=10';
  console.log('nameeee '+url) 
  getData(url).then((response)=>{ 
  res.send(response) 
 }).catch((error)=>{
   console.log(error) 
 })   

})

app.get('/get/artist/:id/', (req, res) => {   
  var id=String(req.params.id);
  var url = 'https://api.artsy.net/api/artists/'+id+'/';
  console.log('urlllllll'+url+" id "+id)
  getData(url).then((response)=>{  
  res.send(response) 
 }).catch((error)=>{ 
   console.log(error)
 })   

})
// https://api.artsy.net/api/artworks?artist_id=4d8b928b4eb68a1b2c0001f2&size=10

app.get('/get/artist/artwork/:id/', (req, res) => {    
  var id=String(req.params.id);
  var url = 'https://api.artsy.net/api/artworks?artist_id='+id+'&size=10'; 
  console.log('urlllllll'+url+" id "+id)
  getData(url).then((response)=>{   
  res.send(response)
 }).catch((error)=>{ 
   console.log(error)
 })   

})

// https://api.artsy.net/api/genes?artwork_id=515b0f9338ad2d78ca000554
app.get('/get/artwork/:artid/', (req, res) => {    
  var artid=String(req.params.artid);
  var url = 'https://api.artsy.net/api/genes?artwork_id='+artid
  console.log('urlllllll '+url+" id "+artid)
  getData(url).then((response)=>{   
  res.send(response)
 }).catch((error)=>{ 
   console.log(error)
 })   

})  


app.listen(port, () => {
  console.log(`Example app listening on port ${port}`) 
})
