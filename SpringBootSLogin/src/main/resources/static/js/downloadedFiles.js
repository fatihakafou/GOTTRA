
		
		function updateSize() {
                var nBytes = 0,
                        oFiles = document.getElementById("fileInput").files,
                        nFiles = oFiles.length;
                for (var nFileId = 0; nFileId < nFiles; nFileId++) {
                    nBytes += oFiles[nFileId].size;
                }

                var sOutput = nBytes + " bytes";
                // optional code for multiples approximation
                for (var aMultiples = ["KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB"], nMultiple = 0, nApprox = nBytes / 1024; nApprox > 1; nApprox /= 1024, nMultiple++) {
                    sOutput = nApprox.toFixed(3) + " " + aMultiples[nMultiple] + " (" + nBytes + " bytes)";
                }
                // end of optional code

                document.getElementById("fileNum").innerHTML = nFiles;
               
                document.getElementById("fileSize").innerHTML = sOutput;
            }
			
			function readFile(clicked_id) {
				//var x = document.getElementById("1").value;		
				//alert('files :'+ clicked_id +'/');
				var x = document.getElementById(clicked_id).value;
				//alert('file to read:'+ x);
				//var arrayOfStrings = x.split('.');
				//alert('arrayOfString:'+ arrayOfStrings[0]);
				//var target = "/fileRead/" + arrayOfStrings[0] ;
				var target = "/fileRead/" + x ;
				//alert('URL :'+ target);
				$.ajax({url: target , success: function(result){
			       // $("#div1").html(result);
				   //alert('file in console:'+ arrayOfStrings[0]);
				   //alert('file in console:'+ target);
			    }});		
			}
			
			function dispalyFile(clicked_id) {	
				// Affiche les noeuds enfant du noeud body
				
				var elements = document.getElementsByTagName('input'); 
				console.log(document.getElementsByTagName('input'));
				for (var i = 0; i < elements.length; ++i) {
				    var element = elements[i] // objet de type Element
				    if (element.checked){
				    	console.log(element);
				    }
				}
				/*
				for (var i = 0; i < document.body.childNodes.length; i++) {
				    console.log(document.body.childNodes[i]);
				    
				}
				*/
				// =========================================================
				var fichier = document.getElementById(clicked_id).value;
				/*
				if(document.getElementById(clicked_id).checked){
					alert (fichier + '  checked');
				}
				*/
				// alert('Fichier à lire:'+ fichier);
				var fichierSelected = document.getElementById("filesList").value;
				if (fichierSelected='undefined'){
					fichierSelected =  fichier ;
				}
				else{
					fichierSelected = fichierSelected + fichier ;
				}
					
				// alert('Fichiers sélectionnés:'+ fichierSelected);
				// fichierSelected = fichierSelected + fichier ;
				// alert('Fichiers sélectionnés:'+ fichierSelected);
				document.getElementById("filesList").innerHTML = fichierSelected;	
			}
			
			function readAll(){
				var target = "/fileRead";
				$.ajax({url: target , success: function(result){
			       // $("#div1").html(result);
				   //alert('file in console:'+ arrayOfStrings[0]);
				  // alert('file in console:'+ target);
					console.log('Appel URL réussi:'+ target);
			    }});
			}
			
			function getResult(){
				var target = "/processFiles";
				$.ajax({url: target , success: function(result){
				       // $("#div1").html(result);
					   //alert('file in console:'+ arrayOfStrings[0]);
					  // alert('file in console:'+ target);
						console.log('Appel URL réussi:'+ target);
				    }});
			}
			
			function dispalyCheckedBox() {
				
				// Affiche les noeuds enfant du noeud body
				
				var elements = document.getElementsByTagName('input'); 
				var fichierSelected="";
				for (var i = 0; i < elements.length; ++i) {
				    var element = elements[i] // objet de type Element
				    if (element.checked){
				    	/*
				    	console.log(element);
				    	console.log(element.id);
				    	console.log(element.value);
				    	console.log(element.type);
				    	console.log("=============");
				    	console.log(fichierSelected);
				    	*/
				    	fichierSelected = fichierSelected+element.value+',';
				    }
				    	
				}
			
				document.getElementById("filesList").innerHTML = fichierSelected;
				var target = "/fileRead/" + fichierSelected ;
				$.ajax({url: target , success: function(result){
			       // $("#div1").html(result);
				   //alert('file in console:'+ arrayOfStrings[0]);
				   //alert('file in console:'+ target);
					console.log('Appel URL réussi:'+ target);
			    }});		
			}
			