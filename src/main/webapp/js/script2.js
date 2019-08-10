function createElem(id)

    {

        var dcont  = document.getElementById('itemsHolder');

        var div = document.createElement('div');

		div.setAttribute('class','note');
		
		div.innerHTML="Какой то возможно длинный кусок текста";
        div.setAttribute('onclick','itemClick('+id+')');

        dcont.appendChild(div);
    }

    function itemClick(id){
		alert(id);
    }
