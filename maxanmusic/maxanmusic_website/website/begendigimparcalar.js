let parcasayfa=0;
let parcasayfanull=false;
let kaydirabilirmi = true;

window.onload = function () {

    nav_kullaniciKontrolEt();
    parcalariyukle();
    window.addEventListener("scroll", function(){if(kaydirabilirmi){sayfakaydir()}});
    
}


function parcalariyukle(){

    if(!parcasayfanull){
      fetch("http://localhost:8083/users/liked-tracks?pageNo="+parcasayfa,{
              method:"GET",
              credentials: "include",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {
  
            if(response.status==404){
              parcasayfanull = true
              return false;
            }
            if(!response.ok){
              return false;  
            }
              return response.json(); 
          }).then((data) => {
            data.forEach(parcaekle);
            parcasayfa++
          });
    }
}
  
function sayfakaydir(sayfa){
    
    if(window.scrollY + window.innerHeight >= document.documentElement.scrollHeight){
        if(kaydirabilirmi){
            kaydirabilirmi = false;
            parcalariyukle()
            kaydirabilirmi = true;
        }
        
    }
}

function parcaekle(parcaitem){

    let parca = document.createElement("div");
    parca.className = "parca";

    let ibilgibolme = document.createElement("div");
    ibilgibolme.className = "ibilgibolme";
    parca.appendChild(ibilgibolme);

    let ibilgi_ana = document.createElement("div");
    ibilgi_ana.className = "ibilgi_ana";
    ibilgibolme.appendChild(ibilgi_ana);

    let parcaartist = document.createElement("h4");
    parcaartist.className="parcaartist";
    parcaartist.innerText=parcaitem.artistUsername;
    ibilgi_ana.appendChild(parcaartist);

    let parcaadi = document.createElement("h3");
    parcaadi.className="parcaadi";
    parcaadi.innerText = parcaitem.trackName;
    ibilgi_ana.appendChild(parcaadi);

    let ibilgi_alt = document.createElement("div");
    ibilgi_alt.className = "ibilgi_alt";
    ibilgibolme.appendChild(ibilgi_alt);

    let ibilgi_alt1 = document.createElement("div");
    ibilgi_alt1.className = "ibilgi_alt1";
    ibilgi_alt.appendChild(ibilgi_alt1);

    let begeni = document.createElement("p");
    begeni.innerText = parcaitem.likeCount + " beğeni";
    ibilgi_alt1.appendChild(begeni);

    let ibilgi_alt2 = document.createElement("div");
    ibilgi_alt2.className = "ibilgi_alt2";
    ibilgi_alt.appendChild(ibilgi_alt2);

   

    /*
    let yorum = document.createElement("p");
    yorum.innerText = "";
    ibilgi_alt2.appendChild(yorum);
    
    yorum sayısı eklenene kadar kullanılmayacak
    */

    let ifotobolme = document.createElement("div");
    ifotobolme.className = "ifotobolme";
    parca.appendChild(ifotobolme);

    let ifotocerceve = document.createElement("div");
    ifotocerceve.className = "ifotocerceve";
    ifotobolme.appendChild(ifotocerceve);

    let pkf = document.createElement("img");
    pkf.id = "pkf";
    pkf.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/tcvr/" + parcaitem.trackId+".png?time=" + (new Date()).getTime();
    ifotocerceve.appendChild(pkf);

    let icerik = document.getElementsByClassName("icerik");

    icerik[0].appendChild(parca);

    parca.addEventListener("click",function(){

      window.location.href="Parca.html?t="+parcaitem.trackId;

      //window.location.replace("Parca.html?t="+parcaitem.trackId);
    })
}