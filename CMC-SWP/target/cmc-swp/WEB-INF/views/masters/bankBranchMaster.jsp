<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="userDetails" value="${serviceOutcome.data}" />

<!-- Include JavaScript utility -->
<script type="text/javascript" src="${contextPath}/assets/appJs/validation/common-utils.js"></script>


<!-- CSS Styles -->
<style>
    .alert-danger {
        border-color: #f3acbb !important;
        color: #730503;
    }

    /* Profile Pic Styles */
    .picture-container {
        position: relative;
        cursor: pointer;
        text-align: center;
    }

    .picture {
        width: 106px;
        height: 106px;
        background-color: #999999;
        border: 4px solid #CCCCCC;
        color: #FFFFFF;
        border-radius: 0px;
        margin: 10px auto;
        overflow: hidden;
        transition: all 0.2s;
        -webkit-transition: all 0.2s;
    }

    .picture:hover {
        border-color: #2ca8ff;
    }

    .content.ct-wizard-green .picture:hover {
        border-color: #05ae0e;
    }

    .content.ct-wizard-blue .picture:hover {
        border-color: #3472f7;
    }

    .content.ct-wizard-orange .picture:hover {
        border-color: #ff9500;
    }

    .content.ct-wizard-red .picture:hover {
        border-color: #ff3b30;
    }

    .picture input[type="file"] {
        cursor: pointer;
        display: block;
        height: 100%;
        left: 0;
        opacity: 0 !important;
        position: absolute;
        top: 0;
        width: 100%;
    }

    .picture img {
        width: 100%;
        height: 106px;
    }

    .picture-src {
        width: 100%;
    }
