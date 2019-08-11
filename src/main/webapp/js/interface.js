//При загрузке страницы выполняем поиск всех заметок
window.onload = function() {
   getNotes("");
};

//Поиск
function search(){
    var key=document.getElementById("searchInput").value;
    getNotes(key);
}

//Очистка поля поиска
function clearSearch(){
    document.getElementById("searchInput").value="";
    search();
}

//Показать заметку
function showNote(id){   
    for(var i in jsonNotes){
        if(jsonNotes[i].id==id){
            var inputTitle=document.getElementById("inputTitle");
            var inputText=document.getElementById("inputText");
            inputTitle.value=jsonNotes[i].title;
            inputText.value=jsonNotes[i].note;
            inputTitle.placeholder="";
            inputText.placeholder="";
            document.getElementById('myModal').style.display="block";
            inputTitle.disabled = true;
            inputText.disabled = true;
            
            var btnAction = document.getElementById("btnAction");
            btnAction.textContent="Delete";
            btnAction.onclick=function(){
                if (confirm('Are you sure you want to delete this note?')) {
                    deleteNote(id);
                    closeModal();
                } 
            };
            break;
        }
    }
}

//Создать заметку
function addNote(){
    document.getElementById('myModal').style.display="block";
    var inputTitle=document.getElementById("inputTitle");
    var inputText=document.getElementById("inputText");
    inputTitle.disabled = false;
    inputText.disabled = false;
    inputTitle.value = "";
    inputText.value = "";
    inputTitle.placeholder="Title";
    inputText.placeholder="Note";
    var btnAction = document.getElementById("btnAction");
        btnAction.textContent="Create";
        btnAction.onclick=function(){
            if(inputText.value=="" && inputTitle.value==""){
                alert("Emty note not allowed");
            }
            else if(byteCount(inputTitle.value)>255){
                alert("The title is too long");
            }
            else{
                createNote(inputTitle.value,inputText.value);
                closeModal();
            }
                
    };
}

//Закрыть модальное окно
function closeModal(){
    document.getElementById('myModal').style.display = "none";
}

window.onclick = function(event) {
    var modal=document.getElementById('myModal');
    if (event.target == modal) {
        closeModal();
    }
};

//Обновление заметок
function updateNotes(){
    var dcont  = document.getElementById('notes-container');
    while (dcont.firstChild) {
        dcont.removeChild(dcont.firstChild);
    }
    
    for(var i in jsonNotes){
        var div = document.createElement('div');
        if(jsonNotes[i].title!=""){
            div.setAttribute('class','note-titled');
            div.innerHTML=jsonNotes[i].title;
        }
        else{
            div.setAttribute('class','note');
            div.innerHTML=jsonNotes[i].note;
        }
        div.setAttribute('onclick','showNote('+jsonNotes[i].id+')');
        dcont.appendChild(div);
    }
	
}

//Показать загрузку
function showLoader(){
    document.getElementById('loader-container').style.display="block";
    setTimeout(loaderTimeout,5000);
}

//Таймаут, если данные от сервера не были получены
function loaderTimeout(){
    var loader=document.getElementById('loader-container');
    if(loader.style.display=="block"){
        closeLoader();
        alert("Server response timeout");
    }
}

//Закрыть загрузку
function closeLoader(){
    document.getElementById('loader-container').style.display="none";
}

//Расчёт длины строки в байтах для UTF-8
function byteCount(s) {
    return encodeURI(s).split(/%..|./).length - 1;
}