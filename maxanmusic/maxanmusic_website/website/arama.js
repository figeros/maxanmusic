window.onload = function () {

    nav_kullaniciKontrolEt();
    
    document.getElementById("sonuc_kullanici").style.display = "none";

    aramaSayfa={
      parca: 0,
      kullanici: 0,
      mevcutsayfa: 0,
      pfull: 0, //aramaya ait daha fazla sonuç yoksa pfull ve kfull 1 olacak ve 
      kfull: 0  //fonksiyonun sunucuya yeni içerik için istek göndermesini engelleyecek
  };

    aramayukle();

    var barbtn = document.getElementById("barBtn");
    barbtn.addEventListener("click",function(){ara()})

    let btnp = document.getElementById("kbtnp");
    btnp.addEventListener("click", function(){sonucgoster(0,aramaSayfa)});
    let btnk = document.getElementById("kbtnk");
    btnk.addEventListener("click", function(){sonucgoster(1,aramaSayfa)});

    window.addEventListener("scroll", function(){icerikyukle(aramaSayfa)});


}

function arananKelime(){
    const adres = document.location.search;
    const parametreler = new URLSearchParams(adres);
    const kelime = parametreler.get("a");
    
    return decodeURIComponent(kelime);
}

function aramayukle(){

    let bar = document.getElementById("bar");
    bar.value=arananKelime();

    parcayukle(arananKelime(),aramaSayfa);
    kullaniciyukle(arananKelime(),aramaSayfa);

}

