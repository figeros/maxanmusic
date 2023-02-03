window.onload = function () {
    var btn = document.getElementById("button_submit");
    btn.addEventListener("click", function (){Giris(btn)});
    
}

function setCookie(cvalue) {
    const d = new Date();
    d.setTime(d.getTime() + (30*24*60*60*1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = "maxanmusicuser=" + cvalue + ";" + expires + ";path=/";
}


function Giris(btn){

    btn.disabled = true;

    let uname = document.getElementById("uname").value;
    let upw = document.getElementById("psw").value;


    if (uname == ""||upw == ""){
        alert("Boş kısımları doldurunuz.");
        btn.disabled = false;
        return;
    }

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
                throw alert("Böyle bir kullanıcı mevcut değil.");  
            }
            alert("giriş başarılı");
            setCookie(uname);
        });

btn.disabled = false;
}