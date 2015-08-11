<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html>
<html>
<head>
<title>Data Export</title>


<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

<link href="${ctx}/static/bootstrap-datetimepicker-0.0.11/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/bootstrap-datetimepicker-0.0.11/js/bootstrap-datetimepicker.min.js"></script>
</head>

<body>
	<p>
	<form id="inputForm" action="${ctx}/${action}" method="post"
		class="form-horizontal">
		<fieldset>
			<legend>
				<small>Data Export</small>
			</legend>
			<div class="control-group">
				<label for="Interval" class="control-label">Interval :</label>
				<div class="controls">
					<label class="radio-inline"><input type="radio" name="interval" value="hourly" >Hourly</label> <label class="radio-inline"><input
						type="radio" name="interval" value="daily" checked>Daily</label> 
						<label class="radio-inline"><input type="radio" name="interval" value="weekly">Weekly</label>
						
						<label class="radio-inline"><input type="radio" name="interval" value="monthly">Monthly</label>
				</div>
			</div>
			<div class="control-group">
				<label for="Area" class="control-label">Area :</label>
				<div class="controls">
					<label class="checkbox-inline"><input type="checkbox" name="areas" value="nanyang" checked>Nanyang</label> <label class="checkbox-inline"><input
						type="checkbox" name="areas" value="NIEO" checked>NIEO</label> <label class="checkbox-inline"><input type="checkbox" name="areas" value="SPMS" checked >SPMS</label>
				</div>
			</div>
			<div class="control-group">
							<label for="DateTime" class="control-label">DateTime :</label>
			
<div class="controls">

<div class="well">
  <div id="datetimepicker4" class="input-append">
    <label>Start ： </label> <input data-format="yyyy-MM-dd" id="start" name = "start" type="text" class="required"></input>
    <span class="add-on">
      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
      </i>
    </span>
  </div>
   <div id="datetimepicker5" class="input-append">
    <label>&nbsp;&nbsp;End ： </label><input data-format="yyyy-MM-dd"  id="end" name = "end" type="text" class="required"></input>
    <span class="add-on">
      <i data-time-icon="icon-time" data-date-icon="icon-calendar">
      </i>
    </span>
  </div>
</div>

<script type="text/javascript">
  $(function() {
    $('#datetimepicker4').datetimepicker({
      pickTime: false
    });
    $('#datetimepicker5').datetimepicker({
        pickTime: false
      });
  });
</script>


				</div>
			</div>

			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Submit" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Back" onclick="history.back()" />
			</div>
		</fieldset>
	</form>

	</p>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			//$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				
				    rules: {
				        end: { greaterThan: "#start" }
				    }
				
			});
		});

		
	</script>
</body>
</html>