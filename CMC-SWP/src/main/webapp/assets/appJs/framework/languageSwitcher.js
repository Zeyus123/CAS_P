
function switchLanguage(lang)
{
	var didIt = false;
	var idx = window.location.href.search(/(\?lang=)/);
	if ( idx >  0)
	{
		window.location  = window.location.href.replace(/(lang=)[a-zA-Z0-9-_]+/, "lang=" + lang);
		didIt = true;
	}
	
	if (!didIt)
	{
		var idx = window.location.href.search(/(\&lang=)/);
		if (idx > 0)
		{
			window.location  = window.location.href.replace(/(lang=)[a-zA-Z0-9-_]+/, "lang=" + lang);
			didIt = true;
		}
		else
		{
			if (!didIt)
			{
				if (window.location.href.indexOf(window.location.search) > 0)
				{
					window.location  = window.location.href + "&lang=" + lang;
					didIt = true;
				}
				else
				{
					window.location  = window.location.href + "?lang=" + lang;
				}
				
			}
		}
	}
}