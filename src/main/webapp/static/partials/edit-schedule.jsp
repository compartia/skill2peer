<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- SCHEDULE -->
<div ng-cloak>

    <div class="form-group">

        <label class="checkbox" for="checkbox2"> <input type="checkbox" 
            checked="checked" value="" id="checkbox2" data-toggle="checkbox" ng-model="viewModel.preliminary"
            class="custom-checkbox">  Предварительная запись <br>
               <small>место и время уточняются</small>
        </label>

    </div>

    <div id="schedule-edit" ng-show="!viewModel.preliminary">
        <div class="form-group">
            <label><span class="fui-time"></span> Дата и время начала</label> 

            <table>
                <tbody>
                    <tr>
                        <td class="form-group">
                            <!--DATE-->
                            <input type="date" class="form-control" ng-model="start.date" ng-change="onDateChange()">
                        </td><td>&nbsp;&nbsp;</td>

                        <td style="width: 5em;" class="form-group" ng-class="{'has-error': courseEditForm.hours.$invalid}">
                            <input name="hours" type="number" integer 
                                ng-model="start.hours" ng-change="onDateChange()" min="0" max="23"
                                class="form-control text-center" maxlength="2"></td>
                        <td>:</td>
                        <td style="width: 5em;" class="form-group" ng-class="{'has-error': courseEditForm.minutes.$invalid}">
                            <input name="minutes" type="number" ng-model="start.minutes" ng-change="onDateChange()" integer 
                            class="form-control text-center" min="0" max="59" maxlength="2">
                        </td>
                    </tr>
                </tbody>
            </table>

        </div>
    </div>


    <div class="form-group" ng-class="{'has-error': courseEditForm.duration.$invalid && courseEditForm.duration.$dirty}">
        <label for="duration">Продолжительность  <small>(в минутах)</small></label> 
        <input name="duration" type="number" min="5" max="3000" ng-model="lesson.schedule.duration" class="form-control text-center" style="width:5em"
            required  maxlength="3">
    </div>


    <div class="form-group">
        <label class="checkbox" for="checkbox3"> <input type="checkbox" ng-model="lesson.schedule.recurrent"
            checked="checked" value="" id="checkbox3" data-toggle="checkbox"> 
            Повторять еженедельно
        </label>	

        <div class="btn-group" ng-show="lesson.schedule.recurrent">
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[0]" btn-checkbox><spring:message code="days.0"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[1]" btn-checkbox><spring:message code="days.1"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[2]" btn-checkbox><spring:message code="days.2"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[3]" btn-checkbox><spring:message code="days.3"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[4]" btn-checkbox><spring:message code="days.4"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[5]" btn-checkbox><spring:message code="days.5"/></label>
            <label class="btn btn-default" ng-model="lesson.schedule.repeatDays[6]" btn-checkbox><spring:message code="days.6"/></label>
        </div>

    </div>



</div><!-- controller -->
<!-- end of PLACE -->