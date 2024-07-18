<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>CMC-SWP</title>
    <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
    <meta name="keywords" content="CMC-SWP" />
    <meta name="description" content="CMC-SWP">
    <meta name="author" content="Aashdit Technologies">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <meta http-equiv="cache-control" content="max-age=0" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="cache-control" content="no-store" />
    <meta http-equiv="cache-control" content="pre-check=0" />
    <meta http-equiv="cache-control" content="post-check=0" />
    <meta http-equiv="cache-control" content="must-revalidate" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="shortcut icon" href="${contextPath}/assets/img/favicon.png">
    <!-- Fonts and icons -->
    <link href="${contextPath}/assets/vendor/fontawesome-free-6.4.2-web/css/all.min.css" rel="stylesheet">

    <link href="${contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/datatables.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/css/bootstrap-select.min.css" rel="stylesheet" />
    <link href="${contextPath}/assets/vendor/jquery_datepicker/jquery.datepick.css" rel="stylesheet" />

    <link href="${contextPath}/assets/vendor/bootstrap-timepicker/bootstrap-datetimepicker.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

    <link rel="stylesheet" href="${contextPath}/assets/vendor/select2full/select2.min.css" />
    <link rel="stylesheet" href="${contextPath}/assets/vendor/select2full/select2-bootstrap.css" />
    <link href="${contextPath}/assets/css/main.css" rel="stylesheet" />

    <!-- <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" /> -->

    <%-- <link rel="stylesheet" href="${contextPath}/assets/vendor/bootstrap-multiselect/bootstrap-multiselect.css" /> --%>

    <script src="${contextPath}/assets/js/jquery-3.7.1.min.js"></script>
    <style>
        #chartdiv {
            width: 100%;
            height: 500px;
        }
        header {
            /*background:linear-gradient(21deg, #0a3e1d 7.42%, #60833c 91.76%) !important;*/
            background:url(${contextPath}/assets/img/bg.jpg);

        }
        @media print {
            .navbar  {
                display: none !important;
            }
            .accordion-body table th, .accordion-body table td{
                font-size: 10px !important;
            }
            .cardcontainer .hide{
                display: none !important;
            }
            .cardcontainer .accordion-item .accordion-button::after {
                content: none !important; /* hide the arrow icon */
            }
            .cardcontainer .accordion-item:first-child.active {
                border-bottom: 1px solid #dee2e6 !important;
            }
            header{
                display: none !important;
            }
            .accordionExample{
                width: 100% !important;

            }

            .cardcontainer{
                display: block !important;
                margin-top: 0px !important;
                margin-bottom: 0px !important;
                margin-left: 0px !important;
                width: 100% !important;
            }


        }
    </style>






    <script>
        window.ctxPath='${contextPath}';
    </script>
    <script type="text/javascript">
        window.contextPath = '${contextPath}';
        $(function()
        {
            var url = window.location.href;
            var newurl = url.split('?');
            var finala=newurl[0];
            $(".nav-primary a").each(function()
            {
                if (finala == this.href)
                {
                    $(this).parent().addClass("active");
                    $(this).parent().parent().parent().addClass("show");
                    $(this).parent().parent().parent().parent().addClass("active");
                    $(this).parent().parent().parent().parent().parent().parent().addClass("show");
                }
            });
        });
    </script>

    <script>
        function showLoader() {
            // add css display flex in loader-div class
            $(".loader-div").css("display", "flex");

        }
        function hideLoader() {
            $(".loader-div").css("display", "none");
        }
    </script>
</head>



<body class="body">
<div class="wrapper">
    <section class="body_bg">
        <div class="jumbo absolute -inset-[10px] opacity-50"></div>
    </section>


    <%@ include file="/WEB-INF/views/newLoader.jsp" %>
    <%@ include file="/WEB-INF/views/stageConfig/stageRawPage.jsp"%>
    <header>
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="menu" />

    </header>

    <%-- 						<c:import url="message.jsp"></c:import> --%>

    <section class="main-panel">
        <div class="content">
            <tiles:insertAttribute name="body" />
        </div>
    </section>

</div>
<!-- wrapper End -->
<!-- Page Specific Scripts -->
<tiles:insertAttribute name="pageScripts" />

<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/AesUtil.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/aes.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/pbkdf2.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/lbase.js"></script>

<script src="${contextPath}/assets/vendor/jquery_datepicker/jquery.plugin.js"></script>
<script src="${contextPath}/assets/vendor/jquery_datepicker/jquery.datepick.min.js"></script>
<script src="${contextPath}/assets/vendor/bootstrap-timepicker/moment.min.js"></script>
<script src="${contextPath}/assets/vendor/bootstrap-timepicker/bootstrap-datetimepicker.min.js"></script>

<script src="${contextPath}/assets/js/bootstrap.bundle.min.js"></script>
<%-- <script src ="${contextPath}/assets/bootstrap/js/popper.js"></script>  --%>

