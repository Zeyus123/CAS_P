<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CMC</title>
<style>
body {
  background-color: #021A4B;
  font-family: "Concert One", cursive;
  margin: 0;
  overflow: hidden;
  padding: 0;
}

/*/////////////////// rules */
/*___________________________________________________*/
/*//////////////////////////////////////////// scene */
.text {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  color: #dbdbdb;
  font-size: 3em;
  margin: 0;
  opacity: 0.3;
  text-align: center;
  top: 50px;
  width: 80%;
}

.container {
  left: 50%;
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  height: 350px;
  width: 400px;
}
.container .bg {
  left: 50%;
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(219, 219, 219, 0.05);
  border-radius: 50%;
  box-shadow: 0px 0px 100px 50px rgba(219, 219, 219, 0.1);
  height: 400px;
  overflow: hidden;
  width: 400px;
}
.container .bg .light {
  left: 50%;
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
  border-color: transparent transparent #dbdbdb transparent;
  border-style: solid;
  border-width: 0 160px 400px 160px;
  height: 0;
  opacity: 0;
  width: 0;
}

.ufo {
  position: absolute;
  height: 100px;
  left: calc(50% - 50px);
  top: 0;
  width: 100px;
}
.ufo .ufo-bottom {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #eba849;
  border-radius: 50%;
  height: 20px;
  top: 55px;
  width: 20px;
}
.ufo .ufo-bottom:after, .ufo .ufo-bottom:before {
  position: absolute;
  background-color: #eba849;
  border-radius: 50%;
  content: "";
  height: 20px;
  top: -6px;
  width: 20px;
}
.ufo .ufo-bottom:after {
  left: -25px;
}
.ufo .ufo-bottom:before {
  left: 25px;
}
.ufo .ufo-top {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #eba849;
  border-radius: 50%;
  height: 70px;
  width: 90px;
}
.ufo .ufo-top:before {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #283b49;
  border-radius: 50%;
  content: "";
  height: 70px;
  top: -10px;
  width: 100px;
}
.ufo .ufo-glass {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #dbdbdb;
  border-radius: 90px 90px 80px 80px;
  height: 80px;
  overflow: hidden;
  top: -40px;
  width: 80px;
}
.ufo .ufo-glass .alien {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #41a88b;
  border-radius: 50px 50px 0 0;
  height: 70px;
  overflow: hidden;
  top: 25px;
  width: 50px;
}
.ufo .ufo-glass .alien .alien-eye {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #dbdbdb;
  border-radius: 50%;
  height: 30px;
  top: 10px;
  width: 30px;
}
.ufo .ufo-glass .alien .alien-eye:after {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #41a88b;
  border-radius: 50%;
  content: "";
  height: 40px;
  bottom: 30px;
  width: 40px;
}
.ufo .ufo-glass .alien .alien-eye:before {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #13242c;
  border-radius: 50%;
  content: "";
  height: 10px;
  bottom: 5px;
  width: 10px;
}

.bed {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #13242c;
  border-radius: 25px;
  bottom: -25px;
  height: 10px;
  width: 230px;
}
.bed .mattress {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: rgba(219, 219, 219, 0.4);
  border-radius: 10px;
  bottom: 10px;
  height: 30px;
  width: 220px;
}

.man {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  border-radius: 50%;
  bottom: 13px;
  height: 150px;
  width: 150px;
}
.man .foot {
  position: absolute;
  background-color: rgba(115, 188, 198, 0.5);
  border-radius: 50%;
  box-shadow: 0px -15px 0 rgba(115, 188, 198, 0.2);
  height: 35px;
  left: 0px;
  top: 113px;
  width: 35px;
}
.man .man-body {
  left: 50%;
  position: absolute;
  transform: translateX(-50%);
  background-color: #ca906f;
  border-radius: 50%;
  box-shadow: -30px -70px 0 -71px #CA906F;
  height: 150px;
  overflow: hidden;
  width: 150px;
}
.man .man-body:after {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: #73bcc6;
  content: "";
  height: 100%;
  left: calc(50% - 20px);
  width: 100px;
}
.man .head {
  position: absolute;
  height: 80px;
  right: -78px;
  top: 35px;
  width: 80px;
}
.man .head .face {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: #ca906f;
  border-radius: 50%;
  height: 70px;
  left: 0;
  overflow: hidden;
  width: 70px;
}
.man .head .face:after {
  position: absolute;
  background-color: #13242c;
  border-radius: 50%;
  content: "";
  height: 70px;
  left: 0;
  top: 50px;
  width: 70px;
}
.man .head .hair {
  position: absolute;
  background-color: #13242c;
  border-radius: 30px 0;
  height: 50px;
  right: 0px;
  top: 20px;
  width: 30px;
}
.man .arm {
  position: absolute;
  background-color: #ca906f;
  border-radius: 50px;
  height: 140px;
  right: 15px;
  top: 60px;
  width: 60px;
}

/*___________________________________________________*/
/*//////////////////////////////////////// animation */
.ufo {
  animation: top-anima 1.5s infinite linear;
}
@keyframes top-anima {
  0% {
    top: 0;
  }
  50% {
    top: -5px;
  }
}

