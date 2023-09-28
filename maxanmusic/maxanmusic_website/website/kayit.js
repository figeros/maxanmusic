window.onload = function () {
    var btn = document.getElementById("button_submit");
    btn.addEventListener("click", function (){kayit(btn)});
}

function kayit(btn){

    btn.disabled = true;

    let uname = document.getElementById("uname").value;
    let uemail = document.getElementById("email").value;
    let upw = document.getElementById("psw").value;
    let pwr = document.getElementById("psw_r").value;

    if (uname == ""||uemail == ""||upw == ""||pwr == ""){
        alert("Boş kısımları doldurunuz.");
        btn.disabled = false;
        return;
    }

    if(boslukVarMi(uname)||boslukVarMi(upw)){
        alert("Kullanıcı Adı ve Şifrede boşluk bulunamaz.");
        btn.disabled = false;
        return;
    }

    if(upw==pwr){
        
        fetch("http://localhost:8083/users",{
            method:"POST",
            body: JSON.stringify({
                username: uname,
                email: uemail,
                password: upw
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {
            console.log(response.json);
            if(!response.ok){
                btn.disabled = false;
                throw alert("Bu kullanıcı adı zaten kullanılıyor.");  
            }
                alert("kullanıcı kaydı oluşturuldu");
                btn.disabled = false;
                return response.json();      
            }).then((data) => {
            //alert(data.username);
            //alert(data.password);
            btn.disabled = false;
            Giris(data);
        });
        /*.then(response => response.json()).then(json => console.log(json));*/
        
    }
    else{
        alert("Girilen şifreler birbiri ile aynı değil.")
        btn.disabled = false;
        return;
    }
}

function Giris(data){

    let uname = data.username;
    let upw = data.password;

    fetch("http://localhost:8083/users/login",{
            method:"POST",
            body: JSON.stringify({
                username: uname,
                password: upw
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }

        }).then((response) => {
            console.log(response.json);
            if(!response.ok){
                btn.disabled = false;
                throw alert("Giriş başarısız oldu.");  
            }
            setCookie(uname);
            window.location.replace("AnaSayfa.html");

        });

}

function setCookie(cvalue) {
    const d = new Date();
    d.setTime(d.getTime() + (30*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = "maxanmusicuser=" + cvalue + ";" + expires + ";path=/";
}

function boslukVarMi(s) {
    return /\s/.test(s);
}