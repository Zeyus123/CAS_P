<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<script src="${contextPath}/assets/appJs/validation/common-utils.js"></script>
<script src="${contextPath}/assets/appJs/validation/commonMaster.js"></script>
<sec:authentication var="principal" property="principal" />
<c:set var="roleCode" value="${principal.primaryRole.roleCode}"/>
    
<div class="cardcontainer">
    <div class="row">
        <div class="col-md-12 d-flex align-items-center justify-content-between">
            <h5>Bank</h5>
            <nav aria-label="breadcrumb" style="float: right;">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}/home">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Add Bank</li>
                </ol>
            </nav>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/message.jsp"%>
    <hr style="margin-top: 5px;" />
    <h6 class="headingbg mb-4">Add Bank</h6>
    <form action="${contextPath}/config/saveBank" method="post" id="saveFormId" class="row">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="bankId" value="${bankMasterData.bankId}" id="bankId" />

        <div class="form-group col-md-4">
            <label class="required">Bank Name</label>
            <input type="text" class="form-control form-control-sm" id="bankName" name="bankName" maxlength="100" value="${bankMasterData.bankName}" onchange="getDuplicateCheckData('bankName','BANK_MST_NAME','bankId')">
        </div>

        <div class="form-group col-md-2">
            <label class="required">Short Name</label>
            <input type="text" class="form-control form-control-sm" id="shortName" name="shortName" maxLength="20" value="${bankMasterData.shortName}" onchange="getDuplicateCheckData('shortName','BANK_MST_SH_NAME','bankId')">
        </div>
        <div class="form-group col-md-4 text-left">
            <button type="button" class="btn btn-success btn-sm" style="margin-top: 23px; margin-left: 8px;" onclick="submitForm('${empty bankMasterData.bankId ? 'SAVE' : 'UPDATE'}')" class="btn searchdiv primary-button btn-sm">${empty bankMasterData.bankId ? 'Submit' : 'Update'}</button>
            <button type="button" class="btn btn-danger btn-sm" style="margin-top: 23px; margin-left: 8px;" onclick="history.back()" class="btn searchdiv danger-button btn-sm">Cancel</button>
        </div>
    </form>
</div>



<div class="cardcontainer cardContainerNotFirst">
	 <div class="row">
	  <h6 class="headingbg mb-4">View Banks</h6>
		<table class="datatable table table-striped table-bordered exportbtn mt-5">
	    	<thead>
		        <tr>
		            <th>Sl.No</th>
		            <th>Bank Name</th>
		            <th>Short Name</th>
		            <th>Action</th>
		        </tr>
	    	</thead>
	    	<tbody>
		        <c:forEach items="${bankList}" var="bm" varStatus="count">
		            <tr>
		                <td>${count.count}</td>
		                <td>${bm.bankName}</td>
		                <td>${bm.shortName}</td>
		                <td>
		                      <a href="#" onclick="editBankForm('${bm.bankId}')" class="btn btn-warning btn-xs" title="Edit"><i class="fa fa-edit"></i></a>
		                        <a href="#" onclick="statusBankForm('${bm.bankId}',${bm.isActive})" class="btn ${bm.isActive eq true ? 'btn-danger' : 'btn-success'} btn-xs">
		                            <i class="${bm.isActive eq true ? 'fa fa-lock' : 'fa fa-unlock'}" title="${bm.isActive eq true ? 'InActive' : 'Active'}"></i>
		                     </a>
		                </td>
		            </tr>
		        </c:forEach>
	    	</tbody>
		</table>
	</div>
</div>

<form action="${contextPath}/config/editBank" method="post" id="editForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" name="bankId" id="bankIdEdit" />
</form>

<form action="${contextPath}/config/updateBankDetails" method="post" id="statusForm">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" name="bankId" id="bankIdStatus" />
    <input type="hidden" name="status" id="isActiveStatus" />
</form>

<script>
    function submitForm(status) {
        if ($("#bankName").val().trim() == "") {
            bootbox.alert("Please provide a bank name");
            return false;
        } else if ($("#shortName").val().trim() == "") {
            bootbox.alert("Please provide a short name for the bank");
        } else {
            bootbox.confirm("Do you want to save Bank data?", function (result) {
                if (result) {
                    showLoader();
                    $("#saveFormId").submit();
                }
            });
        }
    }

    function editBankForm(id) {
        $("#bankIdEdit").val(id);
        showLoader();
        $("#editForm").submit();
    }


    function statusBankForm(id, status) {
        document.getElementById("bankIdStatus").value = id;
        if (status) {
            $("#isActiveStatus").val(false);
        } else {
            $("#isActiveStatus").val(true);
        }
        bootbox.confirm("Do you want to change the status?", function (result) {
            if (result) {
                showLoader();
                $("#statusForm").submit();
            }
        });
    }
</script>


