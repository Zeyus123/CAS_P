function enc_inputCaptcha(inputCaptcha)
{
	
	let key = $("input[name='_csrf']").val().substring(0,16);
	
	 var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
     var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

     var aesUtil = new AesUtil(128, 1000);
     var ciphertext = aesUtil.encrypt(salt, iv, key , inputCaptcha);

     var aesInputCaptcha = (iv + "::" + salt + "::" + ciphertext);
     var newInputCaptcha = btoa(aesInputCaptcha);
     
     return newInputCaptcha;
}

function enc_generatedCaptcha(generatedCaptcha)
{
	
	let key = $("input[name='_csrf']").val().substring(0,16);
	
	 var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
     var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

     var aesUtil = new AesUtil(128, 1000);
     var ciphertext = aesUtil.encrypt(salt, iv, key , generatedCaptcha);

     var aesGeneratedCaptcha = (iv + "::" + salt + "::" + ciphertext);
     var newGeneratedCaptcha = btoa(aesGeneratedCaptcha);
     
     return newGeneratedCaptcha;
}

function enc_password(pwd)
{
	
	let key = $("input[name='_csrf']").val().substring(0,16);
	
	 var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
     var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

     var aesUtil = new AesUtil(128, 1000);
     var ciphertext = aesUtil.encrypt(salt, iv, key , pwd);

     var aesPassword = (iv + "::" + salt + "::" + ciphertext);
     var password = btoa(aesPassword);
     
     return password;
}
	