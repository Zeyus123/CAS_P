<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>CMC-SWP</title>
    <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
    <meta name="keywords" content="CMC-SWP" />
    <meta name="description" content="CMC-SWP">
    <meta name="author" content="Aashdit Technologies">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/img/favicon.png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/assets/js/core/jquery-3.5.1.min.js"></script>
          <style>
        body {
            background-color: #f5f5f5;
            font-family: 'Arial', sans-serif;
        }

        .container {
            width: 80%;
            margin: 0 auto;
            text-align: center;
            animation: fadeIn 1s ease-in-out;
        }

        h1 {
            font-size: 5rem;
            margin-top: 1rem;
            color: #333;
            animation: slideIn 1s ease-in-out;
        }

        p {
            font-size: 2rem;
            margin-top: 2rem;
            color: #666;
            animation: slideIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        @keyframes slideIn {
            from {
                transform: translateY(50px);
                opacity: 0;
            }
            to {
                transform: translateY(0);
                opacity: 1;
            }
        }

        @keyframes shake {
            0% {
                transform: translateX(0);
            }
            10%, 30%, 50%, 70%, 90% {
                transform: translateX(-5px);
            }
            20%, 40%, 60%, 80% {
                transform: translateX(5px);
            }
            100% {
                transform: translateX(0);
            }
        }

        .error {
            font-size: 10rem;
            color: #f44336;
            animation: shake 1s ease-in-out;
        }

        a {
            display: block;
            margin-top: 2rem;
            font-size: 1.5rem;
            text-decoration: none;
            color: #2196F3;
            animation: slideIn 1s ease-in-out;
        }

    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/message.jsp" %>
<div class="container">
    <div class="error">${code}</div>
    <h1>Page Not Found</h1>
    <p>The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
    <div id="countdown" style="font-size: 40px; margin-top: 20px;"></div>
    <a href="${contextPath}/">Back to home page</a>
</div>
<script>
    // Countdown timer
    var timeLeft = 10;
    var countdownElement = document.getElementById('countdown');

    function updateTimer() {
        countdownElement.innerText = timeLeft;
        timeLeft--;
        if (timeLeft < 0) {
            window.location.href = "${contextPath}/";
        }
    }

    updateTimer(); // Initial call
    var countdownInterval = setInterval(updateTimer, 1000);

    // Redirect animation after the timer ends
    setTimeout(function() {
        clearInterval(countdownInterval);
        countdownElement.style.fontSize = '80px';
        countdownElement.style.transition = 'font-size 1s';
        countdownElement.innerText = 'Redirecting...';
        setTimeout(function() {
            window.location.href = "${contextPath}/"+'${url}';
        }, 1000);
    }, (timeLeft + 1) * 1000);
</script>
</body>

</html>