/*  При загрузке страницы:
 *  добавляем событие на Enter в поле ввода
 *  добавляем слушаетля на очистку поля ввода
 *  запускаем таймер тймаута
 *  выполняем поиск всех заметок 
 */
window.onload = function() {
   var searchInput=document.getElementById("searchInput");
   searchInput.onkeyup=function(event)
    {
        event = event || window.event;
        if (event.keyCode == 0xD)
        {
            search();
            return false;
        } 
    };
   searchInput.oninput=function()
    {
        if(searchInput.value=="")
            search();
    }; 
    setInterval(loaderTimeout,1000);
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
    console.log("show loader");
}

//Таймаут, если данные от сервера не были получены
function loaderTimeout(){
    var loader=document.getElementById('loader-container');
    if(loader.style.display=="block"){
        timeout+=1000;
        if(timeout>4000)
        {
            console.log("loader timeout");
            closeLoader();
            alert("Server response timeout");
            timeout=0;
        }
    }
    else
    {
        timeout=0;
    }
}

//Закрыть загрузку
function closeLoader(){
    document.getElementById('loader-container').style.display="none";
    console.log("close loader");
}

//Расчёт длины строки в байтах для UTF-8
function byteCount(s) {
    return encodeURI(s).split(/%..|./).length - 1;
}