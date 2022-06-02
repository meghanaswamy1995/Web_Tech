 console.log("in js console")

function run() {
  
  // Creating Our XMLHttpRequest object 
  var xhr = new XMLHttpRequest();
  console.log(xhr);
  // Making our connection  
  var url = 'http://localhost:8080//search/artists/picasso';  
  console.log(url);
  xhr.open('GET', url, true);
  let result;
  // function execute after request is successful  
  xhr.onreadystatechange = function () { 
      if (this.readyState == 4 && this.status == 200) {
       // console.log(this.responseText);
        result = JSON.parse(this.responseText);  

          console.log(result);
          displayCharacters(result);
         
      }
  }
  // Sending our request 
  xhr.send();   
  console.log(result); 
  displayCharacters(result);  
}

const displayCharacters = (characters) => {
  console.log("char-- " +characters);
  const artistsList = document.getElementById('val');  
  const htmlString = characters._embedded.results.map((character) => {  
    let artistId="";
    if(character.og_type == "artist"){  
      console.log("only artist fetch") 
      let artImg=character._links.thumbnail.href;
      return `
            <button class="artists" id="" type="button" onclick="getArtist(this.id)">  
                <img src="${artImg}"></img>   
                <div>${character.title}</div>
            </button> 
        `;
    }
}).join('');
console.log("final-str:"+htmlString); 
artistsList.innerHTML = htmlString; 
};

run();


// function postData(input) {
//   $.ajax({
//       type: "GET",
//       url: "http://localhost:8080//search/artists/picasso",  
//       success: callbackFunc
//   });
// }

// function callbackFunc(response) {
//   // do something with the response
//   console.log(response);
// }
 
// postData('data to process');


// fetch('http://localhost:8080//search/artists/picasso')
// .then(function (response) {
//     return response.json();
// }).then(function (text) {
//     console.log('GET response:');
//     console.log(text.total_count);  
// });