function parcayukle(kelime,sayfanum){
    fetch("http://localhost:8083/tracks?keyword="+kelime+"&pageNo="+sayfanum.parca,{
              method:"GET",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {

              if(response.status==404){
                sayfanum.pfull=1;
                throw false;
              }

              if(!response.ok){
                return false; 
              }

              sayfanum.parca++;
              return response.json(); 

              }).then((data) => {
                data.forEach(parcaekle);
                return true;
              });
  }

  function kullaniciyukle(kelime,sayfanum){

    fetch("http://localhost:8083/users?keyword="+kelime+"&pageNo="+sayfanum.kullanici,{
              method:"GET",
              headers: {
                  "Content-type": "application/json; charset=UTF-8"
              }
  
          }).then((response) => {
              if(response.status==404){
                sayfanum.kfull = 1;
                throw false;
                window.removeEventListener("scroll", icerikyukle);
              }

              if(!response.ok){
                throw false; 
              }
              sayfanum.kullanici++;
              return response.json(); 
  
              }).then((data) => {
                data.forEach(kullaniciekle);

                return true;
              });
  }

  function parcaekle(parcaitem){

    
    let icerik = document.createElement("div");
    icerik.className = "icerik";

    let ibilgibolme = document.createElement("div");
    ibilgibolme.className = "ibilgibolme";
    icerik.appendChild(ibilgibolme);

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
    icerik.appendChild(ifotobolme);

    let ifotocerceve = document.createElement("div");
    ifotocerceve.className = "ifotocerceve";
    ifotobolme.appendChild(ifotocerceve);

    let pkf = document.createElement("img");
    pkf.id = "pkf";
    pkf.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/tcvr/" + parcaitem.trackId+".png?time=" + (new Date()).getTime();
    ifotocerceve.appendChild(pkf);

    let sonuc_parca = document.getElementById("sonuc_parca");

    sonuc_parca.appendChild(icerik);

    icerik.addEventListener("click",function(){

      window.location.href="Parca.html?t="+parcaitem.trackId;

      //window.location.replace("Parca.html?t="+parcaitem.trackId);
    })
}

function kullaniciekle(kullaniciitem){

    let icerik = document.createElement("div");
    icerik.className = "icerik";

    let ifotobolme = document.createElement("div");
    ifotobolme.className = "ifotobolme";
    icerik.appendChild(ifotobolme);

    let ifotocerceve = document.createElement("div");
    ifotocerceve.className = "ifotocerceve";
    ifotobolme.appendChild(ifotocerceve);

    let kpf = document.createElement("img");
    kpf.id = "kpf";
    kpf.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/pf/" + kullaniciitem.avatar+".png?time=" + (new Date()).getTime();
    ifotocerceve.appendChild(kpf);

    let ibilgibolme = document.createElement("div");
    ibilgibolme.className = "ibilgibolme";
    icerik.appendChild(ibilgibolme);

    let ibilgi_ana = document.createElement("div");
    ibilgi_ana.className = "ibilgi_ana";
    ibilgibolme.appendChild(ibilgi_ana);

    let kullaniciadi = document.createElement("h3");
    kullaniciadi.className="kullaniciadi";
    kullaniciadi.innerText = kullaniciitem.username;
    ibilgi_ana.appendChild(kullaniciadi);

    let kullaniciaciklama = document.createElement("p");
    kullaniciaciklama.className="kullaniciadi";
    kullaniciaciklama.innerText = kullaniciitem.aboutuser;
    ibilgi_ana.appendChild(kullaniciaciklama);

    let ibilgi_alt = document.createElement("div");
    ibilgi_alt.className = "ibilgi_alt";
    ibilgibolme.appendChild(ibilgi_alt);

    let ibilgi_alt1 = document.createElement("div");
    ibilgi_alt1.className = "ibilgi_alt1";
    ibilgi_alt.appendChild(ibilgi_alt1);

    let begeni = document.createElement("p");
    begeni.innerText = kullaniciitem.followercount + " Takipçi";
    ibilgi_alt1.appendChild(begeni);

    let ibilgi_alt2 = document.createElement("div");
    ibilgi_alt2.className = "ibilgi_alt2";
    ibilgi_alt.appendChild(ibilgi_alt2);

    let yorum = document.createElement("p");
    yorum.innerText = kullaniciitem.followedcount + " Takip Edilen";
    ibilgi_alt2.appendChild(yorum);

    let sonuc_kullanici = document.getElementById("sonuc_kullanici");

    sonuc_kullanici.appendChild(icerik);

    icerik.addEventListener("click",function(){
      
      window.location.href="Profil.html?k="+kullaniciitem.username;
      
      //window.location.replace("Profil.html?kullaniciAdi="+kullaniciitem.username);
    })
}

function sonucgoster(sayfa,sayfabilgi){
  
  if(sayfa==sayfabilgi.mevcutsayfa){
    return;
  }

  let sonucp = document.getElementById("sonuc_parca");
  let sonuck = document.getElementById("sonuc_kullanici");
  let btnp = document.getElementById("kbtnp");
  let btnk = document.getElementById("kbtnk");

  if(sayfa==0){
    sayfabilgi.mevcutsayfa=0;
    sonucp.style.display = "block";
    sonuck.style.display = "none";
    btnp.style.backgroundColor = "#cccccc";
    btnk.style.backgroundColor = "#aaaaaa";
  }else{
    sayfabilgi.mevcutsayfa=1;
    sonucp.style.display = "none";
    sonuck.style.display = "block";
    btnp.style.backgroundColor = "#aaaaaa";
    btnk.style.backgroundColor = "#cccccc";
  }
}

function icerikyukle(sayfabilgi){
  if(window.scrollY + window.innerHeight >= document.documentElement.scrollHeight){
    if(sayfabilgi.mevcutsayfa==0 && sayfabilgi.pfull!= 1){
      parcayukle(arananKelime(),sayfabilgi);
    }else if(sayfabilgi.mevcutsayfa==1 && sayfabilgi.kfull!=1){
      kullaniciyukle(arananKelime(),sayfabilgi);
    }
  }
}

function ara(){
  var kelime = document.getElementById("bar").value;
  if(kelime.lenght>2){
    var kelime_enc = encodeURIComponent(kelime);
    window.location.href="Arama.html?a=" + kelime_enc;
  }else alert("Aranacak kelime en az 3 harfli olmalıdır")
}