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
            for(var i=0;i<json.length;i++){
                console.log(json[i].title);
                console.log(json[i].text);
            }
        }
    };
    xhr.send(data);
}

function showAllNames()
{
    var jsonData = new Object();
    jsonData.command = "0";

    serverConnectFunc(window.location.href,JSON.stringify(jsonData));
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
    jsonData.command = "5";

    serverConnectFunc(window.location.href,JSON.stringify(jsonData));
};

btnOpen.onclick = function() {
    modal.style.display = "block";
    modalTitle.textContent="Заметка";
    btnAction.textContent="Удалить";
    inputTitle.disabled = true;
    inputText.disabled = true;
    
    var jsonData = new Object();
    jsonData.command = "10";

    serverConnectFunc(window.location.href,JSON.stringify(jsonData));
};


span.onclick = function() {
    modal.style.display = "none";
};


window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};