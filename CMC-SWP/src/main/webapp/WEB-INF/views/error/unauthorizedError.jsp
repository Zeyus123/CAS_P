<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        img {
            width: 100%;
            height: 100vh;
        }

        body {
            position: relative;
        }

        .text {
            position: absolute;
            color: #fff;
            left: 50%;
            top: 30%;
            transform: translate(-50%, 50%);
        }
    </style>
</head>

<body>
    <img src="${contextPath}/assets/img/hacker.jpg" alt="Hackr Akash">
    <div class="text text-center">
        <h1 class="bg-white text-danger py-1 px-2">Don't try too Hard Mr. ${localHostName} 
        </h1>
        <h5 class="bg-white text-danger py-1 px-1">Your IP "${localHostAddress}" Will be Block Soon</h5>
        <a href="#" class="btn btn-warning">Go Back</a>
    </div>
</body>

</html>