var pphoto = document.getElementById("pp");
var ad = document.getElementById("kullaniciAdi");
var aciklama = document.getElementById("hakkinda");
var takipciSayi = document.getElementById("takipciSayisi");
var takipEdilenSayi = document.getElementById("takipEdilen");

var tkpbtn = document.getElementById("takipEt");
var dznlbtn = document.getElementById("PDuzenle");
var takipEdiliyorMu = false;

var parcasayfa=0;
var parcasayfanull=false;

var ekranDuzenle = document.getElementsByClassName("ekranDuzenle");

var ed_ppfile = document.getElementById("ed_pffile");
var ed_udesc = document.getElementById("ed_udesc")
var ed_p = document.getElementById("ed_p");
var ed_n = document.getElementById("ed_n");

window.onload = function () {

    nav_kullaniciKontrolEt();

    parcaAramaSayfa={
      num: 1
    };
    profilYukle();
    kullaniciKontrol()
    
}

function profilYukle(){

    let kullaniciAdi = sayfakullanicisi();

    if(kullaniciAdi==null){
        throw alert("Hata."); 
    }

    fetch("http://localhost:8083/users/"+kullaniciAdi,{
            method:"GET",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {
            if(!response.ok){
                throw alert("Hata, kullanıcı profili yüklenemedi.");  
            }
                return response.json();      
            }).then((data) => {

                
                pphoto.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/pf/" + data.avatar+".png?time=" + (new Date()).getTime();
                ad.textContent=data.username;
                aciklama.textContent=data.aboutuser;
                takipciSayi.textContent=data.followercount;
                takipEdilenSayi.textContent=data.followedcount;
            });
    parcalariyukle(kullaniciAdi);
    window.addEventListener("scroll", function(){sayfakaydir()});

// eğer kullanıcı zaten takip ediliyorsa takip butonunun adını değiştir
}

function parcalariyukle(kullanici){

  if(!parcasayfanull){
    fetch("http://localhost:8083/users/"+kullanici+"/tracks?pageNo="+parcasayfa,{
            method:"GET",
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
        });
  parcasayfa++;
  }
}

function parcaekle(parcabilgiitem){

    let parca = document.createElement("div");
    parca.className = "parca";

    let pbilgiler = document.createElement("div");
    pbilgiler.className = "pbilgiler";
    parca.appendChild(pbilgiler);

    let pbilgi_ad = document.createElement("div");
    pbilgi_ad.className = "pbilgi_ad";
    pbilgiler.appendChild(pbilgi_ad);

    let parcaadi = document.createElement("h3");
    parcaadi.className="parcaadi";
    parcaadi.innerText = parcabilgiitem.trackName;
    pbilgi_ad.appendChild(parcaadi);

    let pbilgi_istatistik = document.createElement("div");
    pbilgi_istatistik.className = "pbilgi_istatistik";
    pbilgiler.appendChild(pbilgi_istatistik);

    let pbilgi_begeni = document.createElement("div");
    pbilgi_begeni.className = "pbilgi_begeni";
    pbilgi_istatistik.appendChild(pbilgi_begeni);

    let begeni = document.createElement("p");
    begeni.innerText = parcabilgiitem.likeCount + " beğeni";
    pbilgi_begeni.appendChild(begeni);

    let pbilgi_yorum = document.createElement("div");
    pbilgi_yorum.className = "pbilgi_yorum";
    pbilgi_istatistik.appendChild(pbilgi_yorum);

    let yorum = document.createElement("p");
    yorum.innerText = "";
    pbilgi_yorum.appendChild(yorum);

    let parcakapakfoto = document.createElement("div");
    parcakapakfoto.className = "parcakapakfoto";
    parca.appendChild(parcakapakfoto);

    let pkfcerceve = document.createElement("div");
    pkfcerceve.className = "pkfcerceve";
    parcakapakfoto.appendChild(pkfcerceve);

    let pkf = document.createElement("img");
    pkf.id = "pkf";
    pkf.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/tcvr/" + parcabilgiitem.trackId+".png?time=" + (new Date()).getTime();
    pkfcerceve.appendChild(pkf);

    let icerik = document.getElementsByClassName("icerik");

    icerik[0].appendChild(parca);

    parca.addEventListener("click",function(){
      window.location.replace("Parca.html?t="+parcabilgiitem.trackId);
    })
}

function sayfakullanicisi(){
    const adres = document.location.search;
    const parametreler = new URLSearchParams(adres);
    const kullaniciAdi = parametreler.get("k");
    return kullaniciAdi;
}

function sayfakaydir(){
  if(window.scrollY + window.innerHeight >= document.documentElement.scrollHeight){
    parcalariyukle(sayfakullanicisi())
  }
}

function kullaniciKontrol(){

  let tkpkullanici = sayfakullanicisi();

  if(mevcutKullanici()==""){
    tkpbtn.style.display = "none";
    return;
  }

  if(mevcutKullanici()==sayfakullanicisi()){
    tkpbtn.style.display = "none";
    dznlbtn.style.display = "block";
    ed_p.addEventListener("click", function (){profilDuzenle()});
    ed_n.addEventListener("click", function (){ekranDuzenle[0].style.display = "none";});
    dznlbtn.addEventListener("click",function (){duzenleBtnevent()})
    }else{
        fetch("http://localhost:8083/follows?followedUser="+tkpkullanici,{
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
            return; 
          }
          takipEdiliyorMu = true;
          tkpbtn.style.backgroundColor = "#ff9999";
          tkpbtn.innerText = "Takip Etme";
        });
        tkpbtn.addEventListener("click",function (){takipBtnevent()});
      }
}

function takipBtnevent(){

  tkpbtn.disabled = true;

  let tkpkullanici = sayfakullanicisi();

  if(takipEdiliyorMu){
      fetch("http://localhost:8083/follows?followedUser="+tkpkullanici,{
            method:"DELETE",
            credentials: "include",
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {

            if(!response.ok){
              return false; 
            }
            takipEdiliyorMu = false;
            tkpbtn.style.backgroundColor = "#dddddd";
            tkpbtn.innerText = "Takip Et";

            });
  }
  else{
      fetch("http://localhost:8083/follows",{
            method:"POST",
            credentials: "include",
            body:tkpkullanici,
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {

            if(!response.ok){
              return false; 
            }

            takipEdiliyorMu = true;
            tkpbtn.style.backgroundColor = "#ff9999";
            tkpbtn.innerText = "Takip Etme";

            });
  }

  tkpbtn.disabled = false;
}

function duzenleBtnevent(){
  ekranDuzenle[0].style.display = "block";
}

function profilDuzenle(){

  ed_p.disabled = true;

  if(ed_udesc == aciklama.textContent && ed_ppfile.files.length==0){
    alert("Profilinizde bir değişiklik yapmadınız.");
    ed_p.disabled = false;
    return;
  }

  let formData = new FormData();

  formData.append('aboutuser', ed_udesc.value);

  let PpFile;

  if(ed_ppfile.files.length!=0){
    PpFile = ed_ppfile.files[0];
    formData.append('ppfile', PpFile);
  } 

  let mk = mevcutKullanici(); 

  fetch("http://localhost:8083/users/"+mk,{
          method:"PATCH",
          credentials: "include",
          body: formData
      }).then((response) => {
        ed_p.disabled = false;
        if(!response.ok){
          throw false; 
        }
        location.reload(true);
        });

      
}
