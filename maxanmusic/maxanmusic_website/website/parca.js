let calanparca = document.createElement('audio');
var pkf = document.getElementById("pkf");
var sad = document.getElementById("sanatci");
var pad = document.getElementById("ad");
var aciklama = document.getElementById("aciklama");
var begenisayi = document.getElementById("pb");
//var yorumsayi = document.getElementById("pi2"); parçalara yorum sayısı eklenecek
var parcabar = document.getElementById("oynatbar");
var calBtn = document.getElementById("playpause");
var calBtnSymbl = document.getElementById("plypssymbol");
var mevcutsuretext = document.getElementById("mevcutsure");
var maxsuretext = document.getElementById("maxsure");

var yorumBtn = document.getElementById("yorum_gonder");
var begenBtn = document.getElementById("begenBtn");

//let parca;
let oynuyormu = false;
let mevcutsure=0;

let yorumsayfa=0;
let yorumsayfanull=0;

let begenildimi = false;

window.onload = function () {

    nav_kullaniciKontrolEt();
    kullaniciKontrol();

    parcabilgiyukle();

    // yavaş bağlantıda aynı eventi tekrar tekrar dinleyip fonksiyonu tekrarlayabiliyor
}

function parcabilgiyukle(){

    let parca = syfparam();

    if(parca==null){
        throw alert("Hata."); 
    }

    fetch("http://localhost:8083/tracks/"+parca,{
            method:"GET",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {
            if(response.status==404){
                throw alert("Parça bulunamadı");
            }
            if(!response.ok){
                throw alert("Hata, parça yüklenemedi.");  
            }
                
                return response.json(); 

            }).then((data) => {
                parcayukle(data);
                yorumyukle();
                window.addEventListener("scroll", function(){kaydiryorumyukle()});
            });
}


function oynatdurdur() {
    if (!oynuyormu){
        calanparca.play();
        oynuyormu = true;
        calBtnSymbl.className = "fa fa-pause";
    }
    else {
        calanparca.pause();
        oynuyormu = false;
        calBtnSymbl.className = "fa fa-play";
    }
}

function syfparam(){
    const adres = document.location.search;
    const parametreler = new URLSearchParams(adres);
    const parca = parametreler.get("t");
    return parca;
}

function parcayukle(data){
    pkf.src = "https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/tcvr/" + data.trackId+".png?time=" + (new Date()).getTime();
    calanparca.src = "https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/trcks/" + data.trackId+".mp3";
    sad.textContent=data.artistUsername;
    pad.textContent=data.trackName
    aciklama.textContent=data.trackDesc;
    begenisayi.textContent=data.likeCount  + " beğeni";

    sad.addEventListener("click",function(){
        let ad =data.artistUsername;
        window.location.href ="Profil.html?k="+ad;
        //window.location.replace("Profil.html?kullaniciAdi="+sad.textContent);
    })

    calanparca.onloadedmetadata = function() {
        let uzunluk = Math.floor(calanparca.duration);
        parcabar.max = Math.floor(uzunluk);
        let uzunluk_d = Math.floor(uzunluk / 60);
        let uzunluk_s = uzunluk % 60;
        if(uzunluk_s<10){uzunluk_s = "0" + uzunluk_s;}

        maxsuretext.innerText = uzunluk_d + ":" + uzunluk_s;
        mevcutsuretext.innerText = "0:00";
    };

    calanparca.addEventListener("timeupdate", function(){
        if(Math.floor(calanparca.duration)!=mevcutsure){
            barilerlet();
        }
   });

    calanparca.addEventListener("ended", function(){
        calanparca.currentTime = 0;
        parcabar.value = 0;
        mevcutsure=0;
        oynuyormu = false;
        calBtnSymbl.className = "fa fa-play"
   });

    calBtn.addEventListener("click", function (){oynatdurdur()});
    parcabar.addEventListener("change", function (){barparcaguncelle()});
    parcabar.addEventListener("input", function (){barsureguncelle()});

}

function barsureguncelle(){
    if (oynuyormu){
        calanparca.pause();
    }
    let mevcutzaman = parcabar.value;
    let uzunluk_d = Math.floor(mevcutzaman / 60);
    let uzunluk_s = Math.floor(mevcutzaman % 60);
    if(uzunluk_s<10){uzunluk_s = "0" + uzunluk_s;}

    mevcutsuretext.innerText = uzunluk_d + ":" + uzunluk_s;
}

function barparcaguncelle(){
    if (oynuyormu){
        calanparca.play();
    }
    let mevcutzaman = parcabar.value;
    calanparca.currentTime = mevcutzaman;

}

function barilerlet(){


    let mevcutzaman = Math.floor(calanparca.currentTime)+1;
    mevcutsure = mevcutzaman;
    parcabar.value = mevcutzaman;

    let uzunluk_d = Math.floor((mevcutzaman-1) / 60);
    let uzunluk_s = (mevcutzaman-1) % 60;
    if(uzunluk_s<10){uzunluk_s = "0" + uzunluk_s;}

    mevcutsuretext.innerText = uzunluk_d + ":" + uzunluk_s;

}

function yorumYap(){
    yorumBtn.disabled = true;
    
    let yorumtext = document.getElementById("yeniyorumtext").value;

    let parca = syfparam();
    
    if (yorumtext.lenght < 3){
        alert("Yorumunuz en az 3 karakter içermelidir");
        yorumBtn.disabled = false;
        return;
    }

    fetch("http://localhost:8083/comments",{
            method:"POST",
            credentials: "include",
            body: JSON.stringify({
                comment: yorumtext,
                commentedTrack: parca
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then((response) => {   
            
            if(!response.ok){
                throw alert("Hata, yorumunuz kaydedilemedi.");  
            }
            return response.json(); 
            }).then((data) => {
                yorumEkle(data,1);
                console.log(data);
            });

    yorumBtn.disabled = false;
}

function yorumEkle(yorumdata,basa){

        let yorumKutu = document.createElement("div");
        yorumKutu.className = "yorumKutu";

        let profFotoDis = document.createElement("div");
        profFotoDis.className = "profFotoDis";
        yorumKutu.appendChild(profFotoDis);
    
        let profFotoCerceve = document.createElement("div");
        profFotoCerceve.className = "profFotoCerceve";
        profFotoDis.appendChild(profFotoCerceve);
    
        let pp = document.createElement("img");
        pp.id = "pp";
        pp.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/pf/" + yorumdata.commentorAvatar+".png?time=" + (new Date()).getTime();
        profFotoCerceve.appendChild(pp);
    
        let yorumDis = document.createElement("div");
        yorumDis.className = "yorumDis";
        yorumKutu.appendChild(yorumDis);
    
        let k_Ad = document.createElement("div");
        k_Ad.className = "k_Ad";
        yorumDis.appendChild(k_Ad);
    
        let kullaniciAdi = document.createElement("b");
        kullaniciAdi.id="kullaniciAdi";
        kullaniciAdi.innerText = yorumdata.commentorUsername;
        k_Ad.appendChild(kullaniciAdi);
    
        let k_Yorum = document.createElement("div");
        k_Yorum.className = "k_Yorum";
        yorumDis.appendChild(k_Yorum);

        let yorum = document.createElement("p");
        yorum.id="yorum";
        yorum.innerText = yorumdata.comment;
        k_Yorum.appendChild(yorum);
    
        let yorumlarKutu = document.getElementsByClassName("yorumlar");
    
        if(basa==1){
            yorumlarKutu[0].prepend(yorumKutu);
        }
        else{yorumlarKutu[0].appendChild(yorumKutu);}
    
        kullaniciAdi.addEventListener("click",function(){
            let yorumad = yorumdata.commentorUsername
            window.location.href="Profil.html?k="+yorumad;
            //window.location.replace("Profil.html?kullaniciAdi="+kullaniciAdi.value);
        })
}

function yorumyukle(){
    let parca = syfparam();

    fetch("http://localhost:8083/tracks/"+parca+"/comments?pageNo="+yorumsayfa,{
              method:"GET",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {

              if(response.status==404){
                yorumsayfanull=1;
                throw false;
              }

              if(!response.ok){
                return false; 
              }

              yorumsayfa++;
              return response.json(); 

              }).then((data) => {
                data.forEach(function (dataitem) { yorumEkle(dataitem,0); });
                return true;
              });
}

function kaydiryorumyukle(){
    if(window.scrollY + window.innerHeight >= document.documentElement.scrollHeight){
      if(yorumsayfanull!= 1){
        yorumyukle();
        }
    }
}

function kullaniciKontrol(){
    if(mevcutKullanici()!="")
    {
        let parca = syfparam();

        fetch("http://localhost:8083/likes?trackId="+parca,{
              method:"GET",
              credentials: "include",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {

              if(response.status==404){
                return;
              }

              if(!response.ok){
                return false; 
              }

              begenildimi = true;
              begenBtn.style.backgroundColor = "#ff9999";
              begenBtn.innerText = "Beğeniyi Kaldır";

              });

        begenBtn.addEventListener("click",function (){begenBtnevent()});
        yorumBtn.addEventListener("click", function(){yorumYap()});

    }else {
        document.getElementsByClassName("yorumyaz")[0].style.display = "none";
        begenBtn.style.display = "none";
    }
}

function begenBtnevent(){

    begenBtn.disabled = true;

    let parca = syfparam();

    if(begenildimi){
        fetch("http://localhost:8083/likes?trackId="+parca,{
              method:"DELETE",
              credentials: "include",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {

              if(!response.ok){
                return false; 
              }

              begenildimi = false;
              begenBtn.style.backgroundColor = "#dddddd";
              begenBtn.innerText = "Beğen";

              });
    }
    else{
        fetch("http://localhost:8083/likes",{
              method:"POST",
              credentials: "include",
              body:parca,
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {

              if(!response.ok){
                return false; 
              }

              begenildimi = true;
              begenBtn.style.backgroundColor = "#ff9999";
              begenBtn.innerText = "Beğeniyi Kaldır";

              });
    }

    begenBtn.disabled = false;
}