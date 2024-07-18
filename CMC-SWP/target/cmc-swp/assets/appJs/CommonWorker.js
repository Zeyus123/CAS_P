
let xhttp = new XMLHttpRequest();

self.addEventListener('message', function(e) {
	const data = e.data;
	
	const method = data.method;
	const url = data.url;
	const body = data.payload;
	
	xhttp.open(method, url, true); 
	xhttp.send(body);
	
	xhttp.onreadystatechange = function(){ 
        if (xhttp.readyState == 4) {
          if(xhttp.status == 200){
            let str =  xhttp.responseText; 
			const json = JSON.parse(str);
			self.postMessage(json);
         } 
       }
    };


}, false);