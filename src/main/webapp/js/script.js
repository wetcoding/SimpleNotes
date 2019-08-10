var modal = document.getElementById('myModal');
var btnAdd = document.getElementById("btnAddNote");
var btnOpen = document.getElementById("btnOpenNote");
var btnAction = document.getElementById("btnAction");
var inputTitle=document.getElementById("inputTitle");
var inputText=document.getElementById("inputText");
var span = document.getElementsByClassName("close")[0];
var modalTitle=document.getElementById("modalTitle");

//Какой то коммент
function serverConnectFunc(serverUrl,data) {
    // Sending and receiving data in JSON format using POST method
    //
    var xhr = new XMLHttpRequest();
    var url = serverUrl;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            
            switch (json.status){
                case "notes":
                    for(var i in json.notes){
                        console.log(json.notes[i].id);
                        console.log(json.notes[i].title);
                        console.log(json.notes[i].text);
                    }
                    alert("Получено "+json.notes.length);
                    break;
                case "success":
                    alert(json.message);
                    showAllNames();
                    break;
                case "error":    
                    alert(json.message);
                    break;
            }
                
        }
    };
    xhr.send(data);
}

function showAllNames()
{
    var jsonData = new Object();
    jsonData.command = "0";
    jsonData.key="";    

   // serverConnectFunc(window.location.href,JSON.stringify(jsonData));
}

window.onload=function () {
    showAllNames();
}

btnAdd.onclick = function() {
    modal.style.display = "block";
    modalTitle.textContent="Добавить новую заметку";
    btnAction.textContent="Добавить";
    inputTitle.disabled = false;
    inputText.disabled = false;
    
    var jsonData = new Object();
    jsonData.command = "1";

   // serverConnectFunc(window.location.href,JSON.stringify(jsonData));
};

btnOpen.onclick = function() {
    modal.style.display = "block";
    modalTitle.textContent="Заметка";
    btnAction.textContent="Удалить";
    inputTitle.disabled = true;
    inputText.disabled = true;
    
    var jsonData = new Object();
    jsonData.command = "2";

  //  serverConnectFunc(window.location.href,JSON.stringify(jsonData));
};


span.onclick = function() {
    modal.style.display = "none";
};


window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};