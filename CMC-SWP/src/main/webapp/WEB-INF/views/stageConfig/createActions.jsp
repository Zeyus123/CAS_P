<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<style>
</style>
<section class="main_container">
    <h1>Create Actions</h1>
    <div class="panel-body">
        <div class="row " style="display: flex; justify-content: flex-center; flex-wrap: wrap;">
            <div class="col-md-12">
                <div class="bagGroundColor px-3">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label" style="color: #fff;">Select Activity</label>
                                <select class="col-md-3 form-control" id="stageName" onchange="getStageConfigData(this)" name="modalName">
                                    <option value="">Select Stage</option>
                                    <c:forEach items="${allEntityClasses}" var="stage">
                                        <option value="${stage.packageName}">${stage.className}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt-3" id="dataSection">
            <!-- Plus Button -->
            <div class="col-md-12 text-right">
                <button type="button" class="btn btn-success" onclick="addNewAction()">Add New Action</button>
            </div>
            <div class="table-responsiv">
                <table class="table table-bordered table-hover table-striped" id="sct">
                    <thead class="text-center bagGroundColorRvr" style="color: #fff;">
                        <tr>
                            <th>Action Name</th>
                            <th>Action Code</th>
                            <th>Status Display Name</th>
                            <th>Display Order</th>
                            <th>Icon</th>
                            <th>Label</th>
                            <th>Is Active</th>
                        </tr>
                    </thead>
                    <tbody id="actionListBody"></tbody>
                </table>
            </div>
        </div>


        <!-- Submit butoon in center -->
        <div class="row" style="display: flex; justify-content: center; flex-wrap: wrap; margin-top: 20px;">
            <div class="col-md-12 text-center">
                <button type="button" id="satgeConfigBtnId" class="btn btn-success" onclick="saveStageConfigData()">Save</button>
            </div>
        </div>
    </div>
</section>
<script>

    function getStageConfigData(obj) {
        var stageName = $(obj).val();
        if (stageName != "") {
            $.ajax({
                url: "${contextPath}/stageConfig/getActionListByModelName",
                type: "GET",
                data: {entityClassName: stageName},
                success: function (response) {
                        var actionList = response;
                        var actionListBody = "";
                        for (var i = 0; i < actionList.length; i++) {
                            var action = actionList[i];
                            actionListBody += "<tr>";
                            actionListBody += "<td><input type='text' class='form-control' name='actionNameEn' value='" + action.actionNameEn + "'></td>";
                            actionListBody += "<td><input type='text' class='form-control' name='actionCode' value='" + action.actionCode + "'></td>";
                            actionListBody += "<td><input type='text' class='form-control' name='displayName' value='" + action.displayName + "'></td>";
                            actionListBody += "<td><input type='text' class='form-control' name='orderNo' value='" + action.orderNo + "'></td>";
                            actionListBody += "<td><input type='text' class='form-control' name='color' value='" + action.color + "'></td>";
                            actionListBody += "<td><input type='text' class='form-control' name='label' value='" + action.label + "'></td>";
                            actionListBody += "<td><input type='checkbox' class='form-control' name='isActive' value='" + action.isActive + "'></td>";
                            actionListBody += "</tr>";
                        }
                        $("#actionListBody").html(actionListBody);
                    },
                error: function (xhr, status, error) {
                    alert(xhr.responseText);
                }
            });
        }
    }

    function addNewAction() {
        var actionListBody = "";
        actionListBody += "<tr>";
        actionListBody += "<td><input type='text' class='form-control' name='actionName' value=''></td>";
        actionListBody += "<td><input type='text' class='form-control' name='actionCode' value=''></td>";
        actionListBody += "<td><input type='text' class='form-control' name='displayName' value=''></td>";
        actionListBody += "<td><input type='text' class='form-control' name='displayOrder' value=''></td>";
        actionListBody += "<td><input type='text' class='form-control' name='icon' value=''></td>";
        actionListBody += "<td><input type='text' class='form-control' name='label' value=''></td>";
        actionListBody += "<td><input type='checkbox' class='form-control' name='isActive' value=''></td>";
        actionListBody += "</tr>";
        $("#actionListBody").append(actionListBody);
    }






</script>