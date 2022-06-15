const artistsList = document.getElementById('artistsList'); 
let hpCharacters = [];
let artistInfo=[];
let authToken="";
let clickedArtist="";
var xhr = new XMLHttpRequest(); 
let loadcount=0; 
//document.getElementById('searchform').addEventListener("click",setborderColor);    

const loadCharacters = async (target) => {   
    try {

      // Making our connection  
      //var url = 'http://localhost:8080/search/artists/'+target;    
      var url = 'http://artistsweb-351508.wl.r.appspot.com/search/artists/'+target; 
      
      xhr.open('GET', url, true);
      let result; 
      // function execute after request is successful  
      xhr.onreadystatechange = function () {
          if (this.readyState == 4 && this.status == 200) { 
           // console.log(this.responseText);
            result = JSON.parse(this.responseText);
              
              displayCharacters(result);
          }
      }
      // Sending our request   
      xhr.send();    

    } catch (err) {
        console.error(err);    
    }
};

const loadArtist = async (artId) => { 
  try{
    const artistinfo = document.getElementById('artistInfoContainer').style.display = "block";  
    //let infoUrl="http://localhost:8080/artist/info/"+ artId;
    let infoUrl="http://artistsweb-351508.wl.r.appspot.com/artist/info/"+ artId;
    
    xhr.open('GET', infoUrl, true);
      let infoRes; 
      // function execute after request is successful  
      xhr.onreadystatechange = function () { 
          if (this.readyState == 4 && this.status == 200) {  
           // console.log(this.responseText);
            infoRes = JSON.parse(this.responseText);   
     
              displayArtistInfo(infoRes); 
             
          }
      }
      // Sending our request 
      xhr.send(); 

  }
  catch(err){
    console.error(err);
  }

 };

const displayArtistInfo = (info)=>{ 
  let biography=info.biography;
  
  if(biography == null){   
    biography="";
  }   
  var h2html=info.name+ " ("+info.birthday+" - "+info.deathday+")";
  var newdiv=`<div id=artistBio> <h2> ${h2html}</h2><h3> ${info.nationality}</h3> <p>${biography}</p></div>`;  
  //var newdiv=`<h2> ${h2html}</h2><h3> ${info.nationality}</h3> <p>${info.biography}</p>`;  
   newdiv+=" ";
 
 const artistInfoContainer = document.getElementById('artistInfoContainer'); 
 //document.getElementById("loading2").style.visibility="hidden"; 
 artistInfoContainer.style.visibility="visible";  
 artistInfoContainer.innerHTML=newdiv;
};

const displayCharacters = (characters) => {
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
                      <div>${character.title}</div>   
                  </button> 
              `;
          }
      }).join('');  
      document.getElementById("artistInfoContainer").style.visibility="hidden";
      document.getElementById("loading1").style.visibility="hidden"; 
      artistsList.style.visibility="visible"; 
      artistsList.innerHTML = htmlString;

      if(count==0){
      var htmlstr='<div id="emptydiv">No results found.</div>'; 
      const artistinfo = document.getElementById('artistInfoContainer').style.visibilty = "hidden"; 
      loadcount=0 ;
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

const artistInput=document.getElementById('artist-input');  
let artiststarget="";
var tasks = [];
artistInput.addEventListener('input', (e) =>{ 
  artiststarget=e.target.value;  
});

//get Artist Info
function getArtist(clickedId){ 
  clickedArtist=clickedId;
  var artistsArr = document.getElementsByClassName('artists');
  for(var i=0;i<artistsArr.length;i++){
   artistsArr[i].style.backgroundColor=null;
   }
  document.getElementById(clickedId).style.backgroundColor='#112b3c';
  document.getElementById("artistInfoContainer").style.visibility="visible";
  document.getElementById("artistInfoContainer").innerHTML='<div id=artistBio><img id="loading2" src="images/loading.gif" alt=""></div>';  
  //document.getElementById("loading2").style.visibility="visible";       
  loadArtist(clickedId);    
}

//search button operation
function searchbutton(event) {      
  if(event){event.stopPropagation();event.preventDefault();}
 
  if(artiststarget!=""){ 
    if(loadcount == 0){ 
    document.getElementById("loading1").style.visibility="visible"; 
    artistsList.style.visibility="hidden"; 
    } 
    else{
    document.getElementById("artistInfoContainer").style.visibility="visible";
    document.getElementById("artistInfoContainer").innerHTML='<div id=artistBio><img id="loading2" src="images/loading.gif" alt=""></div>';  
  }  
  loadcount=1;
  document.getElementById('searchform').style.borderColor='#D7D7D7';     
  loadCharacters(artiststarget);
  } 
} 

function clearborder(id){    
  document.getElementById('searchform').style.borderColor='#D7D7D7';
  artiststarget="";
}

function setborderColor(id){  
  document.getElementById('searchform').style.borderColor='orange';
}

//listen to Enter keypress and click on submit when entered 
artistInput.addEventListener("keypress", function(event) {  
  if (event.key === "Enter") {
    event.preventDefault(); 
      document.getElementById("searchButton").click();    
  }
});  


document.addEventListener("click", function(event) { 
  if (event.target.closest(".search-container")){ return} 
  else{
   
  document.getElementById('searchform').style.borderColor='#D7D7D7'; 
  } 
}) ;  

window.addEventListener( "pageshow", function ( event ) { 
  var historyTraversal = event.persisted || ( typeof window.performance != "undefined" && window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    // Handle page restore.
    //alert('refresh');
    window.location.reload();
  }
}); 