<script src="${contextPath}/assets/vendor/bootbox5/bootbox.all.min.js"></script>
<script src="${contextPath}/assets/js/bootstrap-select.min.js"></script>

<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/AesUtil.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/aes.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/pbkdf2.js"></script>
<script src="${pageContext.request.contextPath}/assets/applicationSpecific/encrypt/lbase.js"></script>
<%-- <script src="${contextPath}/assets/vendor/bootstrap-multiselect/bootstrap-multiselect.js"></script> --%>


<script>
    $(document).ready(function () {
        $(".sub-menu > ul").hide();
        $('.js-example-basic-multiple').select2();
        $('form').attr('autocomplete', 'off');
        $(".sub-menu a").click(function () {
            $(this).parent(".sub-menu").children("ul").slideToggle("100");
            $(this).find(".right").toggleClass("fa-caret-up fa-caret-down");
        });
    });

    $(document).ready(function() {
        $('.select2').select2({
            closeOnSelect: false
        });

    });
</script>







<!-- jQuery Scrollbar -->
<script src="${contextPath}/assets/vendor/select2full/select2.full.js"></script>
<script src="${contextPath}/assets/js/datatables.min.js"></script>
<script src="${contextPath}/assets/js/main.js"></script>





<!-- <script>
$(document).ready(function() {
    $('.exportbtn').DataTable({
        dom: 'lBfrtip',
        responsive: true,
        pageLength: 10,
         lengthMenu: [0, 5, 10, 20, 50, 100, 200, 500],

        buttons: [
            'copy', 'excel', 'pdf'
        ]

    });
});
</script> -->


<script>
    $(document).ready(function() {
        $('.exportbtn').DataTable({
            dom: 'lBfrtip',
            responsive: true,
            pageLength: 10,
            lengthMenu: [10, 20, 50, 100, 200],
            buttons: ["copy","excel","pdf"]
        });

        $('.exportRptDataTable').DataTable({
            dom: 'lBfrtip',
            responsive: true,
            pageLength: 20,
            lengthMenu: [10, 20, 50, 100, 200],
            buttons: ["copy","excel","pdf"]
        });

    });


    /*  $(function () {
        $(".datepicker").datepicker({
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
        });

    });  */
</script>


<script>
    $(function () {
        $('.datepicker').datepick({
            onShow: $.datepick.monthOnly,dateFormat: 'dd/mm/yyyy', yearRange: 'c-20:c+10', showOnFocus: true,
            showTrigger: '<button type="button" class="trigger"><i class="fa fa-calendar"></i></button>'
        });
    });


    function convertDateFormat(dateString) {
        const parts = dateString.split('/');
        if (parts.length === 3) {
            const dd = parts[0];
            const mm = parts[1];
            const yyyy = parts[2];
            const finalDate= yyyy+"-"+mm+"-"+dd;
            return finalDate ;
        }
        return null;
    }


</script>


<form method="post" action="${contextPath}/umt/logout" id="frmLogout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="submit" style="display: none" />
</form>
<script>
    var timeout;

    function startTimeout() {
        //timeout = setTimeout(logout, 1500000); // 25 minutes timeout
        timeout = setTimeout(logout, 43200000); // 12 hr
    }

    function resetTimeout() {
        clearTimeout(timeout);
        startTimeout();
    }

    function logout() {

        bootbox.confirm({
            title : "<span style='color:red;'>USER ALERT !!</span>",
            message : "<span style='color:red;'>Your page has been in standby mode for a long time. Kindly refresh it else your login session will be timed out.</span>" ,
            buttons : {
                confirm : {label: "Login", className: "btn btn-sm btn-success"},
                cancel : {label: "Refresh", className: "btn btn-sm btn-primary"}
            },
            callback: function(result){
                if (result){
                    $("#frmLogout").submit();
                    showLoader();
                }else{
                    location.reload();
                }
            }
        });
    }

    // Start the timeout when the page is loaded
    window.onload = resetTimeout;

    // Reset the timeout whenever there is user activity (e.g., mousemove, keydown)
    document.onmousemove = resetTimeout;
    document.onkeydown = resetTimeout;
</script>


<script>
    $(document).ready(function () {
        $('.timepicker').datetimepicker({
            "allowInputToggle": true,
            "showClose": true,
            "showClear": true,
            "showTodayButton": true,
            "format": "hh:mm A",
        });
    });
</script>
<script>
    function convertFormToJSONArray(form) {
        const array = form;
        const json = {};
        $.each(array, function() {
            var contentCheck = this.name;
            if (json[this.name]) {
                if (!json[this.name].push) {
                    json[this.name] = [json[this.name]];
                }
                json[this.name].push(this.value || '');
            } else if (contentCheck.includes("Array")) {
                json[this.name] = [this.value];
            } else {
                json[this.name] = this.value || '';
            }
        });
        return json;
    }
</script>



</body>

</html>