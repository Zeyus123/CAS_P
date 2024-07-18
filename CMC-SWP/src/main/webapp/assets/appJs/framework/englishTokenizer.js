/*
 * https://stackoverflow.com/questions/7225407/convert-camelcasetext-to-sentence-case-text
 */

function camelCaseToTitleCase(in_camelCaseString) {
        var result = in_camelCaseString                         
            .replace(/([a-z])([A-Z][a-z])/g, "$1 $2")           
            .replace(/([A-Z][a-z])([A-Z])/g, "$1 $2")          
            .replace(/([a-z])([A-Z]+[a-z])/g, "$1 $2")         
            .replace(/([A-Z]+)([A-Z][a-z][a-z])/g, "$1 $2")    
            .replace(/([a-z]+)([A-Z0-9]+)/g, "$1 $2")           
            
            // Note: the next regex includes a special case to exclude plurals of acronyms, e.g. "ABCs"
            .replace(/([A-Z]+)([A-Z][a-rt-z][a-z]*)/g, "$1 $2") 
            .replace(/([0-9])([A-Z][a-z]+)/g, "$1 $2")           

            // Note: the next two regexes use {2,} instead of + to add space on phrases like Room26A and 26ABCs but not on phrases like R2D2 and C3PO"
            .replace(/([A-Z]{2,})([0-9]{2,})/g, "$1 $2")       
            .replace(/([0-9]{2,})([A-Z]{2,})/g, "$1 $2")    
            .trim();


  // capitalize the first letter
  return result.charAt(0).toUpperCase() + result.slice(1);
}

function dbColNameToUILabel(db_col_name)
{
	 var result = db_col_name
	 				.replace('_en','_English')
	 				.replace('_hi','_Hindi')
	 				.replace(/(_)/g,' ')
	                .trim();
	 
	 var items = result.split(' ');
	 var finalRes='';
	 items.forEach((entry) =>{
		 finalRes += entry.charAt(0).toUpperCase() + entry.slice(1) + ' ';
	 })
	 return finalRes.trim(); //.charAt(0).toUpperCase() + result.slice(1);
}