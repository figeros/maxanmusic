window.onload = function () {

    if(mevcutKullanici()!=""){
        let drpdwn = document.getElementsByClassName("dropdown");
        drpdwn[0].style.display = "block";
        let giris = document.getElementsByClassName("giris");
        giris[0].style.display = "hidden";
    }
    
    var btn = document.getElementById("cikis");
    btn.addEventListener("click", function (){cikisYap()});
    
}


    function mevcutKullanici(){
        let user = document.cookie.match('(^|;)\\s*' + "maxanmusicuser" + '\\s*=\\s*([^;]+)')?.pop() || ''
        return user;
    }

    function cikisYap(){
        document.cookie = "maxanmusicuser=J";
        window.location.replace("Index.html");
    }