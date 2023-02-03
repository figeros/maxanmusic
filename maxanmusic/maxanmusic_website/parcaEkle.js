window.onload = function () {

    if(mevcutKullanici()==""){
        alert("Hata.");
        window.location.replace("Index.html");
    }
    
    var btn = document.getElementById("button_upload");
    btn.addEventListener("click", function (){parcaKaydet(btn)});

    /*const getCookieValue = (name) => (
        document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)')?.pop() || ''
    )
    
    let ck = getCookieValue("maxanmusicuser");*/
    
}

function parcaKaydet(btn){


    

    btn.disabled = true;

    let trackName = document.getElementById("tname").value;
    let musicFiles = document.getElementById("mfile");
    let trackDesc = document.getElementById("desc").value;
    let coverFiles = document.getElementById("cfile");


    if (trackName == ""||musicFiles.files.length == 0||trackDesc == ""||coverFiles.files.length == 0){
        alert("Parça için isim girmeyi ve dosya seçimini unutmayınız.");
        btn.disabled = false;
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
        });

    btn.disabled = false;

    }

    function mevcutKullanici(){
        let user = document.cookie.match('(^|;)\\s*' + "maxanmusicuser" + '\\s*=\\s*([^;]+)')?.pop() || ''
        return user;
    }