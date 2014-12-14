<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- SCHEDULE -->

<div class="form-group">
	<label class="checkbox" for="checkbox2"> <input type="checkbox" 
		checked="checked" value="" id="checkbox2" data-toggle="checkbox"
		class="custom-checkbox"><span class="icons"><span
			class="icon-unchecked"></span><span class="icon-checked"></span></span>
		Предварительная запись <br>
	<small>место и время уточняются</small>
	</label>

</div>



<div class="form-group">
	<label><span class="fui-time"></span> Дата и время</label> 
</div>

<div class="form-group">
	<table>
		<tbody>

			<tr>
				<td class="form-group">
					<input type="date" class="form-control" ng-model="lesson.schedule.dateTime.startDateStr">
					
					
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


<div class="form-group">
	<label class="checkbox" for="checkbox3"> <input type="checkbox"
		checked="checked" value="" id="checkbox3" data-toggle="checkbox"
		class="custom-checkbox"><span class="icons"><span
			class="icon-unchecked"></span><span class="icon-checked"></span></span>
		Повторять еженедельно
	</label>

	<div class="btn-group">
		<button type="button" class="btn btn-sm btn-default">ПН</button>
		<button type="button" class="btn btn-sm btn-default">ВТ</button>
		<button type="button" class="btn btn-sm btn-default">СР</button>
		<button type="button" class="btn btn-sm btn-danger">ЧТ</button>
		<button type="button" class="btn btn-sm btn-default">ПТ</button>
		<button type="button" class="btn btn-sm btn-danger">СБ</button>
		<button type="button" class="btn btn-sm btn-default">ВС</button>
	</div>


</div>




<!-- end of PLACE -->