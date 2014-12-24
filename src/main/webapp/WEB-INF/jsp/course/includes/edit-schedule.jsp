<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- SCHEDULE -->
<div ng-controller="lessonEditController" >
{{weekStart}}
<div class="form-group">
	<label class="checkbox" for="checkbox2"> <input type="checkbox" 
		checked="checked" value="" id="checkbox2" data-toggle="checkbox" ng-model="viewModel.preliminary"
		class="custom-checkbox"><span class="icons"><span
			class="icon-unchecked"></span><span class="icon-checked"></span></span> Предварительная запись <br>
	<small>место и время уточняются</small>
	</label>

</div>

<div id="schedule-edit" ng-show="!viewModel.preliminary">


<div class="form-group">
	<label><span class="fui-time"></span> Дата и время</label> 
</div>

<div class="form-group">
	 
	<table>
		<tbody>

			<tr>
				<td class="form-group">
					<input type="date" class="form-control" ng-model="lesson.schedule.dateTime.startDateStr" ng-change="getWeekStart(lesson)">
				</td><td>&nbsp;&nbsp;</td>
				
				<td style="width: 50px;" class="form-group"
					ng-class="{'has-error': invalidHours}"><input type="text"
						ng-model="lesson.schedule.dateTime.hours" ng-change="updateHours()"
						class="form-control text-center ng-valid ng-dirty"
						ng-mousewheel="incrementHours()" ng-readonly="readonlyInput"
						maxlength="2"></td>
				<td>:</td>
				<td style="width: 50px;" class="form-group"
					ng-class="{'has-error': invalidMinutes}">
					<input type="text"
					ng-model="lesson.schedule.dateTime.minutes" ng-change="updateMinutes()"
					class="form-control text-center ng-valid ng-dirty"
					ng-readonly="readonlyInput" maxlength="2">
					</td>
			</tr>

		</tbody>
	</table>

</div>
</div>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="form-group">
	<label class="checkbox" for="checkbox3"> <input type="checkbox" ng-model="viewModel.recurrent"
		checked="checked" value="" id="checkbox3" data-toggle="checkbox" 
		class="custom-checkbox"><span class="icons"><span
			class="icon-unchecked"></span><span class="icon-checked"></span></span>
		Повторять еженедельно
	</label>	

    <div class="btn-group" ng-show="viewModel.recurrent">
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[0]" btn-checkbox><spring:message code="days.0"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[1]" btn-checkbox><spring:message code="days.1"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[3]" btn-checkbox><spring:message code="days.2"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[4]" btn-checkbox><spring:message code="days.3"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[5]" btn-checkbox><spring:message code="days.4"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[6]" btn-checkbox><spring:message code="days.5"/></label>
        <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[7]" btn-checkbox><spring:message code="days.6"/></label>
    </div>


</div>



</div><!-- controller -->
<!-- end of PLACE -->