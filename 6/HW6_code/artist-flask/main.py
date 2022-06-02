from codecs import ignore_errors
import json
from urllib import response
from flask import Flask, jsonify, render_template
import requests
from flask import send_from_directory

app = Flask(__name__, static_folder='static',static_url_path='',) 
AUTH_TOKEN = None


@app.route("/")
def home():

    params = {
        'client_id': '120872502231defb0204',
        'client_secret': '45b6764ebca39b6f9613372a3eb45577'
    }
    r = requests.post(
        'https://api.artsy.net/api/tokens/xapp_token', params=params)     

    global AUTH_TOKEN
    AUTH_TOKEN = r.json()['token'].strip()
    print("auth-token"+AUTH_TOKEN)
    return app.send_static_file("index.html")


@app.route("/search/artists/<name>") 
def searchArtists(name):
    
    params = {
        'client_id': '120872502231defb0204',
        'client_secret': '45b6764ebca39b6f9613372a3eb45577'
    }
    r = requests.post(
        'https://api.artsy.net/api/tokens/xapp_token', params=params)     

    AUTH_TOKEN = r.json()['token'].strip() 
    print(AUTH_TOKEN) 

    newHeader = {
        'X-XAPP-Token': AUTH_TOKEN  
    }
    #target = "picasso"
    url = "https://api.artsy.net/api/search?q="+name+"&size=10"   

    q = requests.get(url, headers={'X-XAPP-Token': AUTH_TOKEN})    
    #print(q.content)
    decodedres = q.content.decode('utf-8', errors='ignore')
    return json.loads(decodedres)


@app.route("/artist/info/<id>") 
def getArtistInfo(id):

    params = {
        'client_id': '120872502231defb0204',
        'client_secret': '45b6764ebca39b6f9613372a3eb45577'
    }
    r = requests.post(
        'https://api.artsy.net/api/tokens/xapp_token', params=params)

    AUTH_TOKEN = r.json()['token'].strip() 
    #print(AUTH_TOKEN)
     
    newHeader = {
        'X-XAPP-Token': AUTH_TOKEN 
    }
    #id = "52b32400275b24533a00019c"   
    infourl = "https://api.artsy.net/api/artists/"+id 
    print(AUTH_TOKEN)
    resp = requests.get(infourl, headers={'X-XAPP-Token': AUTH_TOKEN})    
    print(resp.content) 
    decodedresp = resp.content.decode('utf-8', errors='ignore') 
    return json.loads(decodedresp) 
    return infourl


if __name__ == "__main__":
    app.run(host='127.0.0.1', port=8080)  
