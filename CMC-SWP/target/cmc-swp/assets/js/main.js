let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
  arrow[i].addEventListener("click", (e)=>{
 let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
 arrowParent.classList.toggle("showMenu");
  });
}

 $( document ).ready(function() {
  $("#btn_navToggle").click(function() {
    $("body").toggleClass("sidebar_collapsed");
  });


                $(document).on("click", '.sub-menu > li > i', function (){ 
                  $(this).closest('li').find('.sub-sub-menu').slideToggle();
                });



});

