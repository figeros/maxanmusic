var ig_yeniparca = document.getElementById("ig_yeniparca");
var ig_parcalar = document.getElementById("ig_parcalar");
var ib_yeniparca = document.getElementById("ib_yeniparca");
var ib_parcalar = document.getElementById("ib_parcalar");

var uploadbtn = document.getElementById("button_upload");

var parcasayfanum = 0;
var parcasayfanull = false;

var mevcutIslem = 0;

var ekranDuzenle = document.getElementsByClassName("ekranDuzenle");
var ed_cfile = document.getElementById("ed_cfile");
var ed_tname = document.getElementById("ed_tname");
var ed_desc = document.getElementById("ed_desc")
var ed_p = document.getElementById("ed_p");
var ed_n = document.getElementById("ed_n");

var dznlnck_prc;

var ekranSil = document.getElementsByClassName("ekranSil");
var es_p = document.getElementById("es_p");
var es_n = document.getElementById("es_n");

var silnck_prc;

window.onload = function () {

    ig_parcalar.style.display = "none";

    nav_kullaniciKontrolEt();

    if(mevcutKullanici()==""){
        alert("Hata.");
        window.location.replace("Index.html");
    }
    
    parcalariyukle();
    
    uploadbtn.addEventListener("click", function (){parcaKaydet()});

    ib_yeniparca.addEventListener("click", function(){sonucgoster(0)});
    ib_parcalar.addEventListener("click", function(){sonucgoster(1)});


    ed_p.addEventListener("click", function (){parcaDuzenle(dznlnck_prc)});
    es_p.addEventListener("click", function (){parcaSil(silnck_prc)});
    ed_n.addEventListener("click", function (){ekranDuzenle[0].style.display = "none";});
    es_n.addEventListener("click", function (){ekranSil[0].style.display = "none";});
    //ed_cfile.addEventListener('change', function(){ed_cimg.src=ed_cfile.value});

}

function parcaKaydet(){

    uploadbtn.disabled = true;

    let trackName = document.getElementById("tname").value;
    let musicFiles = document.getElementById("mfile");
    let trackDesc = document.getElementById("desc").value;
    let coverFiles = document.getElementById("cfile");


    if (trackName == ""||musicFiles.files.length == 0||trackDesc == ""||coverFiles.files.length == 0){
        alert("Parça için isim girmeyi ve dosya seçimini unutmayınız.");
        uploadbtn.disabled = false;
        return;
    }

    let musicFile = musicFiles.files[0];

    let coverFile = coverFiles.files[0];


    let formData = new FormData();

    formData.append('trackName', trackName);
    formData.append('trackAudio', musicFile);
    formData.append('trackDesc', trackDesc);
    formData.append('cover', coverFile);


    fetch("http://localhost:8083/tracks/",{
            method:"POST",
            credentials: "include",
            body: formData
        }).then((response) => {
          uploadbtn.disabled = false;
          if(!response.ok){
            throw false; 
          }
          location.reload(true);
    });
}

function sonucgoster(sayfa){
  
  if(sayfa==mevcutIslem){
    return;
  }
      
  if(sayfa==0){   
    mevcutIslem=0;  
    ig_yeniparca.style.display = "block";     
    ig_parcalar.style.display = "none";        
    ib_yeniparca.style.backgroundColor = "#cccccc";
    ib_parcalar.style.backgroundColor = "#aaaaaa";
  }else{
    mevcutIslem=1;            
    ig_yeniparca.style.display = "none";           
    ig_parcalar.style.display = "block";            
    ib_yeniparca.style.backgroundColor = "#aaaaaa";          
    ib_parcalar.style.backgroundColor = "#cccccc";    
  }  
}


function parcalariyukle(){
  if(!parcasayfanull){
    var kullanici = mevcutKullanici();
     
    fetch("http://localhost:8083/users/"+kullanici+"/tracks?pageNo="+parcasayfanum,{             
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
      parcasayfanum++              
    });       
  } 
}

    function parcaekle(parcaitem){

        let parca = document.createElement("div");
        parca.className = "parca";
        
        let pbolme1 = document.createElement("div");
        pbolme1.className = "pbolme1";
        parca.appendChild(pbolme1);
    
        let ibilgibolme = document.createElement("div");
        ibilgibolme.className = "ibilgibolme";
        pbolme1.appendChild(ibilgibolme);
    
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
        pbolme1.appendChild(ifotobolme);
    
        let ifotocerceve = document.createElement("div");
        ifotocerceve.className = "ifotocerceve";
        ifotobolme.appendChild(ifotocerceve);
    
        let pkf = document.createElement("img");
        pkf.id = "pkf";
        pkf.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/tcvr/" + parcaitem.trackId+".png?time=" + (new Date()).getTime();
        ifotocerceve.appendChild(pkf);

        let pbolme2 = document.createElement("div");
        pbolme2.className = "pbolme2";
        parca.appendChild(pbolme2);

        let dznleBtn = document.createElement("button");
        dznleBtn.className = "dznleBtn";
        dznleBtn.textContent = "Düzenle";
        pbolme2.appendChild(dznleBtn);

        let silBtn = document.createElement("button");
        silBtn.className = "silBtn";
        silBtn.textContent = "Sil";
        pbolme2.appendChild(silBtn);
    
        ig_parcalar.appendChild(parca);

        dznleBtn.addEventListener("click", function (){parcaDuzenleEkran(parcaitem)});
        silBtn.addEventListener("click", function (){parcaSilEkran(parcaitem.trackId)});
    }

function parcaDuzenleEkran(parcaitem){
  dznlnck_prc = parcaitem;
  ed_tname.value = parcaitem.trackName;
  ed_desc.value = parcaitem.trackDesc;
  ekranDuzenle[0].style.display = "block";
}

function parcaSilEkran(id){
  silnck_prc = id;
  ekranSil[0].style.display = "block";
}

function parcaDuzenle(parcaitem){

  ed_p.disabled = true;

  if (ed_tname.value == ""){
    alert("Parça için isim girmeyi unutmayınız.");
    ed_p.disabled = false;
    return;
  }

  if(ed_tname.value == parcaitem.trackName && ed_desc == parcaitem.trackDesc && ed_cfile.files.length==0){
    alert("Parça için bir değişiklik yapmadınız.");
    ed_p.disabled = false;
    return;
  }

  let formData = new FormData();

  formData.append('trackName', ed_tname.value);
  formData.append('trackDesc', ed_desc.value);

  let coverFile;

  if(ed_cfile.files.length!=0){
    coverFile = ed_cfile.files[0];
    formData.append('cover', coverFile);
  } 

  fetch("http://localhost:8083/tracks/"+parcaitem.trackId,{
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

function parcaSil(id){

  es_p.disabled = false; 

  fetch("http://localhost:8083/tracks/"+id,{
          method:"DELETE",
          credentials: "include",
  }).then((response) => {
        es_p.disabled = false;
        if(!response.ok){
          throw false; 
        }
        location.reload(true);
  });
}
    