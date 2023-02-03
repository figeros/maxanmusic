window.onload = function () {
    var btn = document.getElementById("takipEt");
    btn.addEventListener("click", function (){takipEt(btn)});

    profilYukle();

    /*const getCookieValue = (name) => (
        document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)')?.pop() || ''
    )
    
    let ck = getCookieValue("maxanmusicuser");*/
    
}

function profilYukle(){

    const adres = document.location.search;
    const parametreler = new URLSearchParams(adres);
    const kullaniciAdi = parametreler.get("kullaniciAdi");

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

                var pphoto = document.getElementById("pp");
                var ad = document.getElementById("kullaniciAdi");
                var aciklama = document.getElementById("hakkinda");
                var takipciSayi = document.getElementById("takipciSayisi");
                var takipEdilenSayi = document.getElementById("takipEdilen");

                pphoto.src="https://maxanmusicbucket.s3.eu-central-1.amazonaws.com/imgs/pf/" + data.avatar+".png";
                ad.textContent=data.username;
                aciklama.textContent=data.aboutuser;
                takipciSayi.textContent=data.followercount;
                takipEdilenSayi.textContent=data.followedcount;
            });

// yeni bir istek ile de kullanıcının oluşturduğu müzik parçalarını listele


// eğer kullanıcı zaten takip ediliyorsa takip butonunun adını değiştir
}

function takipEt(){}
//daha önemli özelliklere öncelik verileceğinden daha sonra yazılacak