.bg .light {
  animation: light-anima 3s infinite linear;
}
@keyframes light-anima {
  0% {
    opacity: 0;
  }
  45% {
    opacity: 0.2;
  }
  52% {
    opacity: 0.2;
  }
  55% {
    opacity: 0;
  }
  60% {
    opcaity: 0;
  }
  100% {
    opcaity: 0;
  }
}

.ufo .alien .alien-eye:after {
  animation: alien01-anima 3s infinite linear;
}
@keyframes alien01-anima {
  0% {
    bottom: 30px;
  }
  52% {
    bottom: 30px;
  }
  55% {
    bottom: 20px;
  }
  60% {
    bottom: 20px;
  }
  100% {
    bottom: 20px;
  }
}

.bed .mattress {
  animation: mattress-anima 3s infinite linear;
}
@keyframes mattress-anima {
  0% {
    bottom: 10px;
  }
  52% {
    bottom: 10px;
  }
  55% {
    bottom: 15px;
  }
  60% {
    bottom: 10px;
  }
  100% {
    bottom: 10px;
  }
}

.man {
  animation: man-anima 3s infinite linear;
}
@keyframes man-anima {
  0% {
    bottom: 13px;
  }
  50% {
    bottom: 80px;
  }
  52% {
    bottom: 10px;
  }
  55% {
    bottom: 30px;
  }
  60% {
    bottom: 13px;
  }
  100% {
    bottom: 13px;
  }
}
.man .head {
  transform: rotate(20deg);
  transform-origin: -75px 40px;
  animation: head-anima 3s infinite linear;
}
@keyframes head-anima {
  0% {
    transform: rotate(20deg);
  }
  50% {
    transform: rotate(40deg);
  }
  52% {
    transform: rotate(20deg);
  }
  55% {
    transform: rotate(10deg);
  }
  60% {
    transform: rotate(20deg);
  }
  100% {
    transform: rotate(20deg);
  }
}
.man .arm {
  transform: rotate(30deg);
  transform-origin: 30px 30px;
  animation: arm-anima 3s infinite linear;
}
@keyframes arm-anima {
  0% {
    transform: rotate(30deg);
  }
  15% {
    transform: rotate(-5deg);
  }
  20% {
    transform: rotate(5deg);
  }
  25% {
    transform: rotate(-2.5deg);
  }
  30% {
    transform: rotate(2.5deg);
  }
  50% {
    transform: rotate(0deg);
  }
  52% {
    transform: rotate(30deg);
  }
  100% {
    transform: rotate(30deg);
  }
}


#link {
  bottom: 20px;
  color: #fff;
  opacity: 0.5;
  display: flex;
  align-items: center;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}

#link p {
  margin: 0;
  margin-left: 5px;
}

#link:hover {
  opacity: 1;
}


 .contents-right {
	float: left;
	width: 35%;
}

.count-con {
	width: 150px;
	height: 150px;
	border: 4px solid rgba(255, 255, 255, 0.43);
	text-align: center;
	border-radius: 50%;
	margin: 0 auto;
	margin-top: 100px;
	box-shadow: 0px 0px 20px #000;
}

.count-con .count {
	font-size: 100px;
	font-weight: 100;
	color: rgba(255, 255, 255, 0.79);
	padding-top: 15px;
}

.contents-con .contents-right h3 {
	text-align: center;
	color: rgba(255, 255, 255, 0.85);
	font-size: 35px;
	margin-top: 10px;
	font-weight: 100;
}
#btHome{
 margin-top:10px;
 background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 8px 18px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius:22px;
}

</style>
</head>
<body>

	<div class="main-container">

		<p class="text">
			Oops..<br>something went wrong
		</p>
		<div class="container">
			<div class="bg">
				<div class="light"></div>
			</div>
			<div class="ufo">
				<div class="ufo-bottom"></div>
				<div class="ufo-top"></div>
				<div class="ufo-glass">
					<div class="alien">
						<div class="alien-eye"></div>
					</div>
				</div>
			</div>
			<div class="bed">
				<div class="mattress"></div>
			</div>
			<div class="man">
				<div class="foot"></div>
				<div class="head">
					<div class="face"></div>
					<div class="hair"></div>
				</div>
				<div class="man-body"></div>
				<div class="arm"></div>
			</div>
		</div>
		
		<div class="contents-right">
				<div class="count-con">
					<div id="timecount" class="count"></div>
					<a href="${contextPath}/" id="btHome">Back to Home</a>
				</div>
				
		</div>
	</div>
	
	<form action="${contextPath}/" method="get" id="formlogin"></form>
	<script src="${contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>

	
	<script type="text/javascript">
(function($) {
	$.fn.jQuerySimpleCounter = function( options ) {
	    var settings = $.extend({
	        start:  10,
	        end:    -1,
	        easing: 'swing',
	        duration: 10000,
	        complete: ''
	    }, options );

	    var thisElement = $(this);

	    $({count: settings.start}).animate({count: settings.end}, {
			duration: settings.duration,
			easing: settings.easing,
			step: function() {
				var mathCount = Math.ceil(this.count);
				thisElement.text(mathCount);
				if(mathCount==0)
					{
					 $("#formlogin").submit();	
					}
			},
			complete: settings.complete
		});
	};

}(jQuery));
$('#timecount').jQuerySimpleCounter();
$("#bclogin").click(function(){	
	$("#formlogin").submit();	
});
 
</script>
</body>
</html>
	