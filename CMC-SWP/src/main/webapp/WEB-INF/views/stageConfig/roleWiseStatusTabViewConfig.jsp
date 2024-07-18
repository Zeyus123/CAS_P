<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="rowNo" value="0" />

<style>
	.patch {
	background: #f1f1f1;
    border-bottom: 1px solid #ccc;
    /* padding-bottom: 5px; */
    display: flex;
    align-items: center;
    flex-wrap: wrap;
	
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    padding: 20px;
	background-color: white;
  	border-radius: 10px;
	border-radius: 10px;
	margin-bottom: 20px;
	}

	.disabled-field {
		pointer-events: none;
		opacity: 0.7; /* You can adjust the opacity to your preference */
	}

	


</style>
<%@ include file="/WEB-INF/views/stageConfig/message.jsp"%>
    <div class="breadcrumb_conatiner">
            <div class="col-md-6">
            <h4 class="change-color">Stage Configuration</h4>
            </div>
            <div class="col-md-6">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="#">Home</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="#">/stageConfig/makeStageConfig</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">Stage Configuration</li>
                </ol>
            </nav>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">

            <div class="card">
                <div class="card-header">
                    <h5 class="card-title">Stage Configuration</h5>
                </div>
                <div class="card-body">
                    <div class="row " style="display: flex; justify-content: flex-center; flex-wrap: wrap;">
                        <form action="${contextPath}/stageConfig/roleWiseStatusTabViewConfig" method="post" id="saveRoleWiseStatusTabViewConfig" modelAttribute="saveRoleWiseStatusTabViewConfig">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <div class="col-md-12">
                                <div class="bagGroundColor px-3">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="control-label" style="color: #fff;">Select Activity</label>
                                                <select class="col-md-3 form-control" id="stageName" onchange="getTableTabDetails(this.value)" name="className">
                                                    <option value="">Select Stage</option>
                                                    <c:forEach items="${allEntityClasses}" var="stage">
                                                        <option value="${stage.packageName}" ${stage.packageName eq tableName ? 'selected' : ''}>${stage.className}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not empty tableName}">
                                <div class="col-md-12">
                                    <div class="table-responsiv">
                                        <table class="table table-bordered table-hover table-striped" id="sct">
                                            <thead class="text-center bagGroundColorRvr" style="color: #fff;">
                                                <tr>
                                                    <th>Sl No</th>
                                                    <th>Role</th>
                                                    <th>Tab</th>
                                                    <th>Stage Forwarded Rule</th>
                                                    <th><button type="button" class="btn btn-sm btn-success" onclick="addRow()"><i class="fa-solid fa-plus"></i></button></th>
                                                </tr>
                                            </thead>
                                            <tbody id="sctBody">
                                                <c:choose>
                                                    <c:when test="${roleMapLst.size() > 0}">
                                                        <c:forEach items="${roleMapLst}" var="tab" varStatus="count">
                                                            <tr id="row${count.index}" class="${count.count ne roleMapLst.size() ? 'disabled-field' : ''}">
                                                                <td>${count.count}<input type="hidden" value="${count.count}" name="tabDto[0].displayOrder"></td>
                                                                <td>
                                                                    <select class="col-md-3 form-control" id="roleId${count.index}" name="tabDto[${count.index}].roleId" >
                                                                        <option value="">Select Role</option>
                                                                        <c:forEach items="${roleList}" var="role">
                                                                            <option value="${role.roleId}" ${role.roleId eq tab.roleId ? 'selected' : ''}>${role.displayName}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <select class="col-md-3 form-control" id="statusId${count.index}" name="tabDto[${count.index}].statusId" >
                                                                        <option value="">Select Tab Status</option>
                                                                        <c:forEach items="${viewStatusList}" var="status">
                                                                            <option value="${status.viewStatusId}" ${status.viewStatusId eq tab.viewStatus.viewStatusId ? 'selected' : ''}>${status.displayName}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <select class="col-md-3 form-control" id="stageId${count.index}" name="tabDto[${count.index}].stageId" >
                                                                        <option value="">Select Stage Forward Rule</option>
                                                                        <c:forEach items="${stageForwardedRuleList}" var="stageRule">
                                                                            <option value="${stageRule.forwardedId}" ${stageRule.forwardedId eq tab.stageForwardedRule.forwardedId ? 'selected' : ''}>${stageRule.fwdRuleCode}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </td>
                                                                <td>
                                                                    <button type="button" class="btn btn-sm btn-danger" onclick="removeRow(${count.index})"><i class="fa-solid fa-minus"></i></button>
                                                                </td>
                                                            </tr>
                                                            <c:set var="rowNo" value="${count.index}" />
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr id="row0">
                                                            <td>1<input type="hidden" value="1" name="tabDto[0].displayOrder"></td>
                                                            <td>
                                                                <select class="col-md-3 form-control" id="roleId0" name="tabDto[0].roleId" >
                                                                    <option value="">Select Role</option>
                                                                    <c:forEach items="${roleList}" var="role">
                                                                        <option value="${role.roleId}">${role.displayName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select class="col-md-3 form-control" id="statusId0" name="tabDto[0].statusId" >
                                                                    <option value="">Select Tab Status</option>
                                                                    <c:forEach items="${viewStatusList}" var="status">
                                                                        <option value="${status.viewStatusId}">${status.displayName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select class="col-md-3 form-control" id="stageId0" name="tabDto[0].stageId" >
                                                                    <option value="">Select Stage Forward Rule</option>
                                                                    <c:forEach items="${stageForwardedRuleList}" var="stageRule">
                                                                        <option value="${stageRule.forwardedId}">${stageRule.fwdRuleCode}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <button type="button" class="btn btn-sm btn-danger" ><i class="fa-solid fa-minus"></i></button>
                                                            </td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-12 text-center">
                                <button class="btn btn-success" type="button" title="Save" onclick="validateForm()">Save</button>
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <form id="stageTabRoleView" method="get" action="${contextPath}/stageConfig/roleWiseStatusTabViewConfig">
        <input type="hidden" id="tableName" name="tableName">
    </form>

<script>
    var index=0+parseInt(${rowNo});
    function getTableTabDetails(tableName){
        $("#tableName").val(tableName);
        $("#stageTabRoleView").submit();
    }

    function addRow(){
        var previousRole=$("#roleId"+index).val();
        var previousStatus=$("#statusId"+index).val();
        var previousStage=$("#stageId"+index).val();
        var addNew=false;
        if(previousRole==''){
            addNew=true;
        }else if(previousStatus==''){
            addNew=true;
        }else if(previousStage==''){
            addNew=true;
        }
        if(addNew){
            bootbox.alert("Please Add Previous Row");
            return false;
        }else{
            $("#row"+index).addClass('disabled-field');
            index++;
            var html=addNewRowTag();
            $("#sctBody").append(html);
            
            
        }
    }

    function addNewRowTag(){
        var html='';
        html += '<tr id="row' + index + '">' +
            '<td>' + (index + 1) + '<input type="hidden" value="' + (index + 1) + '" name="tabDto[' + index + '].displayOrder"></td>' +
            '<td>' +
            '<select class="col-md-3 form-control" id="roleId' + index + '" name="tabDto[' + index + '].roleId" >' +
            '<option value="">Select Role</option>' +
            '<c:forEach items="${roleList}" var="role">' +
            '<option value="${role.roleId}">${role.displayName}</option>' +
            '</c:forEach>' +
            '</select>' +
            '</td>' +
            '<td>' +
            '<select class="col-md-3 form-control" id="statusId' + index + '" name="tabDto[' + index + '].statusId" >' +
            '<option value="">Select Tab Status</option>' +
            '<c:forEach items="${viewStatusList}" var="status">' +
            '<option value="${status.viewStatusId}">${status.displayName}</option>' +
            '</c:forEach>' +
            '</select>' +
            '</td>' +
            '<td>' +
            '<select class="col-md-3 form-control" id="stageId' + index + '" name="tabDto[' + index + '].stageId" >' +
            '<option value="">Select Stage Forward Rule</option>' +
            '<c:forEach items="${stageForwardedRuleList}" var="stageRule">' +
            '<option value="${stageRule.forwardedId}">${stageRule.fwdRuleCode}</option>' +
            '</c:forEach>' +
            '</select>' +
            '</td>' +
            '<td>' +
                '<button type="button" class="btn btn-sm btn-danger" onclick="removeRow(' + index + ')"><i class="fa-solid fa-minus"></i></button>'
            '</td>' +
            '</tr>';
            return html; 
    }
    function removeRow(id){
        if(index==0){
            bootbox.alert("One Row Mandatory");
            return false;
        }
        if(id==index){
            $("#row"+id).remove();
            index--;
            $("#row"+index).removeClass('disabled-field');
        }
        
    }
    function validateForm(){

        var result=false;
        for(i=0;i<=index;i++){
            var role=$("#roleId"+i).val();
            var status=$("#statusId"+i).val();
            var stage=$("#stageId"+i).val();
            if(role==''){
                result=true;
            }else if(status==''){
                result=true;
            }else if(stage==''){
                result=true;
            }
        }
        if(result) {
            bootbox.alert("please fill all row");
            return false;
        }else{
            bootbox.confirm({
                message: "Are you sure you want to save?",
                buttons: {
                    confirm: {
                        label: 'Yes',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: 'No',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if(result){
                        $("#saveRoleWiseStatusTabViewConfig").submit();
                    }
                }
            });
            
        }
    }
</script>


	

