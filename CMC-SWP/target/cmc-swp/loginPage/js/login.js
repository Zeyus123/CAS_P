$(window).load(function() {
  $('.flexslider').flexslider({
    animation: "slide",
    controlNav: false
  });
});

// script for forgot password and login password window

$("#forgrtPasswordWindow").click(function(){
	$("#forget-password").removeClass("hidden");
	$("#login-window").hide();
})

$("#loginPasswordWindow").click(function(){
	$("#login-window").show();
	$("#forget-password").addClass("hidden");
	
})