var jsonNotes;

function getNotes(key){ 
    var jsonData = new Object();
    jsonData.command = "0";
    jsonData.key=key;
    console.log("search for "+key);
    dataPost(window.location.href,JSON.stringify(jsonData));
}

function createNote(title,note){
    var jsonData = new Object();
    jsonData.command = "1";
    jsonData.title=title; 
    jsonData.note=note;
    console.log("creating note");
    dataPost(window.location.href,JSON.stringify(jsonData));
}

function deleteNote(id){
    var jsonData = new Object();
    jsonData.command = "2";
    jsonData.id=id; 
    console.log("delete note with id="+id);
    dataPost(window.location.href,JSON.stringify(jsonData));
}



//Отправка и приём данных с сервера в формате JSON используя POST
function dataPost(serverUrl,data) {
    //показываем загрузку
    showLoader();
    var xhr = new XMLHttpRequest();
    var url = serverUrl;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            
            switch (json.status){
                case "notes":
                    console.log("recieved "+json.notes.length+" notes");
                    jsonNotes=json.notes;
                    updateNotes();
                    closeLoader();
                    break;
                case "success":
                    console.log("sucess operation");
                    clearSearch();
                    closeLoader();
                    break;
                case "error":    
                    alert(json.message);
                    closeLoader();
                    break;
            }
                
        }
    };
    xhr.send(data);
}