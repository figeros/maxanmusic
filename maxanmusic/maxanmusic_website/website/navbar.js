/*window.onload = function () {

    if(mevcutKullanici()!=""){
        let drpdwn = document.getElementsByClassName("dropdown");
        drpdwn[0].style.display = "block";
        let giris = document.getElementsByClassName("giris");
        giris[0].style.display = "hidden";
    }
    
    var btn = document.getElementById("cikis");
    btn.addEventListener("click", function (){cikisYap()});
    
}*/

    function nav_kullaniciKontrolEt(){

        if(mevcutKullanici()!=""){
            let drpdwn = document.getElementsByClassName("dropdown");
            drpdwn[0].style.display = "block";
            let anasayfa = document.getElementById("anasayfa");
            anasayfa.style.visibility = "visible";
            let giris = document.getElementById("giris");
            giris.style.display = "none";
        }
        
        var indxbtn = document.getElementById("index");
        indxbtn.addEventListener("click", function (){
            window.location.href="Index.html";
            //window.location.replace("Index.html");
        });


        var ansfbtn = document.getElementById("anasayfa");
        ansfbtn.addEventListener("click", function (){
            window.location.href="AnaSayfa.html";
            //window.location.replace("AnaSayfa.html");
        });


        var prfbtn = document.getElementById("profilim");
        prfbtn.addEventListener("click", function (){
            let k =mevcutKullanici();
            window.location.href="Profil.html?k="+k;
            //window.location.replace("Profil.html?kullaniciAdi=" +mevcutKullanici());
        });

        var cksbtn = document.getElementById("cikis");
        cksbtn.addEventListener("click", function (){cikisYap()});

        var arabtn = document.getElementById("araBtn");
        arabtn.addEventListener("click",function(){navAra()})
        
        var studyo = document.getElementById("studyo");
        studyo.addEventListener("click",function(){
            window.location.href="Studyo.html";
            //window.location.replace("ParcaEkle.html");
        })

        var bgnprclr = document.getElementById("bgnprclr");
        bgnprclr.addEventListener("click",function(){
            window.location.href="BegendigimParcalar.html";
            //window.location.replace("BegendigimParcalar.html");
        })
    }


    function mevcutKullanici(){
        let user = document.cookie.match('(^|;)\\s*' + "maxanmusicuser" + '\\s*=\\s*([^;]+)')?.pop() || ''
        return user;
    }

    function cikisYap(){
        document.cookie = "maxanmusicuser=";
        window.location.replace("Index.html");
    }

    function navAra(){
        var kelime = document.getElementById("arainput").value;
        if(kelime.length>2){
            var kelime_enc = encodeURIComponent(kelime);
            window.location.href="Arama.html?a=" + kelime_enc;
        }else alert("Aranacak kelime en az 3 harfli olmalıdır")
    }