</style>
 <div class="contain">
    	<div class="breadcrumb_conatiner">
            <div class="col-md-6">
              <h4 class="change-color">Add Bank Branch</h4>
            </div>
            <div class="col-md-6">
              <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                   <a href="${contextPath}/home">Home</a>
                  </li>
              
                  <li class="breadcrumb-item active" aria-current="page">Bank Branch</li>
                </ol>
              </nav>
            </div>
          </div>



   <%@ include file="/WEB-INF/views/message.jsp"%>
         <div class="row">
                	<div class="col-md-12">
					<div class="card">
                    <div class="card-header">
                      <h5 class="card-title">Bank Branch Details</h5>
                    </div>
                    <div class="card-body">
    <form class="form-horizontal" action="${contextPath}/config/bankBranchMst" method="POST" id="userForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="hidden" name="bankBranchMaster.bankBranchId" value="${editBranchMaster.bankBranchMaster.bankBranchId}" />
        <input type="hidden" name="statu" id="statu" />
        <div class="row">
            <div class="form-group col-md-2">
                <label class="required">Bank Name</label>
                <div id="hideUserId">
                    <div class="col-md-12">
                        <select name="bankBranchMaster.bankId.bankId" id="bankId" class="form-control form-select" required="required" value="${editBranchMaster.bankBranchMaster.bankId.bankName}">
                            <option value="0"><spring:message code="label.common.select"></spring:message></option>
                            <c:forEach var="bank" items="${bankList}">
                                <option value="${bank.bankId}" ${bank.bankId eq editBranchMaster.bankBranchMaster.bankId.bankId ? 'selected' : ''}>${bank.bankName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group col-md-2">
                <label class="required">Branch Name</label>
                <div class="col-md-12">
                    <input type="text" id="branchName" name="bankBranchMaster.branchName" placeholder="Max 100 chars" class="form-control form-control-sm" required maxlength="100" value="${editBranchMaster.bankBranchMaster.branchName}">
                </div>
            </div>
            <div class="form-group col-md-2">
                <label class="required">IFSC Code</label>
                <div class="col-md-12">
                    <input type="text" id="ifscCode" name="bankBranchMaster.ifscCode" class="form-control form-control-sm" required placeholder="Max 11 chars" maxlength="11" value="${editBranchMaster.bankBranchMaster.ifscCode}">
                </div>
            </div>
            <div class="form-group col-md-2">
                <label class="col-md-12" for="mobile">Branch Contact No.</label>
                <div class="col-md-12">
		        	 <input type="text" readonly class="form-control form-control-sm mobile91Prefix" name="contPrefixSpv" id="contPrefixSpv" value="+91">
                    <input type="text" name="bankBranchMaster.branchMobile" id="branchMobile" class="form-control form-control-sm NumbersOnly mobileNoText" placeholder="Max 10 chars" maxlength="10" onchange="validateMobileNo(this);" value="${editBranchMaster.bankBranchMaster.branchMobile}">
                </div>
            </div>
            <div class="form-group col-md-2">
                <label class="col-md-12" for="Email">Branch Email Id</label>
                <div class="col-md-12">
                    <input type="email" name="bankBranchMaster.branchEmail" id="branchEmail" class="form-control form-control-sm emailsOnly" placeholder="Max 100 chars" onchange="validateEmail(this);" maxlength="150" value="${editBranchMaster.bankBranchMaster.branchEmail}">
                </div>
            </div>
            <div class="form-group col-md-2">
                <label class="col-md-12">Address</label>
                <div class="col-md-12">
                    <input type="text" name="bankBranchMaster.branchAddress" id="branchAddress" placeholder="Max 100 chars" class="form-control form-control-sm" maxlength="100" value="${editBranchMaster.bankBranchMaster.branchAddress}">
                </div>
            </div>
        </div>
        <div class="form-group col-md-12 text-center" style="margin-top: 7px;">
            <button type="button" class="btn-submit" onclick="validateForm('${empty editBranchMaster.bankBranchMaster.bankBranchId ? 'SAVE' : 'UPDATE'}')">
                <spring:message code="${empty editBranchMaster.bankBranchMaster.bankBranchId ? 'site.common.submit' : 'site.common.update'}" />
            </button>
            <button type="button" class="btn-cancel btn-danger" onclick="history.back()">
                <spring:message code="site.common.back" />
            </button>
        </div>

    </form>
      </div>
 </div>
 </div>
    
</div>

     			 <div class="row">
                	<div class="col-md-12">
					<div class="card">
                    <div class="card-header">
                      <h5 class="card-title">Bank Branch Details</h5>
                    </div>
                    <div class="card-body">
            <table class="datatable table table-striped table-bordered exportbtn">
                <thead>
                    <tr>
                        <th><label><b>Sl No.</b></label></th>
                        <th><label><b>Bank Name</b></label></th>
                        <th><label><b>Branch Name</b></label></th>
                        <th><label><b>IFSC Code</b></label></th>
                        <th><label><b>Address</b></label></th>
                        <th><label><b>Action</b></label></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${manageBranch.bankBranchMasterList}" var="branch" varStatus="index">
                        <tr>
                            <td align="center">
                                <input type="hidden" name="userRoleHcMapId[]" value="${branch.bankBranchId}" /> ${index.count}
                            </td>
                            <td>${branch.bankId.bankName}</td>
                            <td>${branch.branchName}</td>
                            <td>${branch.ifscCode}</td>
                            <td>${branch.branchAddress}</td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-info btn-sm" onclick="editbrch(${branch.bankBranchId});"><i class='fa fa-pencil' aria-hidden='true' title='Edit'></i></button>
                                <%-- <button type="button" title="Active" onclick="statusUniversityForm('${branch.bankBranchId}',${branch.isActive})"   class="btn ${branch.isActive eq true ? 'btn-success' : 'btn-danger'} btn-sm"><i class="${branch.isActive eq true ? 'fa fa-lock' : 'fa fa-unlock'}" title="${branch.isActive eq true ? 'InActive' : 'Active'}" ></i></button> --%>
                            </td>
                            <c:set var="countRow" value="${index.count+1}"></c:set>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>

<form id="editBranchMst" action="${contextPath}/config/editBranch" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input type="hidden" name="bankBranchMaster.bankBranchId" id="editBankBranchId" />
</form>

<form action ="${contextPath}/config/branchStatus" method="post" id="statusForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   	<input type="hidden" name="bankBranchId" id="bankBranchId"/>
    <input type="hidden" name="status" id="isActiveStatus"/>
    <input type="hidden" name="statusCall" value="STATUS" />
</form>
<script>
    function validateForm(status) {
        var bid = $('#bankId').val();
        var branchName = $('#branchName').val();
        var fCode = $('#ifscCode').val();
        var address = $('#branchAddress').val();
        var umob = $('#branchMobile').val();
        var email = $('#branchEmail').val();

        if (bid == "0") {
            bootbox.alert("Please select bank name.");
            return false;
        }

        if (branchName.trim() == "") {
            bootbox.alert("Please provide branch name.");
            return false;
        }

        if (fCode.trim() == "") {
            bootbox.alert("Please provide IFSC code.");
            return false;
        }
        else{
    		var msg = status === 'SAVE' ? 'save' : 'update';
    		
    		bootbox.confirm("Do you want to " +msg+" Branch  data ?",
    				function(result){
    					if(result){
    						showLoader();
    						$("#statu").val(status);
    						$('#userForm').submit();
    					}
    		});
    	}
    }

 function editbrch(id) {
     $("#editBankBranchId").val(id);
     showLoader();
     $("#editBranchMst").submit();
 }
 function statusUniversityForm(id,status){
	 
		$(".loader-div").css("display", "flex");
		$("#bankBranchId").val(id);
		if(status){
			$("#isActiveStatus").val(false);
		}else{
			$("#isActiveStatus").val(true);
		}
		showLoader();
		$("#statusForm").submit();
	}
</script>

<script>
    $(document).ready(function () {
        $('#basic-datatables').DataTable({});
    });
</script>
