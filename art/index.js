
const artistsList = document.getElementById('artistsList'); 

let hpCharacters = [];
let artistInfo=[];
let authToken="";

const loadCharacters = async (target) => { 
    try { 
      const artistsList = document.getElementById('artistsList').style.display = "block";  
        
      //const emptyList = document.getElementById('emptyList').style.display = "none";   
        let artistUrl="https://api.artsy.net/api/search?q="+target+"&size=10";
        console.log();
        const res = await fetch(artistUrl, 
        { method :'GET',
          headers : {   
            'X-XAPP-Token': authToken  } 
        });  
        hpCharacters = await res.json(); 
        displayCharacters(hpCharacters);  

    } catch (err) {
        console.error(err);  
    }
};

const loadArtist = async (artId) => { 
  try{
    const artistinfo = document.getElementById('artistInfoContainer').style.display = "block"; 
    let infoUrl="https://api.artsy.net/api/artists/"+ artId;
    const info = await fetch(infoUrl, 
      { method :'GET',
        headers : {   
          'X-XAPP-Token': authToken  }
      }); 
      artistInfo = await info.json();
      displayArtistInfo(artistInfo);
  }
  catch(err){
    console.error(err);
  }
 };

const displayArtistInfo = (info)=>{ 

  var h2html=info.name+ " ("+info.birthday+" - "+info.deathday+")";
  var newdiv=`<div id=artistBio> <h2> ${h2html}</h2><h3> ${info.nationality}</h3> <p>${info.biography}</p></div>`; 
   newdiv+=" "; 
 
 const artistInfoContainer = document.getElementById('artistInfoContainer');
 artistInfoContainer.innerHTML=newdiv;  
 
};

const displayCharacters = (characters) => {
  //  for (var counter in characters._embedded.results) {
  //   if(characters._embedded.results[counter].og_type == "artist"){
              
  //    // console.log("ID: "+characters._embedded.results[counter]._links.self.href);
  //     //console.log("Title: "+characters._embedded.results[counter].title);
  //     console.log("img: "+characters._embedded.results[counter]._links.thumbnail.href); 
  //   }
  //  }
  let count=0; 
   const htmlString = characters._embedded.results.map((character) => {  
          let artistId="";
          if(character.og_type == "artist"){ 
            count++;  
            let artImg=character._links.thumbnail.href;
            if(artImg=="/assets/shared/missing_image.png"){ 
              artImg="images/artsy_logo.svg";
            }
            if(character._links.self.href!=null){ 
              let val=character._links.self.href.lastIndexOf('/');
              artistId = character._links.self.href.substring(val + 1);  
            }
            return `
                  <button class="artists" id="${artistId}" type="button" onclick="getArtist(this.id)">  
                      <img src="${artImg}"></img>   
                      <h2>${character.title}</h2> 
                      <p>${artistId}</p>  
                  </button> 
              `;
          }
      }).join(''); 
  
      artistsList.innerHTML = htmlString; 

      if(count==0){
      var htmlstr='<div id="emptydiv">No results found.</div>';   
    //   const artistsList = document.getElementById('artistsList').style.display = "none";
     const artistinfo = document.getElementById('artistInfoContainer').style.display = "none";  
      //const emptyList = document.getElementById('emptyList').style.display = "block";  
      artistsList.innerHTML = htmlstr;       
      }  
};

const getAuthentication = async (target) => {  
  let authjson = [];
  let authUrl="https://api.artsy.net/api/tokens/xapp_token?client_id=120872502231defb0204&client_secret=45b6764ebca39b6f9613372a3eb45577";
  const auth = await fetch(authUrl, 
    { method :'POST'
    }); 
    authjson = await auth.json();  
    authToken = authjson.token;
}

//get auth token
getAuthentication();   
//const emptyList = document.getElementById('emptyList').style.display = "none"; 

//take user input
const artistInput=document.getElementById('artist-input');  
let artiststarget="";
var tasks = [];
artistInput.addEventListener('input', (e) =>{ 
  artiststarget=e.target.value;  
});

//get Artist Info
function getArtist(clickedId){
  loadArtist(clickedId);
}

//search button operation
function search(event) {  
  if(event){event.stopPropagation();event.preventDefault();}
  if(artiststarget!=""){
  loadCharacters(artiststarget);
  } 
}

//listen to Enter keypress and click on submit when entered 
artistInput.addEventListener("keypress", function(event) {
  if (event.key === "Enter") {
    event.preventDefault();
    document.getElementById("searchButton").click();
  } 
});  
